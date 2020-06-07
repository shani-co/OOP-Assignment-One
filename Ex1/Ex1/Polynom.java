package Ex1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Ex1.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 * @author Ben Itzhak
 * @author Shani Cohen
 *
 */
public class Polynom implements Polynom_able{
	
	/**
	 * Zero (empty polynom)
	 * creating the Arraylist<Monom> of size 0 
	 */
	public Polynom() {
		// creating the Arraylist<Monom>
		set_polynom_zero();
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 * Split the String through Regex for polynomial expression to group of monoms 
	 * source code from https://stackoverflow.com/questions/36490757/regex-for-polynomial-expression
	 * then check if each monom using isValidMonom() 
     * if valid add it to the _polynom Arraylist
     * union the monoms in the polynom by power
     * then sort them using Arraylist.sort(comparator)
	 */
	public Polynom(String s) {
		// creating the Arraylist<Monom>
			set_polynom();
			Monom m;
			s = s.replaceAll(" ", "").toLowerCase();
			// Split the String through Regex for polynomial expression to group of monoms
			Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
			Matcher matcher = pattern.matcher(s);
			while (matcher.find()) {
			// check if string is valid monom, using isValidMonom()
			/*
			 * if(!(Monom.isValidMonom(matcher.group(1)))) { throw new
			 * RuntimeException("Error: Not Valid Polynom"); }
			 */
				try {
					m = new Monom(matcher.group(1));
				}catch(Exception e) {
					throw new RuntimeException("Error: Not Valid Polynom");
				}
				get_polynom().add(m);
			}
			// unions monoms of same power
			union_monoms();
			// sort the polynom in order using comparator
			this.get_polynom().sort(Monom.getComp());
	}
	/** 
	 * this method returns the value of the polynom in given x.
	 * using iterator on the polynom then check each monoms value of a given x.
	 * @param x
	 * @return f(x) 
	 */
	@Override
	public double f(double x) {
		Iterator<Monom> iter = this.iteretor();
		double f_x = 0;
		Monom m;
		while(iter.hasNext()) {
			m = iter.next();
			f_x += (m.get_coefficient() * Math.pow(x, m.get_power()));
		}
		return f_x;
	}
	
	/**
	 * working on copy of the polynom in case p1 is the same as Polynom Class object
     * add two polynoms by merge them into one polynom then use union_monoms()
   	 * and sort polynom by power
	 * using comperator
	 * @param m
	 */
	@Override
	public void add(Polynom_able p1) {
		Polynom_able temp = (Polynom_able) p1.copy();
		Iterator<Monom> iter = temp.iteretor();
		Monom m;
		while(iter.hasNext()) {
			m = iter.next();
			this.get_polynom().add(m);
		}
		// unions monoms of same power
		union_monoms();
		// sort the polynom in order using comparator
		this.get_polynom().sort(Monom.getComp());
	}
	
	/**
     * add monom to a polynom then use union_monoms()
   	 * and sort the polynom by power
	 * using comperator
	 * @param m
	 */
	@Override
	public void add(Monom m1) {
		this.get_polynom().add(m1);
		// unions monoms of same power
		union_monoms();
		// sort the polynom in order using comparator
		this.get_polynom().sort(Monom.getComp());
 
	}
	/**
	 * working on copy of the polynom in case p1 is the same as Polynom Class object
     * substract two polynoms multiply p1 by monom_minus1 then use add function on p1
	 * @param m
	 */
	@Override
	public void substract(Polynom_able p1) {
		Polynom_able temp = (Polynom_able) p1.copy();
		Iterator<Monom> iter = temp.iteretor();
		Monom m;
		// multiply every monom in p1 with monom minus1;
		while(iter.hasNext()) {
			m = iter.next();
			m.multipy(Monom.MINUS1);
		}
		// add -(p1) to _polynom
		add(temp);
	}
	/**
	 * multiply monom to every monom in the polynom then use union_monoms()
  	 * and sort the polynom by power
	 * using comperator
	 * @param m
	 */
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> iter = this.iteretor();
		Monom m;
		while(iter.hasNext()) {
				m = iter.next();
				m.multipy(m1);
		}
		// unions monoms of same power
		union_monoms();
		// sort the polynom in order using comparator
		this.get_polynom().sort(Monom.getComp());
		
	}
	/**
	 * working on copy of the polynom in case p1 is the same as Polynom Class object
     * multiply two polynoms by iterate each one using iterator and multiply this.monon * p1.monom 
 	 * then use union_monoms() and sort the polynom by power
	 * using comperator
	 * @param m
	 */
	@Override
	public void multiply(Polynom_able p1) {
		// in case p1 is this.polynom , need to copy twice 
		Polynom_able temp = (Polynom_able) this.copy();
		Polynom_able temp1 = (Polynom_able) p1.copy();
		this.get_polynom().clear();
		Iterator<Monom> iter;
		Iterator<Monom> iter1 = temp1.iteretor();
		Monom m, m1;
		while(iter1.hasNext()) {
			iter = temp.iteretor();
			m1 = iter1.next();
			while(iter.hasNext()) {
				m = new Monom(iter.next().toString());
				m.multipy(m1);
				this.add(m);
			}
		}
		// unions monoms of same power
		union_monoms();
		// sort the polynom in order using comparator
		this.get_polynom().sort(Monom.getComp());
	}
	/**
	 * compare polynom and object,check instanceof 
	 * and handle accordingly 
	 * @param o2
	 * @return true if equal else false
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof function)) {
			return false;
		}
		else if(!(obj instanceof ComplexFunction)) {
			Polynom p = new Polynom(obj.toString());
			Polynom temp = (Polynom) this.copy();
			// p or temp has the zero_Monom
			int index_p = p.get_polynom().size() - 1;
			int index_temp = temp.get_polynom().size() - 1;
			if(p.get_polynom().get(index_p).isZero()){
				p.get_polynom().remove(index_p);
			}
			if(temp.get_polynom().get(index_temp).isZero()){
				temp.get_polynom().remove(index_temp);
			}
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
			function cf = cf1.initFromString(obj.toString());
			if(!(cf1.equals(cf))) {
				return false;
			}
			return true;
		}

	}
	/**
	 * check if polynom is zero 
	 * if empty then return true
	 * if every monom has coefficient of zero then return true
	 * else false
	 */
	@Override
	public boolean isZero() {
		//empty polynom
		if(this.get_polynom().size() == 0) {return true;}
		Monom m;
		// iterate through the monoms and check whether the coefficient is zero 
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			m = iter.next();
			if(!(m.isZero())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, else should throws runtimeException 
	 * computes f(x') such that:
	 * 	(i) x0<=x'<=x1 && 
	 * 	(ii) |f(x')|<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps>0 (positive) representing the epsilon range the solution should be within.
	 * @return an approximated value (root) for this (cont.) function 
	 */
	@Override
	public double root(double x0, double x1, double eps) {
	
		/* try { */
			if(eps <= 0) {
			throw new RuntimeException("Error: epsilon must be bigger than 0");
			}
			else if(this.f(x0) * this.f(x1) > 0) {
				throw new RuntimeException("Error: both f(x0) and f(x1) are positive value - there is no root");
			}
			// f(x0) or f(x1) is 0 return it
			if(this.f(x0) * this.f(x1) == 0) {
				if(this.f(x0) < this.f(x1)) {return x0;}
				else {return x1;}
			}
			// using the 
			double negetive, positive, middle;
			if(x0 < x1) {
				negetive = x0; positive = x1;
			}
			else {negetive = x1; positive = x0; }
			// f(x0) or f(x1) equal 0 return the root; 
			middle = (negetive+positive)/2;
			while(Math.abs(f(middle)) > eps) {
				if(f(middle) < 0) {
					negetive = middle;
					middle = (negetive+positive)/2;
				}
				else {
					positive = middle;
					middle = (negetive+middle)/2;
				}
			}
			return middle;
		/*
		 * } catch(Exception e){ //catch block e.printStackTrace(); }
		 */

	}
	/**
	 * copy polynom by iterate on each monom string then create new polynom 
	 */
	@Override
	public function copy() {
		function p1 = new Polynom(this.toString());
		return p1;
	}
	/** 
	 * this method returns the derivative polynom of this.
	 * @return
	 */
	@Override
	public Polynom_able derivative() {
		Polynom p1;
		String polynom1 = "";
		Iterator<Monom> iter = this.iteretor();
		Monom m;
		while(iter.hasNext()) {
			m = iter.next();
			polynom1 += m.derivative().toString().concat(" ");
		}
		p1 = new Polynom(polynom1);
		return p1;
	
	}
	/**
	 * Compute a Riman's integral from x0 to x1 in eps steps. 
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps positive step value
	 * @return the approximated area above X-axis below this function bounded in the range of [x0,x1]
	 */
	@Override
	public double area(double x0, double x1, double eps) {
			if(eps <= 0) {
			throw new RuntimeException("Error: epsilon must be bigger than 0");
			}
			double riman_sum = 0;
			while(x0<x1){
				// check if f(x0)=y > 0 (above above X-axis)
				if(f(x0) > 0){
					riman_sum += eps *f(x0);
				}
			x0 += eps;			
			}
		return riman_sum;
	}
	/**
	 * creating a iterator to the private Arryalist _polynom
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return get_polynom().iterator();
	}
	/**
	 * return a string of the polynom
	 */
	@Override
	public String toString() {
		String ans = "";
		Iterator<Monom> iter = this.iteretor();
		Monom m;
		while(iter.hasNext()) {
			m = iter.next();
			ans += m.toString().concat(" ");
		}
		return ans.trim();
	}
 
	/**
	 * return the private Arraylist of the polynom 
	 */
	public ArrayList<Monom> get_polynom() {
		return this._polynom;
	}
	
	@Override
	public function initFromString(String s) {
		function f = new Polynom(s);
		return f;
	}
	
	
	//****************** Private Methods and Data *****************
	
	/**
	 * iterate on the polynom and check if each monom has other monom of the same power if there is
	 * union monoms of same power else continue to other monoms
	 * remove monoms-zero 
	 * @param p1
	 */
	private void union_monoms() {
		int i = 0;
		Monom m, m1;
		while (i < this.get_polynom().size()) {
			m = this.get_polynom().get(i);
			for(int j = i + 1; j < this.get_polynom().size(); j++) {
				m1 = this.get_polynom().get(j);
			// clear zero monom
				if(m.isZero()) {
					this.get_polynom().remove(i);
					i = -1;
					break;
				}
				// both has same power
				else if(Monom.getComp().compare(m, m1) == 0) {
					m1.add(m);
					this.get_polynom().remove(i);
					i = -1;
					break;
				}
			}
			i++;
			if(i == this.get_polynom().size() && m.isZero() && m.get_power() != 0) {
				this.get_polynom().remove(i - 1);
			}
		}
		
	}

	
	private void set_polynom_zero(){
		this._polynom = new ArrayList<Monom>(0);
	}
	private void set_polynom(){
		this._polynom = new ArrayList<Monom>();
	}
	/**
	 * represents a collection of monoms
	 */
	private ArrayList<Monom> _polynom;
}
