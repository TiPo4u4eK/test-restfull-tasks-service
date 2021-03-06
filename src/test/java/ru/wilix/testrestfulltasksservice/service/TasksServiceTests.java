package ru.wilix.testrestfulltasksservice.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.wilix.testrestfulltasksservice.AbstractTest;
import ru.wilix.testrestfulltasksservice.model.Page;
import ru.wilix.testrestfulltasksservice.model.PageInfo;
import ru.wilix.testrestfulltasksservice.model.domain.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс отвечающий за тесты над {@link ru.wilix.testrestfulltasksservice.service.TaskService},
 * Необходим для использования единой реализации тестов при разных активных профилях
 */
public abstract class TasksServiceTests extends AbstractTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(TasksServiceTests.class);

    private TaskService service;

    public TasksServiceTests(TaskService service) {
        this.service = service;
    }

    @Test
    public void whenTaskCreatedThenIdIsSetTest() {
        Task task = getSimpleTask();
        service.createTask(task);

        assertNotNull(task.getId());

        printTestPassed("whenTaskCreatedThenIdIsSetTest");
    }

    @Test
    public void whenTaskUpdatedThenEqualsTest() {
        Task expected = getSimpleTask();
        service.createTask(expected);

        Task actual = service.findTaskById(expected.getId());

        expected.setDescription("updated");
        service.updateTask(expected);

        actual = service.findTaskById(actual.getId());

        assertEquals(expected, actual);

        printTestPassed("whenTaskUpdatedThenEqualsTest");
    }

    @Test
    public void whenTaskDeletedThenCheckTest() {
        Task task = getSimpleTask();

        service.createTask(task);

        long id = task.getId();

        service.deleteTask(id);

        Task actual = service.findTaskById(id);

        assertNull(actual);

        printTestPassed("whenTaskDeletedThenCheckTest");
    }

    @Test
    public void findAllTasksTest() {
        service.createTask(getSimpleTask());
        service.createTask(getSimpleTask());
        service.createTask(getSimpleTask());

        Page<Task> tasks = service.findTasks(new PageInfo(0, 2, 0));

        assertEquals(0, tasks.getPage());
        assertEquals(0, tasks.getOffset());
        assertEquals(2, tasks.getPageSize());
        assertEquals(2, tasks.getElementsSize());
        assertEquals(3, tasks.getTotalElements());

        printTestPassed("findAllTasksTest");
    }

    @Test
    public void existsCheckTest() {
        Task task = getSimpleTask();
        service.createTask(task);

        long id = task.getId();

        assertTrue(service.exists(id));

        printTestPassed("existsCheckTest");
    }

    private Task getSimpleTask() {
        return new Task(null, "name", "description", null);
    }

    private void printTestPassed(String methodName) {
        LOGGER.info("{} passed for service {}", methodName, service.getClass().getSimpleName());
    }

}