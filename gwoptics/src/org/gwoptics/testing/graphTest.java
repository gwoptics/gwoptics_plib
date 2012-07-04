package org.gwoptics.testing;
/*
ScatterPlot

Example showing how to use blank2DCanvas to quickly create a simple scatter plot.
*/
import java.util.ArrayList;

import org.gwoptics.ValueType;
import org.gwoptics.graphics.GWColour;
import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.backgrounds.GridBackground;
import org.gwoptics.graphics.graph2D.traces.Blank2DTrace;
import org.gwoptics.graphics.graph2D.traces.ILine2DEquation;
import org.gwoptics.graphics.graph2D.traces.Line2DTrace;
import org.gwoptics.graphics.graph2D.traces.RollingLine2DTrace;

import processing.core.PApplet;
import processing.core.PGraphics;


public class graphTest extends PApplet{

        public static void main(String args[])
        {
          
        }
        
	class eq implements ILine2DEquation{
		public double computePoint(double x,int pos) {
			return mouseX;
		}		
	}

	class eq2 implements ILine2DEquation{
		public double computePoint(double x,int pos) {
			return mouseY;
		}		
	}

	class eq3 implements ILine2DEquation{
		public double computePoint(double x,int pos) {
			if(mousePressed)
				return 400;
			else
				return 0;
		}		
	}
	RollingLine2DTrace r,r2,r3;
	Graph2D g;
		
	public void setup(){
		size(600,300);
		
		r  = new RollingLine2DTrace(new eq() ,10,1.0f);
		r.setTraceColour(0, 255, 0);
		
		r2 = new RollingLine2DTrace(new eq2(),10,1.0f);
		r2.setTraceColour(255, 0, 0);
		
		r3 = new RollingLine2DTrace(new eq3(),10,1.0f);
		r3.setTraceColour(0, 0, 255);
		 
		g = new Graph2D(this, 400, 200, false);
		g.setYAxisMax(600);
		g.addTrace(r);
		g.addTrace(r2);
		g.addTrace(r3);
		g.position.y = 50;
		g.position.x = 100;
		g.setYAxisTickSpacing(100);
		g.setXAxisTickSpacing(1f);
		g.setXAxisLabelAccuracy(2);
		g.setXAxisMin(0f);
		g.setXAxisMax(10f);
	}

	public void draw(){
		background(200);
		g.draw();
	}
	
}