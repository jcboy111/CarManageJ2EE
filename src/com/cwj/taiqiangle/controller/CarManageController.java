package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蛟川小盆友 on 2017/12/4.
 */

@Controller
@RequestMapping(value = "/car")
public class CarManageController {

    CarService carService=new CarService();

    @RequestMapping(value="/helloj",method = RequestMethod.GET)
    @ResponseBody
    public String printWelcome() {
        return "hello";
    }

    @RequestMapping(value="/getCarRentIn",method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getAllRentInCars(String id) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(carService.getAllRentInCars());
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    @RequestMapping(value="/addCarRentIn",method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg addCarRentIn(String title,String user,String date,String pay,String status) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(carService.addRentInCar(title,user,date,pay,status));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    @RequestMapping(value="/deleteCarRentIn",method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg deletePeople(String id) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(carService.deleteRentInCar(id));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }

    @RequestMapping(value="/passCarRentIn",method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg passRentInCar(String id) {
        JsonMsg jsonMsg = new JsonMsg();
        try {
            jsonMsg.setCode("200");
            jsonMsg.setData(carService.passRentInCar(id));
        } catch (Exception e) {
            jsonMsg.setCode("404");
            e.printStackTrace();
        }
        return jsonMsg;
    }
}
