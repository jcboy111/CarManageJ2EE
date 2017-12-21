package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.MessageBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    public static void main(String[] args)
    {
        MessageService messageService=new MessageService();
        try {
            messageService.addMsg("哈哈哈哈");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public int addMsg(String msg) throws SQLException {
        conn= DBUtil.getConnection();
        String sql="insert into broadcast(msg,time) values(?,?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,msg);
        ps.setObject(2,new Date(System.currentTimeMillis()));
        return ps.executeUpdate();
    }

    public int deleteMsg(int id) throws SQLException {
        conn= DBUtil.getConnection();
        String sql="delete from broadcast where id=?";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,id);
        return ps.executeUpdate();
    }

    public List<MessageBean> getAllMsg()throws  SQLException
    {
        conn=DBUtil.getConnection();
        List<MessageBean> msgs=new ArrayList<MessageBean>();
        String sql="select * from broadcast";
        ps=conn.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next())
        {
            MessageBean msg=new MessageBean();
            msg.setId(rs.getInt("id"));
            msg.setMsg(rs.getString("msg"));
            msg.setTime(rs.getDate("time"));
            msgs.add(msg);

        }
        return msgs;
    }
}
