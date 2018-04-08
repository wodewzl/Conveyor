package com.wuzhanglong.conveyor.model;

import com.wuzhanglong.library.mode.BaseVO;

/**
 * Created by ${Wuzhanglong} on 2018/4/8.
 */

public class UpdateVO extends BaseVO{

    /**
     * data : {"v_number":"2","v_name":"2.0","v_minimum":"1","v_address":"http://bags.work.csongdai.com/Public/csongdai.apk","v_content":"1.优化app更新控制。\r\n2.优化工作发布, 再也不用担心提交错误无法修改了。","v_size":"","v_platform":"1","v_time":"1516848142"}
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
         * v_number : 2
         * v_name : 2.0
         * v_minimum : 1
         * v_address : http://bags.work.csongdai.com/Public/csongdai.apk
         * v_content : 1.优化app更新控制。
         2.优化工作发布, 再也不用担心提交错误无法修改了。
         * v_size :
         * v_platform : 1
         * v_time : 1516848142
         */

        private String v_number;
        private String v_name;
        private String v_minimum;
        private String v_address;
        private String v_content;
        private String v_size;
        private String v_platform;
        private String v_time;

        public String getV_number() {
            return v_number;
        }

        public void setV_number(String v_number) {
            this.v_number = v_number;
        }

        public String getV_name() {
            return v_name;
        }

        public void setV_name(String v_name) {
            this.v_name = v_name;
        }

        public String getV_minimum() {
            return v_minimum;
        }

        public void setV_minimum(String v_minimum) {
            this.v_minimum = v_minimum;
        }

        public String getV_address() {
            return v_address;
        }

        public void setV_address(String v_address) {
            this.v_address = v_address;
        }

        public String getV_content() {
            return v_content;
        }

        public void setV_content(String v_content) {
            this.v_content = v_content;
        }

        public String getV_size() {
            return v_size;
        }

        public void setV_size(String v_size) {
            this.v_size = v_size;
        }

        public String getV_platform() {
            return v_platform;
        }

        public void setV_platform(String v_platform) {
            this.v_platform = v_platform;
        }

        public String getV_time() {
            return v_time;
        }

        public void setV_time(String v_time) {
            this.v_time = v_time;
        }
    }
}
