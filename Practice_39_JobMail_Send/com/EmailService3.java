package com;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

public class EmailService3 {
	
	static void sendOperatorCalculatorEmail(String recipient , String companyName,int counter_i){  
		  
		  Main.can_I_Send = false;
		  String to=recipient;//change accordingly  
		  
		   Session session = Session.getDefaultInstance(EmailService.getGamailSmtpProperties(),  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(EmailService.gmailUser,EmailService.gmailPassword);  
		   }  
		  });  
		  //2) compose message     
		  try{  
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(EmailService.gmailUser));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject("C.V. Eugen Fazekas - Inginer Autovehicule Rutiere");  
		      
		    //3) create MimeBodyPart object and set your message text     
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setContent(
		    		getOperatorCalculator(companyName,counter_i),"text/html; charset=UTF-8"
		    		);  
		      
		    //4) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String filename = "C.V. Eugen Fazekas.pdf";//change accordingly  
		    DataSource source = new FileDataSource(filename);  
		    messageBodyPart2.setDataHandler(new DataHandler(source));  
		    messageBodyPart2.setFileName(filename);  
		    
		    MimeBodyPart messageBodyPart3 = new MimeBodyPart();  			   
		    source = new FileDataSource("GDPR_RO.pdf");  
		    messageBodyPart3.setDataHandler(new DataHandler(source));  
		    messageBodyPart3.setFileName("GDPR_RO.pdf"); 
		     
		     
		    //5) create Multipart object and add MimeBodyPart objects to this object      
		    Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
		    multipart.addBodyPart(messageBodyPart2); 
		    multipart.addBodyPart(messageBodyPart3);  
		    		  
		    //6) set the multiplart object to the message object  
		    message.setContent(multipart);  
		     
		    //7) send message  
		    Transport.send(message);    
			System.out.println("Message was sent to: "+recipient);  
			Main.can_I_Send = true;
			
			}catch (MessagingException ex) {ex.printStackTrace();}  
		} 

	static String getOperatorCalculator(String companyName,int counter_i) {
		
		String  html = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "	<head>\r\n"
				+ "		<style>\r\n"
				+ "			.container{\r\n"
				+ "				margin: 50px 0px 0px 0px;\r\n"
				+ "				width:800px;\r\n"
				+ "				height:auto;\r\n"
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
				+ "			.title{\r\n"
				+ "				margin: 50px 0px 0px 50px;\r\n"
				+ "				font-family:\"Arial\";\r\n"
				+ "				font-size:20px;\r\n"
				+ "				animation-name: example;\r\n"
				+ "				animation-duration: 2s;\r\n"
				+ "				animation-iteration-count: infinite;\r\n"
				+ "			}\r\n"
				+ "				\r\n"
				+ "			@keyframes example {\r\n"
				+ "				0%   {color: #203c69;}\r\n"
				+ "				33%  {color: #2b579e;}\r\n"
				+ "				66%   {color: #2071f5;}\r\n"
				+ "				100%  {color: #2663c7;}\r\n"
				+ "			}\r\n"
				+ "		\r\n"
				+ "			.baseAnimationClass {}\r\n"
				+ "			\r\n"
				+ "			.baseAnimationClass:hover {\r\n"
				+ "				  border:1px solid #00f;\r\n"
				+ "				  box-shadow:10px 10px 10px grey;\r\n"
				+ "				  background-color:#fff;\r\n"
				+ "				  transition:0.4s;\r\n"
				+ "			}\r\n"
				+ "			.color-change          {}\r\n"
				+ "			.color-change:hover    { color:#990;}\r\n"
				+ "			\r\n"
				+ "			.paddingBig         { padding: 5px 10px  0px 50px;}\r\n"
				+ "			.paddingBig:hover   { padding: 0px 30px  0px 20px;}\r\n"
				+ "			.paddingSmall       { padding: 5px 10px  5px 50px;}\r\n"
				+ "			.paddingSmall:hover { padding: 5px 20px  5px 40px;}\r\n"
				+ "			\r\n"
				+ "			.font-15       {font-size:15px;}\r\n"
				+ "			.font-15:hover {font-size:20px;}\r\n"
				+ "			\r\n"
				+ "			.w-70         {width:  70px;} \r\n"
				+ "			.w-70:hover   {width:  80px;} \r\n"
				+ "			.w-150        {width: 150px;} \r\n"
				+ "			.w-150:hover  {width: 165px;}\r\n"
				+ "			.w-220        {width: 220px;} \r\n"
				+ "			.w-220:hover  {width: 240px;} \r\n"
				+ "			.w-270        {width: 270px;} \r\n"
				+ "			.w-270:hover  {width: 300px;}   			\r\n"
				+ "			.w-600        {width: 600px;}\r\n"
				+ "			.w-600:hover  {width: 650px;}\r\n"
				+ "			\r\n"
				+ "			.h-8 { height:8% }			\r\n"
				+ "			.text3 {\r\n"
				+ "				padding: 5px 10px 5px 50px;\r\n"
				+ "				width: 120px;\r\n"
				+ "				height: 8%;\r\n"
				+ "				font-size:15px;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.float-left {\r\n"
				+ "				float:left;\r\n"
				+ "			}\r\n"
				+ "					\r\n"
				+ "			.img:hover {\r\n"
				+ "					width:430px;\r\n"
				+ "					height:280px;\r\n"
				+ "					box-shadow:10px 10px 10px grey;\r\n"
				+ "					transition:0.4s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
				+ "			.redText {\r\n"
				+ "			font-size: 20px;\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "		.baseAnimationClass:hover .redText{\r\n"
				+ "			color:#f00;\r\n"
				+ "		}\r\n"
				+ "			\r\n"
				+ "		</style>\r\n"
				+ "	</head>\r\n"
				+ "	<body>\r\n"
				+ "	<div  id=\"a\" class=\"container\">\r\n"
				+ "		\r\n"
				+ "			<div class=\"title\">\r\n"
				+ "				<h1>Cu Stimă Drag "+companyName+"!</h1>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Bună Ziua,</p>\r\n"
				+ "					<p>Aș dori să îmi depun C.V.-ul în vederea unor oportunități viitoare,</p>\r\n"
				+ "					<p>pentru un post ce implică activități desfășurate pe calculator sau laptop</p>\r\n"
				+ "					<p>și care necesită studii medii sau superioare.</p>\r\n"
				+ "					<p>Menționez că nu sunt interesat de poziții de conducere,</p>\r\n"
				+ "					<p>cum ar fi cea de șef de tură sau manager,</p>\r\n"
				+ "					<p>întrucât nu am experiență în astfel de roluri și,</p>\r\n"
				+ "					<p>din motive personale, nu caut astfel de posturi.</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Am experiența in urmatoarele programe, cu niveluri de experiența</p>\r\n"
				+ "				<p>specificate de mai jos.</p>\r\n"
				+ "				<p>Descrierea mai exactă nivelurile de experiență:</p>\r\n"
				+ "				<p>1. Entry Level (Very few Basics).</p>\r\n"
				+ "				<p>2. Beginner Level (Only neccessary Basiscs).</p>\r\n"
				+ "				<p>3. Advanded  Level (Optimized Knowledge to Do Anything neccessary).</p>\r\n"
				+ "				<p>4. Well Known Level (Very good knowledge about the program features and usage).</p>\r\n"
				+ "				<p>5. Senior (Heavy expirienced with very good knowledge).</p>\r\n"
				+ "			</div>	\r\n"
				+ "				</br>\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">	\r\n"
				+ "				<p class=\"redText\"><b>//Windows</b></p>\r\n"
				+ "				<p>Microsodt Excel = 2, Microsodt Word = 2, Microsodt PowerPoint = 2, </p>\r\n"
				+ "				<p>PowerShell = 1, CMD = 1</p>\r\n"
				+ "\r\n"
				+ "				<p class=\"redText\"><b>//Engeneering Design</b></p>\r\n"
				+ "				<p>AutoCAd = 3, Catia = 3, Solid Edge = 4, Femap = 3</p>\r\n"
				+ "\r\n"
				+ "				<p class=\"redText\"><b>// Web Development</b></p>\r\n"
				+ "				<p>Html5 = 3, Css = 3, JavaScipt = 2, TypeScript = 2, Angular = 3,</p>\r\n"
				+ "				<p>Java = 4, SQL = 2, MongoDB = 2, SpringBoot = 3, Microservices = 2,</p>\r\n"
				+ "				<p>Linux = 1, Docker = 2, Kubernetes = 3, Microservices = 2, Jenkns = 1,</p>\r\n"
				+ "				<p>OpenSSL = 2, </p>\r\n"
				+ "\r\n"
				+ "				<p class=\"redText\"><b>// Volkswagen Service Diagnostics</b></p>\r\n"
				+ "				<p>VCDS = 2, ODIS = 1, Elsa = 1</p>\r\n"
				+ "\r\n"
				+ "				<p class=\"redText\"><b>//Others</b></p>\r\n"
				+ "				<p>Visual Studio C++ 6.0 = 1, C++ = 1, TCP/IP Connections = 1</p>\r\n"
				+ "				\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Menționez că am abilitate de a invăța orice program nou in timp scurt</p>\r\n"
				+ "				<p>inclusiv și limbaj de programare in caz neccesar. </p>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Dacă cumva nu faceți parte din personalul care selectează noi angajați,</p>\r\n"
				+ "				<p>vă-rog sa dați un forward la emailul meu către hr (resurse umane).</p>\r\n"
				+ "			</div>\r\n"
				+ "  \r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Declarația de GDPR pentru prelucrarea datelor cu caracter personal,</p>\r\n"
				+ "				<p>gasiți atasat în email.</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Daca cumva a-ți mai citit mail anterior de la mine  </p>\r\n"
				+ "				<p>în care caut job, si nu aveți post disponibil</p>\r\n"
				+ "				<p>vă-rog sa ignorați acest mesaj, Vă  multumesc.</p>							\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Acesta email a fost trimis cu ajutorul unui applicație Java,</p>\r\n"
				+ "				<p>si un server smtp.</p>\r\n"
				+ "				<p>Cu respect Eugen Fazekas,</p>\r\n"
				+ "			</div>\r\n"
				+ "					\r\n"
				+ "			<div>		\r\n"
				+ "				<div class=\"baseAnimationClass color-change paddingSmall font-15 w-150\">\r\n"
				+ "					<div style=\"color:#0D6EFD\">\r\n"
				+ "						<b>King Regards,</b>\r\n"
				+ "					</div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div class=\"baseAnimationClass color-change paddingSmall font-15 w-150\">\r\n"
				+ "					<div><b>Eugen Fazekas,</b></div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD;  \">\r\n"
				+ "							Mobile:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-150 float-left\">\r\n"
				+ "						<div>\r\n"
				+ "							+40 771 326 745</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div style=\"clear:both\"></div>			\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD;  \">\r\n"
				+ "							Address:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-150 float-left\">\r\n"
				+ "						<div>\r\n"
				+ "							Romania, Oradea</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div style=\"clear:both\"></div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD;  \">\r\n"
				+ "							Email:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-270 float-left\">\r\n"
				+ "						<div>\r\n"
				+ "							eugen.fazekas@gmail.com</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div style=\"clear:both\"></div>\r\n"
				+ "				</div>\r\n"
				+ "\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-50 float-left\">\r\n"
				+ "						<a style=\"color:#0D6EFD; \" href=\"https://github.com/eugenfazekas\" target=\"_blank\">GitHub:</a>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div  style=\"visibility:hidden\" class=\"w-70 h-8 float-left\"> .\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-270 float-left\">\r\n"
				+ "							https://github.com/eugenfazekas\r\n"
				+ "					</div>\r\n"
				+ "				</div>					\r\n"
				+ "				<div style=\"clear:both\"></div>\r\n"
				+ "					 \r\n"
				+ "			</div>\r\n"
				+ "			<p style=\"margin: 20px 0px 0px 50px\">Acesta email este "+ counter_i+" -lea din cele 250 firme diferite</p>\r\n"
				+ "		</div>\r\n"
				+ "</body>\r\n"
				+ "</html>"; 
		
			return html;
	}
}
