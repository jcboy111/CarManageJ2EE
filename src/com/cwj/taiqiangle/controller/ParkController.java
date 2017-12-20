package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.model.ParkBean;
import com.cwj.taiqiangle.service.ParkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/park")
public class ParkController {

    ParkService parkService=new ParkService();

    /**
     * 添加park
     *自动生成id
     * Code200 1成功添加
     * Code404 -1 添加失败/异常/price格式错误/缺少参数
     * @param name
     * @param price
     * @return
     */
    @RequestMapping(value = "/parkAdd", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkAdd(String name,String price)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            if(name==null||price==null)
            {
                throw new Exception("缺少参数");
            }
            int i=parkService.addPark(name,Integer.parseInt(price));
            jsonMsg.setCode("200");
            jsonMsg.setData(1);
        } catch (Exception e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     *利用id删除park
     * code200 1找到并删除成功
     * code200 0没有找到
     * code202 0车位正在被使用，不可以删除
     * code404 -1删除异常
     * @param id
     * @return
     */
    @RequestMapping(value = "/parkDelete", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkDelete(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            ParkBean park=parkService.getParkById(id);
            if(park.getStatus()==0)
            {
                int i=parkService.deleteParkById(id);
                jsonMsg.setCode("200");
                jsonMsg.setData(i);
            }
            else{
                jsonMsg.setCode("202");
                jsonMsg.setData(0);
            }

        }
        catch (Exception e)
        {
            jsonMsg.setCode("404");
            jsonMsg.setData("-1");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     *Code 200 得到所有parks
     * Code 404  -1异常
     * @return
     */
    @RequestMapping(value = "/parkTraversal", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkTraversal()
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            List<ParkBean> parks=parkService.getAllPark();
            jsonMsg.setCode("200");
            jsonMsg.setData(parks);
        }
        catch (Exception e)
        {
            jsonMsg.setCode("404");
            jsonMsg.setData("-1");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     *id必须设置
     * 其他参数不设置代表不更新
     * Code 200 data=1更新成功
     * Code 200 data=0 没有更新
     * Code 404 异常
     * @param id
     * @param name
     * @param price_per_day
     * @param status
     * @return
     */
    @RequestMapping(value = "/parkUpdate", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkUpdate(int id,String name,String price_per_day,String status)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            ParkBean parkBean=new ParkBean();
            if(name!=null)
            {
                parkBean.setName(name);
            }
            if(price_per_day!=null)
            {
                parkBean.setPrice_per_day(Integer.parseInt(price_per_day));
            }
            if(status!=null)
            {
                parkBean.setStatus(Integer.parseInt(status));
            }
            int i=parkService.updateParkById(id,parkBean);
            jsonMsg.setCode("200");
            jsonMsg.setData(1);
        }
        catch (Exception e)
        {
            jsonMsg.setCode("404");
            jsonMsg.setData("-1");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
    *Code 200 得到所有空闲状态的park
     * Code 404 异常
    * @return
    */
    @RequestMapping(value = "/parkIdleTraversal", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg parkIdleTraversal()
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            List<ParkBean> parkBeans=parkService.getIdlePark();
            jsonMsg.setCode("200");
            jsonMsg.setData(parkBeans);
        }
        catch (Exception e)
        {
            jsonMsg.setCode("404");
            jsonMsg.setData("-1");
            e.printStackTrace();
        }
        return jsonMsg;
    }

}
