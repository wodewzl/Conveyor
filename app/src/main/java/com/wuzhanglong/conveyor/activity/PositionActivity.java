package com.wuzhanglong.conveyor.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.PositionAdapter;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.PositionVO;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class PositionActivity extends BaseActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener, View.OnClickListener, android.widget.TextView.OnEditorActionListener, TextWatcher {
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private PositionAdapter mAdapter;

    private String mKeyword = "";
    private String mFirstid = "";
    private String mLastid = "";
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private EditText mSearchEt;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.activity_position);
    }

    @Override
    public void initView() {
        mBaseTitleTv.setText("塔标");
        mSearchEt = getViewById(R.id.search_et);
        mSearchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mAutoSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mActivity.setSwipeRefreshLayoutColors(mAutoSwipeRefreshLayout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        mAdapter = new PositionAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        BGADivider divider = DividerUtil.bagDividerNoLast(0, 0, mAdapter);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void bindViewsListener() {
        mRecyclerView.setOnLoadMoreListener(this);
        mAutoSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnRVItemClickListener(this);
        mSearchEt.setOnEditorActionListener(this);
        mSearchEt.addTextChangedListener(this);
    }

    @Override
    public void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("firstid", mFirstid);
        map.put("lastid", mLastid);
        map.put("keyword", mKeyword);
        HttpGetDataUtil.get(PositionActivity.this, Constant.POSITION_URL, map, PositionVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        mAutoSwipeRefreshLayout.setRefreshing(false);
        PositionVO positionVO = (PositionVO) vo;

        if ("200".equals(positionVO.getCode())) {
            List<PositionVO.DataBean.ListBean> lsit = positionVO.getData().getList();
            if (1 == mState) {
                mAdapter.updateDataFrist(lsit);
            } else if (2 == mState) {
                mAdapter.updateDataLast(lsit);
            } else {
                mAdapter.updateData(lsit);
            }
            mRecyclerView.setNoMore(false);
        } else if ("300".equals(positionVO.getCode())) {
            mRecyclerView.setNoMore(true);
            if (mState == 0) {
                mAdapter.updateData(new ArrayList<PositionVO.DataBean.ListBean>());
            }
        } else {

        }
    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if ("".equals(s.toString())) {
            mKeyword = "";
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        mKeyword = textView.getText().toString();
        match(3, mKeyword);
        return false;

    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if (mAdapter.getData().size() == 0 || TextUtils.isEmpty(((PositionVO.DataBean.ListBean) mAdapter.getData().get(position)).getSign_id()))
            return;
        Bundle bundle = new Bundle();
        String lat = ((PositionVO.DataBean.ListBean) mAdapter.getData().get(position)).getSign_lat();
        String lng = ((PositionVO.DataBean.ListBean) mAdapter.getData().get(position)).getSign_lng();
        String title = ((PositionVO.DataBean.ListBean) mAdapter.getData().get(position)).getSign_name();
        bundle.putString("lat", lat);
        bundle.putString("lng", lng);
        bundle.putString("title",title );
        bundle.putString("type", "3");

        open(MapActivity.class, bundle, 0);
    }

    @Override
    public void onRefresh() {
        if (mAdapter.getData().size() > 0) {
            match(1, ((PositionVO.DataBean.ListBean) mAdapter.getData().get(0)).getSign_id());
        } else {
            match(0, "");
        }
    }

    @Override
    public void onLoadMore() {
        match(2, ((PositionVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getSign_id());

    }

    public void match(int key, String value) {
        mLastid = "";
        mFirstid = "";
        mState = 0;
        switch (key) {
            case 0:
                mKeyword = "";
                mFirstid = "";
                mLastid = "";
                mState = 0;
                break;
            case 1:
                mFirstid = value;
                mLastid = "";
                mState = 1;
                break;
            case 2:
                mLastid = value;
                mFirstid = "";
                mState = 2;
                break;
            case 3:
                mKeyword = value;
                break;
            default:
                break;
        }
        getData();
    }
}
