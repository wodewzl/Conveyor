
package com.wuzhanglong.conveyor.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;


import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wuzhanglong.library.R;
import com.wuzhanglong.library.activity.BaseActivity;

public class ShareUtil {
//    SHARE_MEDIA.SINA, ,SHARE_MEDIA.WEIXIN_FAVORITE收藏
    final static SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
    {
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
    };

    public static void share(final BaseActivity activity, String img, final String title, final String content, final String url) {
        final UMShareListener umslistener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {

                Toast.makeText(activity, platform + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        ShareAction shareAction= new ShareAction(activity);
//        UMImage thumb =  new UMImage(activity,img);
//        UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
//        image.setThumb(thumb);
//        UMImage image = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.mipmap.copy));

        UMImage image = new UMImage(activity, img);
        if (!"".equals(img) && img != null) {
            image = new UMImage(activity, img);
        } else {
            image = new UMImage(activity,R.drawable.ic_launcher);
        }
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//        压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
        shareAction.withMedia(web);
        shareAction.setDisplayList(displaylist);

        shareAction.setCallback(umslistener);
        shareAction.open();
    }



    /**
     * 分享链接
     */
    public static void shareWeb(final Activity activity,String imageUrl, String title, String description, String WebUrl) {
        UMWeb web = new UMWeb(WebUrl);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        if (TextUtils.isEmpty(imageUrl)) {
            web.setThumb(new UMImage(activity, R.drawable.ic_launcher));  //本地缩略图
        } else {
            web.setThumb(new UMImage(activity, imageUrl));  //网络缩略图
        }
        new ShareAction(activity)
                .setDisplayList(displaylist)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (share_media.name().equals("WEIXIN_FAVORITE")) {
                                    Toast.makeText(activity, share_media + " 收藏成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, share_media + " 分享成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, share_media + " 分享失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, share_media + " 分享取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .open();

        //新浪微博中图文+链接
        /*new ShareAction(activity)
                .setPlatform(platform)
                .withText(description + " " + WebUrl)
                .withMedia(new UMImage(activity,imageID))
                .share();*/
    }




}
