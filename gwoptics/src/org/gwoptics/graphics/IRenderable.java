package org.gwoptics.graphics;

import processing.core.PApplet;

/**
 * <p>
 * Basic interface that defines commonly used functions for any rendered object.
 * Implement this if your object is eventually drawn to some output. Makes the
 * object define methods for setting its parent PApplet and draw method.
 * </p>
 * @author Daniel Brown 21/6/09
 * @since 0.3.0
 */
public interface IRenderable {
	public void draw();
	void setParent(PApplet parent);
}
