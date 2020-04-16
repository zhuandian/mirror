package com.zhuandian.mirror.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-16.
 */
public class ContentEntity extends BmobObject {
    private String content;
    private int type;  //1  图片  2。 文字  3 视频


    public ContentEntity(String content, int type) {
        this.content = content;
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
