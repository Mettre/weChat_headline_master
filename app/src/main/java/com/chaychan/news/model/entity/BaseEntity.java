package com.chaychan.news.model.entity;

public class BaseEntity<T> {
    /**
     * success : true
     * message : success
     * code : 200
     * timestamp : 1544330763790
     * data : {}
     */

    private boolean success;
    private String message;
    private String code;
    private long timestamp;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
