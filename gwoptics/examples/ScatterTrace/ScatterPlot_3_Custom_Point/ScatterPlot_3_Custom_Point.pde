/**
 * Scatter Plot Custom Point
 * Demonstrates how to plot a scatter plot creating your own custom point
 **/

import org.gwoptics.graphics.GWColour; 
import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.traces.ScatterTrace;

Graph2D grph;

void setup(){
  // Need to specify P2D when using Scatter Trace!
  size(600,600,P2D);

  // Creating the Graph2D object:
  // arguments are the parent object, xsize, ysize, cross axes at zero point
  grph = new Graph2D(this, 450, 450, false); 

  // Defining the main properties of the X and Y-Axis
  grph.setYAxisMin(-10);
  grph.setYAxisMax(10);
  grph.setXAxisMin(-10);
  grph.setXAxisMax(10);
  grph.setXAxisLabel("X-Axis");
  grph.setYAxisLabel("Y-Axis");
  grph.setXAxisTickSpacing(2.5f);
  grph.setYAxisTickSpacing(2.5f);
  
  // Offset of the top left corner of the plotting area
  // to the sketch origin (should not be zero in order to
  // see the y-axis label
  grph.position.x = 80;
  grph.position.y = 60;
  
  ScatterTrace.IScatterPoint Sqaure = new ScatterTrace.IScatterPoint() {
    // everything should be drawn to the canvas object, all the same drawing functions
    // are available to you. The position of the point is (x,y), the HashMap contains
    // various different properties telling you how to plot your point:
    // "size" - the size of the point
    // "colour" - the GWColour object that you need to use to colour the point
    // Of course you can also add extra information but that is covered later on
    // as well as making these more interesting!
    public void drawPoint(float x, float y, PGraphics canvas, HashMap<String, Object> info) {
      float psize = ((Number) info.get("size")).floatValue();
      GWColour c = (GWColour) info.get("colour");

      canvas.stroke(c.toInt());
      canvas.strokeCap(PApplet.ROUND);
      canvas.strokeWeight(0.05f);
      canvas.rect(x - psize, y-psize, 2*psize, 2*psize);
    }
  };

  // Now we add square in here
  ScatterTrace t = new ScatterTrace(Sqaure);
  
  // Sets the size of the point
  t.setDefaultSize(1f);
  
  //Loop 40 times and add a random point
  for(int i=0;i<40;i++){      
    float x =-9f + (float)Math.random()*18f;
    float y = -9f + (float)Math.random()*18f;

    t.addPoint(x, y);
  }
  
  // Adding the trace to the graph
  grph.addTrace(t);
}

void draw(){
  background(255);
  grph.draw();
}