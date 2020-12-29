package ru.wilix.testrestfulltasksservice.model;

import java.util.Date;
import java.util.Objects;

/**
 * Сущность задачи
 */
public class Task {

    private Long id;
    private String name;
    private String description;
    private Date modificationDate;

    public Task() {
    }

    public Task(Long id, String name, String description, Date modificationDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, modificationDate);
    }

}