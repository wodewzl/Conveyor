package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.RecyclerView;

import com.wuzhanglong.conveyor.model.HomeVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class WorkAdapter extends RecyclerBaseAdapter<HomeVO> {
    public WorkAdapter(RecyclerView recyclerView, int itemLayoutId) {
        super(recyclerView, itemLayoutId);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
