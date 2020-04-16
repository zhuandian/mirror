package com.zhuandian.mirror.apapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.mirror.R;
import com.zhuandian.mirror.activity.PictureDetailActivity;
import com.zhuandian.mirror.entity.ContentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.http.I;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-16.
 */
public class PictureAdapter extends BaseAdapter<ContentEntity, BaseViewHolder> {
    @BindView(R.id.iv_img)
    ImageView ivImg;

    public PictureAdapter(List<ContentEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, ContentEntity contentEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        Glide.with(mContext).load(contentEntity.getContent()).into(ivImg);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PictureDetailActivity.class);
                intent.putExtra("entity",contentEntity);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_picture;
    }
}
