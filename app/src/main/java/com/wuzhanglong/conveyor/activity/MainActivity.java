package com.wuzhanglong.conveyor.activity;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ImageView mHomeHeadImg;
    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.main_activity);
    }

    @Override
    public void initView() {
        mDrawerLayout = getViewById(R.id.dl_left);
        mHomeHeadImg=getViewById(R.id.home_head_img);
    }

    @Override
    public void bindViewsListener() {
        mHomeHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void hasData(BaseVO vo) {

    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }

}
