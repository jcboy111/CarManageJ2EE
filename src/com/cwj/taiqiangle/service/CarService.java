package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.CarBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2017/12/6.
 */
public class CarService {
    public static void main(String[] args)
    {
        CarService carService=new CarService();
        try {
            System.out.println(carService.addCar("aCar",120,null));

//
//            CarBean car=new CarBean();
//            car.setName("adad");
//            carService.updateCar(1,car);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;


    /**
     * 增加，成功返回1
     * @param name
     * @param price
     * @return
     * @throws SQLException
     */
    public int addCar(String name,int price,String pic) throws SQLException {
        conn=DBUtil.getConnection();
        String sql="insert into car(name,price,pic) values(?,?,?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,name);
        ps.setObject(2,price);
        ps.setObject(3,pic);
        int i=ps.executeUpdate();
        rs=ps.getGeneratedKeys();
        if(rs!=null)
        {
            rs.next();
            return rs.getInt(1);
        }
        conn.close();
        return i;
    }

    /**
     * 得到车子，成功返回carBean
     * @param id
     * @return
     * @throws SQLException
     */
    public CarBean getCar(int id) throws SQLException {
        conn=DBUtil.getConnection();
        String sql="select * from car where id="+id;
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next())
        {
            CarBean car=new CarBean();
            car.setId(rs.getInt("id"));
            car.setName(rs.getString("name"));
            car.setPrice(rs.getInt("price"));
            car.setPic(rs.getString("pic"));
            return car;
        }
        return null;
    }

    /**
     * 修改车辆信息
     * 成功返回1
     * @param id
     * @param car
     * @return
     * @throws SQLException
     */
    public int updateCar(int id,CarBean car) throws SQLException {
        conn=DBUtil.getConnection();
        int status=0;
        if(car.getName()!=null)
        {
            String sql="update car set name='"+car.getName()+"' where id="+id;
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if(car.getPrice()!=Integer.MIN_VALUE)
        {
            String sql="update car set price="+car.getPrice()+" where id="+id;
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if(car.getPic()!=null)
        {
            String sql="update car set pic='"+car.getPic()+"' where id="+id;
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }

        return status;
    }


}
