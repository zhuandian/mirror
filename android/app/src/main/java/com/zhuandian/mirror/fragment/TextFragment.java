package com.zhuandian.mirror.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.mirror.R;
import com.zhuandian.mirror.entity.ContentEntity;
import com.zhuandian.mirror.entity.SendEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-16.
 */
public class TextFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_text_content)
    EditText etTextContent;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_text;
    }

    @Override
    protected void initView() {
        tvTitle.setText("发送文字");

    }

    @OnClick({R.id.tv_send,R.id.tv_close})
    public void onViewClicked(View view) {

        switch (view.getId()){
            case R.id.tv_send:
                sendText2Web();
                break;
            case R.id.tv_close:

                BmobQuery<SendEntity> query = new BmobQuery<>();
                query.findObjects(new FindListener<SendEntity>() {
                    @Override
                    public void done(List<SendEntity> list, BmobException e) {
                        if (e == null && list.size() > 0) {
                            SendEntity sendEntity = list.get(0);
                            sendEntity.setOpen(false);
                            sendEntity.setCode(sendEntity.getCode() + 1);
                            sendEntity.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(actitity, "关闭成功", Toast.LENGTH_SHORT).show();
                                        etTextContent.setText("");
                                    }
                                }
                            });
                        } else {
                            SendEntity sendEntity = new SendEntity();
                            sendEntity.setOpen(false);
                            sendEntity.setCode(0);
                            sendEntity.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(actitity, "关闭成功", Toast.LENGTH_SHORT).show();
                                        etTextContent.setText("");
                                    }
                                }
                            });
                        }
                    }
                });

                break;

        }



    }

    private void sendText2Web() {
        content = etTextContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(actitity, "发送内容不允许为空", Toast.LENGTH_SHORT).show();
            return;
        }


        ContentEntity contentEntity = new ContentEntity(content, 2);
        contentEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    BmobQuery<SendEntity> query = new BmobQuery<>();
                    query.findObjects(new FindListener<SendEntity>() {
                        @Override
                        public void done(List<SendEntity> list, BmobException e) {
                            if (e == null && list.size() > 0) {
                                SendEntity sendEntity = list.get(0);
                                sendEntity.setContentEntity(contentEntity);
                                sendEntity.setCode(sendEntity.getCode() + 1);
                                sendEntity.setOpen(true);
                                sendEntity.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(actitity, "发送成功", Toast.LENGTH_SHORT).show();
                                            etTextContent.setText("");
                                        }
                                    }
                                });
                            } else {
                                SendEntity sendEntity = new SendEntity();
                                sendEntity.setContentEntity(contentEntity);
                                sendEntity.setCode(0);
                                sendEntity.setOpen(true);
                                sendEntity.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(actitity, "发送成功", Toast.LENGTH_SHORT).show();
                                            etTextContent.setText("");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
