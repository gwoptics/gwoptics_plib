package org.gwoptics.graphics.colourmap;

import org.gwoptics.graphics.Colour;

/**
 * <p>
 * ColourmapNode is a class that encapsulates the colour and location of a point, on a 
 * colourmap. These nodes are then used to generate a gradient of colour between 2 locations.
 * Implements the Comparable interface to allow sorting of nodes into location order.
 * </p>
 * 
 * @author Daniel Brown 12/6/09
 * @since 0.1.1
 */

public final class ColourmapNode implements Comparable<ColourmapNode>{		
	public Colour colour;
	public float location;

	public ColourmapNode(){
		colour  = new Colour();
		location = 0.0f;
	}
	
	/**
	 * Custom constructor that allows user specified RGB and location values.
	 * 
	 * @param R Value between 0.0f and 1.0f relating to the red colour.
	 * @param G Value between 0.0f and 1.0f relating to the green colour.
	 * @param B Value between 0.0f and 1.0f relating to the blue colour.
	 * @param l Value between 0.0f and 1.0f relating to the location of the colour on the colourmap.
	 */
	public ColourmapNode(float R,float G, float B, float l){
		colour  = new Colour(1f,R,G,B);
		location = l;
	}
	
	public ColourmapNode(float Alpha, float R,float G, float B, float l){
		colour  = new Colour(Alpha,R,G,B);
		location = l;
	}
	
	public int compareTo(ColourmapNode o) {
		if(location < o.location){return -1;}
		if(location == o.location ){return 0;}
		if(location > o.location ){return 1;}
		return 0;
	}
}