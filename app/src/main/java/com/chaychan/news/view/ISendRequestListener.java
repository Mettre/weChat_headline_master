package com.chaychan.news.view;

/**
 * 两次请求
 * @param <T>
 */
public interface ISendRequestListener<T> {

    void onRequestFirstSuccess(T response);

    void onRequestSecondSuccess(T response);

    void onError();
}
