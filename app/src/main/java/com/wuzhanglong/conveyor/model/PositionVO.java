package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/28.
 */

public class PositionVO extends BaseVO{


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
            private String sign_name;
            private String sign_no;
            private String sign_lat;
            private String sign_lng;
            private String sign_id;

            public String getSign_name() {
                return sign_name;
            }

            public void setSign_name(String sign_name) {
                this.sign_name = sign_name;
            }

            public String getSign_no() {
                return sign_no;
            }

            public void setSign_no(String sign_no) {
                this.sign_no = sign_no;
            }

            public String getSign_lat() {
                return sign_lat;
            }

            public void setSign_lat(String sign_lat) {
                this.sign_lat = sign_lat;
            }

            public String getSign_lng() {
                return sign_lng;
            }

            public void setSign_lng(String sign_lng) {
                this.sign_lng = sign_lng;
            }

            public String getSign_id() {
                return sign_id;
            }

            public void setSign_id(String sign_id) {
                this.sign_id = sign_id;
            }
        }
    }
}
