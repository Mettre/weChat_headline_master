package com.chaychan.news.utils;

import com.chaychan.news.model.entity.DataBean;

import org.litepal.crud.DataSupport;

import java.util.List;

public class FriendsRecordHelper {

    public static List<DataBean> selectFriendsRecords(String myUserId) {
        return DataSupport
                .where("myUserId = ?", myUserId)
                .find(DataBean.class);
    }

    public static void save(List<DataBean> friendsList) {
        //保存新的记录
        for (DataBean friends : friendsList) {
            friends.saveOrUpdate("myUserId = ?", friends.getMyUserId());
        }
    }


}
