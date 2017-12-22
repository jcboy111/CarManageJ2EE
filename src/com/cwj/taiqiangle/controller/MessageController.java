package com.cwj.taiqiangle.controller;


import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping(value="/msg")
public class MessageController {
    MessageService messageService=new MessageService();


    /**
     * 添加广播信息
     * Code=200 data=1成功
     * Code=200 data=0失败
     * Code=404 data=-1异常
     * @param msg
     * @return
     */
    @RequestMapping(value = "/msgAdd", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg msgAdd(String msg)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(messageService.addMsg(msg));
        } catch (SQLException e) {
            jsonMsg.setData(-1);
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * * 删除广播信息
     * Code=200 data=1成功
     * Code=200 data=0失败
     * Code=404 data=-1异常
     * @param id
     * @return
     */
    @RequestMapping(value = "/msgDelete", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg msgAdd(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(messageService.deleteMsg(id));
        } catch (SQLException e) {
            jsonMsg.setData(-1);
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * * 得到全部广播信息
     * Code=200 data=list成功
     * Code=200 data=null失败，没有信息
     * Code=404 data=-1异常
     * @return
     */
    @RequestMapping(value = "/msgTraversal", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg msgTraversal()
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(messageService.getAllMsg());
        } catch (SQLException e) {
            jsonMsg.setData(-1);
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }
}
