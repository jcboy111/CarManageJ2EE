package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.TestBean;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2017/12/5.
 */
public class TestService {

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public  List<TestBean> getAllTestBean() throws SQLException {
        conn= DBUtil.getConnection();
        List<TestBean>testBeans=new ArrayList<TestBean>();
        String sql="select * from test";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
           TestBean testBean=new TestBean();
            testBean.setId(rs.getString("id"));
            testBean.setName(rs.getString("name"));
            testBean.setSex(rs.getString("sex"));
            testBean.setAddress(rs.getString("address"));
            testBean.setPhone(rs.getString("phone"));
            testBeans.add(testBean);

        }
        return testBeans;
    }

    public int deleteTestBean(String id) throws SQLException {
        conn= DBUtil.getConnection();
        String sql = "delete from test where id = ?";
        ps = conn.prepareStatement(sql);
        ps.setObject(1, id);
        int r=ps.executeUpdate();
        return r;
    }


}
