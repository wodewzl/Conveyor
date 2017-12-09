package com.wuzhanglong.conveyor.util;

import com.wuzhanglong.conveyor.model.ContanctVO;

import java.util.Comparator;



public class PinyinComparator implements Comparator<ContanctVO.DataBean> {

    public int compare(ContanctVO.DataBean o1, ContanctVO.DataBean o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}