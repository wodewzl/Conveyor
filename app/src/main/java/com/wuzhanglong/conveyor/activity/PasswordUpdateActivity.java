package com.wuzhanglong.conveyor.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.UserInfoVO;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.BaseCommonUtils;

import java.util.HashMap;

public class PasswordUpdateActivity extends BaseActivity implements View.OnClickListener, PostCallback {
    private EditText mPasswordUpdate01Et, mPasswordUpdate02Et, mPasswordUpdate03Et;
    private TextView mOkTv;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.password_update_activity);
    }

    @Override
    public void initView() {
        mBaseTitleTv.setText("修改密码");
        mPasswordUpdate01Et = getViewById(R.id.pwd_update_01_et);
        mPasswordUpdate02Et = getViewById(R.id.pwd_update_02_et);
        mPasswordUpdate03Et = getViewById(R.id.pwd_update_03_et);
        mOkTv = getViewById(R.id.ok_tv);
        mOkTv.setBackground(BaseCommonUtils.setBackgroundShap(this,5,R.color.conveyor_title,R.color.conveyor_title));
    }

    @Override
    public void bindViewsListener() {
        mOkTv.setOnClickListener(this);

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
            case R.id.ok_tv:
                if (TextUtils.isEmpty(mPasswordUpdate01Et.getText())) {
                    showCustomToast("请输入原始密码");
                    return;
                }

                if (TextUtils.isEmpty(mPasswordUpdate02Et.getText())) {
                    showCustomToast("请输入新密码");
                    return;
                }

                if (TextUtils.isEmpty(mPasswordUpdate03Et.getText())) {
                    showCustomToast("请再次输入密码");
                    return;
                }

                if (!mPasswordUpdate02Et.getText().toString().equals(mPasswordUpdate03Et.getText().toString())) {
                    showCustomToast("输入的两次密码不一致");
                    return;
                }

                HashMap<String, Object> map = new HashMap<>();
                map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
                map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
                map.put("oldpwd", mPasswordUpdate01Et.getText().toString());
                map.put("pwd", mPasswordUpdate02Et.getText().toString());
                map.put("conpwd", mPasswordUpdate03Et.getText().toString());
                HttpGetDataUtil.post(PasswordUpdateActivity.this, Constant.UPDATE_PWD_URL, map, UserInfoVO.class, PasswordUpdateActivity.this);
               showProgressDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void success(BaseVO vo) {
        dismissProgressDialog();
        this.finish();
    }
}
