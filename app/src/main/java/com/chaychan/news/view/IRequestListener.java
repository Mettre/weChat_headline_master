package com.chaychan.news.view;

public interface IRequestListener<T> {

    void onRequestFirstSuccess(T response);

    void onError();
}
