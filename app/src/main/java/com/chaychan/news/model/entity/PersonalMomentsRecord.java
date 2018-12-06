package com.chaychan.news.model.entity;

import org.litepal.crud.DataSupport;

public class PersonalMomentsRecord extends DataSupport {

    private String publisherUserId;
    private int page;
    private String json;
    private long time;

    public PersonalMomentsRecord() {

    }

    public PersonalMomentsRecord(String publisherUserId, int page, String json, long time) {
        this.publisherUserId = publisherUserId;
        this.page = page;
        this.json = json;
        this.time = time;
    }

    public String getPublisherUserId() {
        return publisherUserId;
    }

    public void setPublisherUserId(String publisherUserId) {
        this.publisherUserId = publisherUserId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
