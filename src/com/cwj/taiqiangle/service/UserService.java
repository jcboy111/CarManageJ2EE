package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.UserBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;


    /**
     * 得到所有的用户数据，封装在UserBean中，装在载list中返回
     *
     * @return
     * @throws SQLException
     */
    public List<UserBean> getAllUserBean() throws SQLException {
        conn = DBUtil.getConnection();
        List<UserBean> userBeans = new ArrayList<UserBean>();
        String sql = "select * from user";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            UserBean userBean = new UserBean();
            userBean.setId(rs.getString("id"));
            userBean.setUserName(rs.getString("username"));
            userBean.setPassword(rs.getString("password"));
            userBean.setEmail(rs.getString("email"));
            userBean.setDescription(rs.getString("description"));
            userBean.setPic(rs.getString("pic"));
            userBean.setMoney(rs.getInt("money"));
            if (userBean != null && userBean.getId() != null) {
                userBeans.add(userBean);
            }
        }
        conn.close();
        return userBeans;
    }

    /**
     * 通过Name来得到UserBean，为了防止重复情况用，返回list
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public List<UserBean> getUserByName(String name) throws SQLException {
        conn = DBUtil.getConnection();
        List<UserBean> userBeans = new ArrayList<UserBean>();
        String sql = "select * from user where username= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, name);
        rs = ps.executeQuery();
        while (rs.next()) {
            UserBean userBean = new UserBean();
            userBean.setId(rs.getString("id"));
            userBean.setUserName(rs.getString("username"));
            userBean.setPassword(rs.getString("password"));
            userBean.setEmail(rs.getString("email"));
            userBean.setDescription(rs.getString("description"));
            userBean.setPic(rs.getString("pic"));
            userBean.setMoney(rs.getInt("money"));
            if (userBean != null && userBean.getId() != null) {
                userBeans.add(userBean);
            }
        }
        conn.close();
        ;
        return userBeans;
    }

    /**
     * 通过id查找UserBean
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public UserBean getUserById(String id) throws SQLException {
        conn = DBUtil.getConnection();
        String sql = "select * from user where id= ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        rs = ps.executeQuery();
        while (rs.next()) {
            UserBean userBean = new UserBean();
            userBean.setId(rs.getString("id"));
            userBean.setUserName(rs.getString("username"));
            userBean.setPassword(rs.getString("password"));
            userBean.setEmail(rs.getString("email"));
            userBean.setDescription(rs.getString("description"));
            userBean.setPic(rs.getString("pic"));
            userBean.setMoney(rs.getInt("money"));
            conn.close();
            return userBean;
        }
        conn.close();
        return null;
    }


    /**
     * 增加用户，如果用户名重复，则插入失败，返回0；
     * 默认金钱是0
     *
     * @param username
     * @param password
     * @param email
     * @param description
     * @param pic
     * @param money
     * @return
     * @throws SQLException
     */
    public int addUser(String username, String password, String email, String description, String pic, int money) throws SQLException {


        /**
         * 我猜测，你在这里是想要看看数据库里面有没有一样的用户。但是这个方法效率太低了。
         * 直接用getUserByName(username):List<UserBean>，如果返回的list非空，那么就有重复的
         * 另外这里的conn还没有获得，是不能直接close掉的。
         * 虽然说这个conn已经在getAllUserBean()里面打开了，但是也在那个东西里面关掉了
         */
        /*List<UserBean> users = this.getAllUserBean();
            for (UserBean ub : users) {
            if (ub.getUserName().equals(username)) {
                System.out.println("这里还没有bug");
                *//* conn.close();*//*
                return 0;
            }
        }*/

        if(this.getUserByName(username).size()>0){
            return 0;
        }
        conn = DBUtil.getConnection();
        String sql = "insert into user(username,password,email,description,pic,money) values(?,?,?,?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, username);
        ps.setObject(2, password);
        ps.setObject(3, email);
        ps.setObject(4, description);
        ps.setObject(5, pic);
        ps.setObject(6, money);
        int r = ps.executeUpdate();
        conn.close();
        return r;
    }

    /**
     * 通过id来删除用户账户
     * 返回0代表此id已经不在账户中
     * 返回1代表删除成功
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public int removeUserById(String id) throws SQLException {
        int i = 0;
        conn = DBUtil.getConnection();
        String sql = "delete from user where id=?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        i = ps.executeUpdate();
        conn.close();
        return i;
    }

    /**
     * 对UserBean中非null值更新
     * 更新成功返回>=1
     * 不变返回0
     * id不存在返回-2
     * 修改名字产生重复返回-3
     *
     * @param id
     * @param ub
     * @return
     */

    public int updateUserById(String id, UserBean ub) throws SQLException {
        int status = 0;
        List<UserBean> users;

        if (getUserById(id) == null) {
            status = -2;
            return status;
        }
        if (ub.getUserName() != null && (users = getUserByName(ub.getUserName())).size() != 0) {

            if (users.size() != 1) {
                status = -3;
                return status;
            } else if (!users.get(0).getId().equals(id)) {
                // System.out.println(users.size());
                status = -3;
                return status;
            }
        }
        conn = DBUtil.getConnection();
        if (ub.getUserName() != null) {
            String sql = "update user set username='" + ub.getUserName() + "' where id='" + id + "'";
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 0) {
                status++;
            }

        }
        if (ub.getPassword() != null) {
            String sql = "update user set password='" + ub.getPassword() + "' where id='" + id + "'";
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 0) {
                status++;
            }
        }
        if (ub.getEmail() != null) {
            String sql = "update user set email='" + ub.getEmail() + "' where id='" + id + "'";
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 0) {
                status++;
            }
        }
        if (ub.getDescription() != null) {
            String sql = null;
            try {
                String str1 = new String("update user set description='".getBytes(), "utf-8");
                String str2 = new String(ub.getDescription().getBytes(), "utf-8");
                String str3 = new String(("' where id='" + id + "'").getBytes(), "utf-8");
                sql = str1 + str2 + str3;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //System.out.println(sql);
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 0) {
                status++;
            }
        }
        if (ub.getPic() != null) {
            String sql = "update user set pic='" + ub.getPic() + "' where id='" + id + "'";
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 0) {
                status++;
            }
        }
        if (ub.getMoney() != Integer.MIN_VALUE) {
            String sql = "update user set money=" + ub.getMoney() + " where id='" + id + "'";
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 0) {
                status++;
            }
        }

        return status;


    }


    public static void main(String[] args) {
        UserService us = new UserService();
        //try {
//        us.addUser("Jiss","a21dwa","690385702@qq.com","dada","dadwad",4141);
//        List<UserBean> users=us.getUserByName("ceej");
//            for(UserBean ub:users)
//            {
//                System.out.println(
//                        ub.getId()+" "
//                                +ub.getUserName()+" "
//                                +ub.getPassword()+" "
//                                +ub.getEmail()+" "
//                                +ub.getDescription()+" "
//                                +ub.getPic()+" "
//                                +ub.getMoney()
//                );
//            }
//            UserBean ub=new UserBean();
//            ub.setMoney(2131);
//            ub.setUserName("User");
//            ub.setPassword("dwadawa");
//            System.out.println(us.updateUserById("6",ub));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
//    /**
//     * Get XML String of utf-8
//     *
//     * @return XML-Formed string
//     */
//    public String getUTF8XMLString(String xml) {
//        // A StringBuffer Object
//        StringBuffer sb = new StringBuffer();
//        sb.append(xml);
//        String xmString = "";
//        String xmlUTF8="";
//        try {
//            xmString = new String(sb.toString().getBytes("utf8"));
//            xmlUTF8 = URLEncoder.encode(xmString, "utf8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        // return to String Formed
//        return xmlUTF8;
//    }

}
