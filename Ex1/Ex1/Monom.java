
package Ex1;

import java.util.Comparator;
import java.util.Iterator;


/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 * @author Ben Itzhak
 * @author Shani Cohen
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	
	
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	
	/** 
	 * this method returns the value of the monom in given x.
	 * @param x
	 * @return f(x) 
	 */
	@Override
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	/**
	 * Constructor - creating monom to a given String 
	 * @param s
	 * modify string to lower case, remove spaces
	 * using isValidMonom function to check if monom is valid
	 * if true initialize using init_monom function
	 * else throw exception
	 */
	public Monom(String s) {
		s = s.replaceAll(" ", "").toLowerCase();
		
		// check if string is valid monom, using isValidMonom()
		/* try { */
			if(!(isValidMonom(s))) {
				throw new RuntimeException("Error: Not Valid Monom");
			}
			init_monom(s);
	}
	/**
	 * add two monons of same power if the given monom has different power throw exception;
	 * using comperator to check powers
	 * @param m
	 */
	public void add(Monom m) {
		if(this.isZero()) {
			this.set_coefficient(m.get_coefficient());
			this.set_power(m.get_power());
		}
		else if(getComp().compare(this, m) != 0) {
				throw new RuntimeException("Error: Power is different, Can't add");
				}
		else {
			//Change only the coefficient, both monoms as same power
			this.set_coefficient(this.get_coefficient() + m.get_coefficient());
		}
	}
	/**
	 * Multiply two monons
	 * Modify monom by multiply coefficient, add power to a given monom
	 * @param d
	 */
	public void multipy(Monom d) {
		this.set_coefficient(this.get_coefficient() * d.get_coefficient());
		this.set_power(this.get_power() + d.get_power());
	
	}
	/**
	 * return a string of the monom
	 */
	@Override
	public String toString() {
		String ans;
		if(this.get_coefficient() < 0) {
			ans = String.valueOf(this.get_coefficient()).concat("x^").concat(String.valueOf(this.get_power()));
		}
		else {
			ans = "+".concat(String.valueOf(this.get_coefficient()).concat("x^").concat(String.valueOf(this.get_power())));
		}
		return ans;
	}
	// you may (always) add other methods.
	
	/**
	 * compare monom and object,check instanceof 
	 * and handle accordingly 
	 * @param obj
	 * @return true if equal else false
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof function)) {
			return false;
		}
		else if(obj instanceof Monom) {
			Monom m = new Monom(obj.toString());
			if(m.isZero() && this.isZero()) {
				return true;
			}
			int dpower = m.get_power() - this.get_power();
			double dcoeff = m.get_coefficient() - this.get_coefficient();
			if(dpower == 0 && Math.abs(dcoeff) < EPSILON) {return true;}
			return false;
		}
		else if(obj instanceof Polynom){
			Polynom p = new Polynom(obj.toString());
			Polynom temp = new Polynom(this.toString());
			// p or temp has the zero_Monom
			int index_p = p.get_polynom().size() - 1;
			int index_temp = temp.get_polynom().size() - 1;
			if(p.get_polynom().get(index_p).isZero()){
				p.get_polynom().remove(index_p);
			}
			if(temp.get_polynom().get(index_temp).isZero()){
				temp.get_polynom().remove(index_temp);
			}
			// check if size is equal after omitting the zero monom
			if(p.get_polynom().size() != temp.get_polynom().size()) {
				return false;
			}
			Iterator<Monom> iter = p.iteretor();
			Iterator<Monom> iter1 = temp.iteretor();
			Monom m, m1;
			while(iter1.hasNext() && iter.hasNext()) {
				m = iter.next(); m1 = iter1.next();
				if(!(m.equals(m1))) {
					return false;
				}
			}
			return true;
		}
		// obj is Complex Function
		else {
			ComplexFunction cf1 = new ComplexFunction(this);
			function cf2 = cf1.initFromString(obj.toString());
			if(!(cf1.equals(cf2))) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * using Integer.parseInt to determine if the String can be Integer
	 * @param s
	 * @return if integer return true else false
	 */
	private Boolean isInteger(String s) {
		int temp;
		Boolean flag = true;
		try {
			  temp = Integer.parseInt(s);
			  // power can be only positive integer
			  if(temp < 0) {flag = false;}
			}
			catch (Exception e)
		// contains not only integer
			{flag = false;}	
		return flag;
	}
	
	/**
	 * using Double.parseDouble to determine if the String can be Double
	 * @param s
	 * @return if double return true else false
	 */
	private Boolean isDouble(String s) {
	boolean flag = true;
	try {
		Double.parseDouble(s);
		}
		catch(Exception e) 
	// contains not only Double
			{flag = false;}
	return flag;
	}

	/**
	 * check if the given string is a valid monom
	 * @param input
	 * @return if is valid return true else false;
	 */
	private boolean isValidMonom(String input) {
		boolean flag = true;
		//Empty string 
		if(input.length() == 0) {
			return false;
		}
		// with x
		if(input.contains("x")) {
			String[] coef_and_pow = input.split("x");
			//Duplicate x
			if(coef_and_pow.length > 2) {
				return false;
			}
			// case of 2xxxxx
			else if(input.endsWith("x"))
				if(input.indexOf("x") != input.length() - 1) {
					return false;
				}
			//power side of x split func
			try {
				// if power is'nt empty string go to nested if
				if(coef_and_pow[1].length() > 0) {
					if(!(coef_and_pow[1].startsWith("^"))) {
						return false;
						}
					else {
						//check if the pow is valid integer
						flag = isInteger(coef_and_pow[1].substring(1));
					}
				}
			}
			catch(Exception e){
				//power is empty string, still valid continue
			}
			// coefficient side of x split function 
			try {
				// if coefficient is'nt empty string go to nested if
				if(coef_and_pow[0].length() > 0) {
					if(flag && coef_and_pow[0].length() == 1 && (coef_and_pow[0].startsWith("-") || coef_and_pow[0].startsWith("+")) ) {
						return true;
					}
					else if(flag) {
							flag = isDouble(coef_and_pow[0]);
					}
				}
			}
			catch(Exception e){
				//coef is empty string, still valid continue
			}
        }
		else {
		//without x
			return isDouble(input);
		}
	 return flag; 
	}
	/**
	 * initialize new monom from string
	 * @param s
	 */
	private void init_monom(String s) {
		// only x
		if(s.length() == 1 && s.equals("x")) {
			this.set_coefficient(1);
			this.set_power(1);
		}
		//only 0 or -0
		else if(s.length() == 1 && s.equals("0") || s.length() == 2 && s.equals("-0")) {
			this.set_coefficient(0);
			this.set_power(0);
		}
		// has x in the string
		else if(s.contains("x")) {
			String[] coef_and_pow = s.split("x");
			// coefficient
		try {
			this.set_coefficient(Double.parseDouble(coef_and_pow[0]));
		}
		catch(Exception e) {
			//Error 1 can't turn '-' or '+' signs to double
			if(coef_and_pow[0].length() == 1) {
				this.set_coefficient(Double.parseDouble(coef_and_pow[0].concat("1")));
			}
			else {
			//Error 2 there is no coefficient
			this.set_coefficient(1);
			}
		}
		//power
		try {
			this.set_power(Integer.parseInt(coef_and_pow[1].substring(1)));
			}
			catch(Exception e) {
				//Error 1 there is no power
				this.set_power(1);
			}
		}
		else {
			this.set_coefficient(Double.parseDouble(s));
			this.set_power(0); 
		}
	}

	
	@Override
	public function initFromString(String s) {
		function f = new Monom(s);
		return f;
	}
	@Override
	public function copy() {
		function f = new Monom(this.toString());
		return f;
	}

	//****************** Private Methods and Data *****************
	

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
}
