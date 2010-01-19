package org.gwoptics.graphicsutils;

import processing.core.PMatrix3D;
import processing.core.PVector;
/**
 * Contains an assortment of functions that can help with some trickier graphical
 * problems.
 * 
 * @author Daniel Brown
 */
public class graphicsUtils {
	
	/**
	 * This function takes a 3D point and determines which pixels on the screen it maps too.
	 * This has various uses such as overlays of 3D displays, or altering pixels using the 
	 * pixel array around certain areas depending on object 3D position. 
	 * 
	 * @param view Camera view matrix
	 * @param proj Camera projection matrix
	 * @param vec 3D point to map to screen coordinates
	 * @param width width of viewport
	 * @param height height of viewport
	 * @return PVector with 2D screen coordinates in the X and Y component of the vector
	 */
	public static float[] convertWorldToScreen(PMatrix3D view, PMatrix3D proj, PVector vec, float width, float height){
		float[] v = new float[4];
		float[] v1 = new float[4];
		float[] v2 = new float[4];
		
		v[0] = vec.x;
		v[1] = vec.y;
		v[2] = vec.z;
		v[3] = 1.0f;
		
		view.mult(v, v1);
		proj.mult(v1, v2);
		  
		v2[0] /= v2[3];
		v2[1] /= v2[3];
		v2[2] /= v2[3];
		
		float[] rtn = new float[2];
		rtn[0] =  (float) (v2[0] + (1 + v2[0]) * width * 0.5);
		rtn[1] = (float) (v2[1] + (1 + v2[1]) * height * 0.5);
		
		return rtn;
	}
}
