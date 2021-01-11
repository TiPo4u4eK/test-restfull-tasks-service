package ru.wilix.testrestfulltasksservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dataBase")
public class DataBaseServiceTests extends TasksServiceTests {

    @Autowired
    public DataBaseServiceTests(DatabaseTaskServiceImpl service) {
        super(service);
    }

}