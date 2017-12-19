package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.CarOutBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarOutService {
    public static void main(String[] args)
    {
        CarOutService carOutService=new CarOutService();
        try {
         carOutService.pass(5);
         carOutService.updateUser(5,5);
//            carInService.deleteOrder(2);

//            carInService.deleteOrder(3);
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private Connection conn;
    private PreparedStatement ps=null;
    private ResultSet rs=null;

    /**
     * 成功增加返回1
     * 失败返回0
     * 异常则抛出
     * @param sender_id
     * @param name
     * @param price
     * @param pic
     * @return
     * @throws SQLException
     */
    public int add(int sender_id,String name,int price,String pic) throws SQLException {
        CarService carService=new CarService();
        int car_id=carService.addCar(name,price,pic);
        if(car_id==0)
        {
            throw new SQLException();
        }
        conn= DBUtil.getConnection();
        String sql="insert into car_out_order(car_id,sender_id,status) values(?,?,?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,car_id);
        ps.setObject(2,sender_id);
        ps.setObject(3,0);
        int r=ps.executeUpdate();
        conn.close();
        return r;
    }

    /**
     * 返回全部的oreder
     * @return
     * @throws SQLException
     */
    public List<CarOutBean> getAllOrder() throws SQLException {
        conn=DBUtil.getConnection();
        List<CarOutBean> orders=new ArrayList<CarOutBean>();
        String sql="select * from car_out_order";
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next())
        {
            CarOutBean carOrder=new CarOutBean();
            carOrder.setId(rs.getInt("id"));
            carOrder.setCar_id(rs.getInt("car_id"));
            carOrder.setSender_id(rs.getInt("sender_id"));
            if(rs.getString("receiver_id")!=null)
            {
                carOrder.setReceiver_id(Integer.parseInt(rs.getString("receiver_id")));
            }
            carOrder.setStatus(rs.getInt("status"));
            orders.add(carOrder);
        }
        return orders;
    }

    public CarOutBean getOrderById(int id) throws SQLException {
        conn=DBUtil.getConnection();
        String sql="select * from car_out_order where id="+id;
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next())
        {
            CarOutBean carOrder=new CarOutBean();
            carOrder.setId(rs.getInt("id"));
            carOrder.setCar_id(rs.getInt("car_id"));
            carOrder.setSender_id(rs.getInt("sender_id"));
            if(rs.getString("receiver_id")!=null)
            {
                carOrder.setReceiver_id(Integer.parseInt(rs.getString("receiver_id")));
            }
            carOrder.setStatus(rs.getInt("status"));
            return carOrder;
        }
        return null;
    }

    /*
     author:陈杰
     */
    public List<CarOutBean> getOrdersByReceiverId(int id) throws SQLException {
        List<CarOutBean> orders = new ArrayList<CarOutBean>();
        conn=DBUtil.getConnection();
        String sql="select * from car_out_order where receiver_id="+id;
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next())
        {
            CarOutBean carOrder=new CarOutBean();
            carOrder.setId(rs.getInt("id"));
            carOrder.setCar_id(rs.getInt("car_id"));
            carOrder.setSender_id(rs.getInt("sender_id"));
            if(rs.getString("receiver_id")!=null)
            {
                carOrder.setReceiver_id(Integer.parseInt(rs.getString("receiver_id")));
            }
            carOrder.setStatus(rs.getInt("status"));
            orders.add(carOrder);
        }
        if(orders.size()>0) return orders;
        else return null;
    }


    /**
     * 审核通过
     * 失效返回0
     * @param id
     * @return
     */
    public int pass(int id) throws SQLException {
        if(getOrderById(id)==null||getOrderById(id).getStatus()!=0)
            return 0;
        conn=DBUtil.getConnection();
        String sql="update car_out_order set status=1 where id="+id;
        ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

    /**
     * 审核不通过
     * 失效返回0
     * @param id
     * @return
     */
    public int ban(int id) throws SQLException {
        if(getOrderById(id)==null||getOrderById(id).getStatus()!=0)
            return 0;
        conn=DBUtil.getConnection();
        String sql="update car_out_order set status=3 where id="+id;
        ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

    /**
     * 接受订单
     * 无效返回0
     * @param id
     * @param receiver_id
     */
    public int updateUser(int id,int receiver_id) throws SQLException {
        if(getOrderById(id)==null||getOrderById(id).getStatus()!=1)
            return 0;
        conn=DBUtil.getConnection();
        String sql="update car_out_order set status=2,receiver_id="+receiver_id+" where id="+id;
        ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

    /**
     * 用户还车
     * 无效返回0
     * @param id
     * @param receiver_id
     * @author ChenJie
     */
    public int userReturnRentCar(int id,int receiver_id) throws SQLException {
        if(getOrderById(id)==null||getOrderById(id).getStatus()!=2)
            return 0;
        conn=DBUtil.getConnection();
        String sql="update car_out_order set status=1,receiver_id=null"+" where id=" +id+ " and receiver_id="+receiver_id;
        System.out.println(sql);
        ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }



    /**
     * 根据id删除order
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteOrder(int id) throws SQLException {
        conn=DBUtil.getConnection();
        String sql="delete from car_out_order where id="+id;
        ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
}
