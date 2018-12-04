package com.chaychan.news.view;

import com.chaychan.news.model.entity.Moments;

import java.util.List;

public interface lMomentsListView {

    void onGetNewsListSuccess(List<Moments> newList, String tipInfo);

    void  onError();
}
