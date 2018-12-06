package com.chaychan.news.view;

import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;

public interface PersonalMomentsListView {

    void onGetNewsListSuccess(BasePageEntity<Moments> response);

    void  onError();

    void onMoreMomentsSuccess(BasePageEntity<Moments> response);
}
