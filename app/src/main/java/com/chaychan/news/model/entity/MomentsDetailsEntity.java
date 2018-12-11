package com.chaychan.news.model.entity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 说说详情
 */
public class MomentsDetailsEntity extends DataSupport {

    private String momentsId;
    private Moments moments;
    private List<Reply> replyList;

    public MomentsDetailsEntity() {

    }

    public MomentsDetailsEntity(String momentsId, Moments moments, List<Reply> replyList) {
        this.momentsId = momentsId;
        this.moments = moments;
        this.replyList = replyList;
    }

    public String getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(String momentsId) {
        this.momentsId = momentsId;
    }

    public Moments getMoments() {
        return moments;
    }

    public void setMoments(Moments moments) {
        this.moments = moments;
    }


    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
