package com.wuzhanglong.conveyor.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.WorkAdapter;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.DepartVO;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.conveyor.view.PinnedHeaderDecoration;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DateUtils;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class WorkActivity extends BaseActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener {
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private WorkAdapter mAdapter;
    private boolean isLoadMore = true;

    private int mCurrentPage = 1;
    private String mDid = "";
    private String mKeyword = "";
    private String mFirstid = "";
    private String mLastid = "";
    private String mStartDate = "";
    private String mendDate = "";
    private String mIsmyself = "";
    private String mIsToday = "";
    private boolean mFlag = true;


    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.work_activity);
    }

    @Override
    public void initView() {
        mAutoSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mActivity.setSwipeRefreshLayoutColors(mAutoSwipeRefreshLayout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(R.layout.work_adapter_type1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
//        mRecyclerView.addItemDecoration(divider);
//        mAdapter = new WorkAdapter(mRecyclerView);
//        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
//        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setLoadMoreEnabled(false);


        mAdapter = new WorkAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadMoreEnabled(true);
        mBaseTitleTv.setText("工作总汇");
    }

    @Override
    public void bindViewsListener() {

    }

    @Override
    public void getData() {
        if (mFlag) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
            map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
            HttpGetDataUtil.get(WorkActivity.this, Constant.DEPART_URL, map, DepartVO.class);
            mFlag = false;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("did", "");
        map.put("firstid", "");
        map.put("lastid", "");
        map.put("start_date", "");
        map.put("end_date", "");
        map.put("ismyself", "");
        map.put("is_today", "");
        HttpGetDataUtil.get(WorkActivity.this, Constant.WORK_LIST_URL, map, WorkVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        if (vo instanceof DepartVO) {
            DepartVO departVO = (DepartVO) vo;
        } else if (vo instanceof WorkVO) {
            WorkVO workVO = (WorkVO) vo;
            List<WorkVO.DataBean.ListBean> lsit = workVO.getData().getList();
            List<WorkVO.DataBean.ListBean> listBean = new ArrayList<>();
            WorkVO.DataBean.ListBean title = new WorkVO.DataBean.ListBean();
            title.setTime(DateUtils.parseDateDay(lsit.get(0).getTime()));
            title.setIsTitle("1");
            listBean.add(title);
            for (int i = 0; i < lsit.size(); i++) {
                listBean.add(lsit.get(i));
                if(i<lsit.size()-2){
                    if (!DateUtils.parseDateDay(lsit.get(i).getTime()).equals(DateUtils.parseDateDay(lsit.get(i+1).getTime()))) {
                        WorkVO.DataBean.ListBean titleBean = new WorkVO.DataBean.ListBean();
                        titleBean.setTime(DateUtils.parseDateDay(lsit.get(i+1).getTime()));
                        titleBean.setIsTitle("1");
                        listBean.add(titleBean);
                    }
                }

            }
            mAdapter.updateData(listBean);
        }

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


//    // 一级二级都带全部的菜单
//    public ArrayList<TreeVO> getTreeVOList(ArrayList<DepartVO> allList) {
//        ArrayList<TreeVO> treeList = new ArrayList<TreeVO>();
//        for (int i = 0; i < allList.size(); i++) {
//            DepartVO departVO = allList.get(i);
//            TreeVO oneTreeVo = new TreeVO();
//            oneTreeVo.setSearchId(departVO.getProvince_id());
//            oneTreeVo.setParentId(0);
//            oneTreeVo.setId(Integer.parseInt(departVO.getProvince_id()));
//            oneTreeVo.setName(departVO.getProvince_name());
//            oneTreeVo.setLevel(1);
//            if (departVO.getCitys().size() > 0) {
//                oneTreeVo.setHaschild(true);
//            } else {
//                oneTreeVo.setHaschild(false);
//            }
//            treeList.add(oneTreeVo);
//            for (int j = 0; j < oneCityVO.getCitys().size(); j++) {
//                CityVO twoCityVO = oneCityVO.getCitys().get(j);
//                TreeVO twoTreeVo = new TreeVO();
//                twoTreeVo.setSearchId(twoCityVO.getCity_id());
//                twoTreeVo.setName(twoCityVO.getCity_name());
//                twoTreeVo.setLevel(2);
//                twoTreeVo.setParentId(Integer.parseInt(oneCityVO.getProvince_id()));
//                twoTreeVo.setId(Integer.parseInt(twoCityVO.getCity_id()));
//                if (twoCityVO.getDistricts().size() > 0) {
//                    twoTreeVo.setHaschild(true);
//                } else {
//                    twoTreeVo.setHaschild(false);
//                }
//                treeList.add(twoTreeVo);
//                for (int k = 0; k < twoCityVO.getDistricts().size(); k++) {
//                    CityVO threeCityVO = twoCityVO.getDistricts().get(k);
//                    TreeVO threeTreeVo = new TreeVO();
//                    threeTreeVo.setSearchId(threeCityVO.getDistrict_id());
//                    threeTreeVo.setName(threeCityVO.getDistrict_name());
//                    threeTreeVo.setLevel(3);
//                    threeTreeVo.setParentId(Integer.parseInt(twoCityVO.getCity_id()));
//                    threeTreeVo.setId(Integer.parseInt(threeCityVO.getDistrict_id()));
//                    threeTreeVo.setHaschild(false);
//                    treeList.add(threeTreeVo);
//                }
//            }
//        }
//        return treeList;
//    }
}
