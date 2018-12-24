package com.chaychan.news.model.response;

import java.util.List;

public class ResultResponse2<T> {

    public String has_more;
    public String message;
    public String success;
    public List<T> data;

    public ResultResponse2(String more, String _message, List<T> result) {
        has_more = more;
        message = _message;
        data = result;
    }
}