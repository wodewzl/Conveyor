package com.wuzhanglong.conveyor.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.WorkAllAdapter;
import com.wuzhanglong.conveyor.model.HomeVO;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class WorkAllActivity extends BaseActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener {
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private WorkAllAdapter mAdapter;
    private int mCurrentPage = 1;
    private boolean isLoadMore = true;
    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.work_all_activity);
    }

    @Override
    public void initView() {
        mAutoSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mActivity.setSwipeRefreshLayoutColors(mAutoSwipeRefreshLayout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new WorkAllAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadMoreEnabled(true);
        mBaseTitleTv.setText("工作总汇");
        List<HomeVO> list = new ArrayList<>();
        mAdapter.updateDataLast(list);
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
        List<HomeVO> list = new ArrayList<>();
        mAdapter.updateDataLast(list);
    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }

    @Override
    public void onLoadMore() {

    }
}
