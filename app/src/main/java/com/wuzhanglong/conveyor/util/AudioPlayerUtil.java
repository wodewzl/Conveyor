package com.wuzhanglong.conveyor.util;

import android.media.MediaPlayer;

/**
 * 作者：wuzhanglong on 2018/5/23.
 * 邮箱：zlwu5@iflytek.com
 * 工号：201801399
 */
public class AudioPlayerUtil {
    private static final String TAG = "AudioRecordTest";
    private MediaPlayer mPlayer;

    public AudioPlayerUtil() {
    }

    public void start(String mFileName, MediaPlayer.OnCompletionListener listener) {
        if(this.mPlayer == null) {
            this.mPlayer = new MediaPlayer();
        } else {
            this.mPlayer.reset();
        }

        try {
            this.mPlayer.setDataSource(mFileName);
            this.mPlayer.prepare();
            this.mPlayer.start();
            if(listener != null) {
                this.mPlayer.setOnCompletionListener(listener);
            }
        } catch (Exception var4) {
        }

    }

    public void stop() {
        if(this.mPlayer != null) {
            this.mPlayer.stop();
            this.mPlayer.release();
            this.mPlayer = null;
        }

    }
}