package org.gwoptics.graphics.graph3D;
/**
 * <p>
 * IGraph3DCallback specifies a function computePoint() that represents the equation of a surface on a graph.
 * This interface is for use with the SurfaceGraph3D control and its various components. The {@link computerPoint}
 * function defines the equation of a surface using the X and Y components, Z being the up direction.
 * </p>
 * 
 * <p>
 * IGraph3DCallback can be used to either generate a quick function or be implemented by a new class to add changeable 
 * variables.
 * </p>
 * 
 * <pre>
 * Example of standing wave class using the IGraph3DCallback interface.
 * t, k and w can all be varied to produce different results.
 * <code>
   class standingWave implements IGraph3DCallback{
		float t;
		float k;
		float w;
		public float computePoint(float X, float Y) {
			return (float) (Math.cos(w*t) * Math.sin(k*X) * Math.sin(k*Y));
		}
	}
 * </code>
 * </pre>
 * 
 * @author Daniel Brown 8/6/09
 * @since 0.1.1
 * @see SurfaceGraph3D
 * @see SurfaceTrace3D
 */

public interface IGraph3DCallback {
	/**
	 * This function returns the result of an equation for a surface using 2 variable X and Y. 
	 * 
	 * @param X Value of X variable
	 * @param Y Value of Y variable
	 * @return Equation output
	 */
	float computePoint(float X, float Y);
}
