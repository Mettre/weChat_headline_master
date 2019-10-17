package com.chaychan.news.view;

import java.util.List;

/**
 * 查找用户
 * @param <T>
 */
public interface FindUserListener<T> {

    void onGetRefreshListSuccess(List<T> response);

    void onError();

    void addFollowSuccess();

    void cancelFollowSuccess();
}
