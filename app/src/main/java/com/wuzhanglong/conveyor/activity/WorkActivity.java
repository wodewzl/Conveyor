package com.wuzhanglong.conveyor.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
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
import com.wuzhanglong.library.mode.TreeVO;
import com.wuzhanglong.library.utils.DateUtils;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;
import com.wuzhanglong.library.view.BSPopupWindowsTitle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class WorkActivity extends BaseActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BGAOnRVItemClickListener, View.OnClickListener,android.widget.TextView.OnEditorActionListener,TextWatcher
{
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private WorkAdapter mAdapter;
    private boolean isLoadMore = true;


    private String mDid = "";
    private String mKeyword = "";
    private String mFirstid = "";
    private String mLastid = "";
    private String mStartDate = "";
    private String mEndDate = "";
    private String mIsmyself = "";
    private boolean mFlag = true;
    private TextView mOptions1Tv, mOptions2Tv;
    private List<DepartVO.DataBean.ListBean> mListBeans;
    private BSPopupWindowsTitle mDepartPop,mDatePickPop;
    private View mDividerView;
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private LinearLayout mTitleLayout;
    private String mType = "1";//1自己的，
    private EditText mSearchEt;
    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.work_activity);
    }

    @Override
    public void initView() {
        mSearchEt = getViewById(R.id.search_et);
        mSearchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mTitleLayout = getViewById(R.id.title_layout);
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

        mAdapter = new WorkAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadMoreEnabled(true);
        mBaseTitleTv.setText("工作总汇");
        mOptions1Tv = getViewById(R.id.options1_tv);
        mOptions2Tv = getViewById(R.id.options2_tv);
        mDividerView = getViewById(R.id.divider);
        mType = this.getIntent().getStringExtra("type");
        if ("1".equals(mType)) {
            mIsmyself = "1";//
            mTitleLayout.setVisibility(View.GONE);
        } else {
            mIsmyself = "";
            mTitleLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bindViewsListener() {
        mOptions1Tv.setOnClickListener(this);
        mOptions2Tv.setOnClickListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
        mAutoSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnRVItemClickListener(this);
        mSearchEt.setOnEditorActionListener(this);
        mSearchEt.addTextChangedListener(this);
    }

    @Override
    public void getData() {
        if (mFlag && !"1".equals(mType)) {
            HashMap<String, Object> departMap = new HashMap<>();
            departMap.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
            departMap.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());

            HttpGetDataUtil.get(WorkActivity.this, Constant.DEPART_URL, departMap, DepartVO.class);
            mFlag = false;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
//        map.put("userid", "6");
        map.put("did", mDid);
        map.put("firstid", mFirstid);
        map.put("lastid", mLastid);
        map.put("start_date", mStartDate);
        map.put("end_date", mEndDate);
        map.put("ismyself", mIsmyself);
        map.put("keyword", mKeyword);
//        map.put("is_today", "");
        HttpGetDataUtil.get(WorkActivity.this, Constant.WORK_LIST_URL, map, WorkVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        if (vo instanceof DepartVO) {
            DepartVO departVO = (DepartVO) vo;
            mListBeans = departVO.getData().getList();
        } else if (vo instanceof WorkVO) {
            mAutoSwipeRefreshLayout.setRefreshing(false);
            WorkVO workVO = (WorkVO) vo;
            if ("300".equals(workVO.getCode())) {
                mRecyclerView.setNoMore(true);
                if(mState==0){
                    mAdapter.updateData(new ArrayList<WorkVO.DataBean.ListBean>());
                }
                return;
            } else {
                mRecyclerView.setNoMore(false);
            }

            List<WorkVO.DataBean.ListBean> lsit = workVO.getData().getList();
            List<WorkVO.DataBean.ListBean> listBean = new ArrayList<>();
            WorkVO.DataBean.ListBean title = new WorkVO.DataBean.ListBean();
            title.setTime(lsit.get(0).getTime());
            title.setDate_week(lsit.get(0).getDate_week());
            title.setIsTitle("1");
            listBean.add(title);
            for (int i = 0; i < lsit.size(); i++) {
                listBean.add(lsit.get(i));
                if (i < lsit.size() - 2) {
                    if (!DateUtils.parseDateDay(lsit.get(i).getTime()).equals(DateUtils.parseDateDay(lsit.get(i + 1).getTime()))) {
                        WorkVO.DataBean.ListBean titleBean = new WorkVO.DataBean.ListBean();
                        titleBean.setTime(lsit.get(i + 1).getTime());
                        titleBean.setIsTitle("1");
                        titleBean.setDate_week(lsit.get(i + 1).getDate_week());
                        listBean.add(titleBean);
                    }
                }

            }
//            mAdapter.updateData(listBean);
            if (1 == mState) {
                if (DateUtils.parseDateDay(listBean.get(listBean.size() - 1).getTime()).
                        equals(DateUtils.parseDateDay(((WorkVO.DataBean.ListBean) mAdapter.getData().get(0)).getTime()))) {
                    mAdapter.getData().remove(0);
                }
                mAdapter.updateDataFrist(listBean);
            } else if (2 == mState) {
                if (DateUtils.parseDateDay(listBean.get(0).getTime()).
                        equals(DateUtils.parseDateDay(((WorkVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getTime()))) {
                    listBean.remove(0);
                }
                mAdapter.updateDataLast(listBean);

            } else {
                mAdapter.updateData(listBean);
            }
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
        if(mAdapter.getData().size()>0){
            match(1, ((WorkVO.DataBean.ListBean) mAdapter.getData().get(1)).getLogid());
        }else{
            match(0,"");
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if(mAdapter.getData().size()==0|| TextUtils.isEmpty(((WorkVO.DataBean.ListBean)mAdapter.getData().get(position)).getLogid()))
            return;;
        Bundle bundle=new Bundle();
                String logid= ((WorkVO.DataBean.ListBean)mAdapter.getData().get(position)).getLogid();
        bundle.putString("logid",logid);
        open(WorkDetailActivity.class,bundle,0);;
    }

    @Override
    public void onLoadMore() {
        match(2, ((WorkVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getLogid());
    }


    // 一级二级都带全部的菜单
    public static ArrayList<TreeVO> getTreeVOList(List<DepartVO.DataBean.ListBean> listBeans) {
        ArrayList<TreeVO> list = new ArrayList<TreeVO>();
        TreeVO allTreeVo = new TreeVO();
        allTreeVo.setId(-1);
        allTreeVo.setParentId(0);
        allTreeVo.setName("全部");
        allTreeVo.setLevel(1);
        allTreeVo.setHaschild(false);
        allTreeVo.setDepartmentid("-1");
        allTreeVo.setDname("全部");
        list.add(allTreeVo);


        // 每个二级菜单添加一个全部，为了让全部排在第一，故拿出来重新写了一遍
        for (int i = 0; i < list.size(); i++) {
            DepartVO.DataBean.ListBean vo = listBeans.get(i);
            if ("0".equals(vo.getBelong())) {
                TreeVO childTreeVo = new TreeVO();
                childTreeVo.setId(Integer.parseInt(vo.getDepartmentid()));
                childTreeVo.setParentId(Integer.parseInt(vo.getDepartmentid()));
                childTreeVo.setName("全部");
                childTreeVo.setLevel(2);
                childTreeVo.setHaschild(false);
                childTreeVo.setDepartmentid(vo.getDepartmentid());
                childTreeVo.setDname(vo.getDname());
                list.add(childTreeVo);
            }
        }
        for (int i = 0; i < listBeans.size(); i++) {
            DepartVO.DataBean.ListBean vo = listBeans.get(i);
            for (int j = 0; j < listBeans.size(); j++) {
                if (vo.getDepartmentid().equals(listBeans.get(j).getBelong())) {
                    vo.setHaschild(true);
                    break;
                } else {
                    vo.setHaschild(false);
                }
            }

            TreeVO treeVo = new TreeVO();
            treeVo.setId(Integer.parseInt(vo.getDepartmentid()));
            treeVo.setParentId(Integer.parseInt(vo.getBelong()));
            treeVo.setName(vo.getDname());
            treeVo.setLevel(Integer.parseInt(vo.getLevel()));
            treeVo.setHaschild(vo.isHaschild());
            treeVo.setDepartmentid(vo.getDepartmentid());
            treeVo.setDname(vo.getDname());
            list.add(treeVo);
        }
        return list;
    }

    // 菜单点击回调函数
    BSPopupWindowsTitle.TreeCallBack callback = new BSPopupWindowsTitle.TreeCallBack() {
        @Override
        public void callback(TreeVO vo) {

            if (vo.getDepartmentid() != null) {
                mDid = vo.getDepartmentid();
                // -1代表全部部门
                if ("-1".equals(mDid)) {
                    mDid = "";
                }
                match(4, mDid);
                mOptions1Tv.setText(vo.getDname());
            } else {
//                match(3, vo.getSearchId());

            }
        }
    };

    BSPopupWindowsTitle.StringCallBack pickCallback=new BSPopupWindowsTitle.StringCallBack() {
        @Override
        public void callback(String star, String end) {
            mStartDate=star;
            mEndDate=end;
            getData();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.options1_tv:
                if (mDepartPop == null) {
                    ArrayList<TreeVO> listDepart = getTreeVOList(mListBeans);
                    mDepartPop = new BSPopupWindowsTitle(mActivity, listDepart, callback);
                }
                    mDepartPop.showPopupWindow(mDividerView);
                break;
            case R.id.options2_tv:

                if (mDatePickPop == null) {
                    View view = LayoutInflater.from(this).inflate(R.layout.pick_date_pop, (ViewGroup) findViewById(android.R.id.content), false);

                    mDatePickPop = new BSPopupWindowsTitle(mActivity, view, pickCallback);
                }
                mDatePickPop.showPopupWindow(mDividerView);

                break;
            default:
                break;
        }
    }

    public void match(int key, String value) {
        if (!"1".equals(mType)) {
            mIsmyself = "";
        }
        mLastid = "";
        mFirstid = "";
        mState=0;
        switch (key) {
            case 0:
                mKeyword="";
                mLastid = "";
                mFirstid = "";
                mState=0;
                mDid="";
                mStartDate="";
                mEndDate="";
                break;
            case 1:
                mFirstid = value;
                mState = 1;
                break;
            case 2:
                mLastid = value;;
                mState = 2;
                break;
            case 3:

                mKeyword = value;
                break;
            case 4:
                mDid = value;
                break;
            case 5:
                mStartDate = value;
                break;
            case 6:
                mEndDate = value;
                break;
            default:
                break;
        }
        getData();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        mKeyword = textView.getText().toString();
        match(3,mKeyword);
        return false;
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



}
