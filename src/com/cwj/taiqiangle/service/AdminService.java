package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.AdminBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2017/12/9.
 */
public class AdminService {
    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public List<AdminBean> getAllAdminBean() throws SQLException {
        conn = DBUtil.getConnection();
        List<AdminBean> adminBeans = new ArrayList<AdminBean>();
        String sql = "select * from admin";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            AdminBean adminBean = new AdminBean();
            adminBean.setId(rs.getString("id"));
            adminBean.setUsername(rs.getString("username"));
            adminBean.setPassword(rs.getString("password"));
            adminBean.setEmail(rs.getString("email"));
            adminBean.setDescription(rs.getString("description"));
            adminBean.setPic(rs.getString("pic"));
            adminBeans.add(adminBean);

        }
        return adminBeans;
    }

    public List<AdminBean> getAdminByNamePassword(String username, String password) throws SQLException {
        conn = DBUtil.getConnection();
        List<AdminBean> adminBeans = new ArrayList<AdminBean>();
        String sql = "select * from admin where username= ? and password= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, username);
        ps.setObject(2, password);
        rs = ps.executeQuery();
        while (rs.next()) {
            AdminBean adminBean = new AdminBean();
            adminBean.setId(rs.getString("id"));
            adminBean.setUsername(rs.getString("username"));
            adminBean.setPassword(rs.getString("password"));
            adminBean.setEmail(rs.getString("email"));
            adminBean.setDescription(rs.getString("description"));
            adminBean.setPic(rs.getString("pic"));
            adminBeans.add(adminBean);
        }
        return adminBeans;
    }

    public List<AdminBean> getAdminById(String id) throws SQLException {
        conn = DBUtil.getConnection();
        List<AdminBean> adminBeans = new ArrayList<AdminBean>();
        String sql = "select * from admin where id= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        rs = ps.executeQuery();
        while (rs.next()) {
            AdminBean adminBean = new AdminBean();
            adminBean.setId(rs.getString("id"));
            adminBean.setUsername(rs.getString("username"));
            adminBean.setPassword(rs.getString("password"));
            adminBean.setEmail(rs.getString("email"));
            adminBean.setDescription(rs.getString("description"));
            adminBean.setPic(rs.getString("pic"));
            adminBeans.add(adminBean);

        }
        return adminBeans;
    }

    public int addAdmin(String username, String password, String email) throws SQLException {
        conn = DBUtil.getConnection();
        String sql = "insert into admin (username,password,email) values(?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, username);
        ps.setObject(2, password);
        ps.setObject(3, email);
        int r = ps.executeUpdate();
        return r;
    }
}
