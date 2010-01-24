package org.gwoptics.graphics.graph2D;

import org.gwoptics.graphics.graph2D.backgrounds.IGraph2DBackground;

/**
 * This interface is used to pass to an IGraph2DTrace object. It provides some minimal methods for
 * a trace object to alter the graph. Originally created to provide traces access to the background
 * renderer to provide moving grids etc.
 * 
 * @author Daniel Brown 7/8/09
 */
public interface IGraph2D {
	/** Gets a reference to the current Background renderer */
	IGraph2DBackground getGraphBackground();
	Axis2D getXAxis();
	Axis2D getYAxis();
}
