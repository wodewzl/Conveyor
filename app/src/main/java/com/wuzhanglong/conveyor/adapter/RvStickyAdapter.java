package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.ContanctVO;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


public class RvStickyAdapter extends BGARecyclerViewAdapter<ContanctVO.DataBean> {

    public RvStickyAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_index_city);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, ContanctVO.DataBean model) {
        ContanctVO.DataBean vo=model;
        if(!TextUtils.isEmpty(vo.getHeadpic()))
        Picasso.with(mContext).load(vo.getHeadpic()).placeholder(R.drawable.user_icon_def).into(helper.getImageView(R.id.head_img));
        helper.setText(R.id.name_tv, model.getFullname());
        helper.setText(R.id.phone_tv, model.getTel());
        helper.setText(R.id.position_tv, model.getPname());
        helper.setText(R.id.depart_tv, model.getDname());
    }

    /**
     * 是否为该分类下的第一个条目
     *
     * @param position
     * @return
     */
    public boolean isCategoryFistItem(int position) {
        // 第一条数据是该分类下的第一个条目
        if (position == 0) {
            return true;
        }

        String currentTopc = getItem(position).topc;
        String preTopc = getItem(position - 1).topc;
        // 当前条目的分类和上一个条目的分类不相等时，当前条目为该分类下的第一个条目
        if (!TextUtils.equals(currentTopc, preTopc)) {
            return true;
        }

        return false;
    }

    public int getPositionForCategory(int category) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getItem(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == category) {
                return i;
            }
        }
        return -1;
    }
}