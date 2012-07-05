/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gwoptics.graphics.graph2D.traces;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.gwoptics.graphics.graph2D.Axis2D;
import org.gwoptics.testing.Scatter_label_test;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

/**
 *
 * @author Daniel
 */
public class ScatterTrace extends Blank2DTrace {

  private ArrayList<String> _labels;
  private ArrayList<Point2D> _data;
  private ArrayList<HashMap<String, Object>> _info;
  private Color defaultColour = Color.RED;
  private float defaultSize = 0.05f;
  private IScatterPoint _pt;
  private PFont _labelfont;
  private LABELPOSITION _lblPos = LABELPOSITION.RIGHT;
  
  public enum LABELPOSITION {

    ABOVE, LEFT, RIGHT, BELOW
  }

  private class Point2D {

    public float x, y;

    public Point2D(float x, float y) {
      this.x = x;
      this.y = y;
    }
  }
  public final static IScatterPoint Cross = new IScatterPoint() {

    @Override
    public void drawPoint(float x, float y, PGraphics canvas, HashMap<String, Object> info) {
      float psize = 0.5f*(Float)info.get("size");
      Color c = (Color)info.get("colour");
      
      canvas.pushStyle();
      canvas.stroke(c.getRed() ,c.getGreen(), c.getBlue());      
      canvas.strokeCap(PApplet.SQUARE);
      canvas.strokeWeight(0.05f);
      canvas.line(x - psize, y, x + psize, y);
      canvas.line(x, y - psize, x, y + psize);
      canvas.popStyle();
    }
  };
  public final static IScatterPoint FilledCircle = new IScatterPoint() {

    @Override
    public void drawPoint( float x, float y, PGraphics canvas, HashMap<String, Object> info) {
      float psize = (Float)info.get("size");
      Color c = (Color)info.get("colour");
      
      canvas.pushStyle();
      canvas.fill(c.getRed() ,c.getGreen(), c.getBlue());      
      canvas.noStroke();
      canvas.ellipse(x-psize/2, y-psize/2, psize, psize);
      canvas.popStyle();
    }
  };
  
  public final static IScatterPoint Ring = new IScatterPoint() {
    
    @Override
    public void drawPoint(float x, float y, PGraphics canvas, HashMap<String, Object> info) {
      float psize = (Float)info.get("size");
      Color c = (Color)info.get("colour");
      float stroke = 0.05f;
      
      if(info.containsKey("stroke"))
        stroke = (Float)info.get("stroke");
      
      canvas.pushStyle();
      canvas.stroke(c.getRed() ,c.getGreen(), c.getBlue());      
      canvas.strokeWeight(stroke);
      canvas.noFill();
      canvas.ellipse(x-psize/2, y-psize/2, psize, psize);
      canvas.popStyle();
    }
  };

  public interface IScatterPoint {
    public void drawPoint(float x, float y, PGraphics canvas, HashMap<String, Object> info);
  }

  public ScatterTrace(IScatterPoint pt) {
    super();
    
    _pt = pt;
    _labels = new ArrayList<String>();
    _info = new ArrayList<HashMap<String, Object>>();
    _data = new ArrayList<Point2D>();
  }
  
  @Override public void onAddTrace(Object[] traces) {
    _labelfont = _parent.createFont("Arial", 20, true);
  }
   
  public void setLablePosition(LABELPOSITION p){
    _lblPos = p;
  }
  
  public void setLabelFont(PFont font){
    if (font == null) 
      throw new NullPointerException("Font object cannot be null");
    
    _labelfont = font;
  }

  public void setDefaultColor(Color c) {
    if (c == null) {
      throw new NullPointerException("Color object cannot be null");
    }

    defaultColour = c;
  }
  
  public void setDefaultSize(float s){
    defaultSize = Math.abs(s);
  }

  public void addPoint(float x, float y, float size, Color c) {
    _data.add(new Point2D(x, y));    
    HashMap<String,Object> hm = new HashMap<String, Object>(2);
    hm.put("colour", c);
    hm.put("size", (Float)size);
    _info.add(hm);
  }
  
  public void addPoint(float x, float y) {
    addPoint(x, y, defaultSize,defaultColour);
  }
  
  public void addPoint(float x, float y, float size, Color c, String label) {
    addPoint(x, y, size, c);
    _labels.add(label);
  }
  
  public void addPoint(float x, float y, float size, String label, Color c, Object... args) {
    if(args.length % 2 != 0)
      throw new RuntimeException("There was not an even number of Key/Value pairs");
    
    addPoint(x, y, size, c, label);
    
    HashMap<String,Object> hm = _info.get(_info.size()-1);
       
    for(int i=0;i<args.length;i+=2){
      
      if(!(args[i] instanceof String))
        throw new RuntimeException("Was expecting vararg number " + i + " to be a string for a key value");
     
      hm.put((String)args[i], args[i+1]);        
    }
  }

  private void drawPoint(int ix, PGraphics canvas) {
    Point2D p = _data.get(ix);

    // the scale factor is [length of axis]/([axis max] - [axis min])
    Axis2D ax = getGraph().getXAxis();
    Axis2D ay = getGraph().getYAxis();

    float sx = ax.getLength() / (ax.getMaxValue() - ax.getMinValue());
    float sy = ay.getLength() / (ay.getMaxValue() - ay.getMinValue());

    _pt.drawPoint(p.x, p.y, canvas, _info.get(ix));
    
    // The BlankCanvas trace works by creating a scaled canvas on which
    // we draw, if we try and draw text to it though this will also be
    // scaled and looks ugly. For a hack we can undo this scaling then
    // draw the text, easy...
    canvas.pushMatrix();
    // -1 for the y is needed here as the coordinate system in Blank canvas is
    // flipped to be like a normal graph, rather than screen coordinates
    canvas.scale(1 / sx, -1 / sy);
    canvas.textFont(_labelfont);
    canvas.fill(0);
    
    float px=0,py=0;
    String lbl = (_labels.isEmpty()) ? String.format("(%.1f, %.1f)", p.x, p.y) : _labels.get(ix);
    
    switch(_lblPos){
      case BELOW:
        px = -canvas.textWidth(lbl)/2;
        py = - defaultSize*sy/2 - _labelfont.ascent() - _labelfont.descent();
        break;
      case ABOVE:
        px = -canvas.textWidth(lbl)/2;
        py = defaultSize*sy/2 + _labelfont.ascent() + _labelfont.descent();
        break;
      case LEFT:
        py = - (_labelfont.ascent() + _labelfont.descent());
        px = - (canvas.textWidth(lbl) + defaultSize*sx/2);
        break;
      case RIGHT:
        py = - (_labelfont.ascent() + _labelfont.descent());
        px = defaultSize*sx/2;
        break;
    }
    
    canvas.text(lbl, (p.x * sx) + px, -(p.y * sy+py));
    
    canvas.popMatrix();
  }

  @Override
  public void TraceDraw(PGraphics canvas) {
    canvas.pushStyle();
    canvas.pushMatrix();
    
    if (_data != null) {
      for (int i = 0; i < _data.size(); i++) {
        drawPoint(i, canvas);
      }
    }
    
    canvas.popMatrix();
    canvas.popStyle();
  }
}
