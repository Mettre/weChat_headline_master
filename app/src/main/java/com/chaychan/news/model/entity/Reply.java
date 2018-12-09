package com.chaychan.news.model.entity;

public class Reply {

    private String replyId;

    private String replyParentId;//评论的父id可空

    private String replyParentUserId;//被回复人的用户id

    private String dynamicUserId;//评论人的用户id

    private String replyContent;

    private Long creationTime;

    private String replyParentUserName;//被回复人的昵称

    private String userName;//昵称

    private String headAvatar;//头像

    public String getHeadAvatar() {
        return headAvatar;
    }

    public void setHeadAvatar(String headAvatar) {
        this.headAvatar = headAvatar;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyParentId() {
        return replyParentId;
    }

    public void setReplyParentId(String replyParentId) {
        this.replyParentId = replyParentId;
    }

    public String getReplyParentUserId() {
        return replyParentUserId;
    }

    public void setReplyParentUserId(String replyParentUserId) {
        this.replyParentUserId = replyParentUserId;
    }

    public String getDynamicUserId() {
        return dynamicUserId;
    }

    public void setDynamicUserId(String dynamicUserId) {
        this.dynamicUserId = dynamicUserId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getReplyParentUserName() {
        return replyParentUserName;
    }

    public void setReplyParentUserName(String replyParentUserName) {
        this.replyParentUserName = replyParentUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
