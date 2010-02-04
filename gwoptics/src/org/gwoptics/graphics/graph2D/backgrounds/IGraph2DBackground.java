package org.gwoptics.graphics.graph2D.backgrounds;

import org.gwoptics.graphics.IRenderable;
import org.gwoptics.graphics.graph2D.Axis2D;

public interface IGraph2DBackground extends IRenderable  {
	void setDimensions(int width, int height);
	void setAxes(Axis2D x, Axis2D y);
}
