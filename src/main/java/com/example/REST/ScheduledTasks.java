package com.example.REST;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class ScheduledTasks {

    RestTemplate restTemplate = new RestTemplate();

//    @Scheduled (fixedRate = 5000)
//    public void periodidcTask1() {
//        System.out.println("The time now is: " + new Date());
//    }

    @Scheduled(cron = "*/5 * * * * *")
    public void getGreeting() {
        String url = "http://localhost:8080/greeting";
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        System.out.println(greeting.getContent());
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void postGreeting() {
        String url = "http://localhost:8080/createGreeting";
        Greeting greeting = restTemplate.postForObject(url, "Bye World", Greeting.class);
        System.out.println(greeting.getContent());
        Greeting greeting1 = restTemplate.postForObject(url, "Hello World", Greeting.class);
    }
}
