package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.AdminService;
import com.cwj.taiqiangle.service.SendMailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蛟川小盆友 on 2017/12/9.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminManageController {

    AdminService adminService = new AdminService();

    SendMailService sendMailService = new SendMailService();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg login(String username, String password) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            if (adminService.getAdminByNamePassword(username, password).size() == 0) {
                jsonMsg.setCode("205");
            } else {
                jsonMsg.setCode("200");
            }
            jsonMsg.setData(adminService.getAdminByNamePassword(username, password));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/getAdminByid", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg login(String id) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(adminService.getAdminById(id));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

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

    /**
     * 这个是用来注册管理员
     * 我加了一个Deprecated的annotation
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public JsonMsg addAdmin(String username, String password, String email) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(adminService.addAdmin(username, password, email));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 用来注册普通用户
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg addUser(String username, String password, String email) {
        JsonMsg jsonMsg = new JsonMsg();
        jsonMsg.setCode("200");
        jsonMsg.setData(1);
        return jsonMsg;
    }

    @RequestMapping(value="/userLogin",method=RequestMethod.GET)
    @ResponseBody
    public JsonMsg userLogin(String username, String password){
        return null;
    }

}
