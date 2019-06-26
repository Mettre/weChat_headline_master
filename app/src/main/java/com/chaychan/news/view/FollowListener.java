package com.chaychan.news.view;

import com.chaychan.news.model.entity.BasePageEntity;

public interface FollowListener<T> {

    void onGetRefreshListSuccess(BasePageEntity<T> response);

    void onError();

}
