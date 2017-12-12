package com.cwj.taiqiangle.test;

import com.cwj.taiqiangle.model.AdminBean;
import com.cwj.taiqiangle.model.SendMail;
import com.cwj.taiqiangle.model.TestBean;
import com.cwj.taiqiangle.service.AdminService;
import com.cwj.taiqiangle.service.SendMailService;
import com.cwj.taiqiangle.service.TestService;
import com.cwj.taiqiangle.util.DBUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2017/12/5.
 */
public class test{
    public static void main(String[] args) {
        queryALL();
        //deleteTestBean();
      //  queryAdmin();
       // send();
//        try {
//            DBUtil.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        SendMailService sendMailService=new SendMailService();
//        sendMailService.sendmail("786197140@qq.com","jcboy");
    }

    public static void queryALL() {
        TestService testService=new TestService();
        List<TestBean> testBeans=new ArrayList<TestBean>();
        System.out.println("The Results Of testBeans Are:");
        try {
            testBeans= testService.getAllTestBean();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(TestBean testBeans1:testBeans){
            System.out.println(testBeans1.getId());
        }
    }
    public static void queryAdmin() {
        AdminService adminService=new AdminService();
        List<AdminBean> adminBeans=new ArrayList<AdminBean>();
        System.out.println("The Results Of testBeans Are:");
        try {
            adminBeans= adminService.getAdminByNamePassword("admin","admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(AdminBean testBeans1:adminBeans){
            System.out.println(testBeans1.toString());
        }
    }

    public static void deleteTestBean() {
        TestService testService=new TestService();

        System.out.println("删除操作：");
        int r = 0;
        try {
            r = testService.deleteTestBean("4");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(r != 0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

    public static void send(){
        String email="786197140@qq.com";
        String username="jcboy";
        if(sendmail(email,username))
        {
            System.out.print("邮件发送成功");
        }
        else
        {
            System.out.print("邮件发送失败");

        }
    }

    private static boolean sendmail(String mailto, String username){
        String MailTo=mailto;
        String MailSubject="Westlake International - Application Received";
        String MailBCopyTo="";
        String MailCopyTo="";
        String MailBody="<p><img alt=\"westlakelogo\" src=\"http://img1.imgtn.bdimg.com/it/u=3426531289,4065224068&fm=27&gp=0.jpg\" border=\"0\" /></p>"
                +"<h1>Welcome to use Car Management System. Dear "+username+",<br />" +
                "<br />" +
                "	Congratulations,you have registered successfully</h1>" +
                "	<p>We'd like to thank you for your interest in our expert   network business. We appreciate you taking time to apply for membership in our   expert community.</p>" +
                "	<p>To ensure the integrity and quality of our network, we seek to verify the   credentials of our experts. We hope that you understand it. We will send you a   confirmation email shortly.</p>" +
                "	<p>Best regards,<br />" +
                "	  <br />	Westlake International Team </p>	<p>&nbsp;</p>";
        String SMTPHost = "smtp.163.com";
        String Port="25";
        String MailUsername = "conglinyu1.0@163.com";
        String MailPassword = "3137341644amd588";
        String MailFrom = "conglinyu1.0@163.com";
        if(SMTPHost==null||SMTPHost==""||MailUsername==null||MailUsername==""||MailPassword==null||MailPassword==""||MailFrom==null||MailFrom=="")
        {
            System.out.println("Servlet parameter Wrongs");
        }
        SendMail send=new SendMail(SMTPHost,Port,MailUsername,MailPassword);
        if(send.sendingMimeMail(MailFrom, MailTo, MailCopyTo, MailBCopyTo, MailSubject, MailBody)){
            return true;
        }
        else
        {
            return false;
        }
    }
}
