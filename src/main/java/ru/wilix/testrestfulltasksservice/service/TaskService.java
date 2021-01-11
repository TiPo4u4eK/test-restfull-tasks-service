package ru.wilix.testrestfulltasksservice.service;

import ru.wilix.testrestfulltasksservice.model.Page;
import ru.wilix.testrestfulltasksservice.model.PageInfo;
import ru.wilix.testrestfulltasksservice.model.domain.Task;

/**
 * Отвечает за работу с сущностями {@link Task}
 */
public interface TaskService {

    /**
     * Сохраняет сущность и присваивает ей уникальный идентификатор
     *
     * @param task сущность для сохранения
     */
    void createTask(Task task);

    /**
     * Поиск сущности по уникальному идентификатор
     *
     * @param id уникальный идентификатор
     * @return найденная сущность, либо null
     */
    Task findTaskById(long id);

    /**
     * Обновление сущности
     *
     * @param task сущность для обновления
     */
    void updateTask(Task task);

    /**
     * Удаление сущности по уникальному идентификатору
     *
     * @param id уникальный идентификатор
     */
    void deleteTask(long id);

    /**
     * Поиск сущностей по информации о пагинации
     *
     * @param pageInfo информация о пагинации
     * @return Список найденных элементов отсортированных по дате последней модификации и информации о пагинации
     */
    Page<Task> findTasks(PageInfo pageInfo);

    /**
     * Проверяет существует ли данная сущность по уникальному идентификатору
     *
     * @param id уникальный идентификатор
     * @return true в случае что данная сущность существует
     */
    boolean exists(long id);

}