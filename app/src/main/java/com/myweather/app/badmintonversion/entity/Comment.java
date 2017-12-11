package com.myweather.app.badmintonversion.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 创建评论的实例
 * 评论的内容包括
 * 1.评论的内容
 * 2.评论所属的状态（外键1）
 * 3.评论所属的状态下的人（外键2）
 * Created by zyt on 2017/11/28.
 */

/**
 * 将comment对象放在动态下面
 */
public class Comment extends BmobObject implements Serializable{
    private String content;
    //因此就不需要momentId
    private String momentId;
    private String momentUserId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getMomentUserId() {
        return momentUserId;
    }

    public void setMomentUserId(String momentUserId) {
        this.momentUserId = momentUserId;
    }
}
