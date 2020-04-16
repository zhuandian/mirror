package com.zhuandian.mirror.apapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.mirror.R;
import com.zhuandian.mirror.activity.PictureDetailActivity;
import com.zhuandian.mirror.entity.ContentEntity;
import com.zhuandian.mirror.entity.SendEntity;
import com.zhuandian.mirror.utils.MyViedeoPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-16.
 */
public class VideoAdapter extends BaseAdapter<ContentEntity, BaseViewHolder> {
    @BindView(R.id.videoplayer)
    MyViedeoPlayer myViedeoPlayer;

    public VideoAdapter(List<ContentEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, ContentEntity contentEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        myViedeoPlayer.setUp(contentEntity.getContent(),
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "");
//        Glide.with(mContext).load(videoEntity.getThumbPath())
//                .into(myViedeoPlayer.thumbImageView);
        myViedeoPlayer.setOnVideoPlayingListener(new MyViedeoPlayer.OnVideoPlayingListener() {
            @Override
            public void onVideoPlaying() {
                sendVideo2Web(contentEntity);
            }
        });

    }

    private void sendVideo2Web(ContentEntity contentEntity) {
        BmobQuery<SendEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<SendEntity>() {
            @Override
            public void done(List<SendEntity> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    SendEntity sendEntity = list.get(0);
                    sendEntity.setContentEntity(contentEntity);
                    sendEntity.setCode(sendEntity.getCode() + 1);
                    sendEntity.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    SendEntity sendEntity = new SendEntity();
                    sendEntity.setContentEntity(contentEntity);
                    sendEntity.setCode(0);
                    sendEntity.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_video;
    }
}
