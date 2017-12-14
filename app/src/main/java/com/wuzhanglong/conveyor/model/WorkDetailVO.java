package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/13.
 */

public class WorkDetailVO extends BaseVO{

    /**
     * data : {"logid":"23","loguid":"3","time":"1513174248","content1":"1.江苏经济纠纷看到快快爆发后直接睡觉的样子，我觉得金额。\n2.接三连四就被打开的经济按等级分结束的家居服。\n测试发布工作任务\n点点滴滴\n44444",
     * "content2":"就是静静的看看打开看得见啊。\n我想大炮跑跑\n钱钱钱钱钱\n44444444","content3":"","content4":"","content5":"","type":"0","fullname":"金仁政","headpic":"http://log.myzhian
     * .com/Uploads/bs0640/Resume/image/1512713442.jpg","sex":"男","dname":"班组1","positionname":"司机","imgs":["http://log.myzhian.com/Uploads/bs0640/Log/image/20171213/5a3134e8b135b.jpg","http://log
     * .myzhian.com/Uploads/bs0640/Log/image/20171213/5a3134e8b27ec.jpg"],"date":"2017-12-13 星期三","nextid":"19","preid":"","typename":"日报"}
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
         * logid : 23
         * loguid : 3
         * time : 1513174248
         * content1 : 1.江苏经济纠纷看到快快爆发后直接睡觉的样子，我觉得金额。
         2.接三连四就被打开的经济按等级分结束的家居服。
         测试发布工作任务
         点点滴滴
         44444
         * content2 : 就是静静的看看打开看得见啊。
         我想大炮跑跑
         钱钱钱钱钱
         44444444
         * content3 :
         * content4 :
         * content5 :
         * type : 0
         * fullname : 金仁政
         * headpic : http://log.myzhian.com/Uploads/bs0640/Resume/image/1512713442.jpg
         * sex : 男
         * dname : 班组1
         * positionname : 司机
         * imgs : ["http://log.myzhian.com/Uploads/bs0640/Log/image/20171213/5a3134e8b135b.jpg","http://log.myzhian.com/Uploads/bs0640/Log/image/20171213/5a3134e8b27ec.jpg"]
         * date : 2017-12-13 星期三
         * nextid : 19
         * preid :
         * typename : 日报
         */

        private String logid;
        private String loguid;
        private String time;
        private String content1;
        private String content2;
        private String content3;
        private String content4;
        private String content5;
        private String type;
        private String fullname;
        private String headpic;
        private String sex;
        private String dname;
        private String positionname;
        private String date;
        private String nextid;
        private String preid;
        private String typename;
        private List<String> imgs;

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNextid() {
            return nextid;
        }

        public void setNextid(String nextid) {
            this.nextid = nextid;
        }

        public String getPreid() {
            return preid;
        }

        public void setPreid(String preid) {
            this.preid = preid;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
