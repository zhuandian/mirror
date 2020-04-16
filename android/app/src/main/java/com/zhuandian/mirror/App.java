package com.zhuandian.mirror;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * desc :
 * author：xiedong
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "425913fbe114b8b3d57cb9f36aec4c17");
    }
}
