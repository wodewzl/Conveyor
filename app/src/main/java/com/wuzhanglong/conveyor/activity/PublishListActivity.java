package com.wuzhanglong.conveyor.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.PublishListAdapter;
import com.wuzhanglong.conveyor.adapter.SignListAdapter;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.PublishListVO;
import com.wuzhanglong.conveyor.model.PublishListVO;
import com.wuzhanglong.conveyor.view.PinnedHeaderDecoration;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PublishListActivity extends BaseActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private String mFirstid = "";
    private String mLastid = "";
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private PublishListAdapter mAdapter;
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private EditText mSearchEt;
    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.publish_list_activity);
    }

    @Override
    public void initView() {
        mAutoSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mActivity.setSwipeRefreshLayoutColors(mAutoSwipeRefreshLayout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_10, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new PublishListAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void bindViewsListener() {

    }

    @Override
    public void getData() {
        HashMap<String, Object> map = new HashMap<>();
        if (AppApplication.getInstance().getUserInfoVO() != null)
            map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        if (AppApplication.getInstance().getUserInfoVO() != null)
            map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("firstid", mFirstid);
        map.put("lastid", mLastid);

        HttpGetDataUtil.get(PublishListActivity.this, Constant.PUBLISH_LIST_URL, map, PublishListVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        PublishListVO publishListVO= (PublishListVO) vo;
        mAutoSwipeRefreshLayout.setRefreshing(false);
        PublishListVO bean = (PublishListVO) vo;
        if ("300".equals(bean.getCode())) {
            mRecyclerView.setNoMore(true);
            if (mState == 0) {
                mAdapter.updateData(new ArrayList<PublishListVO.DataBean.ListBean>());
            }
            return;
        } else {
            mRecyclerView.setNoMore(false);
        }

        List<PublishListVO.DataBean.ListBean> listBean = bean.getData().getList();
        if (1 == mState) {
            mAdapter.updateDataFrist(listBean);
        } else if (2 == mState) {
            mAdapter.updateDataLast(listBean);
        } else {
            mAdapter.updateData(listBean);
        }

    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }

    public void onRefresh() {
        if (mAdapter.getData().size() > 0) {
            match(1, ((PublishListVO.DataBean.ListBean) mAdapter.getData().get(0)).getId());
        } else {
            match(1, "");
            mState=0;
        }
    }


    @Override
    public void onLoadMore() {
        match(2, ((PublishListVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getId());
    }

    public void match(int key, String value) {
        mLastid = "";
        mFirstid = "";
        mState = 0;
        switch (key) {
            case 0:
                mLastid = "";
                mFirstid = "";
                mState = 0;
                break;
            case 1:
                mFirstid = value;
                mState = 1;
                break;
            case 2:
                mLastid = value;
                mState = 2;
                break;

            case 5:
                break;

            default:
                break;
        }
        getData();
    }
}
