package com.wuzhanglong.conveyor.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.utils.BaseCommonUtils;
import com.wuzhanglong.library.utils.DateUtils;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/29.
 */

public class HomeAdapter extends RecyclerBaseAdapter<WorkVO.DataBean.ListBean> {

    public HomeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.home_adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        WorkVO.DataBean.ListBean vo = (WorkVO.DataBean.ListBean) model;
        Picasso.with(mContext).load(vo.getHeadpic()).placeholder(R.drawable.user_icon_def).into(helper.getImageView(R.id.home_head_img));
        helper.setText(R.id.name_tv, vo.getFullname());
        helper.setText(R.id.depart_tv, vo.getDname()+"/"+vo.getPositionname());
        helper.setText(R.id.type_tv, "日报");
        helper.getTextView(R.id.type_tv).setBackground(BaseCommonUtils.setBackgroundShap(mContext,5,R.color.conveyor_title,R.color.conveyor_title));
        helper.setText(R.id.time_tv, DateUtils.parseDateDay(vo.getTime()));
        helper.setText(R.id.content1_tv, vo.getContent1());
        helper.setText(R.id.content2_tv,vo.getContent2());
    }


}

