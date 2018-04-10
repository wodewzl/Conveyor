package com.wuzhanglong.conveyor.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.activity.MapActivity;
import com.wuzhanglong.conveyor.activity.SignListActivity;
import com.wuzhanglong.conveyor.model.SignListVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.utils.BaseCommonUtils;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class SignListAdapter extends RecyclerBaseAdapter {
    public SignListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.work_adapter_type1);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        final SignListVO.DataBean.ListBean vo = (SignListVO.DataBean.ListBean) model;
        if ("1".equals(vo.getIsTitle())) {
            helper.setText(R.id.time_tv, vo.getDate());
        } else {
            if (!TextUtils.isEmpty(vo.getHeadpic()))
                Picasso.with(mContext).load(vo.getHeadpic()).placeholder(R.drawable.user_def).into(helper.getImageView(R.id.head_img));

            helper.setText(R.id.name_tv, vo.getFullname());
            helper.setText(R.id.depart_tv, vo.getDname() + "/" + vo.getPositionname());
            helper.setText(R.id.time_tv, vo.getDate_timeX());
            helper.setText(R.id.type_tv, vo.getStatus_text());
            if ("1".equals(vo.getStatus())) {
                helper.getTextView(R.id.type_tv).setBackground(BaseCommonUtils.setBackgroundShap(mContext, 5, R.color.colorAccent, R.color.colorAccent));

            } else if ("2".equals(vo.getStatus())) {
                helper.getTextView(R.id.type_tv).setBackground(BaseCommonUtils.setBackgroundShap(mContext, 5, R.color.C9, R.color.C9));
            } else {
                helper.getTextView(R.id.type_tv).setBackground(BaseCommonUtils.setBackgroundShap(mContext, 5, R.color.color1, R.color.color1));
            }

            if (!TextUtils.isEmpty(vo.getPic())) {
                helper.getImageView(R.id.img).setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(vo.getPic()).into(helper.getImageView(R.id.img));
            } else {
                helper.getImageView(R.id.img).setVisibility(View.GONE);
            }
            ImageView imageView = helper.getImageView(R.id.img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SignListActivity activity = (SignListActivity) mActivity;
                    activity.choicePhotoWrapper(vo.getPic());
                }
            });

            helper.setText(R.id.address_tv, vo.getAddress());
            BaseCommonUtils.setTextThree(mContext, helper.getTextView(R.id.distance_tv), "偏差距离：", vo.getDistance(), "KM", R.color.C9, 1.3f);
            if ("2".equals(vo.getStatus())) {
                helper.getTextView(R.id.distance_tv).setVisibility(View.VISIBLE);
            } else {
                helper.getTextView(R.id.distance_tv).setVisibility(View.GONE);
            }
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return super.getItemViewType(position);
        }
        SignListVO.DataBean.ListBean vo = (SignListVO.DataBean.ListBean) mData.get(position);
        if ("1".equals(vo.getIsTitle())) {
            return R.layout.work_adapter_type1;
        } else {
            return R.layout.sgin_list_adapter_type2;
        }
    }

}
