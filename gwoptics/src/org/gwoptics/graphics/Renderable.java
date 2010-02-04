package org.gwoptics.graphics;
import processing.core.PApplet;
import processing.core.PVector;
/**
 * An abstract class that can be inherited to provide some common functionality that
 * all rendered objects have. Such as a reference to its parent object its position, and
 * a draw function that renders the object. 
 * 
 * @author Daniel Brown 
 */
public abstract class Renderable implements IRenderable {
	protected PApplet _parent;

	public PVector position;
	
	public Renderable(PApplet parent) {
		if(parent == null) {throw new NullPointerException();}
		position = new PVector(0,0,0);
		_parent = parent;
	}
	
	public void setParent(PApplet parent){
		if(_parent != null)
			throw new RuntimeException("Parent object has already been set.");
	}
	
	abstract public void draw();
}
