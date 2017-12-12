package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/10.
 */

public class DepartVO extends BaseVO{

    /**
     * data : {"list":[{"departmentid":"1","dname":"分公司1","belong":"0","level":"1","order":"1","status":"1"},{"departmentid":"2","dname":"部门1","belong":"1","level":"2","order":"1","status":"1"},
     * {"departmentid":"5","dname":"班组1","belong":"2","level":"3","order":"1","status":"1"},{"departmentid":"3","dname":"部门2","belong":"0","level":"1","order":"2","status":"0"},{"departmentid":"4",
     * "dname":"部门2","belong":"1","level":"2","order":"2","status":"1"},{"departmentid":"6","dname":"班组2","belong":"2","level":"3","order":"2","status":"1"}]}
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
             * departmentid : 1
             * dname : 分公司1
             * belong : 0
             * level : 1
             * order : 1
             * status : 1
             */

            private String departmentid;
            private String dname;
            private String belong;
            private String level;
            private String order;
            private String status;

            public String getDepartmentid() {
                return departmentid;
            }

            public void setDepartmentid(String departmentid) {
                this.departmentid = departmentid;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public String getBelong() {
                return belong;
            }

            public void setBelong(String belong) {
                this.belong = belong;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
