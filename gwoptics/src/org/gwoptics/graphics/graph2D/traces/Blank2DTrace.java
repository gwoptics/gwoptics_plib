/**
 * Copyright notice
 *
 * This file is part of the Processing library `gwoptics'
 * http://www.gwoptics.org/processing/gwoptics_p5lib/
 *
 * Copyright (C) 2009 onwards Daniel Brown and Andreas Freise
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License version 2.1 as published
 * by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.gwoptics.graphics.graph2D.traces;

import com.sun.corba.se.spi.oa.OADefault;
import org.gwoptics.graphics.graph2D.Axis2D;
import org.gwoptics.graphics.graph2D.IGraph2D;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PGraphicsOpenGL;

public abstract class Blank2DTrace implements IGraph2DTrace {

  protected IGraph2D _graphDrawable;
  protected PApplet _parent;
  protected PGraphics _backBuffer;
  protected PImage _traceImg;
  private boolean _redraw;
  private String _renderer = PApplet.JAVA2D;

  /**
   * Sets the renderer for the PGraphics object that is used for drawing to.
   * 
   * @param renderer P2D or JAVA2D
   */
  public final void setRenderer(String renderer){
    if(renderer != PApplet.JAVA2D || renderer != PApplet.P2D)
      throw new RuntimeException("Renderer must be JAVA2D or P2D");
    
    _renderer = renderer;
  }
  
  public void generate() {
    _redraw = true;
  }

  public void onAddTrace(Object[] traces) {
  }

  public void onRemoveTrace() {
  }

  public void setPosition(int x, int y) {
  }

  public void setParent(PApplet parent) {
    if (parent == null) {
      throw new NullPointerException("Cannot assign a null PApplet object as a parent.");
    } else {
      _parent = parent;
    }
  }

  public void setGraph(IGraph2D grp) {
    if (grp == null) {
      throw new NullPointerException("Cannot assign a null graph2D object to draw on.");
    } else if (_graphDrawable != null) {
      throw new RuntimeException("A Graph2D object has already been set for this trace"
              + ", other components may have already referenced the previous Graphs objects.");
    }

    if (_parent == null) {
      throw new NullPointerException("Parent PApplet object is null.");
    }

    _graphDrawable = grp;
    _backBuffer = _parent.createGraphics(grp.getXAxis().getLength(), grp.getYAxis().getLength(), _renderer);    
  }

  public void draw() {
    if (_redraw) {
      _backBuffer.beginDraw();

      Axis2D ax = _graphDrawable.getXAxis();
      Axis2D ay = _graphDrawable.getYAxis();

      float xoff = ax.valueToPosition(0);
      float yoff = _backBuffer.height - ay.valueToPosition(0);

      _backBuffer.translate(xoff, yoff);

      _backBuffer.pushMatrix();

      float xscale = (float) ax.getLength() / (ax.getMaxValue() - ax.getMinValue());
      float yscale = (float) ay.getLength() / (ay.getMaxValue() - ay.getMinValue());

      _backBuffer.scale(xscale, -yscale);
      _backBuffer.background(0, 0, 0, 0);
      
      // for some reason the stroke width is going weird, needs to be set small
      // otherwise it looks like a massive blob
      //_backBuffer.strokeWeight(0.1f);
      
      TraceDraw(_backBuffer);

      _backBuffer.popMatrix();
      _backBuffer.endDraw();

      _traceImg = _backBuffer.get(0, 0, _backBuffer.width, _backBuffer.height);
      _redraw = false;
    }

    _parent.image(_traceImg, 0, -_backBuffer.height);
  }

  public abstract void TraceDraw(PGraphics backBuffer);
}
