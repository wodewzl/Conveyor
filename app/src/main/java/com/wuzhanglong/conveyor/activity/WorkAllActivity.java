package com.wuzhanglong.conveyor.activity;

import android.widget.EditText;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

public class WorkAllActivity extends BaseActivity  {
    private EditText mContent1Et, mContent2Et, mContent3Et, mContent4Et, mContent5Et;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.work_all_activity);
    }

    @Override
    public void initView() {
        mContent1Et = getViewById(R.id.content1_et);
        mContent2Et = getViewById(R.id.content2_et);
        mContent3Et = getViewById(R.id.content3_et);
        mContent4Et = getViewById(R.id.content4_et);
        mContent5Et = getViewById(R.id.content5_et);
    }

    @Override
    public void bindViewsListener() {

    }

    @Override
    public void getData() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
//        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
//        map.put("content1", mContent1Et.getText().toString());
//        map.put("content2", mContent2Et.getText().toString());
//        if ("1".equals(mType)) {
//            map.put("content3", mContent3Et.getText().toString());
//            map.put("content4", mContent4Et.getText().toString());
//            map.put("content5", mContent5Et.getText().toString());
//            map.put("old_pics", "");
//        }
//
//        for (int i = 0; i < mOneFiles.size(); i++) {
//            map.put("files" + i, mOneFiles.get(i));
//        }
//        map.put("type", mType);
//        HttpGetDataUtil.post(WorkReportActivity.this, Constant.LOGIN_URL, map, UserInfoVO.class, WorkReportActivity.this);
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
}
