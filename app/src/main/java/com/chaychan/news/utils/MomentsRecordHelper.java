package com.chaychan.news.utils;

import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.List;


public class MomentsRecordHelper {

    private static Gson mGson = new Gson();

    /**
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param publisherUserId 频道
     * @return
     */
    public static MomentsRecord getLastNewsRecord(String publisherUserId) {
        return DataSupport.where("publisherUserId=?and page = ?", publisherUserId, String.valueOf(1)).findFirst(MomentsRecord.class);
    }

    /**
     * 获取某个频道上一组朋友圈记录
     *
     * @param publisherUserId 频道
     * @param page            页码
     * @return
     */
    public static MomentsRecord getPreNewsRecord(String publisherUserId, int page) {
        List<MomentsRecord> newsRecords = selectNewsRecords(publisherUserId, page - 1);

        if (ListUtils.isEmpty(newsRecords)) {
            return null;
        }

        return newsRecords.get(0);
    }


    /**
     * 保存朋友圈
     *
     * @param publisherUserId
     * @param json
     */
    public static void save(String publisherUserId, String json) {
        int page = 1;
        //保存新的记录
        MomentsRecord newsRecord = new MomentsRecord(publisherUserId, page, json, System.currentTimeMillis());
        newsRecord.saveOrUpdate("publisherUserId = ? and page = ?", publisherUserId, String.valueOf(page));
    }


    /**
     * 根据频道码和页码查询朋友圈记录
     *
     * @param publisherUserId
     * @param page
     * @return
     */
    private static List<MomentsRecord> selectNewsRecords(String publisherUserId, int page) {
        return DataSupport
                .where("publisherUserId = ? and page = ?", publisherUserId, String.valueOf(page))
                .find(MomentsRecord.class);
    }

    /**
     * 将json转换成新闻集合
     *
     * @param json
     * @return
     */
    public static List<Moments> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<Moments>>() {
        }.getType());
    }
}
