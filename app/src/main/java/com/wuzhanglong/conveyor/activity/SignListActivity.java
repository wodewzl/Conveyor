package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.wuzhanglong.conveyor.adapter.SignListAdapter;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.PositionVO;
import com.wuzhanglong.conveyor.model.SignListVO;
import com.wuzhanglong.conveyor.view.PinnedHeaderDecoration;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.constant.BaseConstant;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SignListActivity extends BaseActivity implements BGAOnRVItemClickListener, OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, android.widget.TextView.OnEditorActionListener, TextWatcher {
    private static final int PRC_PHOTO_PICKER = 1;
    private String mKeyword = "";
    private String mFirstid = "";
    private String mLastid = "";
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private SignListAdapter mAdapter;
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private EditText mSearchEt;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.activity_sign_list);
    }

    @Override
    public void initView() {
        mBaseTitleTv.setText("签到记录");
        mSearchEt = getViewById(R.id.search_et);
        mSearchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mAutoSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mActivity.setSwipeRefreshLayoutColors(mAutoSwipeRefreshLayout);
        mRecyclerView = getViewById(R.id.recycler_view);
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(R.layout.work_adapter_type1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_10, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new SignListAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void bindViewsListener() {
        mRecyclerView.setOnLoadMoreListener(this);
        mAutoSwipeRefreshLayout.setOnRefreshListener(this);
        mSearchEt.setOnEditorActionListener(this);
        mSearchEt.addTextChangedListener(this);
        mAdapter.setOnRVItemClickListener(this);
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
        map.put("keyword", mKeyword);
        HttpGetDataUtil.get(SignListActivity.this, Constant.SIGN_LIST_URL, map, SignListVO.class);

    }

    @Override
    public void hasData(BaseVO vo) {
        mAutoSwipeRefreshLayout.setRefreshing(false);
        SignListVO bean = (SignListVO) vo;
        if ("300".equals(bean.getCode())) {
            mRecyclerView.setNoMore(true);
            if (mState == 0) {
                mAdapter.updateData(new ArrayList<SignListVO.DataBean.ListBean>());
            }
            return;
        } else {
            mRecyclerView.setNoMore(false);
        }

        List<SignListVO.DataBean.ListBean> lsit = bean.getData().getList();
        List<SignListVO.DataBean.ListBean> listBean = new ArrayList<>();
        SignListVO.DataBean.ListBean title = new SignListVO.DataBean.ListBean();
        title.setDate(lsit.get(0).getDate());
        title.setIsTitle("1");
        listBean.add(title);
        for (int i = 0; i < lsit.size(); i++) {
            listBean.add(lsit.get(i));
            if (i < lsit.size() - 2) {
                if (!lsit.get(i).getDate().equals(lsit.get(i + 1).getDate())) {
                    SignListVO.DataBean.ListBean titleBean = new SignListVO.DataBean.ListBean();
                    titleBean.setIsTitle("1");
                    titleBean.setDate(lsit.get(i + 1).getDate());
                    listBean.add(titleBean);
                }
            }

        }

        if (1 == mState) {
            if (listBean.get(listBean.size() - 1).getDate().
                    equals(((SignListVO.DataBean.ListBean) mAdapter.getData().get(0)).getDate())) {
                mAdapter.getData().remove(0);
            }
            mAdapter.updateDataFrist(listBean);
        } else if (2 == mState) {
            if (listBean.get(0).getDate().
                    equals(((SignListVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getDate())) {
                listBean.remove(0);
            }
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

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper(String path) {
//        ArrayList<String> list=new ArrayList<>();
//        list.add(path);
//        mPhotoLyout.setData(list);
//        mPhotoLyout.setDelegate(this);
//        path="http://gwhb.work.csongdai.com/Uploads/bs0640/Log/image/20180120/5a62e3a52890a.jpeg";
        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能

            photoPreviewIntentBuilder.previewPhoto(path);
            startActivity(photoPreviewIntentBuilder.build());
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRefresh() {
        if (mAdapter.getData().size() > 0) {
            match(1, ((SignListVO.DataBean.ListBean) mAdapter.getData().get(1)).getCsid());
        } else {
            match(1, "");
            mState = 0;
        }
    }


    @Override
    public void onLoadMore() {
        match(2, ((SignListVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getCsid());
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        mKeyword = textView.getText().toString();
        match(3, mKeyword);
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if ("".equals(s.toString())) {
            mKeyword = "";
            mAutoSwipeRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void match(int key, String value) {
        mLastid = "";
        mFirstid = "";
        mState = 0;
        switch (key) {
            case 0:
                mKeyword = "";
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
            case 3:
                mKeyword = value;
                break;

            case 5:
                break;

            default:
                break;
        }
        getData();
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if (mAdapter.getData().size() == 0 || TextUtils.isEmpty(((SignListVO.DataBean.ListBean) mAdapter.getData().get(position)).getCsid()))
            return;
        Bundle bundle = new Bundle();
        String lat = ((SignListVO.DataBean.ListBean) mAdapter.getData().get(position)).getLat();
        String lng = ((SignListVO.DataBean.ListBean) mAdapter.getData().get(position)).getLng();
        bundle.putString("lat", lat);
        bundle.putString("lng", lng);
        bundle.putString("title", "签到");
        bundle.putString("type", "3");
        open(MapActivity.class, bundle, 0);

    }
}
