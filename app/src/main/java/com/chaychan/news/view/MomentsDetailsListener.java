package com.chaychan.news.view;

import com.chaychan.news.model.entity.BaseEntity;

public interface MomentsDetailsListener<T> {

    void onGetListSuccess(BaseEntity<T> response);

    void onError();
}
