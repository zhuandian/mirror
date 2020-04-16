package com.zhuandian.mirror.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-16.
 */
public class SendEntity extends BmobObject {

    private ContentEntity contentEntity;
    private int code;   //web端根据code判断，当前是否需要更新投射内容


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ContentEntity getContentEntity() {
        return contentEntity;
    }

    public void setContentEntity(ContentEntity contentEntity) {
        this.contentEntity = contentEntity;
    }
}
