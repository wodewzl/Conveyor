package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.utils.BaseCommonUtils;
import com.wuzhanglong.library.utils.DateUtils;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class WorkAdapter extends RecyclerBaseAdapter<WorkVO.DataBean.ListBean> {
    public WorkAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.work_adapter_type1);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        final WorkVO.DataBean.ListBean vo = (WorkVO.DataBean.ListBean) model;
        if ("1".equals(vo.getIsTitle())) {
            helper.setText(R.id.time_tv, vo.getDate_week());
        } else {
            if(!TextUtils.isEmpty(vo.getHeadpic())){
                Picasso.with(mContext).load(vo.getHeadpic()).placeholder(R.drawable.user_def).into(helper.getImageView(R.id.head_img));
            }

            helper.setText(R.id.name_tv, vo.getFullname());
            helper.setText(R.id.depart_tv, vo.getDname());

            helper.setText(R.id.type_tv, vo.getTypename());
            helper.getTextView(R.id.type_tv).setBackground(BaseCommonUtils.setBackgroundShap(mContext,5,R.color.conveyor_title,R.color.conveyor_title));

            helper.setText(R.id.time_tv, DateUtils.parseDateDay(vo.getTime()));
            helper.setText(R.id.today_tv, vo.getContent1());
            helper.setText(R.id.tomory_tv, vo.getContent2());
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return super.getItemViewType(position);
        }
        WorkVO.DataBean.ListBean vo = (WorkVO.DataBean.ListBean) mData.get(position);
        if ("1".equals(vo.getIsTitle())) {
            return R.layout.work_adapter_type1;
        } else {
            return R.layout.work_adapter_type2;
        }
    }

}
