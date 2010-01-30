package org.gwoptics.graphics.colourmap;

import org.gwoptics.graphics.GWColour;

/**
 * Simple interface which acts as a callback object for EquationColourmap
 * to generate a colourmap from a specific equation. Result should be 
 * return as a Colour object
 * 
 * @author Daniel Brown 18/6/09
 * @since 0.2.4
 * @see IColourmapEquation
 * @see EquationColourmap
 * @see GWColour
 */
public interface IColourmapEquation {
	public GWColour colourmapEquation(float loc);
}
