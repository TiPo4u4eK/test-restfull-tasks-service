package ru.wilix.testrestfulltasksservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.wilix.testrestfulltasksservice.service.Cleaner;

@SpringBootTest
public class AbstractTest {

    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    private Cleaner cleaner;

    @AfterEach
    public void afterEach() {
        cleaner.clear();
    }

}