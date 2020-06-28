package com.chaychan.news.api;

import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FeedbackBean;
import com.chaychan.news.model.entity.FindUserBean;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.model.entity.VideoModel;
import com.chaychan.news.model.entity.VisitorBean;
import com.chaychan.news.model.response.CommentResponse;
import com.chaychan.news.model.response.ResultResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
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
    @POST("/module-friend/moments/loginEd/circleFriendsList")
    Observable<ResultResponse<BasePageEntity<Moments>>> circleFriendsList(
            @Header("authorities") String Authorization,
            @Body HashMap<String, Object> map);

    /**
     * 个人朋友圈
     */
    @Headers({"urlName:information"})
    @POST("/module-friend/moments/loginEd/findMomentsListWithPublisherUserId")
    Observable<ResultResponse<BasePageEntity<Moments>>> PersonalMomentsList(@Header("authorities") String Authorization, @Body HashMap<String, Object> map);

    /**
     * 朋友圈详情
     */
    @Headers({"urlName:information"})
    @GET("/module-friend/moments/loginEd/selectMomentsDetails/{momentsId}")
    Observable<ResultResponse<MomentsDetailsEntity>> momentsDetails(@Header("authorities") String Authorization, @Path("momentsId") String momentsId);


    /**
     * 说说详情
     */
    @Headers({"urlName:information"})
    @POST("/module-friend/moments/findMomentsListWithPublisherUserId")
    Observable<ResultResponse<BasePageEntity<Moments>>> MomentsDetails(@Body HashMap<String, Object> map);


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
    @POST("/module-client/login")
    Observable<ResultResponse<LoginBean>> loginRequest(@Query("phone") String phone, @Query("password") String password);

    /**
     * 注册
     */
    @Headers({"urlName:information"})
    @POST("/module-client/register")
    Observable<ResultResponse<LoginBean>> registerRequest(@Body HashMap<String, Object> map);

    /**
     * 主人删除空间访问某个人记录
     */
    @Headers({"urlName:account"})
    @POST("/module-client/loginEd/deletePersonalVisitor")
    Observable<ResultResponse<VisitorBean>> deleteVisitorRequest(@Header("authorities") String authorities, @Query("visitorId") Long visitorId);


    /**
     * 手机验证码
     */
    @Headers({"urlName:information"})
    @POST("/module-client/sendMessage")
    Observable<ResultResponse<LoginBean>> sendMessageRequest(@Body HashMap<String, Object> map);

    /**
     * 忘记密码
     */
    @Headers({"urlName:information"})
    @POST("/module-client/forgetPassword")
    Observable<ResultResponse<Object>> forgetPasswordRequest(@Body HashMap<String, Object> map);

    /**
     * 修改个人信息
     */
    @Headers({"urlName:information"})
    @POST("/module-client/loginEd/modifyUserInfo")
    Observable<ResultResponse<Object>> modifyUserInfoRequest(@Header("authorities") String authorities, @Body HashMap<String, Object> map);

    /**
     * 修改密码
     */
    @Headers({"urlName:information"})
    @POST("/module-client/loginEd/modifyPassword")
    Observable<ResultResponse<Object>> modifyPasswordRequest(@Header("authorities") String authorities, @Body HashMap<String, Object> map);


    /**
     * 获取用户信息
     */
    @Headers({"urlName:information"})
    @GET("/module-client/loginEd/getUserInfo")
    Observable<ResultResponse<UserInfo>> getUserInfo(@Header("authorities") String authorities);

    /**
     * 用户反馈
     */
    @Headers({"urlName:usually"})
    @POST("/module-friend/loginEd/addFeedback")
    Observable<ResultResponse<Object>> FeedbackRequest(@Header("authorities") String authorities, @Body HashMap<String, String> map);

    /**
     * 用户反馈列表
     */
    @Headers({"urlName:usually"})
    @POST("/module-friend/loginEd/FeedbackPageVo")
    Observable<ResultResponse<BasePageEntity<FeedbackBean>>> findFeedbackListRequest(@Header("authorities") String authorities, @Body HashMap<String, Object> map);


    /**
     * 我的访客记录
     */
    @Headers({"urlName:account"})
    @POST("/module-friend/visitor/loginEd/myVisitorList")
    Observable<ResultResponse<BasePageEntity<VisitorBean>>> RefreshVisitorListRequest(@Header("authorities") String authorities, @Body HashMap<String, Object> map);


    /**
     * 我的关注
     */
    @Headers({"urlName:account"})
    @POST("/module-friend/follow/loginEd/myFollowList")
    Observable<ResultResponse<BasePageEntity<FollowBean>>> RefreshFollowListRequest(@Header("authorities") String authorities, @Body HashMap<String, Object> map);


    /**
     * 我的粉丝
     */
    @Headers({"urlName:account"})
    @POST("/module-friend/follow/loginEd/myFansList")
    Observable<ResultResponse<BasePageEntity<FollowBean>>> RefreshFansListRequest(@Header("authorities") String authorities, @Body HashMap<String, Object> map);

    /**
     * 新增评论
     */
    @Headers({"urlName:information"})
    @POST("/module-friend/loginEd/addReply")
    Observable<ResultResponse<MomentsDetailsEntity>> addReply(@Header("authorities") String authorities, @Body HashMap<String, Object> map);

    /**
     * 删除评论
     */
    @Headers({"urlName:information"})
    @GET("/module-friend/loginEd/deleteReplyFromUser{replyId}")
    Observable<ResultResponse<MomentsDetailsEntity>> deleteReplyMoments(@Header("authorities") String Authorization, @Path("replyId") String replyId);


    /**
     * 发布说说
     */
    @Headers({"urlName:information"})
    @POST("/module-friend/moments/loginEd/addMoments")
    Observable<ResultResponse<Object>> releaseMoments(@Header("authorities") String Authorization, @Body HashMap<String, Object> map);

    /**
     * 好友列表
     */
    @Headers({"urlName:account"})
    @GET("/module-friend/follow/loginEd/myFriendsList")
    Observable<ResultResponse<Friends>> FriendsList(@Header("authorities") String authorities);

    /**
     * 查找用户
     */
    @Headers({"urlName:account"})
    @GET("/module-friend/follow/loginEd/findUserList")
    Observable<ResultResponse<ResultList<FindUserBean>>> findUserListRequest(@Header("authorities") String authorities, @Query("findUserId") String findUserId);


    /**
     * 添加关注
     */
    @Headers({"urlName:account"})
    @POST("/module-friend/follow/loginEd/addFollow")
    Observable<ResultResponse<Object>> addFollowRequest(@Header("authorities") String authorities, @Body HashMap<String, String> map);

    /**
     * 取消关注
     */
    @Headers({"urlName:account"})
    @POST("/module-friend/follow/loginEd/cancelFollow")
    Observable<ResultResponse<Object>> cancelFollowRequest(@Header("authorities") String authorities, @Body HashMap<String, String> map);



    /**
     * 上传文件
     *
     * @param Body
     * @return
     */
    @Headers({"urlName:information"})
    @POST("/module-friend/qiniu/upload")
    Observable<ResultResponse<Object>> upLoadFile(
            @Header("authorities") String Authorization,
            @Body RequestBody Body);


}
