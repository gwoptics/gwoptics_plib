package org.gwoptics.graphics.graph2D.effects;

import org.gwoptics.graphics.GWColour;
import org.gwoptics.graphics.colourmap.IColourmap;

/**
 * 
 * @author Daniel Brown 13/7/09
 * @since 0.4.0
 *
 */
public class XAxisColourmapEffect extends AxisColourmapEffect {

	public XAxisColourmapEffect(IColourmap map) {
		super(map);
	}

	public GWColour getPixelColour(int pos, int pos2, float xVal, float yVal) {
		if(!_xaxisDataSet || !_yaxisDataSet)
			throw new RuntimeException("Axis data has not been set. Set using setXAxisValues and setYAxisValues before using.");
		
		if(_map.isCentreAtZero()){
			//if map is centred about 0 then we need to make sure we 
			//are colouring the right segments in the right colour			
			if(xContainsZero){
				float range = Math.max(Math.abs(xMax),Math.abs(xMin));
				
				return _map.getColourAtLocation(1 - Math.abs(xVal - range) /(range * 2));
			}else{
				//we have a situation where we are either completly +ive or -ive
				//range so we need to decide which part of the map to use
				float range = yMax - yMin;
				
				if(Math.signum(range) == 1){//in the 0.5 -> 1.0 range
					//position section
					return _map.getColourAtLocation(0.5f + (float) ((xVal - xMin) * 0.5 / range));
				}else if(Math.signum(range) == -1){ //in the 0.0 -> 0.5 range
					return _map.getColourAtLocation((float) ((xVal - xMin) * 0.5 / range));
				}				
			}
			
		}else{		
			return _map.getColourAtLocation(Math.abs((xVal-xMin)/(xMax-xMin)));			
		}
		
		return new GWColour(0,0,0);
	}
}
