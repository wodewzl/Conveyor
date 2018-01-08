package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.library.activity.BaseLogoActivity;
import com.wuzhanglong.library.mode.EBMessageVO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/3/8.
 */

public class LogoActivity extends BaseLogoActivity implements EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_PERMISSIONS = 1;
    private boolean mFlag = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initLogo() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        mLogoImageView.setBackgroundResource(R.drawable.logo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EBMessageVO event) {
        mFlag = true;
        reuestPerm();
//        Intent intent = new Intent();
//        if (AppApplication.getInstance().getUserInfoVO() != null) {
//            intent.setClass(this, MainActivity.class);
//        } else {
//            intent.setClass(this, LoginActivity.class);
//        }
//        startActivity(intent);
//        this.finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @AfterPermissionGranted(REQUEST_PERMISSIONS)
    private void reuestPerm() {
        String[] perms = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (mFlag) {
                mFlag=false;
                Intent intent = new Intent();
                if (AppApplication.getInstance().getUserInfoVO() != null) {
                    intent.setClass(this, MainActivity.class);
                } else {
                    intent.setClass(this, LoginActivity.class);
                }
                startActivity(intent);
                this.finish();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", REQUEST_PERMISSIONS, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }



    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Intent intent = new Intent();
//        if (AppApplication.getInstance().getUserInfoVO() != null) {
//            intent.setClass(this, MainActivity.class);
//        } else {
//            intent.setClass(this, LoginActivity.class);
//        }
//        startActivity(intent);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_PERMISSIONS) {
        }
    }

}
