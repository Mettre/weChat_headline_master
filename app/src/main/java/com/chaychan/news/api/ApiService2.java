package com.chaychan.news.api;

import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.model.entity.VideoModel;
import com.chaychan.news.model.response.CommentResponse;
import com.chaychan.news.model.response.ResultResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    @POST("/information/loginEd/circleFriendsList")
    Observable<ResultResponse<BasePageEntity<Moments>>> circleFriendsList(
            @Header("Authorization") String Authorization,
            @Body HashMap<String, Object> map);

    /**
     * 个人朋友圈
     */
    @Headers({"urlName:information"})
    @POST("/information/loginEd/findMomentsListWithPublisherUserId")
    Observable<ResultResponse<BasePageEntity<Moments>>> PersonalMomentsList(@Header("Authorization") String Authorization, @Body HashMap<String, Object> map);

    /**
     * 朋友圈详情
     */
    @Headers({"urlName:information"})
    @GET("/selectMomentsDetails/{momentsId}")
    Observable<ResultResponse<MomentsDetailsEntity>> momentsDetails(@Path("momentsId") String momentsId);


    /**
     * 说说详情
     */
    @Headers({"urlName:information"})
    @POST("/findMomentsListWithPublisherUserId")
    Observable<ResultResponse<BasePageEntity<Moments>>> MomentsDetails(@Body HashMap<String, Object> map);


    /**
     * 好友列表
     */
    @Headers({"urlName:account"})
    @GET("/loginEd/myFriendsList")
    Observable<ResultResponse<ResultList>> FriendsList(@Header("authorities") String authorities);

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

    /**
     * 登陆
     */
    @Headers({"urlName:information"})
    @POST("/account/login")
    Observable<ResultResponse<LoginBean>> loginRequest(@Query("phone") String phone, @Query("password") String password);

    /**
     * 登陆
     */
    @Headers({"urlName:information"})
    @GET("/loginEd/getUserInfo")
    Observable<ResultResponse<UserInfo>> getUserInfo(@Header("authorities") String authorities);


}
