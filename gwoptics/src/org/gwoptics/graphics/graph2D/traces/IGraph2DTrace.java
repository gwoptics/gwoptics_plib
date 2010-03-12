package org.gwoptics.graphics.graph2D.traces;

import org.gwoptics.graphics.IRenderable;
import org.gwoptics.graphics.graph2D.IGraph2D;
import org.gwoptics.graphics.graph2D.effects.ITraceColourEffect;

/**
 * <p>
 * This interface provides the functionality that is required of a trace object, to be used by the 
 * Graph2D control.
 * </p>
 * <p>
 * Classes implementing this interface should be programmed for efficiency, as they are called many
 * times per draw loop. The intended way to work is to store the points of the line in an internal
 * array. When the draw() method is called from the IRenderable interface the method should use the
 * array for plotting the trace. Usually the trace will not change so it is unnescessary to recalculate
 * the equations everytime.
 * </p>
 * <p>
 * Traces also have the option of having an ITraceColourEffect applied to it. The draw() method should
 * use this effect object to determine the colour of the trace at given points.
 * </p>
 * 
 * @author Daniel Brown 13/7/09
 * @since 0.4.0
 * @see ITraceColourEffect
 * @see ILine2DEquation
 */
public interface IGraph2DTrace extends IRenderable {
	/** Sets an internal variable to store a reference to the graph object the
	 * trace is being plotted on */
	void setGraph(IGraph2D grp);
	/** This is called everytime the equation callback object is changed. */
	void generate();
	/** provides an object implementing the ILineEquation interface, this is stored
	 * and called to generate the trace points.	 */
	void setEquationCallback(ILine2DEquation equation);
	/** alters the initial position of the trace on the graph */
	void setPosition(int x, int y);
	/** If no effect is applied this is the solid colour of the trace.*/
	void setTraceColour(int R, int G, int B);
	/** Provides an object that implements the ITraceColourEffect interface. This object
	 * returns a colour value for each point on the trace and allows for more interesting
	 * effects to be applied */
	void setTraceEffect(ITraceColourEffect effect);
	/** Should remove any ITraceColourEffect's applied to the trace.*/
	void removeEffect();
	/**<p>
	 * Before the trace is added to the graph control this method is called. It allows a
	 * trace to check the settings of other traces that have previously been added for in
	 * Compatibilities. Leave method empty in implementation if no checks are necessary.
	 * </p>
	 * <p>w
	 * onAddTrace is called from with a synchronised lock so the traces object won't
	 * be modified whilst reading it. Therefore it is not necessary to provide custom thread
	 * locks.  
	 * </p> */
	void onAddTrace(Object traces[]);
	/**<p>
	 * Before the trace is officially removed from the trace list of a Graph2D object, the onRemove
	 * method is called. This allows the trace object to provide any cleanup needed, if at all needed.
	 * Leave blank if nothing is needed.
	 * </p>*/
	void onRemoveTrace();
}
