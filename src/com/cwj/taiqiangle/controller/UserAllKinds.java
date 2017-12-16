package com.cwj.taiqiangle.controller;


import com.cwj.taiqiangle.model.AdminBean;
import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.AdminService;
import com.cwj.taiqiangle.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "/userAll")
public class UserAllKinds {
    AdminService adminService = new AdminService();
    UserService userService = new UserService();

    public static void main(String[] args){
        UserAllKinds test = new UserAllKinds();
        test.checkUser("jason66");
    }
    /**
     * Code: "1" 为管理员账号
     * Code: "2" 为普通用户账号
     * Code: "404" 账号不存在
     * Code: "-1" 内部故障
     *
     * @param username
     * @return JsonMsg
     * @auther ChenJie
     */
    @RequestMapping(value = "/checkUser", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg checkUser(String username) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            System.out.println(username);
            List<AdminBean> adminBeans = adminService.getAdminByName(username);
            System.out.println(this.getClass().getName()+"陈杰在Debug：在数据库中查找"+username+"。有多少条："+adminBeans.size());
            for (int i = 0; i < adminBeans.size(); i++) {
                System.out.println(i+" "+adminBeans.get(i).getUsername());
            }
            if (adminService.getAdminByName(username).size() == 1) {
                jsonMsg.setCode("1");
                System.out.println(jsonMsg.getCode());
                return jsonMsg;
            } else if (userService.getUserByName(username).size() == 1) {
                jsonMsg.setCode("2");
                return jsonMsg;
            } else {
                jsonMsg.setCode("404");
                return jsonMsg;
            }
        } catch (SQLException e) {
            jsonMsg.setCode("-1");
        }
        return jsonMsg;
    }
}
