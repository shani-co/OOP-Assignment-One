package Ex1;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import java.util.Scanner;

import com.google.gson.Gson;



/**
 * this class represents a collection of mathematical functions
 * @author ben itzhak
 * @author shani cohen
 */

public class Functions_GUI  implements functions {


	/**
	 * Initialize an ArrayList<function>
	 */
	public Functions_GUI() {
		setFunctions_list(new ArrayList<function>());
	}
	/**
	 * Returns the number of elements in this list.

	 */
	@Override
	public int size() {
		return getFunctions_list().size();
	}
	/**
	 * Returns true if this list contains no elements.
	 */
	@Override
	public boolean isEmpty() {
		return getFunctions_list().isEmpty();
	}
	/**
	 * Returns true if this list contains the specified element.
	 */
	@Override
	public boolean contains(Object o) {
		return getFunctions_list().contains(o);
	}
	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 */
	@Override
	public Iterator<function> iterator() {
		return getFunctions_list().iterator();
	}
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
	 */
	@Override
	public Object[] toArray() {
		return getFunctions_list().toArray();
	}
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return getFunctions_list().toArray(a);
	}
	/**
	 * Appends the specified element to the end of this list.
	 */
	@Override
	public boolean add(function e) {
		return getFunctions_list().add(e);
	}

	/**
	 * Removes the first occurrence of the specified element from this list, if it is present.
	 */
	@Override
	public boolean remove(Object o) {
		return getFunctions_list().remove(o);
	}
	/**
	 * Returns true if this collection contains all of the elements in the specified collection.
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return getFunctions_list().containsAll(c);
	}
	/**
	 * Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's Iterator.
	 */
	@Override
	public boolean addAll(Collection<? extends function> c) {
		return getFunctions_list().addAll(c);
	}
	/**
	 * Removes from this list all of its elements that are contained in the specified collection.
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return getFunctions_list().removeAll(c);
	}
	/**
	 * Retains only the elements in this list that are contained in the specified collection.
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return getFunctions_list().retainAll(c);
	}
	/**
	 * Removes all of the elements from this list.
	 */
	@Override
	public void clear() {
		getFunctions_list().clear();

	}
	/**
	 * Init a new collection of functions from a file
	 * using scanner to read the file
	 * source code from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
	 * @param file - the file name
	 * @throws IOException if the file does not exists of unreadable (wrong format)
	 */
	@Override
	public void initFromFile(String file) throws IOException {
		try {
			Scanner scanner = new Scanner(new File(file));
			function cf = new ComplexFunction(new Monom("1"));
			while (scanner.hasNextLine()) {
				this.getFunctions_list().add(cf.initFromString(scanner.nextLine()));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new IOException("File not found");
		}
	}
	/**
	 * Write a new collection of functions to a file
	 * using File, FileWriter
	 * source code https://howtodoinjava.com/java/io/java-write-to-file/
	 * @param file - the file name
	 * @throws IOException if the file is not writable
	 */
	@Override
	public void saveToFile(String file) throws IOException {
		File file_to_save = null;
		FileWriter filewriter = null;
		try {
			file_to_save = new File(file);
			filewriter = new FileWriter(file_to_save);
			Iterator<function> iter = this.iterator();
			while(iter.hasNext()) {
				filewriter.write(iter.next().toString());
				filewriter.write(System.getProperty( "line.separator" ));
			}
			filewriter.close();
		}
		catch (Exception e) {
			throw new IOException("File is not writable");
		} finally {
			try {
				if (filewriter != null) {
					filewriter.close();
				}
			} catch (Exception e) {
				throw new IOException("File is not writable");
			}
		}

	}
	/**
	 * Draws all the functions in the collection in a GUI window using the
	 * given parameters for the GUI windo and the range & resolution
	 * by using StdDraw for drawing
	 * @param width - the width of the window - in pixels
	 * @param height - the height of the window - in pixels
	 * @param rx - the range of the horizontal axis
	 * @param ry - the range of the vertical axis
	 * @param resolution - the number of samples with in rx: the X_step = rx/resulution
	 */
	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		Draw d = new Draw(this.getFunctions_list());
		d.draw_functions(width, height, rx, ry, resolution);
	}

	/**
	 * Draws all the functions in the collection in a GUI window using the given JSON file
	 * using external Gson.jar, and a Class of params to get the right format
	 * @param json_file - the file with all the parameters for the GUI window. 
	 * Note: if the file is not readable or in wrong format should use default values. 
	 */
	@Override
	public void drawFunctions(String json_file) {
		Gson gson = new Gson();
		try 
		{
			FileReader reader = new FileReader(json_file);
			Params param = gson.fromJson(reader,Params.class);
			int height = param.get_height();
			int width = param.get_width();
			int resolution = param.get_resolution();
			Range rx = new Range(param.get_rx()[0],param.get_rx()[1]);
			Range ry = new Range(param.get_ry()[0],param.get_ry()[1]);
			drawFunctions(width, height, rx, ry, resolution);
		} 
		catch (FileNotFoundException e) {
			System.out.println("Not found ,using default paramters");
			Params param = new Params();
			int height = param.get_height();
			int width = param.get_width();
			int resolution = param.get_resolution();
			Range rx = new Range(param.get_rx()[0],param.get_rx()[1]);
			Range ry = new Range(param.get_ry()[0],param.get_ry()[1]);
			drawFunctions(width, height, rx, ry, resolution);
		}
	}
	public ArrayList<function> getFunctions_list() {
		return _functions_list;
	}
	public void setFunctions_list(ArrayList<function> functions_list) {
		this._functions_list = functions_list;
	}
	/** **************** private methods and data ******/
	/**
	 * represents a collection of mathematical functions
	 */
	private ArrayList<function> _functions_list;

}
