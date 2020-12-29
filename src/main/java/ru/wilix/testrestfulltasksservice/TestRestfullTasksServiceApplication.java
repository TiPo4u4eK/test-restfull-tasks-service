package ru.wilix.testrestfulltasksservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.util.Arrays;

/**
 * REST сервис для работы над задачами
 */
@SpringBootApplication
public class TestRestfullTasksServiceApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestRestfullTasksServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TestRestfullTasksServiceApplication.class, args);
    }

    @EventListener
    public void handleApplicationStart(ApplicationStartedEvent startedEvent) {
        String port = startedEvent.getApplicationContext().getEnvironment().getProperty("local.server.port");
        String activeProfiles = Arrays.toString(startedEvent.getApplicationContext().getEnvironment().getActiveProfiles());
        LOGGER.info("Application started at port {} with active profiles {}", port, activeProfiles);
    }

}