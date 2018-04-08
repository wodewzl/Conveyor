package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/10.
 */

public class WorkAllVO extends BaseVO{

    /**
     * data : {"summary_content1":"班组1：\n1","summary_content2":"班组1：\n2","summary_content3":"班组1：\n3","summary_content4":"班组1：\n4","summary_content5":"班组1：","summary_imgs":["http://log.myzhian
     * .com/Uploads/bs0640/Log/image/20171211/5a2de60d6fcb4.jpg","http://log.myzhian.com/Uploads/bs0640/Log/image/20171211/5a2dffa883205.jpg","http://log.myzhian
     * .com/Uploads/bs0640/Log/image/20171211/5a2dffa8840bd.jpg","http://log.myzhian.com/Uploads/bs0640/Log/image/20171211/5a2dffe969aee.jpg"],"date":"2017-12-14 星期四"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * summary_content1 : 班组1：
         1
         * summary_content2 : 班组1：
         2
         * summary_content3 : 班组1：
         3
         * summary_content4 : 班组1：
         4
         * summary_content5 : 班组1：
         * summary_imgs : ["http://log.myzhian.com/Uploads/bs0640/Log/image/20171211/5a2de60d6fcb4.jpg","http://log.myzhian.com/Uploads/bs0640/Log/image/20171211/5a2dffa883205.jpg","http://log
         * .myzhian.com/Uploads/bs0640/Log/image/20171211/5a2dffa8840bd.jpg","http://log.myzhian.com/Uploads/bs0640/Log/image/20171211/5a2dffe969aee.jpg"]
         * date : 2017-12-14 星期四
         */

        private String summary_content1;
        private String summary_content2;
        private String summary_content3;
        private String summary_content4;
        private String summary_content5;
        private String is_content3;
        private String is_content4;
        private String is_content5;
        private String content3_title;
        private String content4_title;
        private String content5_title;
        private String date;

        public String getIs_content3() {
            return is_content3;
        }

        public void setIs_content3(String is_content3) {
            this.is_content3 = is_content3;
        }

        public String getIs_content4() {
            return is_content4;
        }

        public void setIs_content4(String is_content4) {
            this.is_content4 = is_content4;
        }

        public String getIs_content5() {
            return is_content5;
        }

        public void setIs_content5(String is_content5) {
            this.is_content5 = is_content5;
        }

        public String getContent3_title() {
            return content3_title;
        }

        public void setContent3_title(String content3_title) {
            this.content3_title = content3_title;
        }

        public String getContent4_title() {
            return content4_title;
        }

        public void setContent4_title(String content4_title) {
            this.content4_title = content4_title;
        }

        public String getContent5_title() {
            return content5_title;
        }

        public void setContent5_title(String content5_title) {
            this.content5_title = content5_title;
        }

        private List<String> summary_imgs;

        public String getSummary_content1() {
            return summary_content1;
        }

        public void setSummary_content1(String summary_content1) {
            this.summary_content1 = summary_content1;
        }

        public String getSummary_content2() {
            return summary_content2;
        }

        public void setSummary_content2(String summary_content2) {
            this.summary_content2 = summary_content2;
        }

        public String getSummary_content3() {
            return summary_content3;
        }

        public void setSummary_content3(String summary_content3) {
            this.summary_content3 = summary_content3;
        }

        public String getSummary_content4() {
            return summary_content4;
        }

        public void setSummary_content4(String summary_content4) {
            this.summary_content4 = summary_content4;
        }

        public String getSummary_content5() {
            return summary_content5;
        }

        public void setSummary_content5(String summary_content5) {
            this.summary_content5 = summary_content5;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<String> getSummary_imgs() {
            return summary_imgs;
        }

        public void setSummary_imgs(List<String> summary_imgs) {
            this.summary_imgs = summary_imgs;
        }
    }
}
