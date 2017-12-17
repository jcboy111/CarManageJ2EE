package com.cwj.taiqiangle.model;

/**
 * Created by 蛟川小盆友 on 2017/12/5.
 */
public class CarBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price=Integer.MIN_VALUE;
    private String name;
    private int id ;
}
