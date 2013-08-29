/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gwoptics.testing;

import org.gwoptics.graphics.GWColour;
import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.traces.ILine2DEquation;
import org.gwoptics.graphics.graph2D.traces.Line2DTrace;
import org.gwoptics.graphics.graph2D.traces.ScatterTrace;
import processing.core.PApplet;

/**
 *
 * @author Daniel
 */
public class ScatterTraceTest extends PApplet {
    
  Graph2D grph;

  public static void main(final String[] args) {  
    PApplet.main( new String[]{ScatterTraceTest.class.getName()} );  
  }

  @Override public void setup(){
    size(600,600);

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

    // Here we create a new trace and set a colour for
    // it, along with passing the equation object to it.
    t = new ScatterTrace(ScatterTrace.Circle);
        
    t.setDefaultSize(7.5f);
    t.setLablePosition(ScatterTrace.LABELPOSITION.CENTER);
    t.setLabelFont(createFont("Arial", 18, true));
    
    for(int i=0;i<5;i++){      
      float x =-9f + (float)Math.random()*18f;
      float y = -9f + (float)Math.random()*18f;
      t.addPoint(x,y,"label",String.format("[%.1f, %.1f]", x,y),"labelcolour", new GWColour(255,255,255));
    }
    // Adding the trace to the graph
    grph.addTrace(t);
    
    frameRate(900);
  }
  
  ScatterTrace t;

  @Override public void draw(){
    background(255);
    grph.draw();
    t.generate();
    println(frameRate);
  }
}
