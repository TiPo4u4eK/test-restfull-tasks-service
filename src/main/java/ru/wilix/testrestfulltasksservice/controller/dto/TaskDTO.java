package ru.wilix.testrestfulltasksservice.controller.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class TaskDTO {

    @Nullable
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Nullable
    private Date modificationDate;

    public TaskDTO(Long id, String name, String description, Date modificationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modificationDate = modificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

}