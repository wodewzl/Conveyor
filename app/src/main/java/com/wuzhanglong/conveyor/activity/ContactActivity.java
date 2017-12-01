package com.wuzhanglong.conveyor.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.RvStickyAdapter;
import com.wuzhanglong.conveyor.model.IndexModel;
import com.wuzhanglong.conveyor.util.CharacterParser;
import com.wuzhanglong.conveyor.util.PinyinComparator;
import com.wuzhanglong.conveyor.view.IndexView;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.mode.BaseVO;

import java.util.ArrayList;
import java.util.Collections;
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
        loadIndexModelData();

        mIndexView.setTipTv(mTipTv);

        mAdapter.setData(loadIndexModelData());
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

    private void initStickyDivider() {

        final BGADivider.StickyDelegate stickyDelegate = new BGADivider.StickyDelegate() {
            @Override
            public void initCategoryAttr() {
//                mCategoryBackgroundColor = getResources().getColor(R.color.category_backgroundColor);
//                mCategoryTextColor = getResources().getColor(R.color.category_textColor);
//                mCategoryTextSize = getResources().getDimensionPixelOffset(R.dimen.textSize_16);
//                mCategoryPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.size_level4);
//                mCategoryHeight = getResources().getDimensionPixelOffset(R.dimen.size_level10);
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

        mDataRv.addItemDecoration(BGADivider.newDrawableDivider(R.drawable.shape_divider)
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.size_level3)
                .setMarginRightResource(R.dimen.size_level9)
                .setDelegate(stickyDelegate));

        mRecyclerViewScrollHelper = BGARVVerticalScrollHelper.newInstance(mDataRv, new BGARVVerticalScrollHelper.SimpleDelegate() {
            @Override
            public int getCategoryHeight() {
                return stickyDelegate.getCategoryHeight();
            }
        });
    }


    public static List<IndexModel> loadIndexModelData() {
        List<IndexModel> data = new ArrayList<>();
        data.add(new IndexModel("安阳"));
        data.add(new IndexModel("鞍山"));
        data.add(new IndexModel("保定"));
        data.add(new IndexModel("包头"));
        data.add(new IndexModel("北京"));
        data.add(new IndexModel("河北"));
        data.add(new IndexModel("北海"));
        data.add(new IndexModel("安庆"));
        data.add(new IndexModel("伊春"));
        data.add(new IndexModel("宝鸡"));
        data.add(new IndexModel("本兮"));
        data.add(new IndexModel("滨州"));
        data.add(new IndexModel("常州"));
        data.add(new IndexModel("常德"));
        data.add(new IndexModel("常熟"));
        data.add(new IndexModel("枣庄"));
        data.add(new IndexModel("内江"));
        data.add(new IndexModel("阿坝州"));
        data.add(new IndexModel("丽水"));
        data.add(new IndexModel("成都"));
        data.add(new IndexModel("承德"));
        data.add(new IndexModel("沧州"));
        data.add(new IndexModel("重庆"));
        data.add(new IndexModel("东莞"));
        data.add(new IndexModel("扬州"));
        data.add(new IndexModel("甘南州"));
        data.add(new IndexModel("大庆"));
        data.add(new IndexModel("佛山"));
        data.add(new IndexModel("广州"));
        data.add(new IndexModel("合肥"));
        data.add(new IndexModel("海口"));
        data.add(new IndexModel("济南"));
        data.add(new IndexModel("兰州"));
        data.add(new IndexModel("南京"));
        data.add(new IndexModel("泉州"));
        data.add(new IndexModel("荣成"));
        data.add(new IndexModel("三亚"));
        data.add(new IndexModel("上海"));
        data.add(new IndexModel("汕头"));
        data.add(new IndexModel("天津"));
        data.add(new IndexModel("武汉"));
        data.add(new IndexModel("厦门"));
        data.add(new IndexModel("宜宾"));
        data.add(new IndexModel("张家界"));
        data.add(new IndexModel("自贡"));


        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : data) {
            indexModel.topc = characterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
            if (indexModel.name.equals("重庆")) {
                indexModel.topc = "C";
            }
        }
        Collections.sort(data, pinyinComparator);
        return data;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }
}
