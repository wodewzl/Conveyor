package com.wuzhanglong.conveyor.activity;

import com.google.gson.Gson;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.model.TestVO;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestActivity extends BaseActivity {

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.activity_test);

    }

    @Override
    public void initView() {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("data.txt");
            int size = inputStream.available();
            int len = -1;
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            String cacheStr = new String(bytes);
            Gson gson = new Gson();
            TestVO datavo = gson.fromJson(cacheStr, TestVO.class);
            List<TestVO> one = datavo.getOne();
            List<TestVO> two = datavo.getTwo();
            for (TestVO oneVO : one) {
                for (TestVO twoVO : two) {
                    if((oneVO.getStationId()+"").equals(twoVO.getStationId()+"")){
                        System.out.println(oneVO.getStationName()+"转=======>"+twoVO.getStationName());
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindViewsListener() {

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

}
