package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/11.
 */

public class WorkVO extends BaseVO {

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

            private String logid;
            private String loguid;
            private String time;
            private String content1;
            private String content2;
            private String content3;
            private String content4;
            private String content5;
            private String type;
            private String praise;
            private String fullname;
            private String headpic;
            private String sex;
            private String dname;
            private String positionname;
            private String typename;
            private String date;
            private String isTitle;
            private String date_week;

            public String getDate_week() {
                return date_week;
            }

            public void setDate_week(String date_week) {
                this.date_week = date_week;
            }

            public String getLogid() {
                return logid;
            }

            public void setLogid(String logid) {
                this.logid = logid;
            }

            public String getLoguid() {
                return loguid;
            }

            public void setLoguid(String loguid) {
                this.loguid = loguid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent1() {
                return content1;
            }

            public void setContent1(String content1) {
                this.content1 = content1;
            }

            public String getContent2() {
                return content2;
            }

            public void setContent2(String content2) {
                this.content2 = content2;
            }

            public String getContent3() {
                return content3;
            }

            public void setContent3(String content3) {
                this.content3 = content3;
            }

            public String getContent4() {
                return content4;
            }

            public void setContent4(String content4) {
                this.content4 = content4;
            }

            public String getContent5() {
                return content5;
            }

            public void setContent5(String content5) {
                this.content5 = content5;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPraise() {
                return praise;
            }

            public void setPraise(String praise) {
                this.praise = praise;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getHeadpic() {
                return headpic;
            }

            public void setHeadpic(String headpic) {
                this.headpic = headpic;
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

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIsTitle() {
                return isTitle;
            }

            public void setIsTitle(String isTitle) {
                this.isTitle = isTitle;
            }
        }
    }
}
