package com.chaychan.news.model.entity;


import java.io.Serializable;
import java.util.List;

public class Friends implements Serializable {

    public List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
