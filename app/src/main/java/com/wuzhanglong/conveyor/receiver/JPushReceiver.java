
package com.wuzhanglong.conveyor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuzhanglong.conveyor.activity.MapActivity;
import com.wuzhanglong.conveyor.activity.WebViewActivity;
import com.wuzhanglong.conveyor.activity.WorkDetailActivity;
import com.wuzhanglong.library.utils.SharePreferenceUtil;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(extra, new TypeToken<Map<String, String>>() {
        }.getType());

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            // 打开自定义的Activity

            String id = map.get("detailid");
            Intent detailIntent = new Intent();
            detailIntent.setClass(context, MapActivity.class);
            detailIntent.setClass(context, WorkDetailActivity.class);
            detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            detailIntent.putExtra("logid", id);
            context.startActivity(detailIntent);
        }
    }

}
