package com.chaychan.news.view;

import com.chaychan.news.model.entity.BasePageEntity;
/**
 * 查找用户
 * @param <T>
 */
public interface FindUserListener<T> {

    void onGetRefreshListSuccess(BasePageEntity<T> response);

    void onError();

    void addFollowSuccess();

    void onFollowedError();
}
