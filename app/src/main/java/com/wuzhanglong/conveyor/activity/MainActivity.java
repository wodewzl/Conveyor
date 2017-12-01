package com.wuzhanglong.conveyor.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.HomeAdapter;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;


public class MainActivity extends BaseActivity implements BGAOnRVItemClickListener, ScrollableHelper.ScrollableContainer, View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ImageView mHomeHeadImg;
    private LuRecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ScrollableLayout mScrollableLayout;
    private TextView mMeunTv01, mMeunTv02, mMeunTv03, mMeunTv04, mMeunTv05;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.main_activity);
    }

    @Override
    public void initView() {
        mBaseHeadLayout.setVisibility(View.GONE);
        mDrawerLayout = getViewById(R.id.dl_left);
        mHomeHeadImg = getViewById(R.id.home_head_img);
        mScrollableLayout = getViewById(R.id.scrollable_Layout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mScrollableLayout.getHelper().setCurrentScrollableContainer(this);

        mMeunTv01 = getViewById(R.id.tv_menu_01);
        mMeunTv02 = getViewById(R.id.tv_menu_02);
        mMeunTv03 = getViewById(R.id.tv_menu_03);
        mMeunTv04 = getViewById(R.id.tv_menu_04);
        mMeunTv05 = getViewById(R.id.tv_menu_05);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new HomeAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
//        adapter.addHeaderView(initHeadView());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadMoreEnabled(false);
    }

    @Override
    public void bindViewsListener() {
        mHomeHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mDrawerLayout.openDrawer(GravityCompat.START);

                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,WorkReportActivity.class);

                intent.setClass(MainActivity.this, WorkReportActivity.class);

                MainActivity.this.startActivity(intent);
            }
        });
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
    public View getScrollableView() {
        return null;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_menu_01:
                intent.setClass(MainActivity.this, WorkActivity.class);
                break;
            case R.id.tv_menu_02:
                intent.setClass(MainActivity.this, WorkReportActivity.class);
                break;
            case R.id.tv_menu_03:
                intent.setClass(MainActivity.this, ContactActivity.class);
                break;
            case R.id.tv_menu_04:
                break;
            case R.id.tv_menu_05:
                break;
            default:
                break;
        }
        if (intent.getComponent() != null)
            openActivity(ContactActivity.class);
    }
}
