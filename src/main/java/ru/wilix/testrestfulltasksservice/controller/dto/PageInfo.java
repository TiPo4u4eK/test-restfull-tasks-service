package ru.wilix.testrestfulltasksservice.controller.dto;

/**
 * Информация о пагинации
 */
public class PageInfo {

    private final long page;
    private final long pageSize;
    private final long offset;

    public PageInfo(long page, long pageSize, long offset) {
        this.page = page;
        this.pageSize = pageSize;
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public long getPageSize() {
        return pageSize;
    }

    public long getPage() {
        return page;
    }

    public long getSkip() {
        return (page * pageSize) + offset;
    }

}