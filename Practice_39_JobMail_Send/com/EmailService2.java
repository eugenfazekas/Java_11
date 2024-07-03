package com;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService2 {

	static String gmailUser = "eugen.fazekas@gmail.com";
	static String gmailPassword = "bleecfnwuamgdapt";
	
	static Properties getGmailSmtpProperties() {
		
		  Properties properties = System.getProperties();  
		  properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		  properties.put("mail.smtp.port", "587"); //TLS Port
		  properties.put("mail.smtp.auth", "true"); //enable authentication
		  properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		  properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		  properties.put("mail.smtp.starttls.required", "true");
		  properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		  
		  	return properties;
	}
	
	static void send_Paza_email(String recipient) {

		  Main.can_I_Send = false;
		
		  Session session = Session.getDefaultInstance(getGmailSmtpProperties(),  
				   new javax.mail.Authenticator() {  
				   protected PasswordAuthentication getPasswordAuthentication() {  
				   return new PasswordAuthentication(gmailUser,gmailPassword);  
				   }  
				  });

	      try{  
	         MimeMessage message = new MimeMessage(session);  
	         BodyPart messageBodyPart = new MimeBodyPart();  
	         message.setFrom(new InternetAddress(gmailUser));  
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));  
	         message.setSubject("Night Job");  
	         
	         
	         messageBodyPart.setContent(getHtmlText(),"text/html");  
	       
	         Multipart multipart = new MimeMultipart(); 
	         
	         multipart.addBodyPart(messageBodyPart);
	         
	         message.setContent(multipart);
	   
	         Transport.send(message);  
	         System.out.println(); 
	         Main.can_I_Send = true;
	  
	      }catch (MessagingException mex) {mex.printStackTrace();}  
	   }  
	
	
	static String getHtmlText() {
		String html = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "	<title>title</title>	\r\n"
				+ "	<head>\r\n"
				+ "		<style>\r\n"
				+ "			.container{\r\n"
				+ "				margin: 50px 0px 0px 0px;\r\n"
				+ "				width:800px;\r\n"
				+ "				height:680px;\r\n"
				+ "				background-color: #141529 ;\r\n"
				+ "				overflow: auto;\r\n"
				+ "				color:#aaa;	\r\n"
				+ "				\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.container:hover  {\r\n"
				+ "				margin: 50px 0px 0px 0px;\r\n"
				+ "				background-color: #fff;			\r\n"
				+ "				overflow: auto;\r\n"
				+ "				color:#222;	\r\n"
				+ "				border: 1px solid #555;	\r\n"
				+ "				border-radius: 8px;				\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "			.title{\r\n"
				+ "				margin: 5% 0px 0px 40%;\r\n"
				+ "				width:20%;\r\n"
				+ "				\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.title:hover {\r\n"
				+ "				margin: 5% 0px 0px 40%;\r\n"
				+ "				background-color: #fff;\r\n"
				+ "				width:300px;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.intro {\r\n"
				+ "				margin: 0px 0px 0px 0px;\r\n"
				+ "				padding: 0px 0px 0px 0px;\r\n"
				+ "				width:450px;\r\n"
				+ "				height:90px;\r\n"
				+ "				\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "					\r\n"
				+ "			.myImage{\r\n"
				+ "				margin: 50px 10% 0px 0px;\r\n"
				+ "				float:right;\r\n"
				+ "				transition:1.5s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.myImage:hover{\r\n"
				+ "				margin: 25px 10% 0px 0px;\r\n"
				+ "				float:right;\r\n"
				+ "				transform: scale(1.5);\r\n"
				+ "				transition:1s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.details {\r\n"
				+ "				clear:both;\r\n"
				+ "				padding:0px 0px 20px 0px;\r\n"
				+ "				margin: 0px 0px 40px 25px;\r\n"
				+ "				width:300px;\r\n"
				+ "				\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			\r\n"
				+ "			.zoomClassIntro {\r\n"
				+ "				margin: 35px 20px 0px 40px;\r\n"
				+ "				padding: 0px 20px 0px 30px;\r\n"
				+ "				font-size: 15px;\r\n"
				+ "				width:320px;\r\n"
				+ "				height:80px;\r\n"
				+ "				transition:1s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.zoomClassIntro:hover {\r\n"
				+ "				margin: 5px 10px 0px 10px;\r\n"
				+ "				padding: 0px 30px 0px 30px;\r\n"
				+ "				width:380px;\r\n"
				+ "				height:115px;\r\n"
				+ "				font-size: 18px;\r\n"
				+ "				transition:0.7s;\r\n"
				+ "				border: 1px solid #555;\r\n"
				+ "				border-radius: 8px;\r\n"
				+ "				box-shadow: 5px 10px 8px #888888;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.zoomClassDetails {\r\n"
				+ "				margin:10px 0px 0px 20px;\r\n"
				+ "				padding: 10px 10px 5px 30px;\r\n"
				+ "				font-size: 15px;\r\n"
				+ "				width:300px;\r\n"
				+ "				height:40px;\r\n"
				+ "				transition:1.5s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.zoomClassDetails:hover {\r\n"
				+ "				margin:0px 0px 0px 0px;\r\n"
				+ "				padding: 0px 10px 5px 20px;\r\n"
				+ "				font-size: 18px;\r\n"
				+ "				transition:0.7s;\r\n"
				+ "				width:350px;\r\n"
				+ "				height:50px;\r\n"
				+ "				border: 1px solid #555;\r\n"
				+ "				border-radius: 8px;\r\n"
				+ "				box-shadow: 5px 10px 8px #888888;\r\n"
				+ "			}	\r\n"
				+ "			\r\n"
				+ "		</style>\r\n"
				+ "	</head>\r\n"
				+ "\r\n"
				+ "	<body>\r\n"
				+ "			<div  id=\"a\" class=\"container\">\r\n"
				+ "					<div>\r\n"
				+ "						<p></p>\r\n"
				+ "					</div>\r\n"
				+ "\r\n"
				+ "					<div class=\"title\">\r\n"
				+ "						<h1>Buna Ziua,\r\n"
				+ "						</h1>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					\r\n"
				+ "					\r\n"
				+ "					<div class=\"myImage\">\r\n"
				+ "						<img src=\"https://scontent.fclj2-1.fna.fbcdn.net/v/t39.30808-6/280144650_544035140764992_2887735435574009487_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_ohc=1FhFmSXfcIUQ7kNvgFdboB6&_nc_ht=scontent.fclj2-1.fna&oh=00_AYD8gaaOEoP-rYm7KocRFNBGEq0lUzK6OAUfw1P57tjfYw&oe=667AA9F2\"  width=\"215\" height=\"140\">\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"intro\">\r\n"
				+ "						<div class=\"zoomClassIntro\">\r\n"
				+ "							<p>   Buna ziua, caut loc de munca Agent de securitate cu atestat in zona Borsului-Rogerius-Uzinelor, tura de noapte(doua cu doua) magazine si cluburi exclusi, Va multumesc.\r\n"
				+ "							</p>\r\n"
				+ "						</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"intro\">\r\n"
				+ "						<div class=\"zoomClassIntro\">\r\n"
				+ "							<p>    Daca cumva a-ti citit mail anterior in care caut job si nu aveti posturi disponibile 2-2 noaptea, va-rog sa ignorati acest mesaj, Va  multumesc.\r\n"
				+ "							</p>\r\n"
				+ "						</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"details\">\r\n"
				+ "					\r\n"
				+ "						<div class=\"zoomClassDetails zoomBorder\">\r\n"
				+ "							<p>Numele: Eugen Fazekas</p>\r\n"
				+ "						</div>\r\n"
				+ "						\r\n"
				+ "						<div div class=\"zoomClassDetails zoomBorder\">\r\n"
				+ "							<p>Domiciliul: Oradea, str: Sovata, Nr: 32, </p>\r\n"
				+ "						</div>\r\n"
				+ "						\r\n"
				+ "						<div div class=\"zoomClassDetails zoomBorder\">\r\n"
				+ "							<p>Varsta: 40,</p>\r\n"
				+ "						</div>\r\n"
				+ "						\r\n"
				+ "						<div div class=\"zoomClassDetails zoomBorder\">\r\n"
				+ "							<p>Telefon: +40771326745</p>\r\n"
				+ "						</div>\r\n"
				+ "						\r\n"
				+ "						<div div class=\"zoomClassDetails zoomBorder\">\r\n"
				+ "							<p>Email: eugen.fazekas@gmail.com</p>\r\n"
				+ "						</div>\r\n"
				+ "					\r\n"
				+ "					</div>\r\n"
				+ "			</div>\r\n"
				+ "	</body>\r\n"
				+ "</html>\r\n"
				+ "\r\n"
				+ "";
		
		return html;
	}
}
