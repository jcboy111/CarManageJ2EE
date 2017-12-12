package com.cwj.taiqiangle.service;

import com.cwj.taiqiangle.model.SendMail;

/**
 * Created by 蛟川小盆友 on 2017/12/10.
 */
public class SendMailService {

    public  boolean sendmail(String mailto, String username){
        String MailTo=mailto;
        String MailSubject="Car Management International - Application Received";
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
