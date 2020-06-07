package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Ex1.Monom;
import Ex1.function;


/**
 * 
 * @author ben itzhak
 * @author shani cohen
 * main test for the Monom class
 */
class MonomTest {
	private Monom []m = new Monom[5];
	
	@BeforeAll
	static void printinit() {
		System.out.println("initialize array of monoms before each test");
		System.out.println("+1.0x^1, -1.0x^1, -4.6x^2, +0.0x^0, -2.6x^0");
	}
	
	@BeforeEach
	void init()
	{
		 m[0] = new Monom("x");
		 m[1] = new Monom("-x");
		 m[2] = new Monom("-4.6X^2");
		 m[3] = new Monom("0");
		 m[4] = new Monom("-2.6");
	}
	
	@Test
	void testDerivative() {
		Monom []ans = {new Monom("1"),new Monom("-1"),new Monom("-9.2x"),new Monom("0"),new Monom("0")};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i].toString();
			String actual = m[i].derivative().toString();
			assertEquals(expected, actual, "Test derivative");
		}
	}
	@Test
	void testF() {
		int x = 2;
		double []ans = {2, -2, -18.4, 0, -2.6};
		for(int i = 0; i < 5; i++) {
			double expected = ans[i];
			double actual = m[i].f(x);
			assertEquals(expected, actual, "Test f(2)");
		}
	}
	@Test
	void testisZero() {
		Boolean []ans = {false, false, false, true, false};
		for(int i = 0; i < 5; i++) {
			Boolean expected = ans[i];
			Boolean actual = m[i].isZero();
			assertEquals(expected, actual, "Test isZero");
		}
	}
	@Test
	void testAdd() {
		Monom m1 = new Monom("3x");
		Monom []ans = {new Monom("4x"),new Monom("2x"),new Monom("-4.6x^2"),new Monom("3x"),new Monom("-2.6")};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i].toString();
			try {
				m[i].add(m1);
			}catch(Exception e) {
				System.out.println("Test Worked, when power is different can't add");
			}
			String actual = m[i].toString();
			assertEquals(expected, actual, "Test add");
		}
	}
	@Test
	void testMultipy() {
		Monom m1 = new Monom("3x");
		Monom []ans = {new Monom("3x^2"),new Monom("-3x^2"),new Monom("-13.8x^3"),new Monom("0"),new Monom("-7.8x")};
		for(int i = 0; i < 5; i++) {
			Monom expected = ans[i];
			m[i].multipy(m1);
			Monom actual = m[i];
			if(!(expected.equals(actual))) {
				fail("expected: "+expected.toString()+" but was: "+actual.toString());
			}
		}
	}
	@Test
	void testEquals() {
		Object obj = new Integer(4);
		Monom []ans = {new Monom(m[0].toString()),new Monom(m[1].toString()),new Monom(m[2].toString()),new Monom(m[3].toString()),new Monom(m[4].toString())};
		for(int i = 0; i < 5; i++) {
			Monom expected = ans[i];
			Monom actual = m[i];
			if(!(expected.equals(actual))) {
				fail("expected: "+expected.toString()+" but was: "+actual.toString());
			}
			if(m[i].equals(obj)) {
				fail("expected: false obj isn't instanceof function");
			}
		}
	}
	@Test
	void testInitFromString_Copy() {
		int x = 2;
		Monom m1 = new Monom("3");
		String []ans = {"+1.0x^1", "-1.0x^1", "-4.6x^2", "+0.0x^0", "-2.6x^0"};
		for(int i = 0; i < 5; i++) {
			String expected_string = m1.initFromString(ans[i]).toString();
			String actual_string = m[i].copy().toString();
			double expected_f = m1.initFromString(ans[i]).f(x);
			double actual_f = m[i].copy().f(x);
			assertEquals(expected_string, actual_string, "Test initFromString and copy");
			assertEquals(expected_f, actual_f, "Test initFromString and copy");
		}
	}
	@Test
	void test_badString() {
		int expected_errors = 5;
		int actual_errors = 0;
		String []bad_Monoms= {"+1.0x^^1", "-1.0^1", "--4.6x^2", "+0.0x^-5", "-2.6xx^0"};
		for(int i = 0; i < 5; i++) {
			try {
				function f = new Monom(bad_Monoms[i]);
			}catch(Exception e) {
				actual_errors++;
			}
		}
		assertEquals(expected_errors, actual_errors, "Test bad string of Monoms");
	}
}
