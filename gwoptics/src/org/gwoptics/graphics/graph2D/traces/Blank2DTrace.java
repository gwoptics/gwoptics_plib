package org.gwoptics.graphics.graph2D.traces;

import org.gwoptics.graphics.graph2D.Axis2D;
import org.gwoptics.graphics.graph2D.IGraph2D;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class Blank2DTrace implements IGraph2DTrace {

	private IGraph2D _graphDrawable;
	private PApplet _parent;
	private PGraphics _backBuffer;
	private PImage _traceImg;
	private boolean _redraw;
	
	public void generate(){
		_redraw = true;
	}

	public void onAddTrace(Object[] traces){}
	public void onRemoveTrace(){}
	public void setPosition(int x, int y){}
	
	public void setParent(PApplet parent) {
		if(parent == null)
			throw new NullPointerException("Cannot assign a null PApplet object as a parent.");
		else
			_parent = parent;
	}
	
	public void setGraph(IGraph2D grp) {
		if(grp == null)
			throw new NullPointerException("Cannot assign a null graph2D object to draw on.");
		else if(_graphDrawable != null)
			throw new RuntimeException("A Graph2D object has already been set for this trace" +
					", other components may have already referenced the previous Graphs objects.");
		
		if(_parent == null)
			throw new NullPointerException("Parent PApplet object is null.");
		
		_graphDrawable = grp;
		_backBuffer = _parent.createGraphics(grp.getXAxis().getLength(),
											 grp.getYAxis().getLength(),
											 PApplet.P2D);
	}
	
	public void draw(){
		if(_redraw){
			_backBuffer.beginDraw();
			
			Axis2D ax = _graphDrawable.getXAxis();
			Axis2D ay = _graphDrawable.getYAxis();
			
			float xPos = PApplet.constrain(ax.valueToPosition(0), 0, ax.getLength());
			float yPos = PApplet.constrain(ay.valueToPosition(0), 0, ay.getLength());
			
			_backBuffer.translate(xPos, _backBuffer.height - yPos);
			
			_backBuffer.pushMatrix();
			
			float xscale = (float)ax.getLength()/(ax.getMaxValue() - ax.getMinValue());
			float yscale = (float)ay.getLength()/(ay.getMaxValue() - ay.getMinValue());
			
			_backBuffer.scale(xscale, -yscale);
			_backBuffer.background(200);
			
			TraceDraw(_backBuffer);
			
			_backBuffer.popMatrix();
			_backBuffer.endDraw();
			
			_traceImg = _backBuffer.get(0, 0, 
										_backBuffer.width, 
										_backBuffer.height);
		}
		
		_parent.image(_traceImg,0,-_backBuffer.height);
	}
	
	public abstract void TraceDraw(PGraphics backBuffer);
}
