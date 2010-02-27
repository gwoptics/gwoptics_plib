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

import java.awt.Point;

import guicomponents.*;

import org.gwoptics.graphics.GWColour;
import org.gwoptics.graphics.IRenderable;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Simple GButton extension to allow you to alter the border colours without needing
 * to create a GScheme.
 * 
 * Update to 1.3 G4P library. col variable is nolonger being used by GButton so had to
 * update to using the localColor.
 * 
 * Also added functionality to use 3 different button images for normal, over and pressed
 * statuses.
 * 
 * @author Daniel Brown 1/7/09
 * @since 0.3.7
 */
public class gwButton extends GButton implements IRenderable {

	public enum gwButtonMode{
		PLAIN_COLOURS,
		IMAGES		
	}
	
	private boolean _drawImages;
	private PImage _imgOver,_imgNorm,_imgPress;
	
	public gwButton(PApplet theApplet, String text, int x, int y, int width,int height) {
		super(theApplet, text, x, y, width, height);
		_drawImages = false;
	}
	
	public void setColours(GWColour normal, GWColour mouseOver, GWColour pressed){
		localColor.btnOff = normal.toInt();
		localColor.btnOver = mouseOver.toInt();
		localColor.btnDown = pressed.toInt();
		localColor.btnDown = pressed.toInt();
	}

	public void setImages(PImage imgNormal, PImage imgOver, PImage imgPressed){
		if(imgNormal == null) throw new RuntimeException("Normal button image cannot be null");
		if(imgOver == null) throw new RuntimeException("Over button image cannot be null");
		if(imgPressed == null) throw new RuntimeException("Pressed button image cannot be null");
		
		_imgNorm = imgNormal;
		_imgOver = imgOver;
		_imgPress = imgPressed;
	}
	
	public void setButtonMode(gwButtonMode mode){_drawImages = (mode == gwButtonMode.IMAGES);}
	
	public void setParent(PApplet parent){
		if(winApp != null)
			throw new RuntimeException("Parent object has already been set.");
		
		winApp = parent;
	}
	
	public void draw(){
		if(!visible) return;
		winApp.pushStyle();
		//winApp.style(G4P.g4pStyle);
		Point pos = new Point(0,0);
		calcAbsPosition(pos);
		
		if(_drawImages){
			switch(status){
			case 0:
				winApp.image(_imgNorm,pos.x,pos.y,width,height);
				break;
			case 1:
				winApp.image(_imgOver,pos.x,pos.y,width,height);
				break;
			case 2:
				winApp.image(_imgPress,pos.x,pos.y,width,height);
				break;
			}
		}else{
			switch(status){
			case 0:
				winApp.fill(localColor.btnOff);
				break;
			case 1:
				winApp.fill(localColor.btnOver);
				break;
			case 2:
				winApp.fill(localColor.btnDown);
				break;
			}

			// Draw button rectangle
			winApp.strokeWeight(1);
			winApp.stroke(localColor.btnBorder);
			winApp.rect(pos.x,pos.y,width,height);
			
			// Draw image
			if(bimage[status] != null){
				winApp.image(bimage[status], pos.x + imgAlignX, pos.y+(height-bimage[status].height)/2);
			}
		}	
		
		// Draw text
		winApp.noStroke();
		winApp.fill(localColor.btnFont);
		winApp.textFont(localFont, localFont.size);
		winApp.text(text, pos.x + alignX, pos.y + (height - localFont.size)/2 - PADV, width, height);
		winApp.popStyle();
	}
}
