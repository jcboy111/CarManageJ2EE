package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.model.ParkBean;
import com.cwj.taiqiangle.model.ParkOrderBean;
import com.cwj.taiqiangle.model.UserBean;
import com.cwj.taiqiangle.service.ParkOrderService;
import com.cwj.taiqiangle.service.ParkService;
import com.cwj.taiqiangle.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/parkOrder")
public class ParkOrderController {


    /**
     * 添加parkOrder
     * 正常返回data=1 code=200
     * 添加失败，余额已经小于等于0返回data=0 code=202
     * 异常throw exception
     * userid非法返回data=-1 code=202
     * parkid非法返回data=-2 code=202
     * @param userid
     * @param parkid
     * @return
     */
    @RequestMapping(value = "/parkOrderAdd", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkOrderAdd(int userid,int parkid)
    {
        JsonMsg jsonMsg=new JsonMsg();



        ParkOrderService parkOrderService=new ParkOrderService();
        UserService userService=new UserService();
        try {
            if(userService.getUserById(userid).getMoney()<=0)
            {
                jsonMsg.setCode("2021");
                jsonMsg.setData(0);
                return jsonMsg;
            }

            int i=parkOrderService.addParkOrder(userid,parkid);
            jsonMsg.setData(i);
            if(i==1)
            {
                jsonMsg.setCode("200");
                System.out.println("ChenJie Debug:成功租用了停车位");
            }
            else{
                jsonMsg.setCode("2022");
            }
        } catch (Exception e) {
            jsonMsg.setCode("2023");
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * 结束parkOrder
     * code=200 data=1成功
     * code=404 data=-1失败
     * @param id
     * @return
     */
    @RequestMapping(value = "/parkOrderFinish", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkOrderFinish(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        ParkOrderService parkOrderService=new ParkOrderService();
        ParkService parkService=new ParkService();
        UserService userService=new UserService();
        try {
            if(parkOrderService.getParkOrderByid(id).getStatus()==1)
            {
                throw new Exception("已经操作过了");
            }

            int status=parkOrderService.updateEnddate(id);
            if(status==1)
            {
                ParkOrderBean parkOrderBean=parkOrderService.getParkOrderByid(id);
                ParkBean parkBean=parkService.getParkById(parkOrderBean.getParkid());
                int days= (int) ((parkOrderBean.getEnddate().getTime()-parkOrderBean.getStartdate().getTime())/(1000*3600*24))+1;
                int spend=days*parkBean.getPrice_per_day();
                int yue=userService.getUserById(parkOrderBean.getUserid()).getMoney()-spend;
                UserBean userBean=new UserBean();
                userBean.setMoney(yue);
                userService.updateUserById(parkOrderBean.getUserid(),userBean);
                jsonMsg.setCode("200");
                jsonMsg.setData(1);
                return jsonMsg;
            }
            throw new Exception("非法的操作");
        } catch (Exception e) {
            jsonMsg.setData(-1);
            jsonMsg.setCode("404");
            e.printStackTrace();
        }

        return jsonMsg;
    }

    /**
     * 通过userid来得到orders
     * 这里用了个比较消耗时间的方法，但是表不打我就偷个懒不在controller开新的方法了
     * Code=200 取出成功
     * code=404 data=-1异常
     * @param userid
     * @return
     */
    @RequestMapping(value = "/getParkorderByUserid", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getParkorderByUserid(int userid)
    {
        JsonMsg jsonMsg=new JsonMsg();
        ParkOrderService parkOrderService=new ParkOrderService();
        try {
           List<ParkOrderBean> orders= parkOrderService.getAllParkOrder();
            List<ParkOrderBean> ordersOut=new ArrayList<ParkOrderBean>();
            for(ParkOrderBean parkOrderBean:orders)
            {
                if(parkOrderBean.getUserid()==userid)
                {
                    ordersOut.add(parkOrderBean);
                }
            }
            jsonMsg.setCode("200");
            jsonMsg.setData(ordersOut);
        } catch (Exception e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 得到所有的order
     * Code=200 List<ParkOrderBean>
     * Code=404 data=-1页面异常
     * @return
     */
    @RequestMapping(value = "/parkOrderTraversal", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkOrderTraversal()
    {
        JsonMsg jsonMsg=new JsonMsg();
         ParkOrderService parkOrderService=new ParkOrderService();
        try {
            List<ParkOrderBean> parkOrderBeans=parkOrderService.getAllParkOrder();
            jsonMsg.setCode("200");
            jsonMsg.setData(parkOrderBeans);
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * 删除id 的parkOrder
     * code=200 data=1成功
     * code=200 data=0删除失败
     * code=200 data=-1删除失败 原因是订单（这个id的）不存在了
     * code=404 data=-1异常
     * @param id
     * @return
     */
    @RequestMapping(value = "/parkOrderDelete", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkOrderDelete(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        ParkOrderService parkOrderService=new ParkOrderService();
        try {

            int i=parkOrderService.deleteParkOrder(id);
            jsonMsg.setData(i);
            jsonMsg.setCode("200");

        } catch (SQLException e) {
            jsonMsg.setData(-1);
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }


}
