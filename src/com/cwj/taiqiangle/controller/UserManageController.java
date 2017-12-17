package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.model.UserBean;
import com.cwj.taiqiangle.service.SendMailService;
import com.cwj.taiqiangle.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserManageController {
    UserService userService = new UserService();
    SendMailService sendMailService = new SendMailService();


    /**
     * Code:200 Data=userBean 用户信息正确
     * Code:205 Data=0 用户不存在
     * Code:206 Data=-3 用户密码错误
     * Code:207 Data=-2 用户账户被重复
     * Code:404 Data=-1 catch异常
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg userLogin(String username, String password) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            if (userService.getUserByName(username).size() == 0) {
                jsonMsg.setCode("205");
                jsonMsg.setData(0);
            } else if (userService.getUserByName(username).size() != 1) {
                jsonMsg.setCode("207");
                jsonMsg.setData(-2);
            }
            else{
                int id;
                if(!userService.getUserByName(username).get(0).getPassword().equals(password))
                {
                    jsonMsg.setCode("206");
                    jsonMsg.setData(-3);
                } else {
                    id = userService.getUserByName(username).get(0).getId();
                    jsonMsg.setCode("200");
                    jsonMsg.setData(userService.getUserById(id));
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
     * 注册用户
     * Code="200"，Data=1（int）则注册成功
     * Code="202"，Data=0 (int)注册失败，用户已经存在
     * Code="404"  Data=-1页面丢失
     *
     * @param username
     * @param password
     * @param email
     * @return
     */

    @RequestMapping(value = "/userRegister", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg addUser(String username, String password, String email) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            int result =
                    userService.addUser(username, password, email, null, null, 0);
            if (result == 0) {
                jsonMsg.setData(0);
                jsonMsg.setCode("202");
            } else {
                jsonMsg.setData(1);
                jsonMsg.setCode("200");
            }


        } catch (Exception e) {
            jsonMsg.setData(-1);
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * 删除用户（管理员操作）
     * Code=200，Data=1，删除成功
     * Code=202，Data=0，删除失败
     * Code=404，Data=-1页面丢失
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/userDelete", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg removeUserById(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {

            int i = userService.removeUserById(id);
            if (i == 1) {
                jsonMsg.setCode("200");
                jsonMsg.setData(1);
            } else {
                jsonMsg.setCode("202");
                jsonMsg.setData(0);
            }
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 发送邮件
     * Code=200 Data=1发送成功
     * Code=205 Data=0发送失败
     * Code=404 Data=-1页面失效
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
                jsonMsg.setData(1);
            } else {
                jsonMsg.setCode("205");
                jsonMsg.setData(0);
            }

        } catch (Exception e) {
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
     * @param money
     * @return
     */
    @RequestMapping(value = "/userUpdate", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg updateUser(int id,String username,String password,String email,String description,String pic,String money)
    {
        int status=0;
        JsonMsg jsonMsg=new JsonMsg();
        UserBean ub=new UserBean();
        ub.setUserName(username);
        ub.setPassword(password);
        ub.setEmail(email);
        ub.setDescription(description);
        ub.setPic(pic);
        try {
            ub.setMoney(Integer.parseInt(money));
        } catch (Exception e) {
            ub.setMoney(Integer.MIN_VALUE);
        }
        try {
            status = userService.updateUserById(id, ub);
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
     * JsonMsg ID具体查找用户
     * Code:404,Data=-1查找失败
     * Code:200,Data=UserBean 查找成功
     * Code：202，Data=0，没有查到
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/userLocate", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getUserByID(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            //陈杰-->曹威杰：我觉得这里的代码写的有点奇怪。我改了一下
/*            List<UserBean> users= userService.getUserById(id);
            if(users.size()==0)
            {
                jsonMsg.setData(0);
                jsonMsg.setCode("202");
            }
            else if(users.size()==1){
                jsonMsg.setData(users.get(0));
                jsonMsg.setCode("200");
            }
            else{
                throw new SQLException("用户名被重复，sql出现问题");
            }*/


            UserBean user = userService.getUserById(id);
            if(user!=null){
                jsonMsg.setCode("200");
                jsonMsg.setData(user);
            } else {
                jsonMsg.setCode("202");
                jsonMsg.setData(0);
            }

            //曹威杰：以上就是我的修改
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * Code200，Data List<UserBean>
     * Code404.Data -1 取出失败
     *
     * @return
     */
    @RequestMapping(value = "/userTraversal", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getUsers() {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setData(userService.getAllUserBean());
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * Code200，UserBean
     * Code202,用户不存在
     * Code404 用户被重复/
     * Code404.Data -1 取出失败
     * @return
     */
    @RequestMapping(value = "/getUserByName", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getUsersByName(String username)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            List<UserBean> users= userService.getUserByName(username);
            if(users.size()==0)
            {
                jsonMsg.setData(0);
                jsonMsg.setCode("202");
            }
            else if(users.size()==1){
                jsonMsg.setData(users.get(0));
                jsonMsg.setCode("200");
            }
            else{
                throw new SQLException("用户名被重复，sql出现问题");
            }

        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }



}
