package ru.wilix.testrestfulltasksservice.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.wilix.testrestfulltasksservice.controller.dto.Page;
import ru.wilix.testrestfulltasksservice.controller.dto.PageInfo;
import ru.wilix.testrestfulltasksservice.model.Task;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Выполняет работу над сущностями в памяти
 */
@Service
@Profile("inMemory")
public class InMemoryTaskServiceImpl implements TaskService {

    private final AtomicLong customSequence = new AtomicLong(0);
    private final Map<Long, Task> tasksMap = new ConcurrentHashMap<>();

    @Override
    public void createTask(Task task) {
        task.setId(customSequence.incrementAndGet());
        task.setModificationDate(new Date());

        tasksMap.put(task.getId(), task);
    }

    @Override
    public Task findTaskById(long id) {
        return tasksMap.get(id);
    }

    @Override
    public void updateTask(Task task) {
        task.setModificationDate(new Date());

        tasksMap.put(task.getId(), task);
    }

    @Override
    public void deleteTask(long id) {
        tasksMap.remove(id);
    }

    @Override
    public Page<Task> findTasks(PageInfo pageInfo) {
        List<Task> tasks = tasksMap.values().stream()
                .sorted(Comparator.comparing(Task::getModificationDate))
                .skip(pageInfo.getSkip())
                .limit(pageInfo.getPageSize())
                .collect(Collectors.toList());

        return new Page<>(tasks, tasksMap.size(), pageInfo);
    }

    @Override
    public boolean exists(long id) {
        return findTaskById(id) != null;
    }

    /**
     * Очистка сущностей, используется в тестах
     */
    protected void clear() {
        tasksMap.clear();
    }

}