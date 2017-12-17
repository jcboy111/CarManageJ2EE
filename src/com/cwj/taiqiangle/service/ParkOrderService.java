package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.ParkBean;
import com.cwj.taiqiangle.model.ParkOrderBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ParkOrderService {
    public static void main(String[] args)
    {
        ParkOrderService ps=new ParkOrderService();
        System.out.println(new Date(System.currentTimeMillis()).getTime());
        try {
//            ps.addParkOrder(3,5);
//            ps.updateEnddate(9);
            ps.getAllParkOrder();
//            ps.deleteParkOrder(9);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(sdf.format(new Date(System.currentTimeMillis())));

    }

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * 添加parkOrder
     * 正常返回1
     * 添加失败返回0
     * 异常throw exception
     * userid非法返回-1
     * parkid非法返回-2
     * @param userid
     * @param parkid
     * @return
     * @throws SQLException
     */
    public int addParkOrder (int userid,int parkid) throws SQLException {
        UserService us=new UserService();
        if(us.getUserById(userid)==null)
        {
            return -1;
        }
        ParkService parkService=new ParkService();
        if(parkService.getParkById(parkid)==null||parkService.getParkById(parkid).getStatus()==1)
        {
            return -2;
        }

        conn= DBUtil.getConnection();
        String sql="insert into park_order(userid,parkid,startdate,status) values(?,?,?,?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,userid);
        ps.setObject(2,parkid);
        ps.setObject(3,new Date(System.currentTimeMillis()));
        ps.setObject(4,0);
        int i=ps.executeUpdate();
        //更新park的状态
        if(i!=0)
        {
            ParkBean parkBean=new ParkBean();
            parkBean.setStatus(1);
            parkService.updateParkById(parkid,parkBean);
        }

        conn.close();
        return i;

    }

    /**
     * 删除为id的parkOrder
     * 成功返回1
     * 不存在返回0
     * *id不存在返回-1
     * 异常抛出
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteParkOrder(int id) throws SQLException {
        if(this.getParkOrderByid(id)==null)
        {
            return -1;
        }
        ParkService parkService=new ParkService();
        //先更新
        ParkBean parkBean=new ParkBean();
        parkBean.setStatus(0);
        parkService.updateParkById(this.getParkOrderByid(id).getParkid(),parkBean);
         conn=DBUtil.getConnection();
         String sql="delete from park_order where id=?";
         ps=conn.prepareStatement(sql);
         ps.setObject(1,id);
        int i=ps.executeUpdate();
        System.out.println(i);
        if(i==0)
        {
            ParkBean parkBean1=new ParkBean();
            parkBean.setStatus(1);
            parkService.updateParkById(this.getParkOrderByid(id).getParkid(),parkBean1);

        }
        conn.close();
        return i;
    }

    /**
     * 得到所有的order
     * 返回null则没有拿到
     * @return
     * @throws SQLException
     */
    public List<ParkOrderBean> getAllParkOrder()throws SQLException
    {
        conn=DBUtil.getConnection();
        List<ParkOrderBean> orders=new ArrayList<ParkOrderBean>();
        String sql="select * from park_order";
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while (rs.next())
        {
            ParkOrderBean order=new ParkOrderBean();
            order.setId(rs.getInt("id"));
            order.setUserid(rs.getInt("userid"));
            order.setParkid(rs.getInt("parkid"));
            order.setStartdate(rs.getDate("startdate"));
            order.setEnddate(rs.getDate("enddate"));
            order.setStatus(rs.getInt("status"));
            orders.add(order);
        }
        conn.close();
        return orders;
    }

    /**
     * 得到所有的order
     * 返回null则没有拿到
     * @return
     * @throws SQLException
     */
    public ParkOrderBean getParkOrderByid(int id)throws SQLException
    {
        conn=DBUtil.getConnection();
        String sql="select * from park_order where id="+id;
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while (rs.next())
        {
            ParkOrderBean order=new ParkOrderBean();
            order.setId(rs.getInt("id"));
            order.setUserid(rs.getInt("userid"));
            order.setParkid(rs.getInt("parkid"));
            order.setStartdate(rs.getDate("startdate"));
            order.setEnddate(rs.getDate("enddate"));
            order.setStatus(rs.getInt("status"));
            return order;
        }
        conn.close();
        return null;
    }

    /**
     * 设置当前时间为结束时间
     * 成功返回1
     * 失败返回0
     * id不存在返回-1
     * @return
     * @throws SQLException
     */
    public int updateEnddate(int id) throws SQLException {
        if(this.getParkOrderByid(id)==null)
        {
            return -1;
        }
        ParkService parkService=new ParkService();
        //先更新
        ParkBean parkBean=new ParkBean();
        parkBean.setStatus(0);
        parkService.updateParkById(this.getParkOrderByid(id).getParkid(),parkBean);

        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        conn=DBUtil.getConnection();
        String sql="update park_order set enddate='"+sdf.format(date)+"',status=1 where id="+id;
        ps=conn.prepareStatement(sql);
        int i=ps.executeUpdate();
        if(i==0)
        {
            ParkBean parkBean1=new ParkBean();
            parkBean.setStatus(1);
            parkService.updateParkById(this.getParkOrderByid(id).getParkid(),parkBean1);
        }
        return i;
    }

//    /**
//     * 设置状态
//     * 1成功
//     * 0未找到id
//     * -1 status不符合
//     * @param id
//     * @param status
//     * @return
//     */
//    public int updateStatus(int id,int status) throws SQLException {
//        if(status<0||status>1)
//        {
//            return -1;
//        }
//        conn=DBUtil.getConnection();
//        String sql="update pard_order set status="+status+" where id="+id;
//        ps=conn.prepareStatement(sql);
//        int i=ps.executeUpdate();
//        return i;
//    }
}
