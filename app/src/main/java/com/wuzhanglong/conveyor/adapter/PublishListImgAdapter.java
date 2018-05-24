package com.wuzhanglong.conveyor.adapter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.activity.PublishListActivity;
import com.wuzhanglong.conveyor.model.PublishListVO;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.utils.BaseCommonUtils;
import com.wuzhanglong.library.utils.DateUtils;

import java.util.HashMap;

import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by ${Wuzhanglong} on 2017/11/30.
 */

public class PublishListImgAdapter extends RecyclerBaseAdapter {
    public PublishListImgAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.publish_list_img_adapter);
    }

    @Override
    public void initData(BGAViewHolderHelper helper, int position, Object model) {
        final PublishListVO.DataBean.ListBean.ImageVideoBean vo = (PublishListVO.DataBean.ListBean.ImageVideoBean) model;
        if (!TextUtils.isEmpty(vo.getUrl())) {
            Picasso.with(mContext).load(vo.getUrl()).into(helper.getImageView(R.id.pic_img));
        }
        helper.setText(R.id.time_tv, vo.getDate_timeX());
        JZVideoPlayerStandard videoPlay = helper.getView(R.id.videop_layer);
        if ("1".equals(vo.getType())) {
            videoPlay.setVisibility(View.GONE);
        } else {
            videoPlay.setVisibility(View.VISIBLE);
            videoPlay.setUp(vo.getUrl()
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
//            videoPlay.thumbImageView.setImageBitmap(createVideoThumbnail(vo.getUrl()));

        }
        helper.getView(R.id.pic_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishListActivity activity= (PublishListActivity) mContext;
                activity.choicePhotoWrapper(vo.getUrl());
            }
        });

    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return super.getItemViewType(position);
        }
        return R.layout.publish_list_img_adapter;
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Bitmap createVideoThumbnail(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
//        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        }
        return bitmap;
    }
}
