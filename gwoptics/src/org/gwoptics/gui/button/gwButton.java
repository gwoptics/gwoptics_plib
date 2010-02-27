/**
 *  Copyright notice
 *  
 *  This file is part of the Processing library `gwoptics' 
 *  http://www.gwoptics.org/processing/gwoptics_p5lib/
 *  
 *  Copyright (C) 2009 onwards Daniel Brown and Andreas Freise
 *  
 *  This library is free software; you can redistribute it and/or modify it under 
 *  the terms of the GNU Lesser General Public License version 2.1 as published 
 *  by the Free Software Foundation.
 *  
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License along with 
 *  this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, 
 *  Suite 330, Boston, MA 02111-1307 USA 
 */

package org.gwoptics.gui.button;

import guicomponents.GButton;
//import guicomponents.*;

import org.gwoptics.graphics.GWColour;
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
	
	public void setColours(GWColour normal, GWColour mouseOver, GWColour pressed){
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
