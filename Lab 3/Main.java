package il.ac.telhai.cn.basics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Main {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
//		Scanner sc = new Scanner(new File("input.txt"));
		System.out.println("Bandwidth:");
		int bandWidth = sc.nextInt();
		System.out.println("Delay:");
		int delay = sc.nextInt();
		PointToPointChannel channel = new PointToPointChannel(bandWidth, delay);
		while (true) {
			System.out.println("Enter bytes to send and length of time slot to display:");
			if (!sc.hasNext()) break;
			int bytesToSend = sc.nextInt();
			int timeSlot = sc.nextInt();
			channel.send(bytesToSend);
			System.out.println(channel);
	        while(!channel.isEmpty()) {
	        	channel.tick(timeSlot);
	    		System.out.println(channel);
	        }
		}
		sc.close();
	}
}