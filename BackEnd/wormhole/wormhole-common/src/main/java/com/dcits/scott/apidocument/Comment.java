package com.dcits.scott.apidocument;

import com.dcits.scott.common.CommonDO;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

public class Comment extends CommonDO {
    private String name;
    private String id;
    private String headImg;
    private String comment;
    private String time;
    private Integer commentNum;
    private Integer like;
    private Integer unLike;
    private boolean inputShow;
    private List<Reply> reply;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }
}
