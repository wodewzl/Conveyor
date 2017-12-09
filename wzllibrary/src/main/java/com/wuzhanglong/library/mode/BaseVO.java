
package com.wuzhanglong.library.mode;

import java.io.Serializable;

public class BaseVO implements Serializable {
    public String desc;
    public String code;
    public String date_time;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
