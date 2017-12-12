package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.model.car;
import com.cwj.taiqiangle.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
/**
 * Created by 蛟川小盆友 on 2017/12/5.
 */
@Controller
@RequestMapping(value = "/people")
public class PeopleManageController {

    TestService testService=new TestService();

    @RequestMapping(value="/people",method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg printWelcome() {
        JsonMsg jsonMsg = new JsonMsg();

        try {
           jsonMsg.setCode("200");
           jsonMsg.setData(testService.getAllTestBean());
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    @RequestMapping(value="/delete",method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg deletePeople(String id) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(testService.deleteTestBean(id));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }
}
