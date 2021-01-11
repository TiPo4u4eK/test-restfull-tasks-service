package ru.wilix.testrestfulltasksservice.utils;

import ru.wilix.testrestfulltasksservice.model.domain.Task;
import ru.wilix.testrestfulltasksservice.model.dto.TaskDTO;

/**
 * Отвечает за преобразование сущностей из/в DTO
 */
public class EntityMapper {

    public static TaskDTO taskToDTO(Task task) {
        return new TaskDTO(task.getId(),
                task.getName(),
                task.getDescription(),
                task.getModificationDate()
        );
    }

    public static Task taskFromDTO(TaskDTO taskDTO) {
        return new Task(taskDTO.getId(),
                taskDTO.getName(),
                taskDTO.getDescription(),
                taskDTO.getModificationDate()
        );
    }

}