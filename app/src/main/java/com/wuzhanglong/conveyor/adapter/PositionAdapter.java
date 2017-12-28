package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.RecyclerView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.PositionVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class PositionAdapter extends RecyclerBaseAdapter<PositionVO.DataBean.ListBean> {
    public PositionAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.position_adapter_layout);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        final PositionVO.DataBean.ListBean vo = (PositionVO.DataBean.ListBean) model;
        helper.setText(R.id.title_tv, vo.getSign_name());
        helper.setText(R.id.desc_tv, vo.getSign_no());
    }


}
