package com.chaychan.news.view;

import com.chaychan.news.model.entity.Friends;

import java.util.List;

public interface IFriendsListView {

    void onGetFriendsSuccess(List<Friends> response);

    void onError();
}
