package com.cwj.taiqiangle.controller;

import com.cwj.taiqiangle.model.CarBean;
import com.cwj.taiqiangle.model.CarOutBean;
import com.cwj.taiqiangle.model.JsonMsg;
import com.cwj.taiqiangle.service.CarOutService;
import com.cwj.taiqiangle.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/carout")
public class CarOutController {
    CarOutService orderService=new CarOutService();


    //对于用户

    /**
     *添加订单，必须设置名字和价格
     * code=200 data=1添加成功
     * code=200 data=0添加失败
     * code=202 data=0参数有错误
     * code=404 data=-1异常
     * @param sender_id
     * @param name
     * @param price
     * @param pic
     * @return
     */
    @RequestMapping(value = "/orderAdd", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderAdd(int sender_id,String name,String price,String pic)
    {
        JsonMsg jsonMsg=new JsonMsg();
        if(price==null||name==null)
        {
            jsonMsg.setCode("202");
            jsonMsg.setData(0);
        }
        try {
            int i=orderService.add(sender_id,name,Integer.parseInt(price),pic);
            jsonMsg.setCode("200");
            jsonMsg.setData(i);
            System.out.println("successfully insert into database");
        } catch (Exception e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * 删除一个订单
     * code=200 data=1删除成功
     * code=200 data=0删除失败
     * code=404 data=-1删除失败
     * @param id  订单的id
     * @return
     */
    @RequestMapping(value = "/orderDelete", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderDelete(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            jsonMsg.setData(orderService.deleteOrder(id));
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }

        return jsonMsg;
    }


    /**
     * 修改订单对应的车辆信息
     * code=200 data=1修改成功
     * code=200 data=0 修改失败
     * code=202 data=0 id不存在
     * code=404 data=-1 异常抛出，比如price不是数字
     * @param id
     * @param name
     * @param price
     * @param pic
     * @return
     */
    @RequestMapping(value = "/orderModify", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderModify(int id,String name,String price,String pic)
    {
        JsonMsg jsonMsg=new JsonMsg();

        try {
            CarBean car=new CarBean();
            CarService carService=new CarService();
            car.setName(name);
            if(price==null)
                car.setPrice(Integer.MIN_VALUE);
            else
                car.setPrice(Integer.parseInt(price));
            car.setPic(pic);
            if(orderService.getOrderById(id)==null)
            {
                jsonMsg.setCode("202");
                jsonMsg.setData(0);
            }
            jsonMsg.setData(carService.updateCar(orderService.getOrderById(id).getCar_id(),car));
            jsonMsg.setCode("200");
        } catch (Exception e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 得到和myId相关的所有订单
     * code=200，data存这些订单
     * code=404 data=-1异常
     * @param myId
     * @return
     */
    @RequestMapping(value = "/myOrder", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getMyOrder(int myId)
    {
        JsonMsg jsonMsg=new JsonMsg();
        List<CarOutBean> orders= null;
        List<CarOutBean> myOrders=new ArrayList<CarOutBean>();
        try {
            orders = orderService.getAllOrder();
            for(CarOutBean car:orders)
            {
                if(car.getSender_id()==myId||car.getReceiver_id()==myId)
                {
                    myOrders.add(car);
                }
            }
            jsonMsg.setData(myOrders);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 得到和myId提交的车辆的订单
     * code=200，data存这些订单
     * code=404 data=-1异常
     * @param myId
     * @return
     */
    @RequestMapping(value = "/myUpOrder", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getMyUpOrder(int myId)
    {
        JsonMsg jsonMsg=new JsonMsg();
        List<CarOutBean> orders= null;
        List<CarOutBean> myOrders=new ArrayList<CarOutBean>();
        try {
            orders = orderService.getAllOrder();
            for(CarOutBean car:orders)
            {
                if(car.getSender_id()==myId)
                {
                    myOrders.add(car);
                }
            }
            jsonMsg.setData(myOrders);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * 得到myId租进来的订单
     * code=200，data存这些订单
     * code=404 data=-1异常
     * @param receiverId
     * @return JsonMsg
     * @author 陈杰
     */
    @RequestMapping(value = "/myInOrder", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getMyInOrder(int receiverId)
    {
        JsonMsg jsonMsg=new JsonMsg();
        List<CarOutBean> orders= null;
        List<CarOutBean> myOrders=new ArrayList<CarOutBean>();
        try {
            orders = orderService.getOrdersByReceiverId(receiverId);
            /*for(CarOutBean car:orders)
            {
                if(car.getSender_id()==myId||car.getReceiver_id()==myId)
                {
                    myOrders.add(car);
                }
            }
            jsonMsg.setData(myOrders);*/
            jsonMsg.setData(orders);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }



    /**
     * 得到和passed的所有订单等待被接受的
     * code=200，data存这些订单
     * code=404 data=-1异常
     * @return
     */
    @RequestMapping(value = "/passedOrder", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg getpassedOrder()
    {
        JsonMsg jsonMsg=new JsonMsg();
        List<CarOutBean> orders= null;
        List<CarOutBean> myOrders=new ArrayList<CarOutBean>();
        try {
            orders = orderService.getAllOrder();
            for(CarOutBean car:orders)
            {
                if(car.getStatus()==1)
                {
                    myOrders.add(car);
                }
            }
            jsonMsg.setData(myOrders);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 得到所有的order
     * code=200 data为这些order
     * code=404 data=-1 异常
     * @return
     */
    @RequestMapping(value = "/orderTraversal", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderTraversal()
    {
        JsonMsg jsonMsg=new JsonMsg();
        List<CarOutBean> orders= null;
        try {
            orders = orderService.getAllOrder();
            jsonMsg.setData(orders);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 接受某个订单
     * 提供订单的id号和接收者的id
     * code=200，data=1 接受成功
     * code=200 data=0 接受失败
     * code=404 data=-1 页面异常
     * @param id
     * @param receiver_id
     * @return
     */
    @RequestMapping(value = "/orderAccept", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderAccept(int id,int receiver_id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            int i=orderService.updateUser(id,receiver_id);
            jsonMsg.setData(i);
            jsonMsg.setCode("200");
            System.out.println("ChenJie Debug: accept car_rent_out order successfully.");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }


    /**
     * 用户还车
     * 提供订单的id号和接收者的id
     * code=200，还车成功
     * code=205，还车失败
     * code=404 data=-1 页面异常
     * @param id
     * @param receiver_id
     * @return
     */
    @RequestMapping(value = "/orderReturn", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderReturn(int id,int receiver_id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            int i=orderService.userReturnRentCar(id,receiver_id);
            if(i==1){jsonMsg.setCode("200");}
            else jsonMsg.setCode("205");
            System.out.println(i);
            System.out.println("ChenJie Debug:return car successfully.");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }
        return jsonMsg;
    }

    /**
     * 管理员通过某个订单
     * code=200 data=1 通过成功
     * code=200 data=0 通过失败，id不存在或者状态异常
     * code=404 data=-1 异常
     * @param id
     * @return
     */
    @RequestMapping(value = "/orderPass", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderPass(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            int i=orderService.pass(id);
            jsonMsg.setData(i);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }

        return jsonMsg;
    }

    /**
     * code=200 data=1 不通过成功
     * code=200 data=0 不通过失败，id不存在或者状态异常
     * code=404 data=-1 异常
     * @param id
     * @return
     */
    @RequestMapping(value = "/orderBan", method = RequestMethod.GET)
    @ResponseBody
    public JsonMsg orderBan(int id)
    {
        JsonMsg jsonMsg=new JsonMsg();
        try {
            int i=orderService.ban(id);
            jsonMsg.setData(i);
            jsonMsg.setCode("200");
        } catch (SQLException e) {
            jsonMsg.setCode("404");
            jsonMsg.setData(-1);
            e.printStackTrace();
        }

        return jsonMsg;
    }
}
