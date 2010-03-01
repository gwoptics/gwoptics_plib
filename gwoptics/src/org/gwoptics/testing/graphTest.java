package org.gwoptics.testing;

import guicomponents.*;
import org.gwoptics.gui.button.*;

import processing.core.PApplet;
import processing.core.PImage;

public class graphTest extends PApplet{
	
	gwButton btn;

	public void setup(){
		  size(300,300);
		    
		  btn = new gwButton(this,"test",10,10,200,200);
		  
		  PImage imgNorm = loadImage("norm.png");
		  PImage imgOver = loadImage("over.png");
		  PImage imgPressed = loadImage("pressed.png");
		  
		  btn.setUseImages(true);
		  btn.setImages(imgNorm,imgOver,imgPressed);
	}
	
	public void draw(){}
}