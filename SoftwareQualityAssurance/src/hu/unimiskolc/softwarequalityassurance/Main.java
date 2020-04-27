/*
 * @(#)Main.java		1.0 20/04/21
 * 
 * Copyright (c) 2020 University of Miskolc
 */

package hu.unimiskolc.softwarequalityassurance;

import java.util.Scanner;

/**
 * Main class. The base of this program which provides entry and exit point.
 * 
 * @version 1.0 21 April 2020
 * @author Mario Posta, University of Miskolc. 2020.04.21.
 */
public class Main {

	/**
	 * The main function where the program begins and ends. This function reads
	 * parameters from the console input, calls the methods of the
	 * {@link LinearCongruentialGenerator} class which generates a mathematical
	 * sequence and calculates the length of the longest repetition of
	 * this sequence.
	 * @param args are not used in this program
	 */
	public static void main(String[] args) {
		int a, c, m, k, numberOfTestCases;
		Scanner scanner = new Scanner(System.in);
		LinearCongruentialGenerator lcg = new LinearCongruentialGenerator();
		
		numberOfTestCases = scanner.nextInt();
		
		for (int index = 0; index < numberOfTestCases; index++)	{
			a = scanner.nextInt();
			c = scanner.nextInt();
			m = scanner.nextInt();
			k = scanner.nextInt();
			
			lcg.setParameters(a, c, m, k);
			System.out.println("a = " + a + " c = " + c + " m = " + m + " k = " + k);
			System.out.println(lcg.calculateCycleLength());
			System.out.println(lcg.getSequence());
			System.out.println(lcg.getSequenceB());
		}
		
		scanner.close();
	}

}
