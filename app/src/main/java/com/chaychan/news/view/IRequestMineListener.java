package com.chaychan.news.view;

import com.chaychan.news.model.entity.AccountStatistics;

public interface IRequestMineListener<T> {

    void onRequestFirstSuccess(T response);

    void onRequestSecondSuccess(AccountStatistics response);

    void onError();
}
