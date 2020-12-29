package ru.wilix.testrestfulltasksservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.wilix.testrestfulltasksservice.AbstractTest;

@ActiveProfiles("inMemory")
public class InMemoryServiceTests extends AbstractTest {

    private final TasksServiceTests serviceTests;

    @Autowired
    public InMemoryServiceTests(InMemoryTaskServiceImpl service) {
        serviceTests = new TasksServiceTests(service);
    }

    @Test
    public void whenTaskCreatedThenIdIsSetTest() {
        serviceTests.whenTaskCreatedThenIdIsSetTest();
    }

    @Test
    public void whenTaskUpdatedThenEqualsTest() {
        serviceTests.whenTaskUpdatedThenEqualsTest();
    }

    @Test
    public void whenTaskDeletedThenCheckTest() {
        serviceTests.whenTaskDeletedThenCheckTest();
    }

    @Test
    public void findAllTasksTest() {
        serviceTests.findAllTasksTest();
    }

    @Test
    public void existsCheckTest() {
        serviceTests.existsCheckTest();
    }

}