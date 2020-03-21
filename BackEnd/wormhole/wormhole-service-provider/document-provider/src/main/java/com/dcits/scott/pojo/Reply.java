package com.dcits.scott.pojo;

import com.dcits.scott.common.CommonDO;
import org.bson.types.ObjectId;

public class Reply extends CommonDO {
    private String from;
    private ObjectId fromId;
    private String fromHeadImg;
    private String to;
    private ObjectId toId;
    private String comment;
    private String time;
    private Integer commentNum;
    private Integer like;
    private Integer unLike;
    private boolean inputShow;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }



    public String getFromHeadImg() {
        return fromHeadImg;
    }

    public void setFromHeadImg(String fromHeadImg) {
        this.fromHeadImg = fromHeadImg;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ObjectId getFromId() {
        return fromId;
    }

    public void setFromId(ObjectId fromId) {
        this.fromId = fromId;
    }

    public ObjectId getToId() {
        return toId;
    }

    public void setToId(ObjectId toId) {
        this.toId = toId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getUnLike() {
        return unLike;
    }

    public void setUnLike(Integer unLike) {
        this.unLike = unLike;
    }

    public boolean isInputShow() {
        return inputShow;
    }

    public void setInputShow(boolean inputShow) {
        this.inputShow = inputShow;
    }
}
