package com.example.REST;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    RestTemplate restTemplate = new RestTemplate();

    private void doUpdate(String newMessage) {
        String putUrl = "http://localhost:8080/updateGreeting";
        restTemplate.put(putUrl, newMessage);
    }

    private Greeting getGreeting() {
        String getUrl = "http://localhost:8080/greeting";
        return restTemplate.getForObject(getUrl, Greeting.class);
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void task1() {
        doUpdate("Hello World");
        System.out.println(getGreeting().getContent());
    }

    @Scheduled(cron = "*/4 * * * * *")
    public void task2() {
        doUpdate("Bye World");
        System.out.println(getGreeting().getContent());
    }


}
