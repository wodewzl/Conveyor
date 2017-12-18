package com.wuzhanglong.conveyor.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.library.activity.BaseLogoActivity;
import com.wuzhanglong.library.mode.EBMessageVO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/3/8.
 */

public class LogoActivity extends BaseLogoActivity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initLogo() {
        EventBus.getDefault().register(this);
        mLogoImageView.setBackgroundResource(R.drawable.logo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EBMessageVO event) {
        if ("guide".equals(event.getMessage())) {
            Intent intent = new Intent();
            if (AppApplication.getInstance().getUserInfoVO() != null) {
                intent.setClass(this, MainActivity.class);
            } else {
                intent.setClass(this, LoginActivity.class);
            }
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
