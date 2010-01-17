package org.gwoptics.graphics.colourmap.presets;

import org.gwoptics.graphics.Colour;
import org.gwoptics.graphics.colourmap.*;

/**
 * FlipColourmap extends RGBColourmap and is a custom made map with black in the
 * center and bright for smaller and a larger values. This map must be generated
 * using generateColourmap() before use.
 * <p>History</p>
 * Version 0.2.4 sees the addition of EquationColourmap class, FlipColourmap is changed
 * to extend this class and provide the static IColourmapEquation FlipEquation to
 * generate the colourmap. 
 * 
 * @author Andreas Freise 17/6/09
 * @since 0.2.3
 * @see generateColourmap()
 */

public final class FlipColourmap extends EquationColourmap {

	private static IColourmapEquation FlipEquation = new IColourmapEquation(){
		public Colour colourmapEquation(float _x) {
			//float xt=1.0f-_x; // flip color so that positive values are red
			float xt=_x;
			double _y=Math.abs(1-2*xt);
			double _f5=Math.pow(_y,3);
			double _f6=Math.pow(_y,4);
			double _f7=Math.sqrt(_y);
			double _f9=Math.sin(0.5*Math.PI*_y);
			double _f15=Math.abs(Math.sin(2*Math.PI*_y));
			double _flip76=0.5*((Math.signum(xt-0.5)+1)*_f7+(1-Math.signum(xt-0.5))*_f6);
			double _flip59=0.5*((Math.signum(xt-0.5)+1)*_f5+(1-Math.signum(xt-0.5))*_f9);
			return new Colour((float)_flip76, (float)_flip59, (float)_f15);
		}		
	};
	
	/**
	 * If you require the colourmap to be generated now rather than later by
	 * calling generateColourmap() manually set generateMapNow to true. This is
	 * useful in the instance that you simply pass a map to a new trace.
	 * 
	 * <pre>
	 * {@code}
	 * SurfaceGraph3D.addSurfaceTrace(new IGraph3DCallback(){
	 * 	public float computePoint(float X, float Z) {
	 * 		return -(X*X + Z*Z);
	 * 	}}, 100, 100, new FlipColourmap(true));
	 * </pre>
	 * 
	 * @param generateMapNow
	 *            Boolean value stating whether to generate map now or not.
	 */
	public FlipColourmap(boolean generateMapNow) {
		super(100,FlipEquation);

		//Black represents 0 at 0.5 so centre at zero must be true
		this.setCentreAtZero(true);
		
		if(generateMapNow)
			this.generateColourmap();
	}

}
