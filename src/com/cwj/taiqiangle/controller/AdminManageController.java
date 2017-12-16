package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.AdminBean;
import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.AdminService;
import com.cwj.taiqiangle.service.SendMailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * Created by 蛟川小盆友 on 2017/12/9.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminManageController {

    public static void main(String[] args) {
    }

    AdminService adminService = new AdminService();

    SendMailService sendMailService = new SendMailService();

    /**
     * Code:200 Data=id (id是String类型)管理员信息正确
     * Code:205 Data=0 管理员不存在
     * Code:206 Data=-3 管理员密码错误
     * Code:207 Data=-2 管理员账户被重复
     * Code:404 Data=-1 catch异常
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg adminLogin(String username, String password) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            if (adminService.getAdminByName(username).size() == 0) {
                jsonMsg.setCode("205");
                jsonMsg.setData(0);
            } else if (adminService.getAdminByName(username).size() != 1) {
                jsonMsg.setCode("207");
                jsonMsg.setData(-2);
            } else {
                String id;
                if (!adminService.getAdminByName(username).get(0).getPassword().equals(password)) {
                    jsonMsg.setCode("206");
                    jsonMsg.setData(-3);
                } else {
                    id = adminService.getAdminByName(username).get(0).getId();
                    jsonMsg.setCode("200");
                    jsonMsg.setData(adminService.getAdminById(id));
                }
            }
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * Code=404 Data=-1 抛出异常不做改变
     * Code=202 Data=0 不变/修改无效
     * Code=202 Data=-2 id不存在
     * Code=202 Data=-3 名字修改时产生重复
     * Code=200 Data>=1 修改成功 Data=修改的信息数量
     * 参数都可以为null，但是id为null无法更新
     * null的参数不做更新
     *
     * @param id
     * @param username
     * @param password
     * @param email
     * @param description
     * @param pic
     * @return
     */
    @RequestMapping(value = "/adminUpdate", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg updateAdmin(String id, String username, String password, String email, String description, String pic) {
        int status = 0;
        JsonMsg jsonMsg = new JsonMsg();
        AdminBean ub = new AdminBean();
        ub.setUsername(username);
        ub.setPassword(password);
        ub.setEmail(email);
        ub.setDescription(description);
        ub.setPic(pic);
        try {
            status = adminService.updateUserById(id, ub);
            jsonMsg.setData(status);
            if (status >= 1) {
                jsonMsg.setCode("200");
            } else {
                jsonMsg.setCode("202");
            }
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 向user的email发邮件
     *
     * @param email
     * @param username
     * @return
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg sendEmail(String email, String username) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            if (sendMailService.sendmail(email, username)) {
                jsonMsg.setCode("200");
                jsonMsg.setData("Send Email Success");
            } else {
                jsonMsg.setCode("205");
                jsonMsg.setData("Send Email Failed");
            }

        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }


//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonMsg login(String username, String password) {
//        JsonMsg jsonMsg = new JsonMsg();
//        try {
//            if (adminService.getAdminByNamePassword(username, password).size() == 0) {
//                jsonMsg.setCode("205");
//            } else {
//                jsonMsg.setCode("200");
//            }
//            jsonMsg.setData(adminService.getAdminByNamePassword(username, password));
//        } catch (Exception e) {
//            jsonMsg.setCode("404");
//            e.printStackTrace();
//        }
//        return jsonMsg;
//    }
//
//    @RequestMapping(value = "/getAdminByid", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonMsg login(String id) {
//        JsonMsg jsonMsg = new JsonMsg();
//        try {
//            jsonMsg.setCode("200");
//            jsonMsg.setData(adminService.getAdminById(id));
//        } catch (Exception e) {
//            jsonMsg.setCode("404");
//            e.printStackTrace();
//        }
//        return jsonMsg;
//    }


//    /**
//     * 这个是用来注册管理员
//     * 我加了一个Deprecated的annotation
//     *
//     * @param username
//     * @param password
//     * @param email
//     * @return
//     */
//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    @ResponseBody
//    @Deprecated
//    public JsonMsg addAdmin(String username, String password, String email) {
//        JsonMsg jsonMsg = new JsonMsg();
//        try {
//            jsonMsg.setCode("200");
//            jsonMsg.setData(adminService.addAdmin(username, password, email));
//        } catch (Exception e) {
//            jsonMsg.setCode("404");
//            e.printStackTrace();
//        }
//        return jsonMsg;
//    }
//
//    /**
//     * 用来注册普通用户
//     *
//     * @param username
//     * @param password
//     * @param email
//     * @return
//     */
//    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
//    @ResponseBody
//    public JsonMsg addUser(String username, String password, String email) {
//        JsonMsg jsonMsg = new JsonMsg();
//        jsonMsg.setCode("200");
//        jsonMsg.setData(1);
//        return jsonMsg;
//    }
//
//    @RequestMapping(value="/userLogin",method=RequestMethod.GET)
//    @ResponseBody
//    public JsonMsg userLogin(String username, String password){
//        return null;
//    }

}
