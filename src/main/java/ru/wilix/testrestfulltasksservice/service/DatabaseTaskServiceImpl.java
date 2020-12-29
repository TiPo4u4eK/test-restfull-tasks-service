package ru.wilix.testrestfulltasksservice.service;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.wilix.testrestfulltasksservice.controller.dto.Page;
import ru.wilix.testrestfulltasksservice.controller.dto.PageInfo;
import ru.wilix.testrestfulltasksservice.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Выполняет работу над сущностями в базе данных с помощью Spring JdbcTemplates
 */
@Service
@Profile("dataBase")
public class DatabaseTaskServiceImpl implements TaskService {

    private final JdbcTemplate template;

    public DatabaseTaskServiceImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void createTask(Task task) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("id");

        long id = insert.executeAndReturnKey(Map.of(
                "name", task.getName(),
                "description", task.getDescription(),
                "modification_date", new Date())).longValue();

        task.setId(id);
    }

    @Override
    public Task findTaskById(long id) {
        return template.query("SELECT * FROM tasks WHERE id = " + id, this::taskMapRow)
                .stream().findFirst().orElse(null);
    }

    @Override
    public void updateTask(Task task) {
        task.setModificationDate(new Date());

        template.update("UPDATE tasks SET name = ?, description = ?, modification_date = ? WHERE id = ?",
                task.getName(), task.getDescription(), task.getModificationDate(), task.getId());
    }

    @Override
    public void deleteTask(long id) {
        template.update("DELETE FROM tasks WHERE id = ?", id);
    }

    @Override
    public Page<Task> findTasks(PageInfo pageInfo) {
        Long count = template.queryForObject("SELECT COUNT(id) FROM tasks", Long.class);

        List<Task> tasks = template.queryForStream("SELECT * FROM tasks", this::taskMapRow)
                .sorted(Comparator.comparing(Task::getModificationDate))
                .skip(pageInfo.getSkip())
                .limit(pageInfo.getPageSize())
                .collect(Collectors.toList());

        return new Page<>(tasks, count, pageInfo);
    }

    @Override
    public boolean exists(long id) {
        return findTaskById(id) != null;
    }

    /**
     * Получает сущность {@link ru.wilix.testrestfulltasksservice.model.Task} из колонок ResultSet
     */
    private Task taskMapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("modification_date")
        );
    }

}