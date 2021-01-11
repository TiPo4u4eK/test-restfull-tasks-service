package ru.wilix.testrestfulltasksservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Содержит в себе элементы и информацию о текущем состоянии пагинации
 */
public class Page<T> {

    private final List<T> elements;
    private final long totalElements;

    @JsonIgnore
    private final PageInfo pageInfo;

    public Page(List<T> elements, long totalElements, PageInfo pageInfo) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.pageInfo = pageInfo;
    }

    public List<T> getElements() {
        return elements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public int getElementsSize() {
        return elements.size();
    }

    public long getOffset() {
        return pageInfo.getOffset();
    }

    public long getPageSize() {
        return pageInfo.getPageSize();
    }

    public long getPage() {
        return pageInfo.getPage();
    }

}