package main;

import java.util.Calendar;

public class Main { 
	//TODO check all access modifiers
	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		///program code here
		new Program(new ArgumentParser(args));
		///end code here
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("Total Execution Time: "+(end-start));
		
	}

}
