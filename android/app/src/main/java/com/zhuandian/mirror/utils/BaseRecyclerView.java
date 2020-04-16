package com.zhuandian.mirror.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.zhuandian.mirror.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :封装RecyclerView
 * author：xiedong
 */

public class BaseRecyclerView<DATA> extends RelativeLayout {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    private List<DATA> mDatas = new ArrayList<>();
    public int loadDatasCount = -10;
    public final static int LOAD_DATAS_SIZE = 10; //每次加载数据的数量
    private Context context;
    private OnRefreshListener refreshListener;


    private OnLoadMoreListener loadMoreListener;
    private LinearLayoutManager linearLayoutManager;

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, LayoutInflater.from(context).inflate(R.layout.layout_base_recyclerview, this));
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshListener != null) {
                    refreshLayout.setRefreshing(false);
                    refreshListener.onRefresh();
                }

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    if (visibleItemCount + pastVisibleItem >= totalItemCount) {
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore();
                        }
                    }
                }
            }
        });
    }


    public void setPullToRefreshEnable(Boolean enable) {
        refreshLayout.setEnabled(enable);
    }


    public void setRefreshLayoutState(Boolean isRefreshing) {
        refreshLayout.setRefreshing(isRefreshing);
    }

    public void setRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setRecyclerViewLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
