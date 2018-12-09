package com.chaychan.news.utils;

import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.model.entity.Reply;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MomentsDetailsEntityHelper {

    private static Gson mGson = new Gson();

    /**
     * `
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param momentsId 频道
     * @return
     */
    public static MomentsDetailsEntity getLastNewsRecord(String momentsId) {
        return DataSupport.where("momentsId=?", momentsId).findFirst(MomentsDetailsEntity.class);
    }

    /**
     * 获取某个频道上一组朋友圈记录
     *
     * @param momentsId 频道
     * @return
     */
    public static MomentsDetailsEntity getPreNewsRecord(String momentsId) {
        List<MomentsDetailsEntity> newsRecords = selectNewsRecords(momentsId);

        if (ListUtils.isEmpty(newsRecords)) {
            return null;
        }

        return newsRecords.get(0);
    }


    /**
     * 保存朋友圈
     *
     * @param momentsId
     * @param moments
     */
    public static void save(String momentsId, Moments moments, List<Reply> reply) {
        //保存新的记录
        MomentsDetailsEntity newsRecord = new MomentsDetailsEntity(momentsId, moments, reply);
        newsRecord.saveOrUpdate("momentsId = ?", momentsId);
    }


    /**
     * 根据频道码和页码查询朋友圈记录
     *
     * @param momentsId
     * @return
     */
    private static List<MomentsDetailsEntity> selectNewsRecords(String momentsId) {
        return DataSupport
                .where("momentsId = ?", momentsId)
                .find(MomentsDetailsEntity.class);
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
