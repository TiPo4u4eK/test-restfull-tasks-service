package ru.wilix.testrestfulltasksservice.model.rest.messages;

/**
 * Обёртка для текстовых ответов REST
 */
public class MessageRestResponse {

    private final String message;

    public MessageRestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}