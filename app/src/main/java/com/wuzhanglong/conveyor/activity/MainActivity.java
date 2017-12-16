package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.nanchen.compresshelper.CompressHelper;
import com.squareup.picasso.Picasso;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.adapter.HomeAdapter;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.WorkVO;
import com.wuzhanglong.conveyor.view.PinnedHeaderDecoration;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.constant.BaseConstant;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.view.AutoSwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements BGAOnRVItemClickListener, OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener , View.OnClickListener, PostCallback {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final int REQUEST_CODE_CROP = 3;
    private DrawerLayout mDrawerLayout;
    private ImageView mHomeHeadImg, mMenuHeadImg;
    private LuRecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ScrollableLayout mScrollableLayout;
    private TextView mHomeTv01, mHomeTv02, mHomeTv03, mHomeTv04, mHomeTv05, mCompanyTv, mNameTv, mDepartTv, mUpdatePwdTv, mMyWorkTv, mAboutTv, mMenuNameTv, mMenuDepartTv, mOutTv;
    private File mHeadImgFile;
    private BGAPhotoHelper mPhotoHelper;

    private String mIsToday = "";
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private String mFirstid = "";
    private String mLastid = "";
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.main_activity);
    }

    @Override
    public void initView() {
        mAutoSwipeRefreshLayout = getViewById(R.id.swipe_refresh_layout);
        mActivity.setSwipeRefreshLayoutColors(mAutoSwipeRefreshLayout);
        mBaseHeadLayout.setVisibility(View.GONE);
        mDrawerLayout = getViewById(R.id.dl_left);
        mUpdatePwdTv = getViewById(R.id.self_tv01);
        mMyWorkTv = getViewById(R.id.self_tv02);
        mAboutTv = getViewById(R.id.self_tv03);
        mMenuHeadImg = getViewById(R.id.menu_head_img);
        mHomeHeadImg = getViewById(R.id.home_head_img);
        mCompanyTv = getViewById(R.id.company_tv);
        mNameTv = getViewById(R.id.name_tv);
        mDepartTv = getViewById(R.id.depart_tv);
        mMenuNameTv = getViewById(R.id.menut_name_tv);
        mMenuDepartTv = getViewById(R.id.menut_depart_tv);
//        mScrollableLayout = getViewById(R.id.scrollable_Layout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mHomeTv01 = getViewById(R.id.tv_home_01);
        mHomeTv02 = getViewById(R.id.tv_home_02);
        mHomeTv03 = getViewById(R.id.tv_home_03);
        mHomeTv04 = getViewById(R.id.tv_home_04);
        mHomeTv05 = getViewById(R.id.tv_home_05);
        mOutTv = getViewById(R.id.out_tv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(R.layout.home_adapter_title, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new HomeAdapter(mRecyclerView);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadMoreEnabled(false);

        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);

        if (AppApplication.getInstance().getUserInfoVO() != null) {
            if (!TextUtils.isEmpty(AppApplication.getInstance().getUserInfoVO().getData().getHeadpic())) {
                Picasso.with(this).load(AppApplication.getInstance().getUserInfoVO().getData().getHeadpic()).placeholder(R.drawable.user_icon_def).into(mHomeHeadImg);
                Picasso.with(this).load(AppApplication.getInstance().getUserInfoVO().getData().getHeadpic()).placeholder(R.drawable.user_icon_def).into(mMenuHeadImg);

            }
            mCompanyTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getFirmcname());
            mNameTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getFullname());
            mMenuNameTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getFullname());
            mDepartTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getDname() + "/" + AppApplication.getInstance().getUserInfoVO().getData().getPname());
            mMenuDepartTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getDname() + "/" + AppApplication.getInstance().getUserInfoVO().getData().getPname());

        }
    }

    @Override
    public void bindViewsListener() {
        mHomeHeadImg.setOnClickListener(this);
//        mHomeTv01.setOnClickListener(this);
//        mHomeTv02.setOnClickListener(this);
//        mHomeTv03.setOnClickListener(this);
//        mHomeTv04.setOnClickListener(this);
//        mHomeTv05.setOnClickListener(this);
        mMenuHeadImg.setOnClickListener(this);
//        mScrollableLayout.getHelper().setCurrentScrollableContainer(this);
        mUpdatePwdTv.setOnClickListener(this);
        mMyWorkTv.setOnClickListener(this);
        mAboutTv.setOnClickListener(this);
        mOutTv.setOnClickListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
        mAutoSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnRVItemClickListener(this);
    }

    @Override
    public void getData() {

        HashMap<String, Object> map = new HashMap<>();
        if (AppApplication.getInstance().getUserInfoVO() != null)
            map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        if (AppApplication.getInstance().getUserInfoVO() != null)

 map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("is_today", "1");
        HttpGetDataUtil.get(MainActivity.this, Constant.WORK_LIST_URL, map, WorkVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        WorkVO workVO = (WorkVO) vo;
        mAutoSwipeRefreshLayout.setRefreshing(false);
        if ("300".equals(workVO.getCode())) {
            mRecyclerView.setNoMore(true);
                List<WorkVO.DataBean.ListBean> listBean = new ArrayList<>();
                WorkVO.DataBean.ListBean homeTitle = new WorkVO.DataBean.ListBean();
                homeTitle.setIsTitle("1");
                listBean.add(homeTitle);
            WorkVO.DataBean.ListBean title = new WorkVO.DataBean.ListBean();
            title.setIsTitle("2");
            listBean.add(title);
                mAdapter.updateData(listBean);
            return;
        } else {
            mRecyclerView.setNoMore(false);
        }
        List<WorkVO.DataBean.ListBean> list = workVO.getData().getList();
        List<WorkVO.DataBean.ListBean> listBean = new ArrayList<>();
        WorkVO.DataBean.ListBean homeTitle = new WorkVO.DataBean.ListBean();
        homeTitle.setIsTitle("1");
        listBean.add(homeTitle);
        WorkVO.DataBean.ListBean title = new WorkVO.DataBean.ListBean();
        title.setIsTitle("2");
        listBean.add(title);
        listBean.addAll(list)
;

        if (1 == mState) {
            mAdapter.getData().remove(0);
            mAdapter.getData().remove(0);
            mAdapter.updateDataFrist(listBean);
        } else if (2 == mState) {
            listBean.remove(0);
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

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.home_head_img:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_home_01:
                intent.setClass(MainActivity.this, WorkActivity.class);
                break;
            case R.id.tv_home_02:
                intent.putExtra("type", "0");
                intent.setClass(MainActivity.this, WorkReportActivity.class);
                break;
            case R.id.tv_home_03:
                intent.setClass(MainActivity.this, ContactActivity.class);
                break;
            case R.id.tv_home_04:
                intent.setClass(MainActivity.this, LoginActivity.class);
                break;
            case R.id.tv_home_05:
                intent.putExtra("type", "1");
                intent.setClass(MainActivity.this, WorkAllActivity.class);
                break;
            case R.id.menu_head_img:
                choicePhotoWrapper(this, 1, BaseConstant.SDCARD_CACHE);
                break;
            case R.id.self_tv01:
                intent.setClass(MainActivity.this, PasswordUpdateActivity.class);

                break;
            case R.id.self_tv02:
                intent.putExtra("type", "1");
                intent.setClass(MainActivity.this, WorkActivity.class);
                break;

            case R.id.self_tv03:
                intent.putExtra("url", AppApplication.getInstance().getUserInfoVO().getData().getAbouts_url());
                intent.setClass(MainActivity.this, WebViewActivity.class);
                break;
            case R.id.out_tv:
                AppApplication.getInstance().saveUserInfoVO(null);
                break;
            default:
                break;
        }
        if (intent.getComponent() != null)
            startActivity(intent);
    }

    @Override
    public void success(BaseVO vo) {

    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper(BaseActivity activity, int maxCount, String file) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(activity)
                    .cameraFileDir(TextUtils.isEmpty(file) ? null : takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(3) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false)
//                    .selectedPhotos(mSelectList)
                    // 滚动列表时是否暂停加载图片
                    .build();
            activity.startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(activity, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_CHOOSE_PHOTO) {
                //是否单选，单选走true 语句，多选走false语句，这么默认false
                List<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
//                File file = new File(selectedPhotos.get(0));
//                mHeadImgFile = CompressHelper.getDefault(MainActivity.this).compressToFile(file);
                try {
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getFilePathFromUri(Uri.parse(selectedPhotos.get(0))), 200, 200), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == RC_PHOTO_PREVIEW) {
                // 在预览界面按返回也会回传预览界面已选择的图片集合
                List<String> selectedPhotos = BGAPhotoPickerPreviewActivity.getSelectedPhotos(data);
                try {
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getCameraFilePath(), 200, 200), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCameraFile();
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
                BGAImage.display(mMenuHeadImg, R.mipmap.bga_pp_ic_holder_light, mPhotoHelper.getCropFilePath(), BGABaseAdapterUtil.dp2px(200));
                File file = new File(mPhotoHelper.getCropFilePath());
                mHeadImgFile = CompressHelper.getDefault(MainActivity.this).compressToFile(file);
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
//                map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
//                map.put("file", mHeadImgFile);
//                HttpGetDataUtil.post(MainActivity.this, Constant.UPLOAD_HEAD_URL, map, MainActivity.this);
                RequestBody requestBody ;
                requestBody=new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ftoken",  AppApplication.getInstance().getUserInfoVO().getData().getFtoken())
                        .addFormDataPart("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid())
                        .addFormDataPart("file", mHeadImgFile.getName(), RequestBody.create(MediaType.parse("image/*"), mHeadImgFile))
                        .build();
                HttpGetDataUtil.post(MainActivity.this, Constant.UPLOAD_HEAD_URL,requestBody,MainActivity.this);

            }
        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();
                mPhotoHelper.deleteCropFile();
            }
        }
    }

    @Override
    public void onRefresh() {
        if(mAdapter.getData().size()>2){
            match(1, ((WorkVO.DataBean.ListBean) mAdapter.getData().get(2)).getLogid());
        }else{
            match(0,"");
        }
    }

    @Override
    public void onLoadMore() {
        match(2, ((WorkVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getLogid());

    }

    public void match(int key, String value) {

        switch (key) {
            case 0:
                mFirstid="";
                mLastid="";
                mState=0;
                break;
            case 1:
                mFirstid = value;
                mLastid="";
                mState = 1;
                break;
            case 2:
                mLastid = value;
                mFirstid="";
                mState = 2;
                break;
            default:
                break;
        }
        getData();
    }
}
