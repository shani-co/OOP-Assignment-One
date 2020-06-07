 package Ex1;

import java.awt.Color;
import java.util.ArrayList;
/**
 * this class Draws all the functions in the collection in a GUI window using the
 * given parameters for the GUI windo and the range & resolution
 * using array of colors to generate new color for each function up to eight
 *  then return to the first color
 * @author ben itzhak
 * @author shnai cohen
 *
 */
public class Draw {
	/**
	 * constructor that get collection of functions
	 * @param functions_list
	 */
	public Draw(ArrayList<function> functions_list) {
		setFunctions_list(functions_list);
	}
	
	/**
	 * Draws all the functions in the collection using StdDraw
	 * @param w
	 * @param h
	 * @param rx
	 * @param ry
	 * @param res
	 */
	public void draw_functions(int w, int h, Range rx, Range ry, int res) {
		StdDraw.setCanvasSize(w, h); // GUI windo witdh and height
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(0, ry.get_min(), 0, ry.get_max()); // X axis
		StdDraw.line(rx.get_min(), 0, ry.get_max(), 0);// Y axis
		
		StdDraw.setPenRadius(0.003);
		for(double x = rx.get_min();x<rx.get_max(); x++ ) {
			StdDraw.setPenColor(Color.LIGHT_GRAY);
			StdDraw.line(x, ry.get_min(), x, ry.get_max());
			StdDraw.setPenColor(Color.BLACK);
			// text
			StdDraw.text(x + 0.05, -0.5, Double.toString(x));
		}
		for(double y = ry.get_min();y<ry.get_max(); y++ ) {
			StdDraw.setPenColor(Color.LIGHT_GRAY);
			StdDraw.line(rx.get_min(), y, rx.get_max(), y);
			StdDraw.setPenColor(Color.BLACK);
			// text 
			StdDraw.text( -0.5, y + 0.05, Double.toString(y));
		}
		StdDraw.setPenRadius(0.01);
		int j;
		for(int i = 0; i < this.getFunctions_list().size(); i += 1) {
			j = i % Colors.length;
			StdDraw.setPenColor(Colors[j]);
			double x_step = Math.abs(rx.get_max() - rx.get_min())/res;
			for(double x = rx.get_min(); x < rx.get_max(); x += x_step){
				try {
					double y1 = this.getFunctions_list().get(i).f(x);
					double y2 = this.getFunctions_list().get(i).f(x + x_step);
					StdDraw.line(x, y1, x + x_step, y2);
				}catch(Exception e) {
					// if divide by 0,dont draw,continue
				}
				
			}
		}
	}	
	
	public ArrayList<function> getFunctions_list() {
		return this._function_list;
	}
	public void setFunctions_list(ArrayList<function> functions_list) {
		this._function_list = functions_list;
	}
	/**** Private methods and data ******/
	/**
	 * array of colors
	 */
	private static Color[] Colors = {Color.blue, Color.cyan, Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.YELLOW, Color.PINK}; 
	/**
	 * collection of functions
	 */
	private ArrayList<function> _function_list;

}
