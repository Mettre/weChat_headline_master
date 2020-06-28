package com.chaychan.news.model.response;

/**
 * @author ChayChan
 * @description: 访问返回的response
 * @date 2017/6/18  19:37
 */
public class ResultResponse<T> {

    public Long timestamp;
    public String msg;
    public int code;
    public T result;

    public ResultResponse(Long timestamp, String msg, T result, int code) {
        this.timestamp = timestamp;
        this.msg = msg;
        this.result = result;
        this.code = code;
    }
}
