package com.wuzhanglong.conveyor.model;

import com.google.gson.annotations.SerializedName;
import com.wuzhanglong.library.mode.BaseVO;

/**
 * Created by ${Wuzhanglong} on 2017/12/8.
 */

public class UserInfoVO extends BaseVO {

    /**
     * data : {"userid":"3","username":"13888888888","headpic":"http://log.myzhian.com/Uploads/bs0640/Resume/image/1512713442.jpg","fullname":"金仁政","sex":"男","tel":"13888888888","isinpost":"1",
     * "islogin":"1","did":"5","pid":"1","logins":"153","role":"1","userflag":"0","fjptags":"bsukab640","jpalias":"bsukab6400003","dname":"班组1","pname":"司机","siteurl":"http://log.myzhian.com",
     * "firmcname":"某某公司","return":"true","ftoken":"QBTTIXyUNNDKNEEMUE2QzcxNzk4RAO0O0OO0O0O"}
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
         * userid : 3
         * username : 13888888888
         * headpic : http://log.myzhian.com/Uploads/bs0640/Resume/image/1512713442.jpg
         * fullname : 金仁政
         * sex : 男
         * tel : 13888888888
         * isinpost : 1
         * islogin : 1
         * did : 5
         * pid : 1
         * logins : 153
         * role : 1
         * userflag : 0
         * fjptags : bsukab640
         * jpalias : bsukab6400003
         * dname : 班组1
         * pname : 司机
         * siteurl : http://log.myzhian.com
         * firmcname : 某某公司
         * return : true
         * ftoken : QBTTIXyUNNDKNEEMUE2QzcxNzk4RAO0O0OO0O0O
         */

        private String userid;
        private String username;
        private String headpic;
        private String fullname;
        private String sex;
        private String tel;
        private String isinpost;
        private String islogin;
        private String did;
        private String pid;
        private String logins;
        private String role;
        private String userflag;
        private String fjptags;
        private String jpalias;
        private String dname;
        private String pname;
        private String siteurl;
        private String firmcname;
        @SerializedName("return")
        private String returnX;
        private String ftoken;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getIsinpost() {
            return isinpost;
        }

        public void setIsinpost(String isinpost) {
            this.isinpost = isinpost;
        }

        public String getIslogin() {
            return islogin;
        }

        public void setIslogin(String islogin) {
            this.islogin = islogin;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getLogins() {
            return logins;
        }

        public void setLogins(String logins) {
            this.logins = logins;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUserflag() {
            return userflag;
        }

        public void setUserflag(String userflag) {
            this.userflag = userflag;
        }

        public String getFjptags() {
            return fjptags;
        }

        public void setFjptags(String fjptags) {
            this.fjptags = fjptags;
        }

        public String getJpalias() {
            return jpalias;
        }

        public void setJpalias(String jpalias) {
            this.jpalias = jpalias;
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

        public String getSiteurl() {
            return siteurl;
        }

        public void setSiteurl(String siteurl) {
            this.siteurl = siteurl;
        }

        public String getFirmcname() {
            return firmcname;
        }

        public void setFirmcname(String firmcname) {
            this.firmcname = firmcname;
        }

        public String getReturnX() {
            return returnX;
        }

        public void setReturnX(String returnX) {
            this.returnX = returnX;
        }

        public String getFtoken() {
            return ftoken;
        }

        public void setFtoken(String ftoken) {
            this.ftoken = ftoken;
        }
    }
}
