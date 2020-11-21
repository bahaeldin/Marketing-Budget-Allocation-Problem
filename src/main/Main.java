/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ga.Channel;

/**
 * @author BAHAELDIN
 *
 */
public class Main {

	/**
	 * @param BAHAELDIN
	 */
	public static void main(String[] args) {
		
		System.out.println(message_1);
		int budget = Integer.parseInt(input.nextLine());
		
		System.out.println(message_2);
		int channelsNumber = Integer.parseInt(input.nextLine());
		
		
		System.out.println(message_3);
		String line;
		List<Channel> channels = new ArrayList<Channel>();
		
		while (channelsNumber != 0) {
			line = input.nextLine();
			channels.add(new Channel().parsLine(line,true));
			channelsNumber--;
		}
		
		System.out.println(message_4);
		for (int i = 0; i < channels.size(); i++) {
			line = input.nextLine();
			channels.get(i).parsLine(line, false);
		}
		
		System.out.println(message_5);
		input.close();
	}
	
	public static Scanner input = new Scanner(System.in); 
	public static String message_1 = "Enter the marketing budget (in thousands):";
	public static String message_2 = "Enter the number of marketing channels:";
	public static String message_3 = "Enter the name and ROI (in %) of each channel separated by space:";
	public static String message_4 = "Enter the lower (k) and upper bounds (%) of investment in each channel:"
								   +"\n(enter x if there is no bound)";
	public static String message_5 = "Please wait while running the GA…";
	public static String message_6 = "The final marketing budget allocation is:";
	public static String message_7 = "The total profit is";

}
