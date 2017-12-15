package com.wuzhanglong.conveyor.adapter;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.activity.ContactActivity;
import com.wuzhanglong.conveyor.activity.LoginActivity;
import com.wuzhanglong.conveyor.activity.MainActivity;
import com.wuzhanglong.conveyor.activity.PasswordUpdateActivity;
import com.wuzhanglong.conveyor.activity.WebViewActivity;
import com.wuzhanglong.conveyor.activity.WorkActivity;
import com.wuzhanglong.conveyor.activity.WorkAllActivity;
import com.wuzhanglong.conveyor.activity.WorkReportActivity;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.constant.BaseConstant;
import com.wuzhanglong.library.utils.BaseCommonUtils;
import com.wuzhanglong.library.utils.DateUtils;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
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
        if ("1".equals(vo.getIsTitle())) {
            helper.setItemChildClickListener(R.id.tv_home_01);
            helper.setItemChildClickListener(R.id.tv_home_02);
            helper.setItemChildClickListener(R.id.tv_home_03);
            helper.setItemChildClickListener(R.id.tv_home_04);
            helper.setItemChildClickListener(R.id.tv_home_05);
            helper.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewGroup parent, View v, int position) {
                    Intent intent = new Intent();
                    switch (v.getId()) {
                        case R.id.tv_home_01:
                            intent.setClass(mContext, WorkActivity.class);
                            break;
                        case R.id.tv_home_02:
                            intent.putExtra("type", "0");
                            intent.setClass(mContext, WorkReportActivity.class);
                            break;
                        case R.id.tv_home_03:
                            intent.setClass(mContext, ContactActivity.class);
                            break;
                        case R.id.tv_home_04:
                            intent.setClass(mContext, LoginActivity.class);
                            break;
                        case R.id.tv_home_05:
                            intent.putExtra("type", "1");
                            intent.setClass(mContext, WorkAllActivity.class);
                            break;
                        case R.id.out_tv:
                            AppApplication.getInstance().saveUserInfoVO(null);
                            break;
                        default:
                            break;
                    }
                    if (intent.getComponent() != null)
                        mContext.startActivity(intent);
                }
            });
        } else if ("2".equals(vo.getIsTitle())) {

        } else {
            if (!TextUtils.isEmpty(vo.getHeadpic()))
                Picasso.with(mContext).load(vo.getHeadpic()).placeholder(R.drawable.user_icon_def).into(helper.getImageView(R.id.home_head_img));
            helper.setText(R.id.name_tv, vo.getFullname());
            helper.setText(R.id.depart_tv, vo.getDname() + "/" + vo.getPositionname());
            helper.setText(R.id.type_tv, "日报");
            helper.getTextView(R.id.type_tv).setBackground(BaseCommonUtils.setBackgroundShap(mContext, 5, R.color.conveyor_title, R.color.conveyor_title));
            helper.setText(R.id.time_tv, DateUtils.parseDateDay(vo.getTime()));
            helper.setText(R.id.content1_tv, vo.getContent1());
            helper.setText(R.id.content2_tv, vo.getContent2());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return super.getItemViewType(position);
        }
        WorkVO.DataBean.ListBean vo = (WorkVO.DataBean.ListBean) mData.get(position);
        if ("1".equals(vo.getIsTitle())) {
            return R.layout.home_title;
        } else if ("2".equals(vo.getIsTitle())) {

            return R.layout.home_adapter_title;
        } else {
            return R.layout.home_adapter;
        }
    }
}

