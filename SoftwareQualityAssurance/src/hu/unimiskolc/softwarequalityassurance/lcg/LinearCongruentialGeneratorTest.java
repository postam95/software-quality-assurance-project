/*
 * @(#)LinearCongruentialGeneratorTest.java		1.0 20/04/21
 * 
 * Copyright (c) 2020 University of Miskolc
 */

package hu.unimiskolc.softwarequalityassurance.lcg;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
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
 * @version 1.0 21 April 2020
 * @author Mario Posta, University of Miskolc, 2020.04.21.
 */
class LinearCongruentialGeneratorTest {
	
	/**
	 * It provides a reference for a LinearCongruentialGenerator instance
	 * to all unit tests.
	 */
	LinearCongruentialGenerator lcg;

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
	private static List<Integer> generateTestListForGetterAndSetterTesting(int sizeOfList)	{
		List<Integer> list = new LinkedList<>();
		int lowerBound = 1, upperBound = 1000;
		
		for (int index = 0; index < sizeOfList; index++)	{
			list.add((int)(Math.random() * ((upperBound - lowerBound) + 1)) + lowerBound);
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
	void testGetterAndSetterMethods(int a, int c, int m, int k, int sizeOfList) {
		List<Integer> testList = generateTestListForGetterAndSetterTesting(sizeOfList);
		lcg.setA(a);
		lcg.setC(c);
		lcg.setK(k);
		lcg.setM(m);
		lcg.setSequence(testList);
		
		int resultA = lcg.getA();
		int resultC = lcg.getC();
		int resultM = lcg.getM();
		int resultK = lcg.getK();
		List<Integer> resultList = lcg.getSequence();
		
		assertEquals(a, resultA);
		assertEquals(c, resultC);
		assertEquals(m, resultM);
		assertEquals(k, resultK);
		assertTrue(testList.equals(resultList));	
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
	void testSetParameter(int a, int c, int m, int k) {
		lcg.setParameters(a, c, m, k);
		
		int resultA = lcg.getA();
		int resultC = lcg.getC();
		int resultM = lcg.getM();
		int resultK = lcg.getK();
		List<Integer> resultSequence = lcg.getSequence();
		
		assertEquals(a, resultA);
		assertEquals(c, resultC);
		assertEquals(m, resultM);
		assertEquals(k, resultK);
		assertNotNull(resultSequence);
		
		lcg.getSequence().add(a);
		lcg.setParameters(a, c, m, k);
		
		int resultSize = lcg.getSequence().size();
		
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
	void testGenerateNextSequenceXElement(int a, int c, int m, int k, int currentXElement) {
		lcg.setParameters(a, c, m, k);
		int expectedResult = (a * currentXElement + c) % m;
		
		int result = lcg.generateNextSequenceXElement(currentXElement);
		
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
	private static List <Integer> generateTestListForSpecifyDistanceBetweenRepeatingValues (int sizeOfList, int minValue, int maxValue)	{
		List<Integer> testList = new LinkedList<>();
		for (int index = 0, valueToList = minValue ; index < sizeOfList; index++, valueToList++)	{
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
	private static Stream<Arguments> generateArgumentsStream() {
	    List<Arguments> listOfArguments = new LinkedList<>();
	    
	    listOfArguments.add(Arguments.of(generateTestListForSpecifyDistanceBetweenRepeatingValues(100, 1, 50)));
	    listOfArguments.add(Arguments.of(generateTestListForSpecifyDistanceBetweenRepeatingValues(200, 1, 1000)));
	    listOfArguments.add(Arguments.of(generateTestListForSpecifyDistanceBetweenRepeatingValues(300, 100, 200)));
	    listOfArguments.add(Arguments.of(generateTestListForSpecifyDistanceBetweenRepeatingValues(50, 1, 1)));
	    listOfArguments.add(Arguments.of(generateTestListForSpecifyDistanceBetweenRepeatingValues(0, 0, 0)));
	    
	    return listOfArguments.stream();
	}
	
	/**
	 * Test method for {@link LinearCongruentialGenerator#specifyDistanceBetweenRepeatingValues}.
	 * @param testList a list of integers that represents the mathematical
	 * sequence generated by the Linear Congruential Generator.
	 */
	@ParameterizedTest
	@DisplayName("Test for 'specifyDistanceBetweenRepeatingValues' method")
	@MethodSource("generateArgumentsStream")
	void testSpecifyDistanceBetweenRepeatingValues(List<Integer> testList) {
		int expectedResult = 0, meetingPosition = 0, repeatingValue = 0;
		
		lcg.setSequence(testList);
		for (int index = 1; index < testList.size(); index++)	{
			if (testList.get(index) < testList.get(index-1))	{
				expectedResult = testList.get(index-1) - testList.get(index) + 1;
				meetingPosition = index;
				repeatingValue = testList.get(index);
				break;
			}
		}
		
		int result = lcg.specifyDistanceBetweenRepeatingValues(meetingPosition, repeatingValue);
		
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
	@CsvSource({"179870, 19879129, 392300, 12, 6", "211508, 3857765, 995028, 16, 5",
				"191276, 82311778, 802656, 15, 2", "601205, 49605847, 551139, 18, 12",
				"772603, 56945167, 285696, 15, 14", "479233, 47185687, 172688, 8, 8",
				"604634, 84517094, 123050, 15, 4", "716541, 58132495, 229013, 5, 6",
				"614594, 32896081, 184751, 14, 10", "292575, 235759, 435933, 20, 8",
				"0, 0, 0, 0, 0", "1, 1, 4, 2, 2"})
	void testCalculateCycleLength(int a, int c, int m, int k, int expectedResult) {
		lcg.setParameters(a, c, m, k);
		
		int result = lcg.calculateCycleLength();
		
		assertEquals(expectedResult, result);
	}
}
