package com.wuzhanglong.conveyor.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.PublishListVO;
import com.wuzhanglong.conveyor.util.AppCache;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.utils.DividerUtil;

import cn.bingoogolapple.baseadapter.BGAGridDivider;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class PublishListAdapter extends RecyclerBaseAdapter {
    public PublishListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.publish_list_adapter);
    }

    @Override
    public void initData(final BGAViewHolderHelper helper, int position, Object model) {
        final PublishListVO.DataBean.ListBean vo = (PublishListVO.DataBean.ListBean) model;
        if (!TextUtils.isEmpty(vo.getHeadpic())) {
            Picasso.with(mContext).load(vo.getHeadpic()).placeholder(R.drawable.user_def).into(helper.getImageView(R.id.head_img));
        }
        helper.setText(R.id.name_tv, vo.getFullname());
        helper.setText(R.id.depart_tv, vo.getDname());
        helper.setText(R.id.time_tv, vo.getDate());
        helper.setText(R.id.desc_tv,vo.getRemark());

        LuRecyclerView recyclerView = helper.getView(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
        recyclerView.setLayoutManager(layoutManager);
        BGAGridDivider divider = DividerUtil.bgaGridDivider(R.dimen.dp_2);
        recyclerView.addItemDecoration(divider);
        PublishListImgAdapter adapter = new PublishListImgAdapter(mRecyclerView);
        LuRecyclerViewAdapter luAdapter = new LuRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(luAdapter);
        recyclerView.setLoadMoreEnabled(false);
        adapter.updateData(vo.getImage_video());

        if(!TextUtils.isEmpty(vo.getAudio_url()) ){
            helper.setVisibility(R.id.voice_layout, View.VISIBLE);
            helper.setText(R.id.voices_time_tv,vo.getAudio_seconds()+"s");
        }else {
            helper.setVisibility(R.id.voice_layout, View.GONE);
        }

        helper.getView(R.id.voice_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppCache.getPlayService() != null) {
                    AppCache.getPlayService().setImageView(helper.getImageView(R.id.voice_img));
                    AppCache.getPlayService().stopPlayVoiceAnimation();
                    AppCache.getPlayService().play(vo.getAudio_url());
                }
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return super.getItemViewType(position);
        }
        return R.layout.publish_list_adapter;
    }

}
