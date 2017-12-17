package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.ParkBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkService {
    public static void main(String[] args)
    {
        ParkService ps=new ParkService();
        try {
            ParkBean pb=ps.getParkById(7);
            System.out.println(pb.getId()+" "+pb.getName()+" "+pb.getPrice_per_day()+" "+pb.getStatus());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    /**
     * 增加
     * 删除
     * 全部取出
     * 更新，名字
     * 更新价格
     * 更新状态
     * 算钱
     *
     */

    /**
     * 增加车位
     * 0添加失败
     * 1添加成功
     * @param name
     * @param price
     * @return
     * @throws SQLException
     */
    public int  addPark(String name,int  price) throws SQLException {
        conn= DBUtil.getConnection();
        String sql="insert into park(name,price_per_day,status) values(?,?,?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,name);
        ps.setObject(2,price);
        ps.setObject(3,0);
        int i=ps.executeUpdate();
        conn.close();
        return i;

    }

    /**
     * 根据id删除park
     * 0失败
     * 1成功
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteParkById(int id)throws SQLException
    {
        conn=DBUtil.getConnection();
        String sql="delete from park where id=?";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,id);
        int i=ps.executeUpdate();
        conn.close();
        return i;
    }

    /**
     * 得到所有的park
     * null为空
     * @return
     * @throws SQLException
     */
    public List<ParkBean> getAllPark() throws SQLException {
        conn=DBUtil.getConnection();
        List<ParkBean> parks=new ArrayList<ParkBean>();
        String sql="select * from park";
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while (rs.next())
        {
            ParkBean park=new ParkBean();
            park.setId(rs.getInt("id"));
            park.setName(rs.getString("name"));
            park.setPrice_per_day(rs.getInt("price_per_day"));
            park.setStatus(rs.getInt("status"));
            parks.add(park);
        }
        conn.close();
        return parks;

    }

    /**
     * 得到所有idle的park
     * null为空
     * @return
     * @throws SQLException
     */
    public List<ParkBean> getIdlePark() throws SQLException {
        conn=DBUtil.getConnection();
        List<ParkBean> parks=new ArrayList<ParkBean>();
        String sql="select * from park where status=0";
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while (rs.next())
        {
            ParkBean park=new ParkBean();
            park.setId(rs.getInt("id"));
            park.setName(rs.getString("name"));
            park.setPrice_per_day(rs.getInt("price_per_day"));
            park.setStatus(rs.getInt("status"));
            parks.add(park);
        }
        conn.close();
        return parks;
    }

    public int updateParkById(int id,ParkBean park) throws SQLException {
        int status=0;
        conn=DBUtil.getConnection();
        if(park.getName()!=null)
        {
            String sql="update park set name='"+park.getName()+"' where id="+id;
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if(park.getPrice_per_day()!=Integer.MIN_VALUE)
        {
            String sql="update park set price_per_day="+park.getPrice_per_day()+" where id="+id;
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if(park.getStatus()!=Integer.MIN_VALUE)
        {
            String sql="update park set status="+park.getStatus()+" where id="+id;
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                System.out.println("1111");
                status++;
            }
        }
        conn.close();
        return status;
    }

    public ParkBean getParkById(int id) throws SQLException {
        conn= DBUtil.getConnection();
        String sql="select * from park where id= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1,id);
        rs = ps.executeQuery();
        while(rs.next()){
            ParkBean userBean=new ParkBean();
            userBean.setId(id);
            userBean.setName(rs.getString("name"));
            userBean.setPrice_per_day(rs.getInt("price_per_day"));
            userBean.setStatus(rs.getInt("status"));
            conn.close();
            return userBean;
        }
        conn.close();
        return null;
    }
}
