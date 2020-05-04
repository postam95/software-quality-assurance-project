/*
 * @(#)LinearCongruentialGeneratorTest.java		1.0 20/05/04
 * 
 * Copyright (c) 2020 University of Miskolc
 */

package hu.unimiskolc.softwarequalityassurance.lcg;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test class for {@link LinearCongruentialGenerator} class. It provides
 * unit tests.
 * 
 * @version 1.0 04 May 2020
 * @author Mario Posta, University of Miskolc. 2020.05.04
 */
class LinearCongruentialGeneratorTest {
	
	/**
	 * It provides a reference for a LinearCongruentialGenerator instance
	 * to all unit tests.
	 */
	static LinearCongruentialGenerator lcg;

	/**
	 * Sets up the LinearCongruentialGenerator instance.
	 */
	@BeforeEach
	void setUp() {
		lcg = new LinearCongruentialGenerator();
	}
	
	/**
	 * It generates lists for the test method of the getters and setters,
	 * {@link LinearCongruentialGeneratorTest#testGetterAndSetterMethods}.
	 * @param sizeOfList the size of the test list
	 * @return a test list
	 */
	private static List<Long> generateTestListForGetterAndSetterTesting(int sizeOfList)	{
		List<Long> list = new LinkedList<>();
		int lowerBound = 1, upperBound = 1000;
		
		for (int index = 0; index < sizeOfList; index++)	{
			list.add((long)(Math.random() * ((upperBound - lowerBound) + 1)) + lowerBound);
		}
		
		return list;
	}

	/**
	 * Test method for getters and setters.
	 * @param a the 'a' parameter of the generator
	 * @param c the 'c' parameter of the generator
	 * @param m the 'm' parameter of the generator
	 * @param k the 'k' parameter of the generator
	 * @param sizeOfList the size of the test list
	 */
	@ParameterizedTest
	@DisplayName("Test for getter and setter methods")
	@CsvSource({"1, 2, 3, 4, 10", "10, 20, 30, 40, 100"})
	void testGetterAndSetterMethods(long a, long c, long m, long k, int sizeOfList) {
		List<Long> testListX = generateTestListForGetterAndSetterTesting(sizeOfList);
		List<Long> testListY = generateTestListForGetterAndSetterTesting(sizeOfList);
		lcg.setA(a);
		lcg.setC(c);
		lcg.setK(k);
		lcg.setM(m);
		lcg.setSequenceX(testListX);
		lcg.setSequenceY(testListY);
		
		long resultA = lcg.getA();
		long resultC = lcg.getC();
		long resultM = lcg.getM();
		long resultK = lcg.getK();
		List<Long> resultListX = lcg.getSequenceX();
		List<Long> resultListY = lcg.getSequenceY();

		assertEquals(a, resultA);
		assertEquals(c, resultC);
		assertEquals(m, resultM);
		assertEquals(k, resultK);
		assertTrue(testListX.equals(resultListX));
		assertTrue(testListY.equals(resultListY));	
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#setParameters}
	 * @param a the 'a' parameter of the generator
	 * @param c the 'c' parameter of the generator
	 * @param m the 'm' parameter of the generator
	 * @param k the 'k' parameter of the generator
	 */
	@ParameterizedTest
	@DisplayName("Test for 'setParamerers' method")
	@CsvSource({"242, 234, 6456, 123, 345", "345, 86780, 786, 68787, 6786767"})
	void testSetParameter(long a, long c, long m, long k) {
		lcg.setParameters(a, c, m, k);
		
		long resultA = lcg.getA();
		long resultC = lcg.getC();
		long resultM = lcg.getM();
		long resultK = lcg.getK();
		List<Long> resultSequence = lcg.getSequenceX();
		
		assertEquals(a, resultA);
		assertEquals(c, resultC);
		assertEquals(m, resultM);
		assertEquals(k, resultK);
		assertNotNull(resultSequence);
		
		lcg.getSequenceX().add(a);
		lcg.setParameters(a, c, m, k);
		
		int resultSize = lcg.getSequenceX().size();
		
		assertEquals(0, resultSize);
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#generateNextSequenceXElement}
	 * @param a the 'a' parameter of the generator
	 * @param c the 'c' parameter of the generator
	 * @param m the 'm' parameter of the generator
	 * @param k the 'k' parameter of the generator
	 * @param currentXElement the current element of the X sequence
	 */
	@ParameterizedTest
	@DisplayName("Test for 'generateNextSequenceXElement' method")
	@CsvSource({"124, 321, 88657, 78, 1424", "2342, 543543, 456, 4, 645645"})
	void testGenerateNextSequenceXElement(long a, long c, long m, long k, long currentXElement) {
		lcg.setParameters(a, c, m, k);
		long expectedResult = (a * currentXElement + c) % m;
		
		long result = lcg.generateNextSequenceXElement(currentXElement);
		
		assertEquals(expectedResult, result);
	}
	
	/**
	 * It generates lists for the test supporter method of the distance specifier method,
	 * {@link LinearCongruentialGeneratorTest#generateArgumentsStream}.
	 * @param sizeOfList size of the list
	 * @param minValue start value of the longest repetition
	 * @param maxValue last value of the longest repetition
	 * @return a test list
	 */
	private static List <Long> generateTestList(long sizeOfList, long minValue, long maxValue)	{
		List<Long> testList = new LinkedList<>();
		for (long index = 0, valueToList = minValue ; index < sizeOfList; index++, valueToList++)	{
			if (valueToList > maxValue)
				valueToList = minValue;
			testList.add(valueToList);
		}
		return testList;
	}
	
	/**
	 * It generates a stream of arguments which contains lists
	 * for the test method of the distance specifier method,
	 * {@link LinearCongruentialGeneratorTest#testSpecifyDistanceBetweenRepeatingValues}.
	 * @return a stream of lists as argument
	 */
	private static Stream<Arguments> generateArgumentsStreamForSpecifyingDistanceBetweenRepeatingValues() {
	    List<Arguments> listOfArguments = new LinkedList<>();
	    
	    listOfArguments.add(Arguments.of(generateTestList(100, 1, 50)));
	    listOfArguments.add(Arguments.of(generateTestList(200, 1, 1000)));
	    listOfArguments.add(Arguments.of(generateTestList(300, 100, 200)));
	    listOfArguments.add(Arguments.of(generateTestList(50, 1, 1)));
	    listOfArguments.add(Arguments.of(generateTestList(0, 0, 0)));
	    
	    return listOfArguments.stream();
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#specifyDistanceBetweenRepeatingValues}.
	 * @param testList a list of integers that represents the mathematical
	 * sequence generated by the Linear Congruential Generator.
	 */
	@ParameterizedTest
	@DisplayName("Test for 'specifyDistanceBetweenRepeatingValues' method")
	@MethodSource("generateArgumentsStreamForSpecifyingDistanceBetweenRepeatingValues")
	void testSpecifyDistanceBetweenRepeatingValues(List<Long> testList) {
		long expectedResult = 0L, repeatingValue = 0L;
		int meetingPosition = 0; 
		
		lcg.setSequenceX(testList);
		for (int index = 1; index < testList.size(); index++)	{
			if (testList.get(index) < testList.get(index-1))	{
				expectedResult = testList.get(index-1) - testList.get(index) + 1L;
				meetingPosition = index;
				repeatingValue = testList.get(index);
				break;
			}
		}
		
		long result = lcg.specifyDistanceBetweenRepeatingValues(meetingPosition, repeatingValue);
		
		assertEquals(expectedResult, result);
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#calculateCycleLength}.
	 * @param a the 'a' parameter of the generator
	 * @param c the 'c' parameter of the generator
	 * @param m the 'm' parameter of the generator
	 * @param k the 'k' parameter of the generator
	 * @param expectedResult the result of the calculation beside the a, c, m,
	 * and k parameters
	 */
	@ParameterizedTest
	@DisplayName("Test for 'calculateCycleLength' method")
	@CsvSource({"2643, 173052, 2029, 9, 2028", "2911, 86166, 2050, 14, 25",
				"1115, 266190, 1393, 10, 198", "2373, 194924, 720, 10, 4",
				"543, 88421, 1447, 9, 1446", "1866, 121353, 1613, 20, 1612",
				"2582, 8024, 1969, 8, 890", "1801, 160908, 2174, 15, 181",
				"1405, 194624, 2004, 10, 498", "2275, 224474, 1653, 6, 126",
				"0, 0, 0, 0, 0", "1, 1, 4, 2, 2"})
	void testCalculateCycleLength(long a, long c, long m, long k, long expectedResult) {
		lcg.setParameters(a, c, m, k);
		
		long result = lcg.calculateCycleLength();
		
		assertEquals(expectedResult, result);
	}
	
	/**
	 * It generates a stream of arguments which contains lists
	 * for the test method of the insider cycle calculator method,
	 * {@link LinearCongruentialGeneratorTest#testSpecifyDistanceBetweenRepeatingValues}.
	 * @return a stream of lists as argument
	 */
	private static Stream<Arguments> generateArgumentsStreamForCalculateInsiderCycle() {
	    List<Arguments> listOfArguments = new LinkedList<>();
	    
	    listOfArguments.add(Arguments.of(generateTestList(300, 1, 50)));
	    
	    return listOfArguments.stream();
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#calculateInsiderCycle}.
	 * @param testList a list of integers that represents the mathematical
	 * sequence generated by the Linear Congruential Generator.
	 */
	@ParameterizedTest
	@DisplayName("Test for 'calculateInsiderCycle' method")
	@MethodSource("generateArgumentsStreamForCalculateInsiderCycle")
	void testCalculateInsiderCycle(List<Long> testList) {	
		long result, expectedResult = 50;
		
		lcg.setSequenceY(testList);
		
		result = lcg.calculateInsiderCycle(1, 50);
		assertEquals(expectedResult, result);
		
		result = lcg.calculateInsiderCycle(1, 100);
		assertEquals(expectedResult, result);
		
		result = lcg.calculateInsiderCycle(1, 200);
		assertEquals(expectedResult, result);
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#calculateInsiderCycle}.
	 * @param testList a list of integers that represents the mathematical
	 * sequence generated by the Linear Congruential Generator.
	 * @throws IOException 
	 */
	@ParameterizedTest
	@DisplayName("Test for 'readFromConsoleInput' method")
	@CsvSource({"124, 321, 88657, 78", "2342, 543543, 456, 645645"})
	void testReadFromConsoleInput(int a, int c, int m, int k) throws IOException {		
		String sysIn = a + " " + c + " " + m + " " + k; 
		System.setIn(new ByteArrayInputStream(sysIn.getBytes()));
		Scanner scanner = new Scanner(System.in);

		lcg.readFromConsoleInput(scanner);
		
		long resultA = lcg.getA();
		assertEquals(a, resultA);
		long resultC = lcg.getC();
		assertEquals(c, resultC);
		long resultM = lcg.getM();
		assertEquals(m, resultM);
		long resultK = lcg.getK();
		assertEquals(k, resultK);
		
		scanner.close();
	}

}
