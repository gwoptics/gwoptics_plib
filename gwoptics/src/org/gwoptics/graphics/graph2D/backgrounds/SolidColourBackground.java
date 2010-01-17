package org.gwoptics.graphics.graph2D.backgrounds;

import org.gwoptics.graphics.Colour;
import org.gwoptics.graphics.graph2D.Axis2D;

import processing.core.PApplet;

public class SolidColourBackground implements IGraph2DBackground {
	protected Axis2D _ax, _ay;
	protected int _width, _height;
	protected PApplet _parent;
	protected Colour _background;
	
	/** Sets the background colour of the graph **/
	public void setBackgroundColour(int R, int G, int B){_background = new Colour(R, G, B);}
	/** Removes any background **/
	public void setNoBackground(){_background = null;}
	
	public SolidColourBackground(Colour bk){
		_background = bk;
	}
	
	public void setAxes(Axis2D x, Axis2D y) {
		_ax = x;
		_ay = y;
	}

	public void setDimensions(int width, int height) {
		_width = width;
		_height = height;
	}
	
	public void setParent(PApplet parent) {
		if(_parent != null)
			throw new RuntimeException("Parent object has already been set"); 
			
		_parent = parent;	
	}
	
	public void draw() {
		if(_background != null && _parent != null){
			_parent.pushStyle();
			_parent.pushMatrix();
			_parent.translate(0, -_height/2);
			_parent.fill(_background.toInt());
			_parent.noStroke();
			_parent.rect(0, 0, _height,_width);
			_parent.popStyle();
			_parent.popMatrix();
		}
	}

}
