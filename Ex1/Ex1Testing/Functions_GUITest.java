package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Functions_GUI;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Params;
import Ex1.Polynom;
import Ex1.Range;
import Ex1.function;
/**
 * @author boaz_benmoshe
 * @author ben itzhak
 * @author shani cohen
 * main test for the GUI_Functions class
 */
class Functions_GUITest {
	private Functions_GUI expected = new Functions_GUI();	
	@BeforeAll
	static void printinit() {
		System.out.println("initialize array of ComplexFunctions before each test");
		System.out.println("plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),\n"
				+ "plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0),\n"
				+ "div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1),\n"
				+ "-1.0x^4 +2.4x^2 +3.1,\n"
				+ "+0.1x^5 -1.2999999999999998x +5.0,\n"
				+ "max(max(max(max(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0),\n"
				+ "min(min(min(min(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)");
	}

	@BeforeEach
	void init()
	{
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for(int i=1;i<s3.length;i++) {
			cf3.mul(new Polynom(s3[i]));
		}
		ComplexFunction cf = new ComplexFunction(Operation.Plus, p1,p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"),cf3);
		cf4.plus(new Monom("2"));
		expected.add(cf.copy());
		expected.add(cf4.copy());
		cf.div(p1);
		expected.add(cf.copy());
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		expected.add(cf5.copy());
		expected.add(cf6.copy());
		Iterator<function> iter = expected.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);
		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		expected.add(max);
		expected.add(min);		
	}

	@Test
	void testSaveToFile_and_InitFromFile() throws IOException {
		expected.saveToFile("function.txt");
		Functions_GUI actual = new Functions_GUI();
		actual.initFromFile("function.txt");
		Iterator<function> iter_expected = expected.iterator();
		Iterator<function> iter_actual = expected.iterator();
		function f1, f2;
		while(iter_expected.hasNext() && iter_actual.hasNext()) {
			f1 = iter_expected.next();
			f2 = iter_actual.next();
			if(!(f1.equals(f2))) {
				fail("expected: "+f1.toString()+"but was: "+f2.toString()+"");
			}
			assertEquals(f1.toString(), f2.toString(), "Test SaveToFile and InitFromFile"); 
		}	
	}
	/**
	 * using default parameter from Params classs
	 */
	@Test
	void testDrawFunctions() {
		if(expected.isEmpty()) {
			fail("No functions to draw");
		}
		Params param = new Params();
		Range r_x = new Range(param.get_rx()[0], param.get_rx()[1]);
		Range r_y = new Range(param.get_ry()[0], param.get_ry()[1]);
		expected.drawFunctions(param.get_width(), param.get_height(), r_x, r_y, param.get_resolution());
	}

	/**
	 * if file doesnt exist using default parameters
	 */
	@Test
	void testDrawFunctionsFromJson() {
		if(expected.isEmpty()) {
			fail("No functions to draw");
		}
		expected.drawFunctions("GUI_params.txt");
	}

}
