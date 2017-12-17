package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.AdminBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;


    /**
     * 得到所有管理员信息
     * @return
     * @throws SQLException
     */
    public List<AdminBean> getAllAdminBean() throws SQLException {
        conn = DBUtil.getConnection();
        List<AdminBean> adminBeans = new ArrayList<AdminBean>();
        String sql = "select * from admin";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            AdminBean adminBean = new AdminBean();
            adminBean.setId(rs.getInt("id"));
            adminBean.setUsername(rs.getString("username"));
            adminBean.setPassword(rs.getString("password"));
            adminBean.setEmail(rs.getString("email"));
            adminBean.setDescription(rs.getString("description"));
            adminBean.setPic(rs.getString("pic"));
            adminBeans.add(adminBean);


        }
        conn.close();
        return adminBeans;
    }

    /**
     * 通过管理员名字查找对应管理员
     * @param username
     * @return
     * @throws SQLException
     */
    public List<AdminBean> getAdminByName(String username) throws SQLException {
        conn = DBUtil.getConnection();
        List<AdminBean> adminBeans = new ArrayList<AdminBean>();
        String sql = "select * from admin where username= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, username);
        rs = ps.executeQuery();
        while (rs.next()) {
            AdminBean adminBean = new AdminBean();
            adminBean.setId(rs.getInt("id"));
            adminBean.setUsername(rs.getString("username"));
            adminBean.setPassword(rs.getString("password"));
            adminBean.setEmail(rs.getString("email"));
            adminBean.setDescription(rs.getString("description"));
            adminBean.setPic(rs.getString("pic"));
            adminBeans.add(adminBean);

        }
        conn.close();
        return adminBeans;
    }

    /**
     * 通过ID得到admin的信息
     * @param id
     * @return
     * @throws SQLException
     */
    public AdminBean getAdminById(int id) throws SQLException {
        conn = DBUtil.getConnection();
        String sql = "select * from admin where id= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        rs = ps.executeQuery();
        while (rs.next()) {
            AdminBean adminBean = new AdminBean();
            adminBean.setId(rs.getInt("id"));
            adminBean.setUsername(rs.getString("username"));
            adminBean.setPassword(rs.getString("password"));
            adminBean.setEmail(rs.getString("email"));
            adminBean.setDescription(rs.getString("description"));
            adminBean.setPic(rs.getString("pic"));
            conn.close();
            return adminBean;
        }
        conn.close();
        return null;
    }

    /**
     * 对AdminBean中非null值更新
     * 更新成功返回>=1
     * 不变返回0
     * id不存在返回-2
     * 修改名字产生重复返回-3
     * @param id
     * @param ub
     * @return
     */
    public int updateUserById(int id,AdminBean ub)throws SQLException
    {
        int status=0;
        List<AdminBean>users;

        if(getAdminById(id)==null)
        {
            status=-2;
            return status;
        }
        if(ub.getUsername()!=null&&(users=getAdminByName(ub.getUsername())).size()!=0)
        {

            if(users.size()!=1)
            {
                status=-3;
                return status;
            }
            else if(users.get(0).getId()!=id)
            {
                // System.out.println(users.size());
                status=-3;
                return status;
            }
        }
        conn=DBUtil.getConnection();
        if(ub.getUsername()!=null)
        {
            String sql="update admin set username='"+ub.getUsername()+"' where id="+id+"";
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0) {
                status++;
            }

        }
        if(ub.getPassword()!=null)
        {
            String sql="update admin set password='"+ub.getPassword()+"' where id="+id+"";
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if (ub.getEmail()!=null)
        {
            String sql="update admin set email='"+ub.getEmail()+"' where id="+id+"";
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if(ub.getDescription()!=null)
        {
            String sql = null;
            try {
                String str1 = new String("update admin set description='".getBytes(),"utf-8");
                String str2 = new String(ub.getDescription().getBytes(),"utf-8");
                String str3 = new String(("' where id=" + id + "").getBytes(),"utf-8");
                sql = str1+str2+str3;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //System.out.println(sql);
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }
        if(ub.getPic()!=null)
        {
            String sql="update admin set pic='"+ub.getPic()+"' where id="+id+"";
            ps=conn.prepareStatement(sql);
            if(ps.executeUpdate()!=0)
            {
                status++;
            }
        }

        return status;



    }

}
