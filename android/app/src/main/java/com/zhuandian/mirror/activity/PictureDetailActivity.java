package com.zhuandian.mirror.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.mirror.R;
import com.zhuandian.mirror.entity.ContentEntity;
import com.zhuandian.mirror.entity.SendEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PictureDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private ContentEntity contentEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_detail;
    }

    @Override
    protected void setUpView() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText("发送图片");
        contentEntity = ((ContentEntity) getIntent().getSerializableExtra("entity"));
        Glide.with(this).load(contentEntity.getContent()).into(ivImg);
    }



    @OnClick({R.id.iv_back, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send:
                sendImg2Web();
                break;
        }
    }

    private void sendImg2Web() {
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
                                Toast.makeText(PictureDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(PictureDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
