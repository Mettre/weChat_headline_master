package com.chaychan.news.utils;

import com.chaychan.news.model.entity.Friends;

import org.litepal.crud.DataSupport;

import java.util.List;

public class FriendsRecordHelper {

    public static List<Friends> selectFriendsRecords(String myUserId) {
        return DataSupport
                .where("myUserId = ?", myUserId)
                .find(Friends.class);
    }

    public static void save(List<Friends> friendsList) {
        //保存新的记录
        for (Friends friends : friendsList) {
            friends.saveOrUpdate("myUserId = ?", friends.getMyUserId());
        }
    }


}
