package org.gwoptics.graphics.colourmap.presets;

import org.gwoptics.graphics.colourmap.ColourmapNode;
import org.gwoptics.graphics.colourmap.RGBColourmap;

/**
 * CoolColourmap extends RGBColourmap and is a gradient of cyan at 0.0 to magneta at 1.0.
 * This map must be generated using generateColourmap() before use.
 * 
 * @author Daniel Brown 17/6/09
 * @since 0.2.2
 * @see generateColourmap()
 */
		
public final class CoolColourmap extends RGBColourmap {
	
	/**
	 * If you require the colourmap to be generated now rather than later by calling generateColourmap() manually
	 * set generateMapNow to true. This is useful in the instance that you simply pass a map to a new trace.
	 * <pre>
	 * {@code}
	 * SurfaceGraph3D.addSurfaceTrace(new IGraph3DCallback(){
	 *		public float computePoint(float X, float Z) {
	 *			return -(X*X + Z*Z);
	 *		}}, 100, 100, new CoolColourmap(true));
	 * </pre>
	 * 
	 * @param generateMapNow Boolean value stating whether to generate map now or not.
	 */
	public CoolColourmap(boolean generateMapNow) {
		super();
		_addNodes();
		if(generateMapNow)
			this.generateColourmap();
	}

	private void _addNodes(){
		this.addNode(new ColourmapNode(0.0f, 1.0f, 1.0f, 0.0f));
		this.addNode(new ColourmapNode(1.0f, 0.0f, 1.0f, 1.0f));
	}
}
