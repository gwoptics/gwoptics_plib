/**
*  this is sketch that uses the Graph2D object from the library.
*  It plots a static sin function, and shows how to use all the
*  various methods available to alter the graph
**/

import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.traces.Line2DTrace;
import org.gwoptics.graphics.graph2D.traces.ILine2DEquation;

Graph2D g;

/**
*  Equations that are to be plot must be encapsulated into a 
* class implementing the IGraph2DCallback interface.
**/
public class eq implements ILine2DEquation{
	public double computePoint(double x,int pos) {
		return Math.sin(x);
	}		
}

void setup(){
    size(500,300);

    //The following shows all the methods that alter the 
    //layout of the graph.
    
    //arguments are the parent object, xsize, ysize, cross axes at zero point
	g = new Graph2D(this, 400, 200, false);
	g.setYAxisMin(-1);
	g.setYAxisMax(1);
	g.setXAxisMin(-2*PI);
	g.setXAxisMax(2*PI);

    g.setXAxisLabel("X-Axis");
    g.setYAxisLabel("Y-Axis");
    
    g.setXAxisLabelAccuracy(2);//dp to show
    g.setYAxisLabelAccuracy(1);
    
    g.setXAxisTickSpacing(PI/2);

    g.setYAxisTickSpacing(0.25);
    
	g.position.y = 50;
	g.position.x = 60;
    
    //Here we create a new trace and set a colour for
    //it, along with passing the equation object to it.
    Line2DTrace trace = new Line2DTrace(new eq());
    trace.setTraceColour(255,0,0);
    //add the trace to the graph
    g.addTrace(trace);
}

void draw(){
  background(200);
  g.draw();
}
