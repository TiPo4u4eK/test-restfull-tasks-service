package ru.wilix.testrestfulltasksservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.wilix.testrestfulltasksservice.model.Page;
import ru.wilix.testrestfulltasksservice.model.PageInfo;
import ru.wilix.testrestfulltasksservice.model.domain.Task;
import ru.wilix.testrestfulltasksservice.model.dto.TaskDTO;
import ru.wilix.testrestfulltasksservice.model.rest.messages.MessageRestResponse;
import ru.wilix.testrestfulltasksservice.service.TaskService;
import ru.wilix.testrestfulltasksservice.utils.EntityMapper;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.stream.Collectors;

import static ru.wilix.testrestfulltasksservice.utils.EntityMapper.taskFromDTO;
import static ru.wilix.testrestfulltasksservice.utils.EntityMapper.taskToDTO;

/**
 * REST-контроллер отвечающий за работу над задачами
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public TaskDTO creteTask(@Valid @RequestBody TaskDTO taskDTO) {
        if (taskDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "For update you should use PUT request method.");
        }

        Task task = taskFromDTO(taskDTO);

        service.createTask(task);

        return taskToDTO(task);
    }

    @GetMapping(value = "/{id}")
    public TaskDTO findTaskById(@PathVariable("id") @Positive long id) {
        Task task = service.findTaskById(id);

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " not found.");
        }

        return taskToDTO(task);
    }

    @PutMapping
    public TaskDTO updateTask(@Valid @RequestBody TaskDTO taskDTO) {
        Long id = taskDTO.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "For create you should use POST request method.");
        }

        if (!service.exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " not found.");
        }

        Task task = taskFromDTO(taskDTO);

        service.updateTask(task);

        return taskToDTO(task);
    }

    @DeleteMapping(value = "/{id}")
    public MessageRestResponse removeQuestion(@PathVariable("id") @Positive long id) {
        if (!service.exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " not found.");
        }

        service.deleteTask(id);

        return new MessageRestResponse("Task with id " + id + " deleted.");
    }

    @GetMapping
    public Page<TaskDTO> findTasks(@RequestParam(value = "page", defaultValue = "0") @Min(0) long page,
                                   @RequestParam(value = "size", defaultValue = "10") @Min(0) long size,
                                   @RequestParam(value = "offset", defaultValue = "0") @Min(0) long offset) {
        Page<Task> tasks = service.findTasks(new PageInfo(page, size, offset));

        return new Page<>(tasks.getElements().stream()
                .map(EntityMapper::taskToDTO)
                .collect(Collectors.toList()),
                tasks.getTotalElements(),
                tasks.getPageInfo());
    }

}