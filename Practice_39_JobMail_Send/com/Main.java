package com;

import java.util.List;

public class Main {
	
	public static boolean can_I_Send = false;

	public static void main(String[] args) {

		//sendCVs("test.csv");
		//sendCVs("Programing.csv");
		//sendCVs("Inginerie.csv");
		sendPazaMail("security companies.txt");
	}

	public static void sendCVs(String filePath) {
	
		can_I_Send = true;
		List<String> records =  FileReader.read(filePath);
		int i = 0;
		
		while(i < records.size() && can_I_Send) {
		//while(i < records.size()-2 && can_I_Send) {
			String [] rec = records.get(i).split(",");
			
			if(rec[2].contains(" ")) {
				System.out.println("Email will not gonna sended to i: "+i + " "+ rec[2]);
				i++;
			}
			if(rec[2].contains("@") && !rec[2].contains(" ")) {
				//EmailService.send_IT_email(rec[2],rec[1],rec[3],i);
				EmailService.sendEngeneeringEmail(rec[2], rec[1], i);
				i++;
			}
		}
		System.out.println("Total "+i +" emails was sended!");
	}
	
	public static void sendPazaMail(String filePath) {
		
		can_I_Send = true;
		List<String> records =  FileReader.read(filePath);
		int i = 0;
		
		while(i < records.size() && can_I_Send) {
		//while(i < records.size()-2 && can_I_Send) {
			String [] rec = records.get(i).split(",");
			//System.out.println("rec.length: "+rec.length);
			if(rec[1].contains(" ")) {
				System.out.println("Email will not gonna sended to i: "+i + " "+ rec[1]);
				i++;
			}
			if(rec[1].contains("@") && !rec[1].contains(" ")) {
				//EmailService.send_IT_email(rec[2],rec[1],rec[3],i);
				EmailService2.send_Paza_email(rec[1]);
				i++;
			}
			System.out.println("Email was send to: "+rec[1]);
		}
		System.out.println(" "+i +" emails was sended!");
	}
}
