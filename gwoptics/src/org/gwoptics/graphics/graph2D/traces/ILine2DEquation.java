package org.gwoptics.graphics.graph2D.traces;

/**
 * <p>
 * This interface should be implemented by objects that are to be represented by a trace on a Graph2D
 * object. The computePoint method is called for each point along the X-Axis and the object implementing
 * the interface is expected to return a double value to plot as the Y-value.
 * </p>
 * @author Daniel Brown 13/7/09
 * @since 0.4.0
 *
 */
public interface ILine2DEquation {
	/**
	 * This method is called for each pixel along the width of a Graph2D object.
	 * The x parameter gives the value of the point to be plotted along the horizontal
	 * axis. The position parameter states the number of pixels along the axis this 
	 * value relates to, going from left to right.
	 * 
	 * @param X value
	 * @param position pixel
	 * @return Y value to be plotted
	 */
	double computePoint(double x, int position);
}
