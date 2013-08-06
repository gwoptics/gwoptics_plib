package org.gwoptics.testing;

import org.gwoptics.graphics.graph2D.*;
import org.gwoptics.graphics.graph2D.traces.*;
import processing.core.PApplet;

/**
 *
 * @author Daniel
 */
public class ButterFly extends PApplet{
  
  Graph2D g;

  public static void main(final String[] args) {  
    PApplet.main( new String[]{ButterFly.class.getName()} );  
  }

  @Override public void setup(){
   
  }

  @Override public void draw(){
    background(255);
    g.draw();
  }
}