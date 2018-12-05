package com.chaychan.news.api;

import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.NewsDetail;
import com.chaychan.news.model.entity.VideoModel;
import com.chaychan.news.model.response.CommentResponse;
import com.chaychan.news.model.response.NewsResponse;
import com.chaychan.news.model.response.ResultResponse;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

import static com.chaychan.news.api.ApiService.GET_COMMENT_LIST;

public interface ApiService2 {

    /**
     * 好友的朋友圈
     */
    @Headers({"urlName:information"})
    @POST("/circleFriendsList")
    Observable<ResultResponse<BasePageEntity<Moments>>> circleFriendsList(@Body HashMap<String, String> map);

    /**
     * 获取新闻详情
     */
    @Headers({"urlName:headlines"})
    @GET
    Observable<ResultResponse<NewsDetail>> getNewsDetail(@Url String url);

    /**
     * 获取评论列表数据
     *
     * @param groupId
     * @param itemId
     * @param offset
     * @param count
     * @return
     */
    @Headers({"urlName:headlines"})
    @GET(GET_COMMENT_LIST)
    Observable<CommentResponse> getComment(@Query("group_id") String groupId, @Query("item_id") String itemId, @Query("offset") String offset, @Query("count") String count);

    /**
     * 获取视频数据json
     *
     * @param url
     * @return
     */
    @Headers({"urlName:headlines"})
    @GET
    Observable<ResultResponse<VideoModel>> getVideoData(@Url String url);


}
