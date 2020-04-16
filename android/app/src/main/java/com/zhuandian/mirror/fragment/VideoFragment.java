package com.zhuandian.mirror.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.mirror.R;
import com.zhuandian.mirror.apapter.VideoAdapter;
import com.zhuandian.mirror.entity.ContentEntity;
import com.zhuandian.mirror.utils.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.jzvd.JZVideoPlayer;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-16.
 */
public class VideoFragment extends BaseFragment {
    @BindView(R.id.brv_list)
    BaseRecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<ContentEntity> mDatas = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private int currentCount = -10;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        tvTitle.setText("视频");
        videoAdapter = new VideoAdapter(mDatas, actitity);
        recyclerView.setRecyclerViewAdapter(videoAdapter);
        loadDatas();
        initRefreshListener();
    }
    private void initRefreshListener() {
        recyclerView.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                videoAdapter.notifyDataSetChanged();
                loadDatas();

            }
        });
        recyclerView.setLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDatas();
            }
        });


    }


    private void loadDatas() {
        currentCount = currentCount + 10;
        BmobQuery<ContentEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.addWhereEqualTo("type",3);
        query.setLimit(10);
        query.setSkip(currentCount);

        query.findObjects(new FindListener<ContentEntity>() {
            @Override
            public void done(List<ContentEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    videoAdapter.notifyDataSetChanged();
                    recyclerView.setRefreshLayoutState(false);
                } else {
                    recyclerView.setRefreshLayoutState(false);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint ( boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            JZVideoPlayer.releaseAllVideos();
        }
    }
}
