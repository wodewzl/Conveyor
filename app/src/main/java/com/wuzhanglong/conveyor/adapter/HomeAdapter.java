package com.wuzhanglong.conveyor.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.HomeVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/29.
 */

public class HomeAdapter extends RecyclerBaseAdapter<HomeVO> {

    public HomeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.home_adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        HomeVO vo = (HomeVO) model;
    }

}

