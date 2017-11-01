package com.example.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController //make it a rest endpoint
public class HelloController {
    private static int id = 1;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("./message.txt"), Greeting.class);
    }

    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public Greeting createGreeting(@RequestBody String content) throws IOException {
        Greeting newGreeting = new Greeting(id++, content);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("./message.txt"), newGreeting);
        return newGreeting;
    }

    @RequestMapping(value = "/getHighestGreeting", method = RequestMethod.POST)
    public Greeting getHighestGreeting(@RequestBody List<Greeting> list) {
        if (list.size() == 0) {
            return null;
        }
        Greeting highestId = list.get(0);
        for(Greeting greet : list) {
            if (highestId.getId() < greet.getId()) {
                highestId = greet;
            }
        }
        return highestId;
    }

    @RequestMapping(value = "/updateGreeting", method = RequestMethod.PUT)
    public Greeting updateGreeting(@RequestBody String newMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8);
        Greeting greeting = mapper.readValue(message, Greeting.class);
        greeting.setContent(newMessage);
        mapper.writeValue(new File("./message.txt"), greeting);
        return greeting;
    }

    @RequestMapping(value = "/deleteGreeting", method = RequestMethod.DELETE)
    public void deleteGreeting(@RequestBody int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8);
        Greeting greeting = mapper.readValue(message, Greeting.class);
        if (greeting.getId() == id) {
            FileUtils.writeStringToFile(new File("./message"), "", StandardCharsets.UTF_8);
        }
    }

}
