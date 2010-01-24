package org.gwoptics.gui.button;

import guicomponents.GButton;
//import guicomponents.*;

import org.gwoptics.graphics.Colour;
import org.gwoptics.graphics.IRenderable;

import processing.core.PApplet;

/**
 * Simple GButton extension to get around restrictive GCSchemes that set colours. The only way I could
 * find otherwise is rebuild the G4P library with custom scheme which is a pain.
 * 
 * @author Daniel Brown 1/7/09
 * @since 0.3.7
 *
 */
public class gwButton extends GButton implements IRenderable {

	public gwButton(PApplet theApplet, String text, int x, int y, int width,int height) {
		super(theApplet, text, x, y, width, height);
	}
	
	public void setColours(Colour normal, Colour mouseOver, Colour pressed){
		this.col[0] = normal.toInt();
		this.col[1] = mouseOver.toInt();
		this.col[2] = pressed.toInt();
	}

	public void setParent(PApplet parent){
		if(winApp != null)
			throw new RuntimeException("Parent object has already been set.");
		
		winApp = parent;
	}
}
