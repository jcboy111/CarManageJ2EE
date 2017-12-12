package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.CarRentInBean;
import com.cwj.taiqiangle.model.TestBean;
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

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public List<CarRentInBean> getAllRentInCars() throws SQLException {
        conn= DBUtil.getConnection();
        List<CarRentInBean>carRentInBeans=new ArrayList<CarRentInBean>();
        String sql="select * from car_rent_in";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            CarRentInBean carRentInBean=new CarRentInBean();
            carRentInBean.setId(rs.getString("id"));
            carRentInBean.setTitle(rs.getString("title"));
            carRentInBean.setUser(rs.getString("user"));
            carRentInBean.setStatus(rs.getString("status"));
            carRentInBean.setPay(rs.getString("pay"));
            carRentInBean.setDate(rs.getString("date"));
            carRentInBeans.add(carRentInBean);

        }
        return carRentInBeans;
    }

    public int addRentInCar(String title,String user,String date,String pay,String status) throws SQLException {
        conn= DBUtil.getConnection();
        String sql="insert into car_rent_in (title,user,date,pay,status) values(?,?,?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, title);
        ps.setObject(2, user);
        ps.setObject(3, date);
        ps.setObject(4, pay);
        ps.setObject(5, status);
        int r=ps.executeUpdate();
        return r;
    }

    public int deleteRentInCar(String id) throws SQLException {
        conn= DBUtil.getConnection();
        String sql = "delete from car_rent_in where id = ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        int r=ps.executeUpdate();
        return r;
    }

    public int passRentInCar(String id) throws SQLException {
        conn= DBUtil.getConnection();
        String sql = "UPDATE car_rent_in SET status = '审核通过' WHERE id = ? ";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        int r=ps.executeUpdate();
        return r;
    }


}
