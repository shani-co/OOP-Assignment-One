package Ex1;
/**
 * this class represent set of parameters for the GUI windo and the range & resolution
 * that will be used for drawing functions with StdwDraw
 * @author ben itzhak
 * @author shani cohen
 *
 */
public class Params {
	
	public int get_width() {
		return Width;
	}

	public void set_width(int _width) {
		this.Width = _width;
	}

	public int get_height() {
		return Height;
	}

	public void set_height(int _height) {
		this.Height = _height;
	}

	public int get_resolution() {
		return Resolution;
	}

	public void set_resolution(int _resolution) {
		this.Resolution = _resolution;
	}

	public int[] get_rx() {
		return Range_X;
	}

	public void set_rx(int[] _rx) {
		this.Range_X = _rx;
	}

	public int[] get_ry() {
		return Range_Y;
	}

	public void set_ry(int[] _ry) {
		this.Range_Y = _ry;
	}
	/**
	 * constructor using defualt Values
	 */
	public Params() {
		set_width(1000);
		set_height(700);
		set_resolution(600);
		int[] r = {-10,10};
		set_rx(r);
		set_ry(r);
		
	}	
	/**
	 * constructor that get values to set the parameters 
	 * @param w
	 * @param h
	 * @param res
	 * @param rx
	 * @param ry
	 */
	public Params(int w, int h, int res, int[] rx, int[] ry) {
		set_width(w);
		set_height(h);
		set_resolution(res);
		set_rx(rx);
		set_ry(ry);
		
	}
	/********** private data *********/
	private int Width, Height, Resolution;
	private int[] Range_X, Range_Y;
	
}
