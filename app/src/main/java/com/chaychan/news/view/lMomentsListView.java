package com.chaychan.news.view;

import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;

import java.util.List;

//我的朋友圈们
public interface lMomentsListView {

    void onGetNewsListSuccess(BasePageEntity<Moments> response);

    void  onError();

    void onMoreMomentsSuccess(BasePageEntity<Moments> response);
}
