package com.wuzhanglong.conveyor.model;

import java.util.List;

/**
 * Created by ${Wuzhanglong} on 2017/12/25.
 */

public class TestVO {
    private TestVO data;
   private  List<TestVO> one;
    private List<TestVO> two;

    public TestVO getData() {
        return data;
    }

    public void setData(TestVO data) {
        this.data = data;
    }

    public List<TestVO> getOne() {
        return one;
    }

    public void setOne(List<TestVO> one) {
        this.one = one;
    }

    public List<TestVO> getTwo() {
        return two;
    }

    public void setTwo(List<TestVO> two) {
        this.two = two;
    }

    /**
     * citycode : 130400
     * direction : 1
     * distance : 22
     * endstation : 火车站
     * id : 91286
     * price : 4
     * reversal : 0
     * routeid : 151
     * routename : 15路
     * routeno : 151
     * routetype : 1
     * shortname : 15路
     * startstation : 姬庄村委
     * stationDirection : 1
     * stationId : 18528
     * stationLatitude : 36.474028
     * stationLongitude : 114.211778
     * stationName : 新城花园
     * stationNo : 20
     * status : 3
     * summerendtime : 19:05
     * summerstarttime : 7:10
     * winterendtime : 19:00
     * winterstarttime : 7:10
     */

    private String citycode;
    private int direction;
    private int distance;
    private String endstation;
    private int id;
    private String price;
    private int reversal;
    private String routeid;
    private String routename;
    private String routeno;
    private int routetype;
    private String shortname;
    private String startstation;
    private int stationDirection;
    private int stationId;
    private double stationLatitude;
    private double stationLongitude;
    private String stationName;
    private int stationNo;
    private int status;
    private String summerendtime;
    private String summerstarttime;
    private String winterendtime;
    private String winterstarttime;

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getEndstation() {
        return endstation;
    }

    public void setEndstation(String endstation) {
        this.endstation = endstation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getReversal() {
        return reversal;
    }

    public void setReversal(int reversal) {
        this.reversal = reversal;
    }

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getRouteno() {
        return routeno;
    }

    public void setRouteno(String routeno) {
        this.routeno = routeno;
    }

    public int getRoutetype() {
        return routetype;
    }

    public void setRoutetype(int routetype) {
        this.routetype = routetype;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getStartstation() {
        return startstation;
    }

    public void setStartstation(String startstation) {
        this.startstation = startstation;
    }

    public int getStationDirection() {
        return stationDirection;
    }

    public void setStationDirection(int stationDirection) {
        this.stationDirection = stationDirection;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public double getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(double stationLatitude) {
        this.stationLatitude = stationLatitude;
    }

    public double getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(double stationLongitude) {
        this.stationLongitude = stationLongitude;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationNo() {
        return stationNo;
    }

    public void setStationNo(int stationNo) {
        this.stationNo = stationNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSummerendtime() {
        return summerendtime;
    }

    public void setSummerendtime(String summerendtime) {
        this.summerendtime = summerendtime;
    }

    public String getSummerstarttime() {
        return summerstarttime;
    }

    public void setSummerstarttime(String summerstarttime) {
        this.summerstarttime = summerstarttime;
    }

    public String getWinterendtime() {
        return winterendtime;
    }

    public void setWinterendtime(String winterendtime) {
        this.winterendtime = winterendtime;
    }

    public String getWinterstarttime() {
        return winterstarttime;
    }

    public void setWinterstarttime(String winterstarttime) {
        this.winterstarttime = winterstarttime;
    }
}
