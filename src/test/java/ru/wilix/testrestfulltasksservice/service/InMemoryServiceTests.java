package ru.wilix.testrestfulltasksservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("inMemory")
public class InMemoryServiceTests extends TasksServiceTests {

    @Autowired
    public InMemoryServiceTests(InMemoryTaskServiceImpl service) {
        super(service);
    }

}