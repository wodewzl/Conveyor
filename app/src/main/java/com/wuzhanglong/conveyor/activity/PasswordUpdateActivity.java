package com.wuzhanglong.conveyor.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

public class PasswordUpdateActivity extends BaseActivity implements View.OnClickListener {
    private EditText mPasswordUpdate01Et, mPasswordUpdate02Et, mPasswordUpdate03Et;
    private TextView mOkTv;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.password_update_activity);
    }

    @Override
    public void initView() {
        mPasswordUpdate01Et = getViewById(R.id.pwd_update_01_et);
        mPasswordUpdate02Et = getViewById(R.id.pwd_update_02_et);
        mPasswordUpdate03Et = getViewById(R.id.pwd_update_03_et);
        mOkTv = getViewById(R.id.ok_tv);
    }

    @Override
    public void bindViewsListener() {
        mPasswordUpdate01Et.setOnClickListener(this);
        mPasswordUpdate02Et.setOnClickListener(this);
        mPasswordUpdate03Et.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwd_update_01_et:
                break;
            case R.id.pwd_update_02_et:
                break;
            case R.id.pwd_update_03_et:
                break;
            default:
                break;
        }
    }
}
