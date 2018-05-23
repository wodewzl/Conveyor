package com.wuzhanglong.conveyor.activity;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilike.voicerecorder.utils.PathUtil;
import com.ilike.voicerecorder.widget.VoiceRecorderView;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.conveyor.util.AppCache;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.mode.EBMessageVO;
import com.wuzhanglong.library.utils.BaseCommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate, View.OnClickListener, PostCallback {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private BGASortableNinePhotoLayout mPhotoLayout;
    private ArrayList<String> mPath = new ArrayList<>();
    private ArrayList<File> mFileList = new ArrayList<>();
    private TextView mVoiceTv, mVoiceTimeTv, mRemarkTv,mOkTv;
    protected VoiceRecorderView voiceRecorderView;
    private LinearLayout mVoiceLayout;
    private File mVoiceFile;
    private ImageView mVoiceImg;

    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.publish_activity);
    }

    @Override
    public void initView() {
        mPhotoLayout = getViewById(R.id.photo_layout);

        mPhotoLayout.setMaxItemCount(9);
        mPhotoLayout.setEditable(true);//有加号，有删除，可以点加号选择，false没有加号，点其他按钮选择，也没有删除
        mPhotoLayout.setPlusEnable(true);//有加号，可以点加号选择，false没有加号，点其他按钮选择
        mPhotoLayout.setSortable(true);//排序
        mVoiceTv = getViewById(R.id.voice_tv);
        mVoiceTv.setBackground(BaseCommonUtils.setBackgroundShap(this, 5, R.color.colorAccent, R.color.colorAccent));

        voiceRecorderView = (VoiceRecorderView) findViewById(R.id.voice_recorder);
        voiceRecorderView.setShowMoveUpToCancelHint("松开手指，取消发送");
        voiceRecorderView.setShowReleaseToCancelHint("手指上滑，取消发送");
        mVoiceLayout = getViewById(R.id.voice_layout);
        mVoiceTimeTv = getViewById(R.id.time_tv);
        mVoiceImg = getViewById(R.id.voice_img);
        mRemarkTv = getViewById(R.id.mark_tv);
        mOkTv=getViewById(R.id.ok_tv);
        mOkTv.setBackground(BaseCommonUtils.setBackgroundShap(this, 5, R.color.colorAccent, R.color.colorAccent));

    }

    @Override
    public void bindViewsListener() {
        mPhotoLayout.setDelegate(this);
        EventBus.getDefault().register(this);
        mVoiceImg.setOnClickListener(this);
        mOkTv.setOnClickListener(this);
        mVoiceTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (AppCache.getPlayService().isPlaying) {
                        AppCache.getPlayService().stopPlayVoiceAnimation2();
                    }
                }

                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new VoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        Log.e("voiceFilePath=", voiceFilePath + "  time = " + voiceTimeLength);
                        //   sendVoiceMessage(voiceFilePath, voiceTimeLength);
                        mVoiceFile = new File(voiceFilePath);
                        mVoiceTimeTv.setText(voiceTimeLength + "s");
                        mVoiceLayout.setVisibility(View.VISIBLE);
                    }
                });
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EBMessageVO event) {
        if ("pic".equals(event.getMessage())) {
            mPath.add(event.getMsg());
            mFileList.add(new File(event.getMsg()));
        } else if ("video".equals(event.getMessage())) {
            String[] strArr = event.getParams();
            mPath.add(strArr[1]);
            mFileList.add(new File(strArr[0]));
        }

        mPhotoLayout.setData(mPath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice_tv:
                PathUtil.getInstance().createDirs("chat", "voice", this);
                break;

            case R.id.voice_img:
                if (AppCache.getPlayService() != null) {
//                    AppCache.getPlayService().setImageView(imageView);
//                    AppCache.getPlayService().stopPlayVoiceAnimation();
                    //  AppCache.getPlayService().play("http://img.layuva.com//b96c4bde124a328d9c6edb5b7d51afb2.amr");
                    AppCache.getPlayService().play(mVoiceFile.getPath());
                }
                break;
            case R.id.ok_tv:
                if(mFileList.size()==0){
                    showCustomToast("请拍照或拍视频");
                    return;
                }
                commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        openActivity(CameraActivity.class);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoLayout.removeItem(position);
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

    public void commit() {
        showProgressDialog();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken())
                .addFormDataPart("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid())
                .addFormDataPart("remark", mRemarkTv.getText().toString());
        for (int i = 0; i < mFileList.size(); i++) {
            if (mFileList.get(i).getName().startsWith(".mp4")) {
                requestBody.addFormDataPart("file" + i, mFileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), mFileList.get(i)));
            } else {
                requestBody.addFormDataPart("file" + i, mFileList.get(i).getName(), RequestBody.create(MediaType.parse("video/mp4"), mFileList.get(i)));
                requestBody.addFormDataPart("audio_seconds",mVoiceTimeTv.getText().toString());
            }
        }
        if(mVoiceFile!=null){
            requestBody.addFormDataPart("file" ,mVoiceFile.getName(), RequestBody.create(MediaType.parse("video/mp4"), mVoiceFile));
        }
        MultipartBody rb = requestBody.build();
        HttpGetDataUtil.post(PublishActivity.this, Constant.PUBLISH_URL, rb, BaseVO.class, PublishActivity.this);
    }

    @Override
    public void success(BaseVO vo) {
        this.finish();
    }
}
