package com.cwj.taiqiangle.model;

public class ParkBean {
    //Modified by Ceej 12.21
    //修改了parkUpdate的时候金钱问题
    private int price_per_day=Integer.MIN_VALUE;
    private int id;
    private String name;
    private int status;

    public int getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(int price_per_day) {
        this.price_per_day = price_per_day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
