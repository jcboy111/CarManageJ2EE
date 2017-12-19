package com.cwj.taiqiangle.controller;


import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.CarOutService;
import com.cwj.taiqiangle.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "/car")
public class CarController {
    CarService orderService = new CarService();
    CarOutService outOrderService = new CarOutService();


    /**
     * Code = "200"  成功存入
     * Code = "202"  输入存在问题
     * Code = "203"  系统错误
     * @param sender_id
     * @param carName
     * @param stringPrice
     * @return
     */
    @RequestMapping(value = "/userAddCar", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg userAddCar(int sender_id, String carName, String stringPrice) {
        int price = 0;
        try {
            price = Integer.parseInt(stringPrice);
        } catch (Exception e) {
            System.out.println("Chenjie Debug");
            e.printStackTrace();
        }
        JsonMsg jsonMsg = new JsonMsg();
        if (price < 0 || carName == null) {
            jsonMsg.setCode("202");
            jsonMsg.setData(0);
        }
        try {
            int i = orderService.addCar(carName, price, "还没有实现照片上传");
            System.out.println("addCar之后的返回值是:"+i);
            if (i >= 1) {
                int r = outOrderService.add(sender_id, carName, price, "还没有实现照片上传");
                if (r == 1) {
                    jsonMsg.setCode("200");
                    jsonMsg.setData(1);
                } else {
                    jsonMsg.setCode("2031");
                    jsonMsg.setData(-1);
                }
            } else {
                jsonMsg.setCode("2032");
                jsonMsg.setData(-1);
            }
        } catch (SQLException e) {
            jsonMsg.setCode("2033");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

}
