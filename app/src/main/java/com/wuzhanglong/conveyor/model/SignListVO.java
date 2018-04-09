package com.wuzhanglong.conveyor.model;

import com.google.gson.annotations.SerializedName;
import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2018/4/9.
 */

public class SignListVO extends BaseVO{

    /**
     * data : {"list":[{"csid":"6","csuid":"3","lng":"114.399322","lat":"30.457549","address":"湖北省武汉市洪山区关东街道东湖高新区关东街汤逊湖社区诺威香卡国际酒店","pic":"http://bags.work.csongdai
     * .com/Uploads/bs0653/Visit/image/20180409/5acb255a46fbd.jpeg","status":"2","userid":"3","headpic":"http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg",
     * "fullname":"金仁政","sex":"男","dname":"樊城变电站","positionname":"保安","distance":"279.51","date":"2018-04-09","date_time":"2018-04-09 16:33:30","status_text":"位置异常"},{"csid":"5","csuid":"3",
     * "lng":"114.399143","lat":"30.457434","address":"湖北省武汉市洪山区关东街道华师园北路56号","pic":"http://bags.work.csongdai.com/Uploads/bs0653/Visit/image/20180409/5acb23b596949.","status":"2","userid":"3",
     * "headpic":"http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","fullname":"金仁政","sex":"男","dname":"樊城变电站","positionname":"保安","distance":"279.5",
     * "date":"2018-04-09","date_time":"2018-04-09 16:26:29","status_text":"位置异常"},{"csid":"4","csuid":"3","lng":"114.39929","lat":"30.45753","address":"湖北省武汉市洪山区关东街道东湖高新区关东街汤逊湖社区诺威香卡国际酒店",
     * "pic":"http://bags.work.csongdai.com/Uploads/bs0653/Visit/image/20180409/5acb20b96138d.","status":"2","userid":"3","headpic":"http://bags.work.csongdai
     * .com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","fullname":"金仁政","sex":"男","dname":"樊城变电站","positionname":"保安","distance":"279.51","date":"2018-04-09","date_time":"2018-04-09
     * 16:13:45","status_text":"位置异常"},{"csid":"3","csuid":"3","lng":"111.94132804870605","lat":"31.96854673938362","address":"湖北省襄阳市襄城区卧龙镇张家山","pic":"","status":"2","userid":"3",
     * "headpic":"http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","fullname":"金仁政","sex":"男","dname":"樊城变电站","positionname":"保安","distance":"21.05",
     * "date":"2018-04-05","date_time":"2018-04-05 23:37:22","status_text":"位置异常"},{"csid":"2","csuid":"3","lng":"112.137066","lat":"32.059203","address":"湖北省襄阳市樊城区高新区紫贞街道中国建设银行(襄阳七里河支行)
     * 航空工业襄樊医院江汉分院","pic":"","status":"1","userid":"3","headpic":"http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","fullname":"金仁政","sex":"男","dname":"樊城变电站",
     * "positionname":"保安","distance":"0","date":"2018-04-05","date_time":"2018-04-05 23:33:06","status_text":"正常"},{"csid":"1","csuid":"3","lng":"112.237066","lat":"32.059203",
     * "address":"湖北省襄阳市襄州区张湾街道冯家寨","pic":"http://bags.work.csongdai.com/Uploads/bs0653/Log/image/20180406/5ac64805ca355.jpeg","status":"0","userid":"3","headpic":"http://bags.work.csongdai
     * .com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg","fullname":"金仁政","sex":"男","dname":"樊城变电站","positionname":"保安","distance":"9.43","date":"2018-04-05","date_time":"2018-04-05
     * 23:32:53","status_text":"未配置"}]}
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
             * csid : 6
             * csuid : 3
             * lng : 114.399322
             * lat : 30.457549
             * address : 湖北省武汉市洪山区关东街道东湖高新区关东街汤逊湖社区诺威香卡国际酒店
             * pic : http://bags.work.csongdai.com/Uploads/bs0653/Visit/image/20180409/5acb255a46fbd.jpeg
             * status : 2
             * userid : 3
             * headpic : http://bags.work.csongdai.com/Uploads/bs0653/Resume/image/20180408/5ac9b6faab099.jpeg
             * fullname : 金仁政
             * sex : 男
             * dname : 樊城变电站
             * positionname : 保安
             * distance : 279.51
             * date : 2018-04-09
             * date_time : 2018-04-09 16:33:30
             * status_text : 位置异常
             */

            private String csid;
            private String csuid;
            private String lng;
            private String lat;
            private String address;
            private String pic;
            private String status;
            private String userid;
            private String headpic;
            private String fullname;
            private String sex;
            private String dname;
            private String positionname;
            private String distance;
            private String date;
            private String time;
            private String isTitle;

            public String getIsTitle() {
                return isTitle;
            }

            public void setIsTitle(String isTitle) {
                this.isTitle = isTitle;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            @SerializedName("date_time")
            private String date_timeX;
            private String status_text;

            public String getCsid() {
                return csid;
            }

            public void setCsid(String csid) {
                this.csid = csid;
            }

            public String getCsuid() {
                return csuid;
            }

            public void setCsuid(String csuid) {
                this.csuid = csuid;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
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

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDate_timeX() {
                return date_timeX;
            }

            public void setDate_timeX(String date_timeX) {
                this.date_timeX = date_timeX;
            }

            public String getStatus_text() {
                return status_text;
            }

            public void setStatus_text(String status_text) {
                this.status_text = status_text;
            }
        }
    }
}
