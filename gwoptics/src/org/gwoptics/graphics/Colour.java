package org.gwoptics.graphics;

import processing.core.PApplet;

/**
 * Simple class that encapsulates the 4 colour components alpha, red, green and blue. It should be noted that
 * the values should be stored as normalised floats between 0.0f and 1.0f not 0 and 255.
 * 
 * Contains several functions to relate ARGB as a 32-bit integer using bit shifting.
 * 
 * @author Daniel Brown 12/6/09
 * @since 0.1.1
 *
 */
public final class Colour{
	public float A;
	public float R;
	public float G;
	public float B;
	
	public Colour(){
		A = 1;
		R = 0;
		G = 0;
		B = 0;
	}
	
	/**
	 * Colour constructor allowing user defined RGB values. Values should be between 0.0f and 1.0f.
	 * 
	 * @param Red Red value between 0.0f and 1.0f.
	 * @param Green Green value between 0.0f and 1.0f.
	 * @param Blue Blue value between 0.0f and 1.0f.
	 */
	public Colour(float Red, float Green, float Blue){
		A = 1;
		R = PApplet.constrain(Red, 0f, 1f);
		G = PApplet.constrain(Green, 0f, 1f);
		B = PApplet.constrain(Blue, 0f, 1f);
	}
	
	public Colour(float Alpha, float Red, float Green, float Blue){
		A = PApplet.constrain(Alpha, 0f, 1f);;
		R = PApplet.constrain(Red, 0f, 1f);
		G = PApplet.constrain(Green, 0f, 1f);
		B = PApplet.constrain(Blue, 0f, 1f);
	}
	
	/**
	 * Colour constructor allowing user defined RGB values. Values should be between 0 and 255.
	 * 
	 * @param Red Red value between 0 and 255.
	 * @param Green Green value between 0 and 255. 
	 * @param Blue Blue value between 0 and 255.
	 */
	public Colour(int Red, int Green, int Blue){
		A = 1;
		R = PApplet.constrain(Red/255f,0f,1f);
		G = PApplet.constrain(Green/255f,0f,1f);
		B = PApplet.constrain(Blue/255f,0f,1f);
	}
	
	public Colour(int alpha, int Red, int Green, int Blue){
		A = PApplet.constrain(alpha/255f,0f,1f);
		R = PApplet.constrain(Red/255f,0f,1f);
		G = PApplet.constrain(Green/255f,0f,1f);
		B = PApplet.constrain(Blue/255f,0f,1f);
	}

	
	/**
	 * Returns a string in the format (R,G,B).
	 */
	public String toString(){
		return "(" + A + "," + R + "," + G + "," + B + ")";
	}
	
	/**
	 * A static function used to convert 0 to 255 values of RGB into an integer. Alpha is assumed to be 255.
	 * 
	 * @param R Red value between 0 and 255
	 * @param G Green value between 0 and 255
	 * @param B Blue value between 0 and 255
	 * @return Integer with 4 bytes in the form ARGB
	 */
	public static int convertRGBToInt(byte R, byte G, byte B){
		return (255 << 24) | (R << 16) | (G << 8) | B;
	}
	
	/**
	 * A static function used to convert 0 to 255 values or ARGB into an integer.
	 * 
	 * @param A Alpha value between 0 and 255
	 * @param R Red value between 0 and 255
	 * @param G Green value between 0 and 255
	 * @param B Blue value between 0 and 255
	 * @return Integer with 4 bytes in the form ARGB
	 */
	public static int convertARGBToInt(byte A, byte R, byte G, byte B){
		return (A << 24) | (R << 16) | (G  << 8) | B ;
	}
	
	/**
	 * Static function to convert a Colour object into an integer.
	 * @param c Colour object
	 * @return Integer with 4 bytes in the form ARGB
	 */
	public static int convertColourToInt(Colour c){
		return (((int)(c.A*255) << 24) | (int)(c.R*255) << 16) | ((int)(c.G*255) << 8) | (int)(c.B*255);
	}
	
	/**
	 * Static function to convert an integer into a Colour object.
	 * 
	 * @param iC Integer to convert
	 * @return Colour object relating to integer
	 */
	public static Colour convertIntToColour(int iC){
		Colour c = new Colour();
		//bit mask and shift
		c.B = (iC & 0xFF)/255f;
		c.G = ((iC >> 8)& 0xFF)/255f;
		c.R = ((iC >> 16)& 0xFF)/255f;
		c.A = ((iC >> 24)& 0xFF)/255f;
		return c;
	}
	
	/**
	 * Adds 2 colours
	 */
	public static Colour add(Colour c1, Colour c2){
		Colour c = new Colour(c1.A * c1.R + c2.A * c2.R,
							c1.A * c1.G + c2.A * c2.G,
							c1.A * c1.B + c2.A * c2.B);
		c.A = 1;
		return c;
	}
	
	/**
	 * Multiplies 2 colours 
	 */
	public static Colour multiply(Colour c1, Colour c2){
		Colour c = new Colour(c1.A * c1.R * c2.A * c2.R,
							c1.A * c1.G * c2.A * c2.G,
							c1.A * c1.B * c2.A * c2.B);
		c.A = 1;
		return c;
	}
	
	/**
	 * Returns int version of a Colour but applies, alpha to each channel
	 */
	public static int getAlphaRGB(Colour c){
		return ((int)(c.R*c.A*255) << 16) | ((int)(c.G*c.A*255) << 8) | (int)(c.B*c.A*255);
	}
	
	/**
	 * Converts Colour object into an integer.
	 */
	public int toInt(){
		return convertColourToInt(this);
	}
	
	/**
	 * Converts Colour object into an integer.
	 */
	public int toAlphaInt(){
		return getAlphaRGB(this);
	}
}
