package com.wuzhanglong.conveyor.activity;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.wuzhanglong.library.utils.BaseCommonUtils;

import java.util.ArrayList;
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
    private EditText mSeacherEt;
    private ContanctVO mContanctVO;
    private List<ContanctVO.DataBean> mList = new ArrayList<>();

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.contact_activity);
    }

    @Override
    public void initView() {

        mBaseTitleTv.setText("通讯录");
        mDataRv = getViewById(R.id.rv_sticky_data);
        mIndexView = getViewById(R.id.iv_sticky_index);
        mTipTv = getViewById(R.id.tv_sticky_tip);
        mSeacherEt = getViewById(R.id.search_et);
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
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                if (mAdapter.getData().size() == 0)
                    return;
                ContanctVO.DataBean vo = mAdapter.getData().get(position);

                if (!TextUtils.isEmpty(vo.getTel()))
                    BaseCommonUtils.call(ContactActivity.this, vo.getTel());
            }
        });

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

        mSeacherEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s + "")) {
                    mList.clear();
                    mList.addAll(mContanctVO.getData());
                    mAdapter.setData(loadDataBeanData(mList));
                } else {
                    filterData(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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
        mContanctVO = (ContanctVO) vo;
        mList.addAll(mContanctVO.getData());
        mAdapter.setData(loadDataBeanData(mList));
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
                mCategoryBackgroundColor = getResources().getColor(R.color.conveyor_bg1);
                mCategoryTextColor = getResources().getColor(R.color.C5);
                mCategoryTextSize = getResources().getDimensionPixelOffset(R.dimen.sp_12);
                mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.dp_15);
                mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.dp_30);
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
                .setColorResource(R.color.conveyor_bg1, true)
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

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContanctVO.DataBean> filterDateList = new ArrayList<ContanctVO.DataBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mList;
        } else {
            filterDateList.clear();
            for (ContanctVO.DataBean personnelVO : mList) {
                String name = personnelVO.getFullname();
                if (name.indexOf(filterStr.toString()) != -1 || CharacterParser.getInstance().getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(personnelVO);
                }
            }
        }

        // 根据a-z进行排序
        PinyinComparator pinyinComparator = new PinyinComparator();
        Collections.sort(filterDateList, pinyinComparator);
        mAdapter.setData(filterDateList);
    }
}
