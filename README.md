# Ex1


For Installation https://github.com/Benitk/OOP-Assignment-One/wiki/Installation


### Authors:
-----------
Ben Itzhak

Shani Cohen


## Summary:
-------
The project focus on functions of type y=f(x), where both y and x are real numbers.
and has four main Classes. 
1. Monom - represents a simple “Monom” of shape a*x^b, where a is a real number and b Is an integer (b is only positive).
2. Polynom - represents an collections of Monoms.
3. ComplexFunction - represents a complex function of type y=g(f1(x), f2(x)), where both f1, f2 are functions (or complex functions), 
   y and x are real numbers and g is an operation: plus, mul, div, max, min, comp (f1(f2(x))). 
   implantation by inner Node class to create a tree of function and operations.
4. Function_GUI - represents an collections of functions (Monon, Polynom, ComplexFunction).
 
## Monom class methods:
------
1. Derivative – Derivative of monom 
2. f – Compute the value of the monom on a given x – f(x) 
3. Add – Add two monoms 
4. Multiply - Multiply two monoms 
5. toString – Return a string of the monom 
6. equals – Compare monom to an object and return true if both equal else false 
7. isZero – Check if the Monom has coefficient of 0 
8. initFromString - Creating an new object for Monom from string
9. copy - Creating deep copy of the Monom object

## Polynom class methods: 
-----
1. Derivative – Derivative of polynom 
2. f – Compute the value of the polynom on a given x – f(x) 
3. Add – Add two polynoms 
4. Add – Add monom with polynom 
5. Substract = Substract two polynoms 
6. Multiply - Multiply two polynoms 
7. Multiply - Multiply monom with polynom 
8. toString – Return a string of the polynom 
9. equals – Compare polynom to an object and return true if both equal else false 
10. isZero – Check if the every monom in the polynom has coefficient of zero or size of the polynom is zero 
11. root – Calculate the point of intersection approximately epsilon between two points 
12. copy – Create a copy of the polynom object
13. area – Calculate the area between two point of the polynom in epsilon steps  
14. iteretor – Create an iterator to the polynom 
15. initFromString - Creating an new object for Polynom from string

## ComplexFunction class methods: 
-----
1. toString – Return a string of the ComplexFunction
2. f – Compute the value of the polynom on a given x – f(x): f 
3. equals – Compare polynom to an object and return true if both equal else false 
4. initFromString - Creating an new object for ComplexFunction from string
5. copy - Creating deep copy of the ComplexFunction object
6. plus - Add to this complex_function a function
7. mul - Multiply to this complex_function a function
8. max - Computes the maximum over this complex_function and a function
9. min - Computes the minimum over this complex_function and a function
10. comp - Wrap this ComplexFunction(cf) with a function(f1) w: this.cf(f1(x))
11. div - Divides to this complex_function the f1 function
12. left - Returns the left side of the complex function - this side should always exists.
13. right - Returns the right side of the complex function - this side might not exists (aka equals null)
14. getOp - The complex_function oparation: plus, mul, div, max, min, comp

## Function_GUI class methods: 
-----
1. initFromFile - Init a new collection of functions from a file using scanner to read the file
2. saveToFile - Write a new collection of functions to a file using File, FileWriter
3. drawFunctions - Draws all the functions in the collection in a GUI window using the given parameters using StdDraw for drawing
4. drawFunctions(String json_file) - Draws all the functions in the collection in a GUI window using the given JSON file using external Gson.jar and StdDraw for drawing

#### Collections builtin methods
1. size - Returns the number of elements in this list
2. isEmpty - Returns true if this list contains no elements
3. contains -Returns true if this list contains the specified element
4. iterator - Returns an iterator over the elements in this list in proper sequence
5. toArray - Returns an array containing all of the elements in this list in proper sequence (from first to last element)
6. toArray(T[] a) - Returns an array containing all of the elements in this list in proper sequence (from first to last element)
7. add - Appends the specified element to the end of this list
8. remove -  Removes the first occurrence of the specified element from this list, if it is present
9. containsAll - Returns true if this collection contains all of the elements in the specified collection
10. addAll - Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's Iterator
11. removeAll -  Removes from this list all of its elements that are contained in the specified collection
12. retainAll - Retains only the elements in this list that are contained in the specified collection
13. clear - Removes all of the elemets from this list

### For Auxiliary classes ,Junit5Tests and more information, Please visit our wiki.
