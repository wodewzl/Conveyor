package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.RecyclerView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.DepartVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class WorkAllAdapter extends RecyclerBaseAdapter<DepartVO.DataBean.ListBean> {
    public WorkAllAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.work_all_adapter);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {

    }


    @Override
    public int getItemCount() {
        return 30;
    }
    @Override
    public int getItemViewType(int position) {
        return R.layout.work_all_adapter;
    }
}
