package com.wuzhanglong.conveyor.model;

import com.google.gson.annotations.SerializedName;
import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * 作者：wuzhanglong on 2018/5/23.
 * 邮箱：zlwu5@iflytek.com
 * 工号：201801399
 */
public class PublishListVO extends BaseVO{

    /**
     * data : {"list":[{"id":"7","headpic":"","date":"2018-05-23 16:49","userid":"85","audio_seconds":"10","remark":"突突突","fullname":"周宏","dname":"管理部","positionname":"总经理","image_video":[{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7066.jpg","date_time":"2018-05-23 16:49:55","type":"1"},{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7556.mp4","date_time":"2018-05-23 16:49:55","type":"2"}],"audio_url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7d29.amr"},{"id":"6","headpic":"","date":"2018-05-23 16:31","userid":"85","audio_seconds":"10","remark":"旅途","fullname":"周宏","dname":"管理部","positionname":"总经理","image_video":[{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0526eb42687.jpg","date_time":"2018-05-23 16:31:39","type":"1"},{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0526eb42d15.mp4","date_time":"2018-05-23 16:31:39","type":"2"}],"audio_url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0526eb43a67.amr"},{"id":"5","headpic":"","date":"2018-05-23 16:28","userid":"85","audio_seconds":"3","remark":"旅途","fullname":"周宏","dname":"管理部","positionname":"总经理","image_video":[{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b05261548f0c.jpg","date_time":"2018-05-23 16:28:05","type":"1"},{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b05261549591.mp4","date_time":"2018-05-23 16:28:05","type":"2"}],"audio_url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0526154a299.amr"},{"id":"4","headpic":"http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","date":"2018-05-23 16:27","userid":"3","audio_seconds":"1","remark":"哈哈哈哈哈","fullname":"金仁政","dname":"樊城变电站","positionname":"保安","image_video":[{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0525f0b4222.png","date_time":"2018-05-23 16:27:28","type":"1"}],"audio_url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0525f0b4092.mp3"},{"id":"3","headpic":"","date":"2018-05-23 16:27","userid":"85","audio_seconds":"0","remark":"旅途","fullname":"周宏","dname":"管理部","positionname":"总经理","image_video":[{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0525ef514d2.jpg","date_time":"2018-05-23 16:27:27","type":"1"},{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0525ef51bc9.mp4","date_time":"2018-05-23 16:27:27","type":"2"}],"audio_url":""},{"id":"2","headpic":"http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","date":"2018-05-23 16:22","userid":"3","audio_seconds":"0","remark":"哈哈哈哈哈","fullname":"金仁政","dname":"樊城变电站","positionname":"保安","image_video":[{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b0524cb31d1e.jpg","date_time":"2018-05-23 16:22:35","type":"1"}],"audio_url":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 7
             * headpic :
             * date : 2018-05-23 16:49
             * userid : 85
             * audio_seconds : 10
             * remark : 突突突
             * fullname : 周宏
             * dname : 管理部
             * positionname : 总经理
             * image_video : [{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7066.jpg","date_time":"2018-05-23 16:49:55","type":"1"},{"url":"http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7556.mp4","date_time":"2018-05-23 16:49:55","type":"2"}]
             * audio_url : http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7d29.amr
             */

            private String id;
            private String headpic;
            private String date;
            private String userid;
            private String audio_seconds;
            private String remark;
            private String fullname;
            private String dname;
            private String positionname;
            private String audio_url;


            private List<ImageVideoBean> image_video;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getAudio_seconds() {
                return audio_seconds;
            }

            public void setAudio_seconds(String audio_seconds) {
                this.audio_seconds = audio_seconds;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public String getPositionname() {
                return positionname;
            }

            public void setPositionname(String positionname) {
                this.positionname = positionname;
            }

            public String getAudio_url() {
                return audio_url;
            }

            public void setAudio_url(String audio_url) {
                this.audio_url = audio_url;
            }

            public List<ImageVideoBean> getImage_video() {
                return image_video;
            }

            public void setImage_video(List<ImageVideoBean> image_video) {
                this.image_video = image_video;
            }

            public static class ImageVideoBean {
                /**
                 * url : http://bags.work.csongdai.com/Uploads/bs0653/EventFiles/20180523/5b052b33e7066.jpg
                 * date_time : 2018-05-23 16:49:55
                 * type : 1
                 */

                private String url;
                @SerializedName("date_time")
                private String date_timeX;
                private String type;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getDate_timeX() {
                    return date_timeX;
                }

                public void setDate_timeX(String date_timeX) {
                    this.date_timeX = date_timeX;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
