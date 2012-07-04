/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gwoptics.testing;

import java.util.ArrayList;
import org.gwoptics.graphics.graph2D.*;
import org.gwoptics.graphics.graph2D.traces.Blank2DTrace;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.opengl.PGraphics3D;

/**
 *
 * @author Daniel
 */
public class Scatter_label_test extends processing.core.PApplet {

  PFont myFont;

  public static void main(final String[] args) {
    PApplet.main(new String[]{Scatter_label_test.class.getName()});
  }

  class Point2D {

    public float X, Y;

    public Point2D(float x, float y) {
      X = x;
      Y = y;
    }
  }

  class ScatterTrace extends Blank2DTrace {

    private ArrayList _data;
    private float pSize = 0.1f;

    public ScatterTrace() {
      _data = new ArrayList();
    }

    public void addPoint(float x, float y) {
      _data.add(new Point2D(x, y));
    }

    private void drawPoint(Point2D p, PGraphics canvas) {
      // the scale factor is [length of axis]/([axis max] - [axis min])
      Axis2D ax = grph.getXAxis();
      Axis2D ay = grph.getYAxis();
      
      float sx = ax.getLength() / (ax.getMaxValue() - ax.getMinValue());
      float sy = ay.getLength() / (ay.getMaxValue() - ay.getMinValue());
      
      canvas.pushStyle();
      canvas.stroke(255, 0, 0);
      canvas.strokeCap(PApplet.SQUARE);
      canvas.strokeWeight(0.05f);
      canvas.line(p.X - pSize, p.Y, p.X + pSize, p.Y);
      canvas.line(p.X, p.Y - pSize, p.X, p.Y + pSize);
      canvas.popStyle();
      
      
      // The BlankCanvas trace works by creating a scaled canvas on which
      // we draw, if we try and draw text to it though this will also be
      // scaled and looks ugly. For a hack we can undo this scaling then
      // draw the text, easy...
      canvas.pushMatrix();  
      // -1 for the y is needed here as the coordinate system in Blank canvas is
      // flipped to be like a normal graph, rather than screen coordinates
      canvas.scale(1 / sx, -1 / sy);
      canvas.textFont(myFont);
      canvas.text(String.format("(%.1f, %.1f)", p.X, p.Y), p.X * sx + 5, -(p.Y * sy - 5));
      canvas.popMatrix();
    }

    @Override public void TraceDraw(PGraphics canvas) {
      if (_data != null) {
        for (int i = 0; i < _data.size(); i++) {
          drawPoint((Point2D) _data.get(i), canvas);
        }
      }
    }
  }
  ScatterTrace sTrace;
  Graph2D grph;

  public void setup() {
    size(600, 500, P2D);
    
    myFont = createFont("Consolas", 14, true);

    sTrace = new ScatterTrace();

    grph = new Graph2D(this, 400, 400, true);
    grph.setAxisColour(220, 220, 220);
    grph.setFontColour(255, 255, 255);

    grph.position.y = 50;
    grph.position.x = 100;

    grph.setYAxisTickSpacing(1f);
    grph.setXAxisTickSpacing(1f);

    grph.setXAxisMinorTicks(1);
    grph.setYAxisMinorTicks(1);

    grph.setYAxisMin(0f);
    grph.setYAxisMax(10f);

    grph.setXAxisMin(0f);
    grph.setXAxisMax(10f);
    grph.setXAxisLabelAccuracy(0);
    
    grph.setYAxisLabelFont("Consolas", 20, true);
    grph.getXAxis().setTickFont("Consolas", 20, true);
    
    grph.addTrace(sTrace);

    for (int i = 0; i < 10; i++) {
      sTrace.addPoint(random(0, 10), random(0, 10));
    }

  }

  public void draw() {
    background(0);
    grph.draw();
  }
}
