package com.wuzhanglong.conveyor.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nanchen.compresshelper.CompressHelper;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.model.WorkAllVO;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.constant.BaseConstant;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.imageloader.BGAImageLoader;
import cn.bingoogolapple.photopicker.util.BGAAsyncTask;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import cn.bingoogolapple.photopicker.util.BGASavePhotoTask;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WorkAllActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate, BGAAsyncTask.Callback<Void>, PostCallback, View.OnClickListener {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private BGASortableNinePhotoLayout mPhotoLayout;
    public ArrayList<String> mOldList = new ArrayList<>();
    public ArrayList<String> mOldLocalList = new ArrayList<>();
    private EditText mContent1Et, mContent2Et, mContent3Et, mContent4Et, mContent5Et;
    private List<File> mFiles = new ArrayList<>();//yiyou
    private BGASavePhotoTask mSavePhotoTask;
    private TextView mTimeTv;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.work_all_activity);
    }

    @Override
    public void initView() {
        mBaseOkTv.setText("提交");
        mBaseTitleTv.setText("工作汇总");
        mTimeTv = getViewById(R.id.time_tv);
        mContent1Et = getViewById(R.id.content1_et);
        mContent2Et = getViewById(R.id.content2_et);
        mContent3Et = getViewById(R.id.content3_et);
        mContent4Et = getViewById(R.id.content4_et);
        mContent5Et = getViewById(R.id.content5_et);
        mPhotoLayout = getViewById(R.id.photo_layout);
        mPhotoLayout.setMaxItemCount(9);
        mPhotoLayout.setEditable(true);//有加号，有删除，可以点加号选择，false没有加号，点其他按钮选择，也没有删除
        mPhotoLayout.setPlusEnable(true);//有加号，可以点加号选择，false没有加号，点其他按钮选择
        mPhotoLayout.setSortable(true);//排序
    }

    @Override
    public void bindViewsListener() {
        mPhotoLayout.setDelegate(this);
        mBaseOkTv.setOnClickListener(this);
    }

    @Override
    public void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        HttpGetDataUtil.get(WorkAllActivity.this, Constant.WORK_ALL_URL, map, WorkAllVO.class);
    }

    @Override
    public void hasData(BaseVO vo) {
        WorkAllVO workAllVO = (WorkAllVO) vo;
        WorkAllVO.DataBean dataBean = workAllVO.getData();
        mTimeTv.setText(dataBean.getDate());
        mContent1Et.setText(dataBean.getSummary_content1());
        mContent2Et.setText(dataBean.getSummary_content2());
        mContent3Et.setText(dataBean.getSummary_content3());
        mContent4Et.setText(dataBean.getSummary_content4());
        mContent5Et.setText(dataBean.getSummary_content5());
        mOldList = (ArrayList<String>) dataBean.getSummary_imgs();
        mPhotoLayout.setData(mOldList);

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < mOldList.size(); i++) {
                    savePic(mOldList.get(i));
                }

            }
        }.start();

    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {

    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper(this, 9, BaseConstant.SDCARD_CACHE);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoLayout.removeItem(position);
        if (mOldLocalList.contains(mPhotoLayout.getData().get(position))) {
            mOldLocalList.remove(position);
        }
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
//                .cameraFileDir(mTakePhotoCb.isChecked() ? takePhotoDir : null) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotoLayout.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的位置
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            //是否单选，单选走true 语句，多选走false语句，这么默认false
//            List<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
            mOldLocalList = BGAPhotoPickerActivity.getSelectedPhotos(data);
            mPhotoLayout.setData(mOldLocalList);
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            // 在预览界面按返回也会回传预览界面已选择的图片集合
//            List<String> selectedPhotos = BGAPhotoPickerPreviewActivity.getSelectedPhotos(data);
//            mPhotoLayout.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
            mOldLocalList = BGAPhotoPickerPreviewActivity.getSelectedPhotos(data);
            mPhotoLayout.setData(mOldLocalList);

        }

        mFiles.clear();
        for (int i = 0; i < mPhotoLayout.getData().size(); i++) {
            File file = new File(mPhotoLayout.getData().get(i));
            File newFile = CompressHelper.getDefault(WorkAllActivity.this).compressToFile(file);
            mFiles.add(newFile);
        }
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper(BaseActivity activity, int maxCount, String file) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), file);

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(activity)
                    .cameraFileDir(TextUtils.isEmpty(file) ? null : takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(9) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false)
                    .selectedPhotos(mOldLocalList)
                    // 滚动列表时是否暂停加载图片
                    .build();
            activity.startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(activity, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    private synchronized void savePic(String url) {
        final File file;
        // 通过MD5加密url生成文件名，避免多次保存同一张图片
        final File downloadDir = new File(Environment.getExternalStorageDirectory(), BaseConstant.SDCARD_CACHE);
        file = new File(downloadDir, BGAPhotoPickerUtil.md5(url) + ".png");
        if (file.exists()) {
//            BGAPhotoPickerUtil.showSafe(getString(cn.bingoogolapple.photopicker.R.string.bga_pp_save_img_success_folder, downloadDir.getAbsolutePath()));
            if (!mOldLocalList.contains(file.getAbsolutePath())) {
                mOldLocalList.add(file.getAbsolutePath());
            }
            if (mOldLocalList.size() == mOldList.size()) {
                WorkAllActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPhotoLayout.setData(mOldLocalList);
                        showCustomToast("下载完了");
                    }
                });

            }
            return;
        }

        mSavePhotoTask = new BGASavePhotoTask(this, WorkAllActivity.this, file);
        BGAImage.download(url, new BGAImageLoader.DownloadDelegate() {
            @Override
            public void onSuccess(String url, Bitmap bitmap) {
                if (!mOldLocalList.contains(file.getAbsolutePath())) {
                    mOldLocalList.add(file.getAbsolutePath());
                }
                if (mOldLocalList.size() == mOldList.size()) {
                    mPhotoLayout.setData(mOldLocalList);
                    showCustomToast("下载完了");
                }
                if (mSavePhotoTask != null) {
                    mSavePhotoTask.setBitmapAndPerform(bitmap);
                }
            }

            @Override
            public void onFailed(String url) {
                mSavePhotoTask = null;
                BGAPhotoPickerUtil.show(cn.bingoogolapple.photopicker.R.string.bga_pp_save_img_failure);
            }
        });
    }

    @Override
    public void onPostExecute(Void aVoid) {
        mSavePhotoTask = null;
    }

    @Override
    public void onTaskCancelled() {
        mSavePhotoTask = null;
    }

    public void commit() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("content1", mContent1Et.getText().toString());
        map.put("content2", mContent2Et.getText().toString());
        map.put("content3", mContent3Et.getText().toString());
        map.put("content4", mContent4Et.getText().toString());
        map.put("content5", mContent5Et.getText().toString());
        map.put("old_pics", "");
        for (int i = 0; i < mFiles.size(); i++) {
            map.put("files" + i, mFiles.get(i));
        }
        map.put("type", "1");
        HttpGetDataUtil.post(WorkAllActivity.this, Constant.WORK_REPORT_URL, map, WorkAllActivity.this);
    }

    @Override
    public void success(BaseVO vo) {
        showCustomToast(vo.getDesc());
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_ok_tv:
                commit();
                break;
            default:
                break;
        }
    }
}
