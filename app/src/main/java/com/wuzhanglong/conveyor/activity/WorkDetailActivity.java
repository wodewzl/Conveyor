package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.WorkDetailVO;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.constant.BaseConstant;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.BaseCommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WorkDetailActivity extends BaseActivity implements BGANinePhotoLayout.Delegate, BGAOnRVItemClickListener, View.OnClickListener , EasyPermissions.PermissionCallbacks{
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int REQUEST_PERMISSIONS = 1;
    private TextView mNameTv, mContent1, mContent2, mContent3, mContent4, mContent5, mContent3TitleTv, mContent4TitleTv, mContent5TitleTv, mTimeTv;
    private String mType = "1";//1日报2汇总
    private BGANinePhotoLayout mPhotoLyout;
    private WorkDetailVO mWorkDetailVO;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.work_detail_activity);
    }

    @Override
    public void initView() {
        mBaseOkTv.setText("分享");
        mTimeTv = getViewById(R.id.time_tv);
        mNameTv = getViewById(R.id.name_tv);
        mContent1 = getViewById(R.id.content1_tv);
        mContent2 = getViewById(R.id.content2_tv);
        mContent3 = getViewById(R.id.content3_tv);
        mContent4 = getViewById(R.id.content4_tv);
        mContent5 = getViewById(R.id.content5_tv);
        mContent3TitleTv = getViewById(R.id.content3_title_tv);
        mContent4TitleTv = getViewById(R.id.content4_title_tv);
        mContent5TitleTv = getViewById(R.id.content5_title_tv);
        mPhotoLyout = getViewById(R.id.photo_layout);
        mTimeTv.setBackground(BaseCommonUtils.setBackgroundShap(this, 30, R.color.colorPrimaryDark, R.color.colorPrimaryDark));
    }

    @Override
    public void bindViewsListener() {
        mBaseOkTv.setOnClickListener(this);
    }

    @Override
    public void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("logid", this.getIntent().getStringExtra("logid"));
        HttpGetDataUtil.get(WorkDetailActivity.this, Constant.WORK_DETAIL_URL, map, WorkDetailVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        mWorkDetailVO = (WorkDetailVO) vo;
        WorkDetailVO.DataBean dataBean = mWorkDetailVO.getData();
        mTimeTv.setText(dataBean.getDate());
        mContent1.setText(dataBean.getContent1());
        mContent2.setText(dataBean.getContent2());
        mContent3.setText(dataBean.getContent3());
        mContent4.setText(dataBean.getContent4());
        mContent5.setText(dataBean.getContent5());
        if ("0".equals(dataBean.getType())) {
            mNameTv.setText(dataBean.getFullname());
            mBaseTitleTv.setText("日报详情");
            mContent3.setVisibility(View.GONE);
            mContent4.setVisibility(View.GONE);
            mContent5.setVisibility(View.GONE);
            mContent3TitleTv.setVisibility(View.GONE);
            mContent4TitleTv.setVisibility(View.GONE);
            mContent5TitleTv.setVisibility(View.GONE);
        } else {
            mBaseTitleTv.setText("汇总详情");
            mNameTv.setText(dataBean.getFullname());
            BaseCommonUtils.setTextTwoLast(this, mNameTv, dataBean.getFullname(), "   " + dataBean.getDname() + "/" + dataBean.getPositionname(), R.color.C6, 0.8f);
            if (TextUtils.isEmpty(mContent3.getText().toString())) {
                mContent3.setVisibility(View.GONE);
                mContent3TitleTv.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(mContent4.getText().toString())) {
                mContent4.setVisibility(View.GONE);
                mContent4TitleTv.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(mContent5.getText().toString())) {
                mContent5.setVisibility(View.GONE);
                mContent5TitleTv.setVisibility(View.GONE);
            }
        }
        if (dataBean.getImgs() != null) {
            mPhotoLyout.setData((ArrayList<String>) dataBean.getImgs());
            mPhotoLyout.setDelegate(this);
        }
    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        choicePhotoWrapper();
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper() {

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能

            if (mPhotoLyout.getItemCount() == 1) {
                // 预览单张图片
                photoPreviewIntentBuilder.previewPhoto(mPhotoLyout.getCurrentClickItem());
            } else if (mPhotoLyout.getItemCount() > 1) {
                // 预览多张图片
                photoPreviewIntentBuilder.previewPhotos(mPhotoLyout.getData())
                        .currentPosition(mPhotoLyout.getCurrentClickItemPosition()); // 当前预览图片的索引
            }
            startActivity(photoPreviewIntentBuilder.build());
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onClick(View view) {

//        if (Build.VERSION.SDK_INT >= 23) {
//            reuestPerm();
//        }else{
//            WorkDetailVO.DataBean.ShareBean shareBean = mWorkDetailVO.getData().getShare_param();
//            ShareUtil.shareWeb(this, shareBean.getImage(), shareBean.getTitle(), shareBean.getDesc(), shareBean.getUrl());
//        }

        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }else {
                        WorkDetailVO.DataBean.ShareBean shareBean = mWorkDetailVO.getData().getShare_param();
            ShareUtil.shareWeb(this, shareBean.getImage(), shareBean.getTitle(), shareBean.getDesc(), shareBean.getUrl());
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        System.out.println("============>");
        if(requestCode==123){
            WorkDetailVO.DataBean.ShareBean shareBean = mWorkDetailVO.getData().getShare_param();
            ShareUtil.shareWeb(this, shareBean.getImage(), shareBean.getTitle(), shareBean.getDesc(), shareBean.getUrl());
        }

    }

    @AfterPermissionGranted(REQUEST_PERMISSIONS)
    private void reuestPerm() {
        String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            WorkDetailVO.DataBean.ShareBean shareBean = mWorkDetailVO.getData().getShare_param();
            ShareUtil.shareWeb(this, shareBean.getImage(), shareBean.getTitle(), shareBean.getDesc(), shareBean.getUrl());
        } else {
            EasyPermissions.requestPermissions(this, "", REQUEST_PERMISSIONS, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Intent intent = new Intent();
        System.out.println("==============>");

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }



}
