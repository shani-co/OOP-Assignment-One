package Ex1;

import java.util.ArrayList;


/** This class represents a complex function of type y=g(f1(x), f2(x)), where both f1, f2 are functions (or complex functions), 
 * y and x are real numbers and g is an operation: plus, mul, div, max, min, comp (f1(f2(x))).
 * @author ben itzhak
 * @author shnai cohen
**/
public class ComplexFunction implements complex_function {
	
	/**
	 * Constructor of ComplexFunction using inner class to create tree of Complexfunction, 
	 * function also could be a Complexfunction 
	 * using Complex_Recrusive method that build the tree recrusively by getting each function
	 * toString and current root
	 * @param f
	 */
	public ComplexFunction(function f) {
		if(f == null) {
			throw new RuntimeException("Error: Not Valid Function");
		}
		this.setComplex_root(Complexfunc_Recrusive(f.toString(), this.getComplex_root()));
	}
	/**
	 * Constructor of ComplexFunction using inner class to create tree of
	 * Complexfunction, operation as is the root, left and right are functions
	 * function also could be a Complexfunction 
	 * using Complex_Recrusive method that build the tree recrusively by getting each function
	 * toString and current root
	 * Convert 's' to Operation using String_toOper()
	 * @param s
	 * @param f1
	 * @param f2
	 */
	public ComplexFunction(String s, function f1, function f2) {
		if(f1 == null || f2 == null) {
			throw new RuntimeException("Error: Not Valid Function");
		}
			// incase Operation dont in enum
		this.setComplex_root(new Function_Node(null, String_toOper(s), s));
		this.getComplex_root().setLeft(Complexfunc_Recrusive(f1.toString(), this.getComplex_root().getLeft()));
		this.getComplex_root().setRight(Complexfunc_Recrusive(f2.toString(), this.getComplex_root().getRight()));
	}
	/**
	 * Constructor of ComplexFunction using inner class to create tree of
	 * Complexfunction, operation as is the root, left and right are functions
	 * function also could be a Complexfunction 
	 * using Complex_Recrusive method that build the tree recrusively by getting each function
	 * toString and current root
	 * Convert 'oper' to String using Oper_toString()
	 * @param oper
	 * @param f1
	 * @param f2
	 */
	public ComplexFunction(Operation oper, function f1, function f2) {
		if(f1 == null || f2 == null) {
			throw new RuntimeException("Error: Not Valid Function");
		}
			// incase Operation dont in enum
		this.setComplex_root(new Function_Node(null, oper, Oper_toString(oper)));
		this.getComplex_root().setLeft(Complexfunc_Recrusive(f1.toString(), this.getComplex_root().getLeft()));
		this.getComplex_root().setRight(Complexfunc_Recrusive(f2.toString(), this.getComplex_root().getRight()));
	}
	/** 
	 * return a String representing this complex function
	 * using getPostorder()
	 */
	@Override
	public String toString() {
		String ans = getPostorder(this.getComplex_root());
		return ans;
	}
	
	/**
	 * check if ComplexFunction equals to given object
	 * if isnt even a instaceof function or null return false
	 * else compring f(x) in rage of [-5,5] in 0.01 steps
	 * @param obj
	 * return boolean if equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof function)) {
			return false;
		}
		else{
			function cf = this.initFromString(obj.toString());
			// issue in Check Equal on ComplexFunctions
			// check f(x) in range of [-5,5]  in 0.01 steps
			for(double x = -5; x <= 5; x += 0.01) {
				if(Math.abs(this.f(x) - cf.f(x)) > Monom.EPSILON) {
					return false;
				}
			}
			return true;
		}

	}
	/** compute f(x) for Complex functions using recrusive function fx()
	 * @param x
	 * return f(x)
	 */
	@Override
	public double f(double x) {
		double ans = fx(this.getComplex_root(), x);
		if(Double.isNaN(ans) || Double.isInfinite(ans)) {
			throw new ArithmeticException("Error: divide by zero.");
		}
		return ans;
	}

	/*creating new ComplexFunction using tmp polynom, then creating other complexroot from the string 
	then replace the root*/
	@Override
	public function initFromString(String s) {
		Function_Node new_root = null;
		new_root = Complexfunc_Recrusive(s, new_root);
		ComplexFunction cf = new ComplexFunction(new Polynom("1"));
		cf.setComplex_root(new_root);
		function f = new ComplexFunction(cf);
		return f;
	}
	/** 
	 * return a function representing this complex function
	 */
	@Override
	public function copy() {
		function f = new ComplexFunction(this);
		return f;
	}
	/** Add to this complex_function the f1 complex_function
	 * 
	 * @param f1 the complex_function which will be added to this complex_function.
	 */
	@Override
	public void plus(function f1) {
		//pointer to the complex root
		ComplexFunction right_subtree = new ComplexFunction(f1);
		Function_Node left_subtree = this.getComplex_root();
		this.setComplex_root(new Function_Node(null, Operation.Plus, "plus"));
		this.getComplex_root().setLeft(left_subtree);
		this.getComplex_root().setRight(right_subtree.getComplex_root());
	}

	/** Multiply this complex_function with the f1 complex_function
	 * 
	 * @param f1 the complex_function which will be multiply be this complex_function.
	 */
	@Override
	public void mul(function f1) {
		//pointer to the complex root
		ComplexFunction right_subtree = new ComplexFunction(f1);
		Function_Node left_subtree = this.getComplex_root();
		this.setComplex_root(new Function_Node(null, Operation.Times, "mul"));
		this.getComplex_root().setLeft(left_subtree);
		this.getComplex_root().setRight(right_subtree.getComplex_root());
	}
	/** Divides this complex_function with the f1 complex_function
	 * 
	 * @param f1 the complex_function which will be divid this complex_function.
	 */
	@Override
	public void div(function f1) {
		//pointer to the complex root
		//pointer to the complex root
		ComplexFunction right_subtree = new ComplexFunction(f1);
		Function_Node left_subtree = this.getComplex_root();
		this.setComplex_root(new Function_Node(null, Operation.Divid, "div"));
		this.getComplex_root().setLeft(left_subtree);
		this.getComplex_root().setRight(right_subtree.getComplex_root());
	}
	/** Computes the maximum over this complex_function and the f1 complex_function
	 * 
	 * @param f1 the complex_function which will be compared with this complex_function - to compute the maximum.
	 */
	@Override
	public void max(function f1) {
		//pointer to the complex root
		ComplexFunction right_subtree = new ComplexFunction(f1);
		Function_Node left_subtree = this.getComplex_root();
		this.setComplex_root(new Function_Node(null, Operation.Max, "max"));
		this.getComplex_root().setLeft(left_subtree);
		this.getComplex_root().setRight(right_subtree.getComplex_root());
	}
	/** Computes the minimum over this complex_function and the f1 complex_function
	 * 
	 * @param f1 the complex_function which will be compared with this complex_function - to compute the minimum.
	 */
	@Override
	public void min(function f1) {
		//pointer to the complex root
		ComplexFunction right_subtree = new ComplexFunction(f1);
		Function_Node left_subtree = this.getComplex_root();
		this.setComplex_root(new Function_Node(null, Operation.Min, "min"));
		this.getComplex_root().setLeft(left_subtree);
		this.getComplex_root().setRight(right_subtree.getComplex_root());
	}
	/**
	 * This method wrap the f1 complex_function with this function: this.f(f1(x))
	 * @param f1 complex function
	 */
	@Override
	public void comp(function f1) {
		//pointer to the complex root
		ComplexFunction right_subtree = new ComplexFunction(f1);
		Function_Node left_subtree = this.getComplex_root();
		this.setComplex_root(new Function_Node(null, Operation.Comp, "comp"));
		this.getComplex_root().setLeft(left_subtree);
		this.getComplex_root().setRight(right_subtree.getComplex_root());
	}
	/** returns the left side of the complex function - this side should always exists (should NOT be null).
	 * @return a function representing the left side of this complex funcation
	 */
	@Override
	public function left() {
		ComplexFunction cf = new ComplexFunction(new Polynom("1"));
		cf.setComplex_root(this.getComplex_root().getLeft());
		function f = new ComplexFunction(cf);
		return f;
	}
	/** returns the right side of the complex function - this side might not exists (aka equals null).
	 * @return a function representing the left side of this complex funcation
	 */
	@Override
	public function right() {
		ComplexFunction cf = new ComplexFunction(new Polynom("1"));
		cf.setComplex_root(this.getComplex_root().getRight());
		function f = new ComplexFunction(cf);
		return f;
	}
	/**
	 * The complex_function oparation: plus, mul, div, max, min, comp
	 * @return Operation
	 */
	@Override
	public Operation getOp() {
		return this.getComplex_root().getOper();
	}
	
	
	public Function_Node getComplex_root() {
		return this._Complex_root;
	}
	public void setComplex_root(Function_Node fn) {
		this._Complex_root = fn;
	}


	/********* private fields, inner class and methods ********/
	/**
	 * 
	 * @author ben itzhak
	 * @author shani cohen
	 *this inner class Function_Node used as a Node/tree
	 *of functions and operations has Operation,
	 * functions, OperAsString and left, right fields.
	 */
	private class Function_Node{ 
		private Operation _oper;
		private String _operAsString;
		private function _func; 
		private Function_Node _left, _right; 

		public Operation getOper() {
			return _oper;
		}

		public void setOper(Operation oper) {
			this._oper = oper;
		}

		public function getFunc() {
			return _func;
		}

		public void setFunc(function func) {
			this._func = func;
		}

		public Function_Node getLeft() {
			return _left;
		}

		public void setLeft(Function_Node left) {
			this._left = left;
		}

		public Function_Node getRight() {
			return _right;
		}

		public void setRight(Function_Node right) {
			this._right = right;
		}
		public String get_operAsString() {
			return _operAsString;
		}

		public void set_operAsString(String operAsString) {
			this._operAsString = operAsString;
		} 
		/**
		 * inner class constructor
		 * @param f
		 * @param o
		 * @param s
		 */
		public Function_Node(function f, Operation o, String s) {
			set_operAsString(s);
			setFunc(f); 
			setOper(o);
			setLeft(null);
			setRight(null); 
		}
	}
	/**
	 * compute f(x) for Complex functions recrusivly by checking the fields on the
	 * function_node
	 *  if Operation None, and function null return 
	 * current.left - f(x)
	 * else if Operation None and has function return the f(x)
	 * else check what operation and calc the f(x).
	 * 
	 * @param current
	 * @param x
	 * @return
	 */
	private double fx(Function_Node current, double x){ 

        if (current.getOper() == Operation.None && current.getFunc() == null) {
            return fx(current.getLeft(), x);
        }
        // current has function
        else if(current.getOper() == Operation.None) {
        	return current.getFunc().f(x);
        }
        // current has operation diffrent from none compute is fx
        switch(current.getOper().toString()){
        	case "Plus":
        		return fx(current.getLeft(), x) + fx(current.getRight(), x);
        		
        	case "Times":
        		return fx(current.getLeft(), x) * fx(current.getRight(), x);
        		
        	case "Divid":
        		return fx(current.getLeft(), x) / fx(current.getRight(), x);
        		
        	case "Max":
        		return Math.max(fx(current.getLeft(), x),fx(current.getRight(), x));
        		
        	case "Min":
        		return Math.min(fx(current.getLeft(), x),fx(current.getRight(), x));
        		
        	case "Comp":
        		return fx(current.getLeft(),fx(current.getRight(), x));
        		
        	default:
        		throw new RuntimeException("Error: Not Valid Operation");
        }
	}
	
	/**
	 * getting Complexfunction string recrusivly
	 * by checking the fields on the Function_Node if current has function/Operation or both
	 * @param current
	 * @return ComplexFunction as String
	 */
	private String getPostorder(Function_Node current){ 

        if (current.getOper() == Operation.None && current.getFunc() == null) {
            return getPostorder(current.getLeft());
        }
        // current has function
        else if(current.getOper() == Operation.None) {
        	return current.getFunc().toString();
        }
        // current has oper diffrent from none
        return current.get_operAsString().concat("(".concat(getPostorder(current.getLeft()).concat(",".concat(getPostorder(current.getRight()).concat(")")))));
    } 
	/**
	 * this method Convert string to Operation
	 * if the string don't match any case throw error
	 * @param s
	 * @return Operation from string
	 */
	private Operation String_toOper(String s) {
		switch(s) {
		 case "plus":
			 return Operation.Plus;
		 case "mul":
			 return Operation.Times;
		 case "div":
			 return Operation.Divid;
		 case "max":
			 return Operation.Max;
		 case "min":
			 return Operation.Min;
		 case "comp":
			 return Operation.Comp;
     	default:
    		throw new RuntimeException("Error: Not Valid Operation");
		}
	}
	
	/**
	 * this method Convert Operation to string 
	 * if the oper don't match any case throw error
	 * @param oper
	 * @return String from Operation
	 */
	private String Oper_toString(Operation oper) {
		switch(oper) {
		 case Plus:
			 return "plus";
		 case Times:
			 return "mul";
		 case Divid:
			 return "div";
		 case Max:
			 return "max";
		 case Min:
			 return "min";
		 case Comp:
			 return "comp";
     	default:
    		throw new RuntimeException("Error: Not Valid Operation");
		}
	}
	
	/**
	 * this method built the Complex tree in recrusive way after getting 
	 * the string from string_peeling(), if the string have operation then
	 * create new Oper,left,right Nodes
	 * else create new None,left,null(by defualt) Nodes;
	 * @param func_s
	 * @param current
	 * @return current function_node
	 */
	private Function_Node Complexfunc_Recrusive(String func_s,Function_Node current) {
		ArrayList<String> arr = string_peeling(func_s);
		// no operation declare None as current and left as 'f'
		if(arr.get(0) == "") {
			current = new Function_Node(null, Operation.None, "None");
			current.setLeft(new Function_Node(new Polynom(func_s), Operation.None, "None"));
		}
		else {
			current = new Function_Node(null, String_toOper(arr.get(0)),arr.get(0));
			// set left 
			current.setLeft(Complexfunc_Recrusive(arr.get(1), current.getLeft()));
			// set right
			current.setRight(Complexfunc_Recrusive(arr.get(2), current.getRight()));
		}
		return current;
	}
	
	
	
	/**
	 * This method receive ComplexFunction as string and split it to
	 * Operation,left(from main comma),right(from main comma) format.
	 * if the string doesn't have comma then string is polynom/monon and
	 * return 's' as index 1 in the list
	 * @param s
	 * @return list of string
	 */
	private ArrayList<String> string_peeling(String s) {
		ArrayList<String> list = new ArrayList<String>();
		// returns the index of first occurrence of the specified character
		int index_operation = s.indexOf('(');
		// returns the last occurrence of the character 
		int index_comma = s.lastIndexOf(',');
		// function isnt a complex one
		if(index_comma == -1) {
			list.add("");
			list.add(s);
			return list;
		}
		int number_parenttheses = 0;
		// this loop is to get the right comma for each parenthesis
		for(int i = 0;i < s.length(); i++) {
			if(s.charAt(i) == '(') { number_parenttheses++;}
			else if(s.charAt(i) == ')') { number_parenttheses--;}
			// got the right comma
			if(s.charAt(i) == ',' && number_parenttheses == 1 ) {
				index_comma = i;
				break;
			}
		} 
		// Operation
		list.add(s.substring(0, index_operation));
		//left side of comma
		list.add(s.substring(index_operation + 1, index_comma));
		// right side of cooma
		list.add(s.substring(index_comma + 1, s.length() - 1));
		
		return list;
	}
	private Function_Node _Complex_root;
}
