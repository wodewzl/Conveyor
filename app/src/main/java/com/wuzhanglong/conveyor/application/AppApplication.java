
package com.wuzhanglong.conveyor.application;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wuzhanglong.conveyor.model.UserInfoVO;
import com.wuzhanglong.library.application.BaseAppApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.jpush.android.api.JPushInterface;

public class AppApplication extends Application {
    private static AppApplication mAppApplication;
    {
        PlatformConfig.setWeixin("wx93027a99f78841b5","12f61fc306634e3470d2b61de397296b");
        PlatformConfig.setQQZone("1106607224","FWo7N3LPeqTdpkgV");
//        PlatformConfig.setSinaWeibo("319845988","fbb3df56985bcd557975e1bd8e8d8a1e","http://xiaojingsc.test.beisheng.wang/ht");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
        JPushInterface.init(this);
        UMShareAPI.get(this);//友盟
        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;

    }

    /**
     * 获取Application
     */
    public static AppApplication getInstance() {
        return mAppApplication;
    }


    public UserInfoVO getUserInfoVO() {
        try {
            FileInputStream stream = this.openFileInput("data.UserInfoVO");
            ObjectInputStream ois = new ObjectInputStream(stream);
            UserInfoVO userInfoVO = (UserInfoVO) ois.readObject();
            return userInfoVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveUserInfoVO(UserInfoVO userInfoVO) {
        try {
            FileOutputStream stream = this.openFileOutput("data.UserInfoVO", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(userInfoVO);// td is an Instance of TableData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
