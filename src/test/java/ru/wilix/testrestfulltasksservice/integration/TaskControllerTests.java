package ru.wilix.testrestfulltasksservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.wilix.testrestfulltasksservice.AbstractTest;
import ru.wilix.testrestfulltasksservice.model.Page;
import ru.wilix.testrestfulltasksservice.model.dto.TaskDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TaskControllerTests extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    //  CREATE

    @Test
    public void whenCreateSuccessThen200() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        createTask(taskDTO);
    }

    @Test
    public void whenCreateHasIdThen400() throws Exception {
        TaskDTO taskDTO = new TaskDTO(1L, "name", "description", null);

        mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCreateBodyIsWrongThen400() throws Exception {
        TaskDTO taskDTO = new TaskDTO(null, "", "description", null);

        mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //  FIND BY ID

    @Test
    public void whenFindByIdIsSuccessThen200() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        String response = mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDTO responseTask = mapper.readValue(response, TaskDTO.class);

        mockMvc.perform(get("/tasks/" + responseTask.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void whenFindByIdIsNullThen404() throws Exception {
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    //  FIND TASKS

    @Test
    public void whenFindTasksThen200() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    public void findTasksWithPaginationTest() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        for (int i = 0; i < 8; i++) {
            createTask(taskDTO);
        }

        String response = mockMvc.perform(get("/tasks")
                .param("page", "1")
                .param("size", "5")
                .param("offset", "1"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Page<TaskDTO> tasks = mapper.readValue(response, new TypeReference<Page<TaskDTO>>() {
        });
        JsonNode jsonNode = mapper.readTree(response);

        long page = jsonNode.get("page").asLong();
        long size = jsonNode.get("pageSize").asLong();
        long offset = jsonNode.get("offset").asLong();

        assertEquals(1, page);
        assertEquals(5, size);
        assertEquals(1, offset);
        assertEquals(2, tasks.getElementsSize());
        assertEquals(8, tasks.getTotalElements());
    }

    // UPDATE

    @Test
    public void whenUpdateTaskIsSuccessThen200() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        String response = mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDTO responseDTO = mapper.readValue(response, TaskDTO.class);
        responseDTO.setName("name2");

        mockMvc.perform(put("/tasks")
                .content(toJSON(responseDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateTaskWithoutIdThen400() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        mockMvc.perform(put("/tasks")
                .content(toJSON(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateTaskIsWrongThen400() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        String response = mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDTO responseDTO = mapper.readValue(response, TaskDTO.class);
        responseDTO.setName("");

        mockMvc.perform(put("/tasks")
                .content(toJSON(responseDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateTaskWithWrongIdThen404() throws Exception {
        TaskDTO taskDTO = new TaskDTO(1L, "name", "description", null);

        mockMvc.perform(put("/tasks")
                .content(toJSON(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //  DELETE

    @Test
    public void whenDeleteSuccessThen200() throws Exception {
        TaskDTO taskDTO = getSimpleTaskDTO();

        String response = mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDTO responseDTO = mapper.readValue(response, TaskDTO.class);

        Long id = responseDTO.getId();

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteWrongIdThen404() throws Exception {
        mockMvc.perform(delete("/tasks/" + Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    private void createTask(TaskDTO taskDTO) throws Exception {
        mockMvc.perform(post("/tasks")
                .content(toJSON(taskDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private TaskDTO getSimpleTaskDTO() {
        return new TaskDTO(null, "name", "description", null);
    }

    private String toJSON(TaskDTO taskDTO) throws JsonProcessingException {
        return mapper.writeValueAsString(taskDTO);
    }

}