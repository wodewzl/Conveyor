package com.wuzhanglong.conveyor.activity;

import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.nanchen.compresshelper.CompressHelper;
import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.UserInfoVO;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.constant.BaseConstant;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignActivity extends BaseActivity implements AMapLocationListener, View.OnClickListener, PostCallback {
    private static final String EXTRA_CAMERA_FILE_DIR = "EXTRA_CAMERA_FILE_DIR";
    private static final int RC_PREVIEW = 2;
    private static final int SPAN_COUNT = 3;
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;
    private ImageView mImageView;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private BGAPhotoHelper mPhotoHelper;
    private TextView mSignTv, mTimeTv, mAddressTv;
    private File mFile;
    private String mLon, mLat;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.activity_sign);
    }

    @Override
    public void initView() {
        mBaseTitleTv.setText("签到");
        mImageView = getViewById(R.id.image_view);
        mSignTv = getViewById(R.id.sign_tv);
        mTimeTv = getViewById(R.id.time_tv);
        mAddressTv = getViewById(R.id.address_tv);
        mTimeTv.setText(DateUtils.parseDateDayAndMin(System.currentTimeMillis() / 1000 + ""));
    }

    @Override
    public void bindViewsListener() {
        mImageView.setOnClickListener(this);
        mSignTv.setOnClickListener(this);
        mAddressTv.setOnClickListener(this);
    }

    @Override
    public void getData() {
        showView();
        startLocation();
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

    private void startLocation() {
        //声明mLocationOption对象
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
        }
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        mLocationOption.setInterval(1000);
        mLocationOption.setLocationCacheEnable(false);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            mLat = aMapLocation.getLatitude() + "";
            mLon = aMapLocation.getLongitude() + "";
            mAddressTv.setText(aMapLocation.getAddress());
            mlocationClient.stopLocation();
        } else {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view:
                File takePhotoDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);
                mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
                try {
                    startActivityForResult(mPhotoHelper.getTakePhotoIntent(), REQUEST_CODE_TAKE_PHOTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.address_tv:
                mlocationClient.stopLocation();
                break;

            case R.id.sign_tv:
                if (TextUtils.isEmpty(mAddressTv.getText().toString())) {
                    showCustomToast("请点击重新定位");
                    return;
                }
                commit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                ArrayList<String> photos = new ArrayList<>(Arrays.asList(mPhotoHelper.getCameraFilePath()));
                if (photos.size() == 0)
                    return;
                File tmepFile = new File(photos.get(0));
                mFile = CompressHelper.getDefault(SignActivity.this).compressToFile(tmepFile);
//                Bitmap imageBitmap = BitmapFactory.decodeFile(photos.get(0));
//                Bitmap timeBitmap = ImageUtil.drawTextToRightBottom(this, imageBitmap, DateUtils.parseDateDayAndMin(System.currentTimeMillis() / 1000 + ""), 11, R.color
//                        .colorAccent, 10, 15);
//                Drawable drawable = new BitmapDrawable(timeBitmap);
//                mImageView.setImageDrawable(drawable);
                Picasso.with(SignActivity.this).load(mFile).into(mImageView);
            }
        }
    }

    public void commit() {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken())
                .addFormDataPart("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid())
                .addFormDataPart("lng", mLon)
                .addFormDataPart("lat", mLat);
        if (mFile != null) {
            requestBody.addFormDataPart("file", mFile.getName(), RequestBody.create(MediaType.parse("image/*"), mFile));
        }

        MultipartBody rb = requestBody.build();
        HttpGetDataUtil.post(SignActivity.this, Constant.SIGN_URL, rb, UserInfoVO.class, SignActivity.this);
    }

    @Override
    public void success(BaseVO vo) {
        showCustomToast(vo.getDesc());
        this.finish();
    }
}
