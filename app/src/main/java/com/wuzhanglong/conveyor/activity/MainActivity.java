package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
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
import com.wuzhanglong.conveyor.model.UpdateVO;
import com.wuzhanglong.conveyor.model.UserInfoVO;
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
import java.util.Set;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import util.UpdateAppUtils;


public class MainActivity extends BaseActivity implements BGAOnRVItemClickListener, OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, PostCallback, ScrollableHelper
        .ScrollableContainer, ScrollableLayout.OnScrollListener {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final int REQUEST_CODE_CROP = 3;
    private DrawerLayout mDrawerLayout;
    private ImageView mHomeHeadImg, mMenuHeadImg;
    private LuRecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ScrollableLayout mScrollableLayout;
    private TextView mHomeTv01, mHomeTv02, mHomeTv03, mHomeTv04, mHomeTv05, mHomeTv06, mhomeTv07, mHomeTv08, mHomeTv09,mHomeTv10, mCompanyTv, mNameTv, mDepartTv, mUpdatePwdTv, mMyWorkTv, mAboutTv,
            mMenuNameTv, mMenuDepartTv, mOutTv;
    private File mHeadImgFile;
    private BGAPhotoHelper mPhotoHelper;

    private String mIsToday = "";
    private int mState = 0; // 0为首次,1为下拉刷新 ，2为加载更多
    private String mFirstid = "";
    private String mLastid = "";
    private AutoSwipeRefreshLayout mAutoSwipeRefreshLayout;
    private LinearLayout mHomeMenuLayout;
    private double mBackPressed;
    private boolean mFlag = true;


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
        mScrollableLayout = getViewById(R.id.scrollable_Layout);
        mRecyclerView = getViewById(R.id.recycler_view);
        mHomeTv01 = getViewById(R.id.tv_home_01);
        mHomeTv02 = getViewById(R.id.tv_home_02);
        mHomeTv03 = getViewById(R.id.tv_home_03);
        mHomeTv04 = getViewById(R.id.tv_home_04);
        mHomeTv05 = getViewById(R.id.tv_home_05);
        mHomeTv06 = getViewById(R.id.tv_home_06);
        mhomeTv07 = getViewById(R.id.tv_home_07);
        mHomeTv08 = getViewById(R.id.tv_home_08);
        mHomeTv09 = getViewById(R.id.tv_home_09);
        mHomeTv10=getViewById(R.id.tv_home_10);
        if ("0".equals(AppApplication.getInstance().getUserInfoVO().getData().getIs_road_sign())) {
            mHomeTv06.setVisibility(View.INVISIBLE);
        } else {
            mHomeTv06.setVisibility(View.VISIBLE);
        }
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
            mDepartTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getDname() + "    " + AppApplication.getInstance().getUserInfoVO().getData().getPname());
            mMenuDepartTv.setText(AppApplication.getInstance().getUserInfoVO().getData().getDname() + "    " + AppApplication.getInstance().getUserInfoVO().getData().getPname());
        }

        mHomeMenuLayout = getViewById(R.id.home_menu_layout);

//        appUpdate();
    }

    @Override
    public void bindViewsListener() {
        mHomeHeadImg.setOnClickListener(this);
        mHomeTv01.setOnClickListener(this);
        mHomeTv02.setOnClickListener(this);
        mHomeTv03.setOnClickListener(this);
        mHomeTv04.setOnClickListener(this);
        mHomeTv05.setOnClickListener(this);
        mHomeTv06.setOnClickListener(this);
        mMenuHeadImg.setOnClickListener(this);
        mScrollableLayout.getHelper().setCurrentScrollableContainer(this);
        mUpdatePwdTv.setOnClickListener(this);
        mMyWorkTv.setOnClickListener(this);
        mAboutTv.setOnClickListener(this);
        mOutTv.setOnClickListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
        mAutoSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnRVItemClickListener(this);
        mhomeTv07.setOnClickListener(this);
        mHomeTv08.setOnClickListener(this);
        mHomeTv09.setOnClickListener(this);
        mHomeTv10.setOnClickListener(this);
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
        map.put("is_today", "1");
        HttpGetDataUtil.get(MainActivity.this, Constant.WORK_LIST_URL, map, WorkVO.class);

        if (mFlag) {
            mFlag = false;
            HttpGetDataUtil.get(MainActivity.this, Constant.APP_UPDATE_URL, map, UpdateVO.class);
        }
    }

    @Override
    public void hasData(BaseVO vo) {
        if (vo instanceof WorkVO) {
            WorkVO workVO = (WorkVO) vo;
            mAutoSwipeRefreshLayout.setRefreshing(false);
            if ("300".equals(workVO.getCode())) {
                mRecyclerView.setNoMore(true);
                if (mState == 0) {
                    List<WorkVO.DataBean.ListBean> listBean = new ArrayList<>();
                    WorkVO.DataBean.ListBean title = new WorkVO.DataBean.ListBean();
                    title.setIsTitle("1");
                    listBean.add(title);
                    mAdapter.updateData(listBean);
                }
                return;
            } else {
                mRecyclerView.setNoMore(false);
            }
            List<WorkVO.DataBean.ListBean> list = workVO.getData().getList();
            List<WorkVO.DataBean.ListBean> listBean = new ArrayList<>();
            WorkVO.DataBean.ListBean title = new WorkVO.DataBean.ListBean();
            title.setIsTitle("1");
            listBean.add(title);
            listBean.addAll(list);
            if (1 == mState) {
                mAdapter.getData().remove(0);
                mAdapter.updateDataFrist(listBean);
            } else if (2 == mState) {
                mAdapter.updateDataLast(listBean);
            } else {
                mAdapter.updateData(listBean);
            }
        } else {
            UpdateVO updateVO = (UpdateVO) vo;
//            AllenVersionChecker
//                    .getInstance()
//                    .downloadOnly(
//                            UIData.create()
//                                    .setDownloadUrl(updateVO.getData()
//                                            .getV_address()).setTitle("更新提示")
//                                    .setContent(((UpdateVO) vo).getData().getV_content())
//                    )
//                    .excuteMission(MainActivity.this);

            UpdateAppUtils.from(this)
                    .serverVersionCode(Integer.parseInt(updateVO.getData().getV_number()))  //服务器versionCode
                    .serverVersionName(updateVO.getData().getV_name()) //服务器versionName
                    .apkPath(updateVO.getData().getV_address()) //最新apk下载地址
                    .isForce(true)
                    .update();

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
        if (mAdapter.getData().size() == 0 || TextUtils.isEmpty(((WorkVO.DataBean.ListBean) mAdapter.getData().get(position)).getLogid()))
            return;
        Bundle bundle = new Bundle();
        String logid = ((WorkVO.DataBean.ListBean) mAdapter.getData().get(position)).getLogid();
        bundle.putString("logid", logid);
        open(WorkDetailActivity.class, bundle, 0);
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
                intent.putExtra("type", "1");
                intent.setClass(MainActivity.this, MapActivity.class);
                break;
            case R.id.tv_home_05:
                intent.putExtra("type", "1");
                intent.setClass(MainActivity.this, WorkAllActivity.class);
                break;
            case R.id.tv_home_06:

                intent.setClass(MainActivity.this, PositionActivity.class);
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
                intent.putExtra("url", AppApplication.getInstance().getUserInfoVO().getData().getAboutus_url());
                intent.putExtra("title", "关于我们");
                intent.setClass(MainActivity.this, WebViewActivity.class);
                break;
            case R.id.out_tv:
                AppApplication.getInstance().saveUserInfoVO(null);
                intent.setClass(MainActivity.this, LoginActivity.class);
                JPushInterface.setAlias(this, "", new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                    }
                });
                break;
            case R.id.tv_home_07:
                intent.setClass(MainActivity.this, SignActivity.class);
                break;
            case R.id.tv_home_08:
                intent.setClass(MainActivity.this, SignListActivity.class);
                break;
            case R.id.tv_home_09:
                intent.setClass(MainActivity.this, PublishActivity.class);
                break;
            case R.id.tv_home_10:
                intent.setClass(MainActivity.this, PublishListActivity.class);
                break;
            default:
                break;
        }
        if (intent.getComponent() != null)
            startActivity(intent);
        if (v.getId() == R.id.out_tv)
            this.finish();

    }

    @Override
    public void success(BaseVO vo) {
        UserInfoVO data = (UserInfoVO) vo;
        UserInfoVO userInfoVO = AppApplication.getInstance().getUserInfoVO();
        userInfoVO.getData().setHeadpic(data.getData().getHeadpic());
        AppApplication.getInstance().saveUserInfoVO(userInfoVO);
        Picasso.with(this).load(AppApplication.getInstance().getUserInfoVO().getData().getHeadpic()).placeholder(R.drawable.user_icon_def).into(mHomeHeadImg);
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper(BaseActivity activity, int maxCount, String file) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(activity)
                    .cameraFileDir(TextUtils.isEmpty(file) ? null : takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(1) // 图片选择张数的最大值
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

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken())
                        .addFormDataPart("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid())
                        .addFormDataPart("file", mHeadImgFile.getName(), RequestBody.create(MediaType.parse("image/*"), mHeadImgFile))
                        .build();
                HttpGetDataUtil.post(MainActivity.this, Constant.UPLOAD_HEAD_URL, requestBody, UserInfoVO.class, MainActivity.this);
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
        if (mAdapter.getData().size() > 2) {
            match(1, ((WorkVO.DataBean.ListBean) mAdapter.getData().get(1)).getLogid());
        } else {
            match(0, "");
        }
    }

    @Override
    public void onLoadMore() {
        match(2, ((WorkVO.DataBean.ListBean) mAdapter.getData().get(mAdapter.getData().size() - 1)).getLogid());

    }


    public void match(int key, String value) {

        switch (key) {
            case 0:
                mFirstid = "";
                mLastid = "";
                mState = 0;
                break;
            case 1:
                mFirstid = value;
                mLastid = "";
                mState = 1;
                break;
            case 2:
                mLastid = value;
                mFirstid = "";
                mState = 2;
                break;
            default:
                break;
        }
        getData();
    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }

    @Override
    public void onScroll(int currentY, int maxY) {
        mHomeMenuLayout.setTranslationY(currentY / 2);
    }

    @Override
    public void onBackPressed() {
        if (isShow()) {
//            dismissProgressDialog();
        } else {
            if (mBackPressed + 3000 > System.currentTimeMillis()) {
                finish();
                super.onBackPressed();
            } else
                showCustomToast("再次点击退出");
            mBackPressed = System.currentTimeMillis();
        }
    }

//    public void appUpdate() {
//        HashMap<String, Object> map = new HashMap<>();
//        if (AppApplication.getInstance().getUserInfoVO() != null)
//            map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
//        if (AppApplication.getInstance().getUserInfoVO() != null)
//            map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
//        AllenVersionChecker
//                .getInstance()
//                .requestVersion()
//                .setRequestUrl(BaseConstant.DOMAIN_NAME + Constant.APP_UPDATE_URL + BaseCommonUtils.getUrl((HashMap<String, Object>) map))
//                .request(new RequestVersionListener() {
//                    @Nullable
//                    @Override
//                    public UIData onRequestVersionSuccess(String result) {
//                        //拿到服务器返回的数据，解析，拿到downloadUrl和一些其他的UI数据
//                        Gson gson = new Gson();
//                        UpdateVO updateVO = (UpdateVO) gson.fromJson(result, UpdateVO.class);
//                        //如果是最新版本直接return null
//                        return UIData.create().setDownloadUrl(updateVO.getData().getV_address());
//                    }
//
//                    @Override
//                    public void onRequestVersionFailure(String message) {
//
//                    }
//                })
//                .excuteMission(MainActivity.this);
//    }

}
