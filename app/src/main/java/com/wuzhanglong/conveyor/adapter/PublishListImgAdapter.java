package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.PublishListVO;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.utils.BaseCommonUtils;
import com.wuzhanglong.library.utils.DateUtils;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class PublishListImgAdapter extends RecyclerBaseAdapter {
    public PublishListImgAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.work_adapter_type1);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        final PublishListVO.DataBean.ListBean.ImageVideoBean vo = (PublishListVO.DataBean.ListBean.ImageVideoBean) model;
        if (!TextUtils.isEmpty(vo.getUrl())) {
            Picasso.with(mContext).load(vo.getUrl()).into(helper.getImageView(R.id.pic_img));
        }
        helper.setTag(R.id.time_tv,vo.getDate_timeX());
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return super.getItemViewType(position);
        }
        return R.layout.publish_list_img_adapter;
    }

}
