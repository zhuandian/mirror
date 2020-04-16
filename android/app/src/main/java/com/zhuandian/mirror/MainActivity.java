package com.zhuandian.mirror;


import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.mirror.apapter.HomePageAdapter;
import com.zhuandian.mirror.fragment.PictureFragment;
import com.zhuandian.mirror.fragment.TextFragment;
import com.zhuandian.mirror.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tab_bottom)
    BottomNavigationView tabBottom;
    public static final int PAGE_PICTURE = 0;
    public static final int PAGE_TEXT = 1;
    public static final int PAGE_VIDEO = 2;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PictureFragment());
        fragmentList.add(new TextFragment());
        fragmentList.add(new VideoFragment());
        vpHome.setAdapter(new HomePageAdapter(getSupportFragmentManager(),fragmentList));
        vpHome.setOffscreenPageLimit(3);

        vpHome.setCurrentItem(PAGE_PICTURE);
        initBottomTab();
    }

    public  void setCurrentPage(int position){
        vpHome.setCurrentItem(position);
    }
    private void initBottomTab() {
        vpHome.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabBottom.getMenu().getItem(position).setChecked(true);
            }
        });

        tabBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.tab_picture:
                        vpHome.setCurrentItem(PAGE_PICTURE);
                        break;
                    case R.id.tab_text:
                        vpHome.setCurrentItem(PAGE_TEXT);
                        break;
                    case R.id.tab_video:
                        vpHome.setCurrentItem(PAGE_VIDEO);
                        break;
                }

                return true;
            }
        });
    }
}
