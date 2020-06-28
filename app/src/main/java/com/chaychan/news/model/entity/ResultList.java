package com.chaychan.news.model.entity;

import java.util.List;

public class ResultList<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
