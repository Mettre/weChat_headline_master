package com.chaychan.news.model.response;

/**
 * @author ChayChan
 * @description: 访问返回的response
 * @date 2017/6/18  19:37
 */
public class ResultResponse<T> {

    public String has_more;
    public String message;
    public Boolean success;
    public String code;
    public T data;

    public ResultResponse(String more, String _message, T result,String code) {
        has_more = more;
        message = _message;
        data = result;
        code = code;
    }
}
