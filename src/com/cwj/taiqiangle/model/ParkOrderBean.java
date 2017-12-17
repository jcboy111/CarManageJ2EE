package com.cwj.taiqiangle.model;

import java.sql.Date;

public class ParkOrderBean {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getParkid() {
        return parkid;
    }

    public void setParkid(int parkid) {
        this.parkid = parkid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int id;
    private int userid=Integer.MIN_VALUE;
    private int parkid=Integer.MIN_VALUE;
    private Date startdate;
    private Date enddate;
    private int status=Integer.MIN_VALUE;


}
