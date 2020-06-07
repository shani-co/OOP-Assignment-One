package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Ex1.Monom;
import Ex1.Polynom;
import Ex1.function;
/**
 * 
 * @author ben itzhak
 * @author shani cohen
 *main test for the Polynom class
 */

class PolynomTest {
	Polynom[] p1 = new Polynom[5];

	@BeforeAll
	static void printinit() {
		System.out.println("initialize array of polynoms before each test");
		System.out.println("x^2+3x+9, 3x^3+x^2+2, 2x^2+5x+3, 3x^3+x^2, x^2+x");
	}

	@BeforeEach
	void init() {
		p1[0]= new Polynom ("x^2+3x+9");
		p1[1]= new Polynom ("3x^3+x^2+2");
		p1[2]= new Polynom ("2x^2+5x+3");
		p1[3]= new Polynom ("3x^3+x^2");
		p1[4]= new Polynom ("x^2+x");
	}
	@Test
	void testDerivative() {
		Polynom []ans = {new Polynom("2X+3+0"),new Polynom("2x+9x^2+0"), new Polynom ("4x+5+0"),new Polynom("2x+9x^2"),new Polynom("1+2x")};
		for(int i=0;i<5;i++) {
			String expected = ans[i].toString();
			String actual = p1[i].derivative().toString();
			assertEquals(expected, actual, "Test derivative");
		}
	}
	@Test
	void testF() {
		int x = 2;
		double []ans = {19, 30,21,28,6};
		for(int i = 0; i < 5; i++) {
			double expected = ans[i];
			double actual = p1[i].f(x);
			assertEquals(expected, actual, "Test f(2)");
		}
	}
	@Test
	void testisZero() {
		Boolean []ans = {false, false, false, false, false};
		for(int i = 0; i < 5; i++) {
			Boolean expected = ans[i];
			Boolean actual = p1[i].isZero();
			assertEquals(expected, actual, "Test isZero");
		}
	}
	@Test
	void testAdd() {
		Polynom p2 = new Polynom("3x^2+5x");
		Polynom []ans = {new Polynom("4x^2+8x+9"),new Polynom("3x^3+4x^2+5x+2"),new Polynom("5x^2+10x+3")
				,new Polynom("4x^2+3x^3+5x"),new Polynom("4x^2+6x")};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i].toString();
			p1[i].add(p2);
			String actual = p1[i].toString();
			assertEquals(expected, actual, "Test add polynom to polynom");
		}
	}
	@Test	
	void testAddM() {
		Monom m;
		Polynom []ans = {new Polynom("x^2+6x+9"),new Polynom("3x^3+x^2+3x+2"),new Polynom("2x^2+8x+3")
				,new Polynom("3x^3+x^2+3x"),new Polynom("x^2+4x")};
		for(int i = 0; i < 5; i++) {
			m = new Monom("3x");
			String expected = ans[i].toString();
			p1[i].add(m);
			String actual = p1[i].toString();
			assertEquals(expected, actual, "Test add monom to polynom");
		}
	}
	@Test
	void testEquals() {
		Object obj = new Integer(4);
		Polynom []ans = {new Polynom(p1[0].toString()),new Polynom(p1[1].toString()),new Polynom(p1[2].toString())
				,new Polynom(p1[3].toString()),new Polynom(p1[4].toString())};
		for(int i = 0; i < 5; i++) {
			Polynom expected = ans[i];
			Polynom actual = p1[i];
			if(!(expected.equals(actual))) {
				fail("expected: "+expected.toString()+" but was: "+actual.toString());
			}
			if(p1[i].equals(obj)) {
				fail("expected: false obj isn't instanceof function");
			}
		}
	}

	@Test
	void testMultipyM() {
		Monom m1 = new Monom("3x");
		Polynom []ans = {new Polynom("3x^3+9x^2+27x"),new Polynom("3x^3+9x^4+6x"),new Polynom("6x^3+15x^2+9X")
				,new Polynom("3x^3+9x^4"),new Polynom("3x^2+3x^3")};
		for(int i = 0; i < 5; i++) {
			Polynom expected = ans[i];
			p1[i].multiply(m1);
			Polynom actual = p1[i];
			if(!(expected.equals(actual))) {
				fail("expected: "+expected.toString()+" but was: "+actual.toString());

			}
		}
	}

	@Test 
	void testMultipy() {
		Polynom m1 = new Polynom("x^2+1");
		Polynom []ans = {new Polynom("x^4+3X^3+10X^2+3X+9"),new Polynom("3X^5+X^4+3X^3+3X^2+2")
				,new Polynom("2X^4+5X^3+5X^2+5x+3"),new Polynom("3X^5+X^4+3X^3+X^2"),new Polynom("X^3+X^4+X+X^2")};
		for(int i = 0; i < 5; i++) {
			Polynom expected = ans[i];
			p1[i].multiply(m1);
			Polynom actual = p1[i];
			if(!(expected.equals(actual))) {
				fail("expected: "+expected.toString()+" but was: "+actual.toString());

			}
		}
	}	

	@Test
	void testSubstract() {
		Polynom p2 = new Polynom("x^2+x");
		Polynom []ans = {new Polynom("2x+9"),new Polynom("3x^3-x+2"),new Polynom("x^2+4x+3")
				,new Polynom("3x^3-x"),new Polynom("")};
		for(int i = 0; i < 5; i++) {
			String expected = ans[i].toString();
			p1[i].substract(p2);
			String actual = p1[i].toString();
			assertEquals(expected, actual, "Test substract polynom");
		}
	}
	@Test
	void Test_toString_and_Copy() throws Exception {
		String[] polynoms = {p1[0].copy().toString(),p1[1].copy().toString(), p1[2].copy().toString(), p1[3].copy().toString(),p1[4].copy().toString()};
		for(int i=0;i<polynoms.length;i++) {
			String expected = polynoms[i];
			String actual = p1[i].toString();
			assertEquals(expected, actual, "Test toString and copy");
		}
	}
	@Test
	void TestRoot() {
		int expected_errors = 2;
		int actual_errors = 0;
		double actual = 0, expected = 0;
		double []ans = {Integer.MIN_VALUE,-1.0, -1.0, -0.33349609375};
		for(int i=0;i<p1.length;i++) {
			try {
				actual = p1[i].root(-1, 2, 0.0001);
				expected = ans[i];
				assertEquals(expected, actual, "Test root");
			}catch(Exception e) {
				actual_errors++;
			}
		}
		assertEquals(expected_errors, actual_errors, "Test root throws");
	}
	@Test
	void TestArea() {
		int expected_errors = 0;
		int actual_errors = 0;
		double actual = 0, expected = 0;
		double []ans = {34.501300004997766,20.251500027493297, 22.501050009996117,
						14.671153119839001, 4.666966669998734};
		for(int i=0;i<p1.length;i++) {
			try {
				actual = p1[i].area(-1, 2, 0.0001);
				expected = ans[i];
				assertEquals(expected, actual, "Test area");
			}catch(Exception e) {
				actual_errors++;
			}
		}
		assertEquals(expected_errors, actual_errors, "Test area throws");
	}
	@Test
	void test_badString() {
		int expected_errors = 6;
		int actual_errors = 0;
		String []bad_Polynoms= {"10x^1 + 3xx", "(x + 2x^3)","-5.5x3*(x + 3x^3)", "(2x^2-4)*(-1.2x-7.1)", "3 + x^3 - bx^3", "3 + 3x^-1"};
		for(int i = 0; i < 6; i++) {
			try {
				function f = new Polynom(bad_Polynoms[i]);
			}catch(Exception e) {
				actual_errors++;
			}
		}
		assertEquals(expected_errors, actual_errors, "Test bad Polynoms");
	}


}
