package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

/**
 * 
 * @author ben itzhak
 * @author shani cohen
 *main test for the ComplexFunction class
 */
class ComplexFunctonTest {
	private ComplexFunction []cf = new ComplexFunction[5];

	@BeforeAll
	static void printinit() {
		System.out.println("initialize array of ComplexFunctions before each test");
		System.out.println("0,\n"
				+ "plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),\n"
				+ "comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),\n"
				+ "min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),\n"
				+ "div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),max(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1))");
	}

	@BeforeEach
	void init()
	{	
		Monom m = new Monom("0");
		Polynom p1 = new Polynom("+1.0x^1 + 3x^2");
		Polynom p2 = new Polynom("-1.0x^4 +2.4x^2 +3.1");
		Polynom p3 = new Polynom("+2.0x^3 +4.0x^0");
		Polynom p4 = new Polynom("+3.0x^2 +5.0x^1");
		cf[0] = new ComplexFunction(m);
		cf[1] = new ComplexFunction("plus",p1, p2);
		cf[2] = new ComplexFunction("comp",p3, p4);
		cf[3] = new ComplexFunction("min", cf[2], p4);
		cf[4] = new ComplexFunction("div",cf[2], cf[3]);
	}
	
	@Test
	void testToString() {
		String []ans = {"+0.0x^0","plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0)","comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1)",
				"min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)",
		"div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1))"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test toString");
		}
	}
	// epsilon problem
	@Test
	void testF() {
		int x = 2;
		double []ans = {0.0, 10.7, 21300.0, 22.0, 968.1818181818181};
		for(int i = 0; i < 5; i++) {
			double expected = ans[i];
			double actual = cf[i].f(x);
			assertEquals(expected, actual, "Test F(2)");
		}
	}
	@Test
	void testInitFromString_Copy() {
		int x = 2;
		function []ans = {cf[0].initFromString(cf[0].toString()), cf[1].initFromString(cf[1].toString()),
				cf[2].initFromString(cf[2].toString()), cf[3].initFromString(cf[3].toString()), cf[4].initFromString(cf[4].toString())};
		for(int i = 0; i < 5; i++) {
			String expected_string = ans[i].toString();
			String actual_string = cf[i].copy().toString();
			double expected_f = ans[i].f(x);
			double actual_f = cf[i].copy().f(x);
			assertEquals(expected_string, actual_string, "Test initFromString and copy");
			assertEquals(expected_f, actual_f, "Test initFromString and copy");
		}
	}	
	@Test
	void testEquals() {
		Object obj = new Integer(4);
		ComplexFunction cf1;
		for(int i = 0; i < 5; i++) {
			cf1 = new ComplexFunction(cf[i]); 
			if(!(cf[i].equals(cf1))) {
				fail("expected: "+cf[0]+" but was: "+ cf1);
			}
			if(cf[i].equals(obj)) {
				fail("expected: False obj isn't instanceof function");
			}
		}
	}	
	@Test
	void testPlus() {
		//+1.0x^0
		ComplexFunction cf1 = new ComplexFunction(new Polynom("1"));
		String []ans = {"plus(+0.0x^0,+1.0x^0)","plus(plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),+1.0x^0)","plus(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+1.0x^0)",
				"plus(min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),+1.0x^0)",
		"plus(div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)),+1.0x^0)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			cf[i].plus(cf1);
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test plus");
		}
	}
	@Test
	void testMul() {
		//+1.0x^0
		ComplexFunction cf1 = new ComplexFunction(new Polynom("1"));
		String []ans = {"mul(+0.0x^0,+1.0x^0)","mul(plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),+1.0x^0)","mul(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+1.0x^0)",
				"mul(min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),+1.0x^0)",
		"mul(div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)),+1.0x^0)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			cf[i].mul(cf1);
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test mul");
		}
	}
	@Test
	void testDiv() {
		//+1.0x^0
		ComplexFunction cf1 = new ComplexFunction(new Polynom("1"));
		String []ans = {"div(+0.0x^0,+1.0x^0)","div(plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),+1.0x^0)","div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+1.0x^0)",
				"div(min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),+1.0x^0)",
		"div(div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)),+1.0x^0)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			cf[i].div(cf1);
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test div");
		}
	}
	@Test
	void testMax() {
		//+1.0x^0
		ComplexFunction cf1 = new ComplexFunction(new Polynom("1"));
		String []ans = {"max(+0.0x^0,+1.0x^0)","max(plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),+1.0x^0)","max(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+1.0x^0)",
				"max(min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),+1.0x^0)",
		"max(div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)),+1.0x^0)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			cf[i].max(cf1);
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test max");
		}
	}
	@Test
	void testMin() {
		//+1.0x^0
		ComplexFunction cf1 = new ComplexFunction(new Polynom("1"));
		String []ans = {"min(+0.0x^0,+1.0x^0)","min(plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),+1.0x^0)","min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+1.0x^0)",
				"min(min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),+1.0x^0)",
		"min(div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)),+1.0x^0)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			cf[i].min(cf1);
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test min");
		}
	}
	@Test
	void testComp() {
		//+1.0x^0
		ComplexFunction cf1 = new ComplexFunction(new Polynom("1"));
		String []ans = {"comp(+0.0x^0,+1.0x^0)","comp(plus(+3.0x^2 +1.0x^1,-1.0x^4 +2.4x^2 +3.1x^0),+1.0x^0)","comp(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+1.0x^0)",
				"comp(min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1),+1.0x^0)",
		"comp(div(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)),+1.0x^0)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			cf[i].comp(cf1);
			String actual = cf[i].toString();
			assertEquals(expected, actual, "Test comp");
		}
	}
	@Test
	void testLeft() {
		String []ans = {"+0.0x^0","+3.0x^2 +1.0x^1","+2.0x^3 +4.0x^0",
				"comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1)",
		"comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1)"};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i];
			String actual = cf[i].left().toString();
			assertEquals(expected, actual, "Test left");
		}
	}
	@Test
	void testRight() {
		String []ans = {null,"-1.0x^4 +2.4x^2 +3.1x^0","+3.0x^2 +5.0x^1",
				"+3.0x^2 +5.0x^1",
			"min(comp(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)"};
		for(int i = 0; i < 5; i++) {
			try {
			String expected = ans[i];
			String actual = cf[i].right().toString();
			assertEquals(expected, actual, "Test right");
			// right might be null;
			}catch(Exception e) {
				continue;
			}
		}
	}
	@Test
	void test_getOp(){
		String []ans = {"None", "Plus", "Comp", "Min", "Divid"};
		for(int i = 0; i < 5; i++) {
			Operation expected = Operation.valueOf(ans[i]);
			Operation actual = cf[i].getOp();
			assertEquals(expected, actual, "Test getOp");
		}
	}
	@Test
	void test_badString() {
		int expected_errors = 5;
		int actual_errors = 0;
		String []bad_Complexfunctions = {null,"-1.0x^4 +2.4x^2, +3.1x^0","plus(+3.0x^2 +5.0x^1)",
				"cos(+3.0x^2 ,+5.0x^1)",
			"min(None(+2.0x^3 +4.0x^0,+3.0x^2 +5.0x^1),+3.0x^2 +5.0x^1)"};
		for(int i = 0; i < 5; i++) {
			try {
				function f = new ComplexFunction(cf[0].initFromString(bad_Complexfunctions[i]));
			}catch(Exception e) {
				actual_errors++;
			}
		}
		assertEquals(expected_errors, actual_errors,"Test bad strings of ComplexFunction");
	}
}
