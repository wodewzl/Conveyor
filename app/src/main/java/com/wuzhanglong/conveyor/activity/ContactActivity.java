package com.wuzhanglong.conveyor.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.RvStickyAdapter;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.ContanctVO;
import com.wuzhanglong.conveyor.util.CharacterParser;
import com.wuzhanglong.conveyor.util.PinyinComparator;
import com.wuzhanglong.conveyor.view.IndexView;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.mode.BaseVO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARVVerticalScrollHelper;

public class ContactActivity extends BaseActivity implements BGAOnRVItemClickListener {
    private RvStickyAdapter mAdapter;
    private RecyclerView mDataRv;
    private IndexView mIndexView;
    private TextView mTipTv;
    private BGARVVerticalScrollHelper mRecyclerViewScrollHelper;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.contact_activity);
    }

    @Override
    public void initView() {
        mDataRv = getViewById(R.id.rv_sticky_data);
        mIndexView = getViewById(R.id.iv_sticky_index);
        mTipTv = getViewById(R.id.tv_sticky_tip);
    }

    @Override
    public void bindViewsListener() {
        mAdapter = new RvStickyAdapter(mDataRv);
        mAdapter.setOnRVItemClickListener(this);


        initStickyDivider();
//        loadIndexModelData();

        mIndexView.setTipTv(mTipTv);

//        mAdapter.setData(loadIndexModelData());
        mDataRv.setAdapter(mAdapter);

        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
//                    mLayoutManager.scrollToPosition(position);

                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
//                    mRecyclerViewScrollHelper.scrollToPosition(position);
                }
            }
        });
    }

    @Override
    public void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        HttpGetDataUtil.get(ContactActivity.this, Constant.CONTACT_URL, map, ContanctVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        ContanctVO contanctVO = (ContanctVO) vo;
        List<ContanctVO.DataBean> lsit = contanctVO.getData();
        mAdapter.setData(loadDataBeanData(lsit));
    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }

    private void initStickyDivider() {

        final BGADivider.StickyDelegate stickyDelegate = new BGADivider.StickyDelegate() {
            @Override
            public void initCategoryAttr() {
                mCategoryBackgroundColor = getResources().getColor(R.color.C7);
                mCategoryTextColor = getResources().getColor(R.color.C5);
                mCategoryTextSize = getResources().getDimensionPixelOffset(R.dimen.sp_12);
                mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.dp_15);
                mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.dp_20);
            }

            @Override
            protected boolean isCategoryFistItem(int position) {
                return mAdapter.isCategoryFistItem(position);
            }

            @Override
            protected String getCategoryName(int position) {
                return mAdapter.getItem(position).topc;
            }

            @Override
            protected int getFirstVisibleItemPosition() {
                return mRecyclerViewScrollHelper.findFirstVisibleItemPosition();
            }
        };

        mDataRv.addItemDecoration(BGADivider.newShapeDivider()
                .setColorResource(R.color.C9, true)
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.dp_15)
                .setMarginRightResource(R.dimen.dp_15)
                .setDelegate(stickyDelegate)
        );

        mRecyclerViewScrollHelper = BGARVVerticalScrollHelper.newInstance(mDataRv, new BGARVVerticalScrollHelper.SimpleDelegate() {
            @Override
            public int getCategoryHeight() {
                return stickyDelegate.getCategoryHeight();
            }
        });
    }


    public List<ContanctVO.DataBean> loadDataBeanData(List<ContanctVO.DataBean> data) {

        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (ContanctVO.DataBean dataBean : data) {
            dataBean.topc = characterParser.getSelling(dataBean.getFullname()).substring(0, 1).toUpperCase();
            if (dataBean.getFullname().equals("重庆")) {
                dataBean.topc = "C";
            }
        }
        Collections.sort(data, pinyinComparator);
        return data;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }
}
