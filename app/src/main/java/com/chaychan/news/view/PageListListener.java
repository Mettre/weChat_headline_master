package com.chaychan.news.view;

import com.chaychan.news.model.entity.BasePageEntity;

public interface PageListListener<T> {

    void onGetRefreshListSuccess(BasePageEntity<T> response);

    void onMoreListSuccess(BasePageEntity<T> response);

    void onError();
}
