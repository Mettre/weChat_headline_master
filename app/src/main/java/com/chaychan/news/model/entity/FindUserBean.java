package com.chaychan.news.model.entity;

public class FindUserBean {

    /**
     * followId : 100002
     * userId : 2018112209485799882
     * userName : 阿凡达
     * updateTime : 1542984973000
     * headAvatar : http://img0.imgtn.bdimg.com/it/u=2577022489,1269768065&fm=26&gp=0.jpg
     * backgroundWall : http://img.zx123.cn/Resources/zx123cn/uploadfile/2018/0426/0429021d4bf64dcc6f20c9b07409e088.jpg
     * gender : MAN
     * isFollow : true
     * isFans : true
     */

    private int followId;
    private String userId;
    private String userName;
    private long updateTime;
    private String headAvatar;
    private String backgroundWall;
    private String gender;
    private boolean isFollow;
    private boolean isFans;

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadAvatar() {
        return headAvatar;
    }

    public void setHeadAvatar(String headAvatar) {
        this.headAvatar = headAvatar;
    }

    public String getBackgroundWall() {
        return backgroundWall;
    }

    public void setBackgroundWall(String backgroundWall) {
        this.backgroundWall = backgroundWall;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public boolean isIsFans() {
        return isFans;
    }

    public void setIsFans(boolean isFans) {
        this.isFans = isFans;
    }

}
