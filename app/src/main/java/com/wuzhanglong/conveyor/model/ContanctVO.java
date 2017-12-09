package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/9.
 */

public class ContanctVO extends BaseVO{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userid : 5
         * headpic :
         * fullname : 班长
         * tel : 13555555555
         * sex : 男
         * isinpost : 1
         * initial : BC
         * dname : 班组1
         * pname : 班长
         * postsname :
         * nickname : 班长
         */

        private String userid;
        private String headpic;
        private String fullname;
        public String topc;
        private String tel;
        private String sex;
        private String isinpost;
        private String initial;
        private String dname;
        private String pname;
        private String postsname;
        private String nickname;

        public String getTopc() {
            return topc;
        }

        public void setTopc(String topc) {
            this.topc = topc;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIsinpost() {
            return isinpost;
        }

        public void setIsinpost(String isinpost) {
            this.isinpost = isinpost;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPostsname() {
            return postsname;
        }

        public void setPostsname(String postsname) {
            this.postsname = postsname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
