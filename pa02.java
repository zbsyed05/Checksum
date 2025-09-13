/*============================================================================
 |	Assignment:	 pa02 - Calculate the checksum of an input file given:
 | 				  -> the name of the input file,
 | 				  -> the checksum size of either 8, 16, or 32 bits
 | 		Author:	Zainab Syed
 | 	  Language: Java
 | 	To Compile: javac pa02.java
 | 				
 | 	To Execute: java -> java pa02 inputFilename.txt checksumSize
 | 				where inputFilename.txt is the input file
 | 				and checksumSize is either 8, 16, or 32
 | 		  Note:
 | 				All input files are simple 8 bit ASCII input
 | 				All execute commands above have been tested on Eustis
 |
 |    Due Date: 3/23/2025
 +===========================================================================*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class pa02 {
	
	// Calculates and returns 8 bit checksum
	public static int calc_8bit_checksum(String txt) {
		// prints text and keeps it within 80 character bound
		if (txt.length() > 80) {	
			System.out.println();
			for(int i = 0; i < txt.length(); i++) {
				System.out.print(txt.charAt(i));
				if(i % 80 == 79) {
					System.out.println();
				}
			}
			System.out.println();
		}
		else {
			System.out.println("\n"+txt);
		}
		
		int checksum = 0;
		// calculates checksum, adding every character
		for(int i = 0; i < txt.length(); i++) {
			checksum += txt.charAt(i);			
		}
		return checksum & 0xFF; // bit masks to last 8 bits
	}
	
	// Calculates and prints 16 bit checksum
	public static void calc_16bit_checksum(String txt) {
		StringBuilder build = new StringBuilder();		
		build.append(txt);
		
		// adds padding if text length isn't even
		if(txt.length() %2 != 0) {
			build.append("X");
		}
		txt = build.toString();
		
		// prints text and keeps it within 80 character bound
		if (txt.length() > 80) {	
			System.out.println();
			for(int i = 0; i < txt.length(); i++) {
				System.out.print(txt.charAt(i));
				if(i % 80 == 79) {
					System.out.println();
				}
			}
			System.out.println();
		}
		else {
			System.out.println("\n"+txt);
		}
		
		int checksum = 0;
		// calculates checksum of every pair of characters
		for( int i = 0; i < txt.length(); i+=2) {
			int top = txt.charAt(i) << 8; 
			int low = txt.charAt(i+1);
			checksum += top + low;
		}
		// print result
		int result = checksum & 0xFFFF; // bit mask to last 16 bits
		System.out.printf("%2d bit checksum is %8x for all %4d chars\n", 16, result, txt.length());
		
		return;
		
	}
	
	// Calculates and print 32 bit checksum
	public static void calc_32bit_checksum (String txt) {		
		StringBuilder build = new StringBuilder();
		build.append(txt);
		
		// adds padding to string
		while(txt.length() %4 != 0) {
			build.append("X");
			txt = build.toString();
		}
		
		// prints text and keeps it within 80 character bound
		if (txt.length() > 80) {	
			System.out.println();
			for(int i = 0; i < txt.length(); i++) {
				System.out.print(txt.charAt(i));
				if(i % 80 == 79) {
					System.out.println();
				}
			}
			System.out.println();
		}
		else {
			System.out.println("\n"+txt);
		}
		
		int checksum = 0;
		// calculates checksum of every four characters
		for(int i = 0; i < txt.length(); i+=4) {
			int first = txt.charAt(i) << 24;
			int second = txt.charAt(i+1) << 16;
			int third = txt.charAt(i+2) << 8;
			int last = txt.charAt(i+3);
			checksum += first + second + third + last;
		}
		
		// print result
		int result = checksum & 0xFFFFFFFF; // bit masks to last 32 bits
		System.out.printf("%2d bit checksum is %8x for all %4d chars\n", 32, result, txt.length());
		
		return;
	}
	
	// reads file and converts to string 
	public static String text(File inputFile) {			
		StringBuilder txt = new StringBuilder();		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line;			
			while((line = reader.readLine())!= null) {
				txt.append(line).append('\n');// adds the new line char	at the end of the file		
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt.toString();
	}

	public static void main(String[] args) {
		// gets input
		String size = args[1];
		File inputFile = new File(args[0]);
		
		// catches input error
		if(!size.equals("8") && !size.equals("16") && !size.equals("32")) {
			System.err.println("Valid checksum sizes are 8, 16, or 32");
		}
		
		// gets contents of the file
		String txt = text(inputFile);
		
		// calculates the checksum
		if(size.equals("8")) {
			int result = calc_8bit_checksum(txt);
			System.out.printf("%2d bit checksum is %8x for all %4d chars\n", 8, result, txt.length());
		}
		
		if(size.equals("16")) {
			calc_16bit_checksum(txt);
		}
		
		if(size.equals("32")) {
			calc_32bit_checksum(txt);
		}
	}

}