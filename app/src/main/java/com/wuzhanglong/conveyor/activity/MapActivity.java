package com.wuzhanglong.conveyor.activity;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

public class MapActivity extends BaseActivity {

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.map_activity);
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindViewsListener() {

    }

    @Override
    public void getData() {
        show();
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