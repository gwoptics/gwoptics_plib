package org.gwoptics.testing;

import java.awt.Image;
import java.util.ArrayList;

import org.gwoptics.graphics.GWColour;
import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.traces.Blank2DTrace;
import org.gwoptics.graphics.graph2D.traces.Line2DTrace;
import org.gwoptics.graphics.graph2D.traces.ILine2DEquation;
import org.gwoptics.graphics.graph2D.traces.RollingLine2DTrace;
import org.gwoptics.graphics.graph2D.traces.UpdatingLine2DTrace;
import org.gwoptics.gui.button.gwButton;
import org.gwoptics.gui.button.gwButton.gwButtonMode;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class graphTest extends PApplet{
	class Histogram extends Blank2DTrace{
      	private ArrayList<Float> _data;
      
      	public void bindData(ArrayList<Float> data) {_data = data;}
      
      	public void TraceDraw(PGraphics canvas) {
          if(_data != null){
              
		      for (int i = 0;i < _data.size(); i++) {		    	  
	              canvas.fill(50 + i*25);
	              
		          float val = _data.get(i);
		          canvas.rect(i+0.5f,0,1f,val);		          
		      }
          }
      	}
	}
	
	Histogram hTrace;
	Graph2D g;
	
	public void setup(){
		size(600,500);
		
		hTrace  = new Histogram();
		 
		g = new Graph2D(this, 400,400, true);
		g.setAxisColour(220, 220, 220);
		g.setFontColour(255, 255, 255);
		
		g.position.y = 50;
		g.position.x = 100;
		
		g.setYAxisTickSpacing(1f);
		g.setXAxisTickSpacing(1f);
		g.setXAxisMinorTicks(1);
	
		g.setYAxisMin(-5f);
		g.setYAxisMax(5f);
		
		g.setXAxisMin(0.1f);
		g.setXAxisMax(5.9f);
		g.setXAxisLabelAccuracy(0);
		
		ArrayList<Float> data = new ArrayList<Float>();
		data.add(3f);
		data.add(6f);
		data.add(-3f);
		data.add(4f);
		data.add(2f);
		
		hTrace.bindData(data);
		
		g.addTrace(hTrace);
	}
	
	public void draw(){
		background(0);
		g.draw();
	}
}