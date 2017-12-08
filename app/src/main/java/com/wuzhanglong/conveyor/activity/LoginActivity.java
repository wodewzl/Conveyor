package com.wuzhanglong.conveyor.activity;

import android.view.View;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements PostCallback, View.OnClickListener {
    private TextView mOkTv, mPhoneTv, mPasswordTv, mForgetTv;
    private CheckBox mRemberCb;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.login_activity);
    }

    @Override
    public void initView() {
        setTranslanteBar();
        mBaseHeadLayout.setVisibility(View.GONE);
        mPhoneTv = getViewById(R.id.phone_tv);
        mPasswordTv = getViewById(R.id.password_tv);
        mForgetTv = getViewById(R.id.forget_tv);
        mRemberCb = getViewById(R.id.rember_cb);
        mOkTv = getViewById(R.id.ok_tv);

    }

    @Override
    public void bindViewsListener() {
        mOkTv.setOnClickListener(this);
        mPasswordTv.setOnClickListener(this);
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
    public void success(BaseVO vo) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_tv:
                HashMap<String, Object> map = new HashMap<>();
                map.put("username", "13888888888");
                map.put("password", "111111");
                HttpGetDataUtil.post(LoginActivity.this, Constant.LOGIN_URL, map, null, LoginActivity.this);
                break;
            default:
                break;
        }
    }
}
