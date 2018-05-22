package com.wuzhanglong.conveyor.activity;

import android.view.View;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class PublishActivity extends BaseActivity implements BGANinePhotoLayout.Delegate{
    private static final int PRC_PHOTO_PICKER = 1;
    private BGANinePhotoLayout mPhotoLyout;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.publish_activity);
    }

    @Override
    public void initView() {
        mPhotoLyout = getViewById(R.id.photo_layout);

    }

    @Override
    public void bindViewsListener() {
        mPhotoLyout.setDelegate(this);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void getData() {
        showView();
    }

    @Override
    public void hasData(BaseVO vo) {

    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }


    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
//        openActivity(PublishActivity.class);  PictureSelector.create(MainActivity.this)
//        PictureSelector.create(PublishActivity.this)
//                .openCamera(PictureMimeType.ofImage())
//                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
