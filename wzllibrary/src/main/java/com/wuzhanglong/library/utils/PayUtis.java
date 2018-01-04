package com.wuzhanglong.library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


import com.wuzhanglong.library.interfaces.PayCallback;
import com.wuzhanglong.library.mode.PayResult;

import java.util.Map;

/**
 * Created by ${Wuzhanglong} on 2017/5/24.
 */

public class PayUtis {
    private static final int SDK_PAY_FLAG = 1;
    private static PayCallback mPayCallback;

    public static void zhiFuBaoPay(final Activity activity, final String payInfo, PayCallback payCallback) {
//        mPayCallback = payCallback;
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(activity);
//                Map<String, String> result = alipay.payV2(payInfo, true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
    }


    static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知
                mPayCallback.payResult(1);
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                mPayCallback.payResult(0);
            }
        }
    };

}
