package com;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
  
class EmailService{  
	
	static String cvpath = "";
	static String gdprpath = "";
	static String gmailUser = "eugen.fazekas@gmail.com";
	static String gmailPassword = "bleecfnwuamgdapt";
	//static String gmailUser = "eugenfazekas.mailtrap@gmail.com";
	//static String gmailPassword = "zbhnhlqyhqknsvnd";
	
	
	static Properties getGamailSmtpProperties() {
		
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
	
	static void sendEngeneeringEmail(String recipient , String companyName,int counter_i){  
  
		  Main.can_I_Send = false;
		  String to=recipient;//change accordingly  
		  
		   Session session = Session.getDefaultInstance(getGamailSmtpProperties(),  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(gmailUser,gmailPassword);  
		   }  
		  });  
		  //2) compose message     
		  try{  
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(gmailUser));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject("C.V Eugen Fazekas");  
		      
		    //3) create MimeBodyPart object and set your message text     
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setContent(
		    		getOradeaCV(companyName,counter_i),"text/html"
		    		);  
		      
		    //4) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String filename = "C V Eugen Fazekas.pdf";//change accordingly  
		    DataSource source = new FileDataSource(filename);  
		    messageBodyPart2.setDataHandler(new DataHandler(source));  
		    messageBodyPart2.setFileName(filename);  
		    
		    MimeBodyPart messageBodyPart3 = new MimeBodyPart();  			   
		    source = new FileDataSource("GDPR_RO.pdf");  
		    messageBodyPart3.setDataHandler(new DataHandler(source));  
		    messageBodyPart3.setFileName(filename); 
		     
		     
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
	
	static void send_IT_email(String recipient , String companyName, String language,int counter_i){  
		  
		  String html = getHTML_Text(companyName,language, counter_i);
		
		  if(html == null) 
			  throw new RuntimeException("NO acceptable Language error!");
		  
		  Main.can_I_Send = false;
		  String to=recipient;//change accordingly  
		  
		  Session session = Session.getDefaultInstance(getGamailSmtpProperties(),  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(gmailUser,gmailPassword);  
		   }  
		  });  
		  //2) compose message     
		  try{  
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(gmailUser));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject("C.V Eugen Fazekas");  
		      
		    //3) create MimeBodyPart object and set your message text     
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.addHeader("Content-type", "text/HTML; charset=UTF-8");
		    messageBodyPart1.addHeader("format", "flowed");
		    messageBodyPart1.addHeader("Content-Transfer-Encoding", "8bit");
		    messageBodyPart1.setContent(html,"text/html;charset=utf-8"
		    		);  
		      
		    //4) create new MimeBodyPart object and set DataHandler object to this object   
		    
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String filename = cvpath;//change accordingly  
		    DataSource source = new FileDataSource(filename);  
		    messageBodyPart2.setDataHandler(new DataHandler(source));  
		    messageBodyPart2.setFileName(filename);  
		    
		    MimeBodyPart messageBodyPart3 = new MimeBodyPart();  
			  
		    filename = gdprpath;//change accordingly  
		    source = new FileDataSource(filename);  
		    messageBodyPart3.setDataHandler(new DataHandler(source));  
		    messageBodyPart3.setFileName(filename); 
		     
		     
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
			
			}catch (MessagingException ex) {
				ex.printStackTrace();
				System.out.println(ex.toString());
			}  
		}
	
	static String getHTML_Text(String companyName, String language,int counter_i) {
		
		String html = null;
		
		if( language.equals("RO")) {
			html  = getRomanianText(companyName,counter_i);
			cvpath = "C.V Eugen Fazekas p-r.pdf";
			gdprpath = "GDPR_RO.pdf";
			
		}
		
		if(language.equals( "HU")) {
			html = gethungarianText(companyName,counter_i);
			cvpath = "C.V Eugen Fazekas p-h.pdf";
			gdprpath = "GDPR_HU.pdf";
		}
		
		if(language.equals("EN")) {
			html = getEnglishText(companyName,counter_i);
			cvpath = "C.V Eugen Fazekas p-e.pdf";
			gdprpath = "GDPR_EN.pdf";
		}
		return html;
	}
	
	static String getEnglishText(String companyName,int counter_i) {
		
		String text = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "	<head>\r\n"
				+ "		<style>\r\n"
				+ "			.title{\r\n"
				+ "				margin: 50px 0px 0px 50px;\r\n"
				+ "				font-family:\"Arial\";\r\n"
				+ "				font-size:20px;\r\n"
				+ "				color:#444;\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "			.baseAnimationClass {}\r\n"
				+ "			\r\n"
				+ "			.baseAnimationClass:hover {\r\n"
				+ "				  border:1px solid #00f;\r\n"
				+ "				  box-shadow:10px 10px 10px grey;\r\n"
				+ "				  background-color:#fff;\r\n"
				+ "				  transition:0.4s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
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
				+ "			.w-500        {width: 500;}\r\n"
				+ "			.w-500:hover  {width: 550px;}\r\n"
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
				+ "			}	\r\n"
				+ "		</style>\r\n"
				+ "	</head>\r\n"
				+ "	<body>\r\n"
				+ "		<div style=\"background-color:#eee\">\r\n"
				+ "		\r\n"
				+ "			<div class=\"title\">\r\n"
				+ "				<h1>Dear "+companyName+" !</h1>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>Hello,</p>\r\n"
				+ "				<p>I'm in job finding as a Java, Angular or Full Stack developer,</p>\r\n"
				+ "				<p>Attached you can find my C.V.,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>Salary negotiation i think not could be a relevant problem,</p>\r\n"
				+ "				<p>My developer skills you can check <a style=\"color:#0D6EFD; \" href=\"https://github.com/eugenfazekas/DemoProject\" target=\"_blank\">here<a/>.</p>\r\n"
				+ "				<div style=\"clear:both\"></div>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>If you not taking part of human resources,</p>\r\n"
				+ "				<p>please forward my email to them, thank you.</p>\r\n"
				+ "			</div>\r\n"
				+ "   \r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>GDPR declaration regarding the processing of personal data,</p>\r\n"
				+ "				<p>you can find attached in my email.</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>This message was sended by a Java App and a smtp server.</p>\r\n"
				+ "				<p>Regards Eugen Fazekas,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
				+ "							Romania, Oradea</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div style=\"clear:both\"></div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD \">\r\n"
				+ "							Email:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-270 float-left\">\r\n"
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "						<div style=\"margin: 20px 0px 0px 50px\">\r\n"
				+ "				<img  class=\"img\" src=\"https://raw.githubusercontent.com/eugenfazekas/Deep_Learning/9072d99e12b06854166eddfef48511ce32a76ade/Data_Extraction_And_Transform/Cube.png\" title=\"reality check\" width=\"215\" height=\"140\">\r\n"
				+ "			</div>\r\n"
				+ "			<p style=\"margin: 20px 0px 0px 50px\">This is the "+counter_i+" -th email from 100 different companys.</p>\r\n"
				+ "		</div>\r\n"
				+ "	</body>\r\n"
				+ "</html>";

		
		return text;
	}
	
	static String gethungarianText(String companyName,int counter_i) {
		
		String text = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "	<head>\r\n"
				+ "		<style>\r\n"
				+ "			.title{\r\n"
				+ "				margin: 50px 0px 0px 50px;\r\n"
				+ "				font-family:\"Arial\";\r\n"
				+ "				font-size:20px;\r\n"
				+ "				color:#444;\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "			.baseAnimationClass {}\r\n"
				+ "			\r\n"
				+ "			.baseAnimationClass:hover {\r\n"
				+ "				  border:1px solid #00f;\r\n"
				+ "				  box-shadow:10px 10px 10px grey;\r\n"
				+ "				  background-color:#fff;\r\n"
				+ "				  transition:0.4s;\r\n"
				+ "			}\r\n"
				+ "			\r\n"
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
				+ "			.w-500        {width: 500px;}\r\n"
				+ "			.w-500:hover  {width: 550px;}\r\n"
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
				+ "			}	\r\n"
				+ "		</style>\r\n"
				+ "	</head>\r\n"
				+ "	<body>\r\n"
				+ "		<div style=\"background-color:#eee\">\r\n"
				+ "		\r\n"
				+ "			<div class=\"title\">\r\n"
				+ "				<h1>Kedves "+companyName+"!</h1>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>Jo Napot Kivanok!</p>\r\n"
				+ "				<p>Szeretnek hagyni egy oneletrajzot ha esetleg,</p>\r\n"
				+ "				<p>Java, Angular vagy Full Stack fejlesztot keresnek,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>Fizetesi resz meghatarozasa nem hiszem,</p>\r\n"
				+ "				<p> hogy relevans problemat okozna,</p>\r\n"
				+ "				<p>A fejlesztoi tapasztalatom<a style=\"color:#0D6EFD; \" href=\"https://github.com/eugenfazekas/DemoProject\" target=\"_blank\"> ezen <a/> a linken tekinthetik meg.</p>\r\n"
				+ "				<div style=\"clear:both\"></div>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>Ha esetleg nem resze a human eroforras keresoknek,</p>\r\n"
				+ "				<p>(hr) reszlegnek, tisztelettel megkernem,</p>\r\n"
				+ "				<p>hogy tovabbitsa az emailem, felejuk, koszonom.</p>\r\n"
				+ "			</div>\r\n"
				+ "   \r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>A GDPR  hoozajarulasi nyilatkozatot a szemelyes adataimhoz,</p>\r\n"
				+ "				<p>az email csatolmanyaban megtalaljak.</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-500\">\r\n"
				+ "				<p>Ez az email egy Java program,</p>\r\n"
				+ "				<p>es egy smtp segitsegevel volt kezbesitve.</p>\r\n"
				+ "				<p>Udvozlettel Eugén Fazekas,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
				+ "							Romania, Oradea</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div style=\"clear:both\"></div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD \">\r\n"
				+ "							Email:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-270 float-left\">\r\n"
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "			<div style=\"margin: 20px 0px 0px 50px\">\r\n"
				+ "				<img  class=\"img\" src=\"https://raw.githubusercontent.com/eugenfazekas/Deep_Learning/9072d99e12b06854166eddfef48511ce32a76ade/Data_Extraction_And_Transform/Cube.png\" title=\"reality check\" width=\"215\" height=\"140\">\r\n"
				+ "			</div>\r\n"
				+ "			<p style=\"margin: 20px 0px 0px 50px\">Ez a " +counter_i+"-ik email a 100 kulombozo cegbol.</p>\r\n"
				+ "		</div>\r\n"
				+ "	</body>\r\n"
				+ "</html>";
		
		return text;
	}
	
	static String getRomanianText(String companyName,int counter_i) {
		
		String text = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "	<head>\r\n"
				+ "		<style>\r\n"
				+ "			.title{\r\n"
				+ "				margin: 50px 0px 0px 50px;\r\n"
				+ "				font-family:\"Arial\";\r\n"
				+ "				font-size:20px;\r\n"
				+ "				color:#444;\r\n"
				+ "			}\r\n"
				+ "\r\n"
				+ "			.baseAnimationClass {}\r\n"
				+ "			\r\n"
				+ "			.baseAnimationClass:hover {\r\n"
				+ "				  border:1px solid #00f;\r\n"
				+ "				  box-shadow:10px 10px 10px grey;\r\n"
				+ "				  background-color:#fff;\r\n"
				+ "				  transition:0.4s;\r\n"
				+ "			}\r\n"
				+ "\r\n"
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
				+ "			}	\r\n"
				+ "		</style>\r\n"
				+ "	</head>\r\n"
				+ "	<body>\r\n"
				+ "		<div style=\"background-color:#eee\">\r\n"
				+ "		\r\n"
				+ "			<div class=\"title\">\r\n"
				+ "				<h1>Cu Stima Drag "+companyName+"!</h1>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Buna Ziua,</p>\r\n"
				+ "				<p>A-si lasa un C.V in caz daca o sa aveti posturi vacante,</p>\r\n"
				+ "				<p>ca si Java Developer, Angular sau Full Stack Developer,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Negocierea salarului nu cred ca ar fi o problema relevanta,</p>\r\n"
				+ "				<p>Skill-urile mele de programare puteti verifica <a style=\"color:#0D6EFD; \" href=\"https://github.com/eugenfazekas/DemoProject\" target=\"_blank\">aici<a/>.</p>\r\n"
				+ "				<div style=\"clear:both\"></div>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Daca cumva nu face-ti parte din personalul care selecteaza noi angajati,</p>\r\n"
				+ "				<p>va-rog sa da-ti un forward la emailul meu catre hr (resurse umane).</p>\r\n"
				+ "			</div>\r\n"
				+ "  \r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Declaratia de GDPR pentru prelucrarea datelor cu caracter personal,</p>\r\n"
				+ "				<p>gasiti atasat in email.</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Acesta email a fost trimis cu ajutorul unui applicatie Java,</p>\r\n"
				+ "				<p>si un server smtp.</p>\r\n"
				+ "				<p>Cu respect Eugen Fazekas,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
				+ "							Romania, Oradea</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div style=\"clear:both\"></div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD \">\r\n"
				+ "							Email:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-270 float-left\">\r\n"
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "			<div style=\"margin: 20px 0px 0px 50px\">\r\n"
				+ "				<img  class=\"img\" src=\"https://raw.githubusercontent.com/eugenfazekas/Deep_Learning/9072d99e12b06854166eddfef48511ce32a76ade/Data_Extraction_And_Transform/Cube.png\" title=\"reality check\" width=\"215\" height=\"140\">\r\n"
				+ "			</div>\r\n"
				+ "			<p style=\"margin: 20px 0px 0px 50px\">Acesta email este "+ counter_i+" -lea din cele 100 firme diferite</p>\r\n"
				+ "		</div>\r\n"
				+ "	</body>\r\n"
				+ "</html>"; 
		
		return text;
	}
	
	static String getOradeaCV(String companyName,int counter_i) {
		
		String text = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "	<head>\r\n"
				+ "		<style>\r\n"
				+ "			.title{\r\n"
				+ "				margin: 50px 0px 0px 50px;\r\n"
				+ "				font-family:\"Arial\";\r\n"
				+ "				font-size:20px;\r\n"
				+ "				color:#444;\r\n"
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
				+ "			}	\r\n"
				+ "		</style>\r\n"
				+ "	</head>\r\n"
				+ "	<body>\r\n"
				+ "		<div style=\"background-color:#eee\">\r\n"
				+ "		\r\n"
				+ "			<div class=\"title\">\r\n"
				+ "				<h1>Cu Stima Drag "+companyName+"!</h1>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p >Buna Ziua,</p>\r\n"
				+ "				<p>A-si lasa un C.V in caz daca o sa aveti posturi vacante,</p>\r\n"
				+ "				<p>care necesita studii superiaore.</p>\r\n"
				+ "			</div>\r\n"
				+ "\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Daca cumva nu face-ti parte din personalul care selecteaza noi angajati,</p>\r\n"
				+ "				<p>va-rog sa da-ti un forward la emailul meu catre hr (resurse umane).</p>\r\n"
				+ "			</div>\r\n"
				+ "  \r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Declaratia de GDPR pentru prelucrarea datelor cu caracter personal,</p>\r\n"
				+ "				<p>gasiti atasat in email.</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
				+ "			<div class=\"baseAnimationClass color-change paddingBig font-15 w-600\">\r\n"
				+ "				<p>Acesta email a fost trimis cu ajutorul unui applicatie Java,</p>\r\n"
				+ "				<p>si un server smtp.</p>\r\n"
				+ "				<p>Cu respect Eugen Fazekas,</p>\r\n"
				+ "			</div>\r\n"
				+ "			\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "						<div style=\"color:#333333;  \">\r\n"
				+ "							Romania, Oradea</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div style=\"clear:both\"></div>\r\n"
				+ "				</div>\r\n"
				+ "				\r\n"
				+ "				<div>\r\n"
				+ "					<div class=\"text3 float-left\">\r\n"
				+ "						<div style=\"color:#0D6EFD \">\r\n"
				+ "							Email:</div>\r\n"
				+ "					</div>\r\n"
				+ "					\r\n"
				+ "					<div class=\"baseAnimationClass color-change paddingSmall font-15 w-270 float-left\">\r\n"
				+ "						<div style=\"color:#333333;  \">\r\n"
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
				+ "			<div style=\"margin: 20px 0px 0px 50px\">\r\n"
				+ "				<img  class=\"img\" src=\"https://raw.githubusercontent.com/eugenfazekas/Deep_Learning/9072d99e12b06854166eddfef48511ce32a76ade/Data_Extraction_And_Transform/Cube.png\" title=\"reality check\" width=\"215\" height=\"140\">\r\n"
				+ "			</div>\r\n"
				+ "			<p style=\"margin: 20px 0px 0px 50px\">Acesta email este "+ counter_i+" -lea din cele 59 firme diferite</p>\r\n"
				+ "		</div>\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
		return text;
	}
}  
