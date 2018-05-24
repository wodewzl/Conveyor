package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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
import com.wuzhanglong.conveyor.service.PlayService;
import com.wuzhanglong.conveyor.util.AppCache;
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

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.jzvd.JZVideoPlayer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PublishListActivity extends BaseActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, android.widget.TextView.OnEditorActionListener, TextWatcher {
    private static final int PRC_PHOTO_PICKER = 1;
    private String mKeyword = "";
    private String mFirstid = "";
    private String mLastid = "";
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private PublishListAdapter mAdapter;
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private EditText mSearchEt;
    private PlayServiceConnection mPlayServiceConnection;
    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.publish_list_activity);
    }

    @Override
    public void initView() {
        mBaseTitleTv.setText("音视频");
        mSearchEt = getViewById(R.id.search_et);
        mSearchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
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

        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void bindViewsListener() {
        mRecyclerView.setOnLoadMoreListener(this);
        mAutoSwipeRefreshLayout.setOnRefreshListener(this);
        mSearchEt.setOnEditorActionListener(this);
        mSearchEt.addTextChangedListener(this);
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
        HttpGetDataUtil.get(PublishListActivity.this, Constant.PUBLISH_LIST_URL, map, PublishListVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        PublishListVO publishListVO = (PublishListVO) vo;
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
            mState = 0;
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
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
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

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper(String path) {
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

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            Log.e("onServiceConnected----", "onServiceConnected");
            AppCache.setPlayService(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }
}
