package com.chaychan.news.view;

import com.chaychan.news.model.entity.BasePageEntity;

public interface VisitorListListener<T> {

    void onGetRefreshListSuccess(BasePageEntity<T> response);

    void onDeleteSuccess(int position);

    void onError();

    void onDeleteError();
}
