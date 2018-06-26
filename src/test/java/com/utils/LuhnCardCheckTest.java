/**
 * 
 */
package com.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author neeagarw
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LuhnCardCheckTest {
	
	@Test
	public void test_luhnTest() throws Exception{
		String number = "4111111111111111";
		assertEquals(LuhnCardCheck.luhnTest(number), true);
	}
	
	@Test
	public void test_luhnTest_False() throws Exception{
		String number = "4111111111111121";
		assertEquals(LuhnCardCheck.luhnTest(number), false);
	}
	
}
