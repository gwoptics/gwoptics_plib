/**
*  This sketch sets out to show how it is possible to use the Rolling2DTrace object
*  on a Graph2D object, while inputting various data using the ILine2DEquation interface.
**/

import org.gwoptics.graphics.colourmap.ColourmapNode;
import org.gwoptics.graphics.colourmap.RGBColourmap;
import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.traces.ILine2DEquation;
import org.gwoptics.graphics.graph2D.traces.RollingLine2DTrace;
import org.gwoptics.graphics.graph2D.traces.UpdatingLine2DTrace;
import org.gwoptics.graphics.graph2D.effects.XAxisColourmapEffect;

//below is 3 equation objects that return data about the mouse position and if a button
//is being pressed. This could easily be used with something like a serial or usb input
//from external devices.

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
	UpdatingLine2DTrace u,u2,u3;
	Graph2D g,g1;
	
	void setup(){
		size(600,600);
		
		r = new RollingLine2DTrace(new eq(),50);
		r.setTraceColour(0, 255, 0);
		
		r2 = new RollingLine2DTrace(new eq2(),50);
		r2.setTraceColour(255, 0, 0);
		
		r3 = new RollingLine2DTrace(new eq3(),50);
		r3.setTraceColour(0, 0, 255);
		 
		g = new Graph2D(this, 400, 200, false);
		g.setYAxisMax(600);
		g.addTrace(r);
		g.addTrace(r2);
		g.addTrace(r3);
		g.position.y = 50;
		g.position.x = 100;
		g.setYAxisTickSpacing(100);                

		u = new UpdatingLine2DTrace(new eq(),50);
		u.setTraceColour(0, 255, 0);
		
		u2 = new UpdatingLine2DTrace(new eq2(),50);
		u2.setTraceColour(255, 0, 0);
		
		u3 = new UpdatingLine2DTrace(new eq3(),50);
		u3.setTraceColour(0, 0, 255);
		 
		g1 = new Graph2D(this, 400, 200, false);
		g1.setYAxisMax(600);
		g1.addTrace(u);
		g1.addTrace(u2);
		g1.addTrace(u3);
		g1.position.y = 300;
		g1.position.x = 100;
		g1.setYAxisTickSpacing(100);
	}
	
	void draw(){
		background(200);
		g.draw();
		g1.draw();
	}
