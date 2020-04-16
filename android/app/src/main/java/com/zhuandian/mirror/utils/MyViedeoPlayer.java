package com.zhuandian.mirror.utils;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * desc :
 * author：xiedong
 * date：2020/02/07
 */
public class MyViedeoPlayer extends JZVideoPlayerStandard {
    private OnVideoPlayingListener videoPlayingListener;

    public MyViedeoPlayer(Context context) {
        this(context, null);
    }

    public MyViedeoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        if (videoPlayingListener != null) {
            videoPlayingListener.onVideoPlaying();
        }
    }


    public void setOnVideoPlayingListener(OnVideoPlayingListener listener) {
        this.videoPlayingListener = listener;
    }

    public interface OnVideoPlayingListener {
        void onVideoPlaying();
    }
}
