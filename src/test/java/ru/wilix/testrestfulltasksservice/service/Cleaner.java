package ru.wilix.testrestfulltasksservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Отвечает за очистку данных перед тестами
 */
@Component
public class Cleaner {

    @Autowired(required = false)
    private InMemoryTaskServiceImpl inMemoryTaskService;
    @Autowired(required = false)
    private JdbcTemplate template;

    public void clear() {
        if (inMemoryTaskService != null) {
            inMemoryTaskService.clear();
        } else {
            template.execute("TRUNCATE TABLE tasks");
        }
    }

}