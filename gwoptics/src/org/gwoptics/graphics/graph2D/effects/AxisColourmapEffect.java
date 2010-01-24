package org.gwoptics.graphics.graph2D.effects;

import org.gwoptics.graphics.colourmap.IColourmap;

/**
 * 
 * @author Daniel Brown 13/7/09
 * @since 0.4.0
 *
 */
public abstract class AxisColourmapEffect implements ITraceColourEffect{

	protected IColourmap _map;
	protected float yMin,yMax,xMin,xMax;
	protected boolean xContainsZero, yContainsZero;
	protected boolean _xaxisDataSet, _yaxisDataSet;
	
	public AxisColourmapEffect(IColourmap map){
		if(map == null)
			throw new NullPointerException("Colourmap argument is null");
		
		if(!map.isGenerated())
			map.generateColourmap();
		
		_map = map;
		_xaxisDataSet = false;
		_yaxisDataSet = false;
	}
	
	public void setXAxisValues(int axisLength, float minValue, float maxValue) {
		xMin = minValue;
		xMax = maxValue;
		_xaxisDataSet = true;
		
		if(xMin < 0 && xMax > 0)
			xContainsZero = true;
		else
			xContainsZero = false;
		
	}

	public void setYAxisValues(int axisLength, float minValue, float maxValue) {
		yMin = minValue;
		yMax = maxValue;
		_yaxisDataSet = true;
		
		if(yMin < 0 && yMax > 0)
			yContainsZero = true;
		else
			yContainsZero = false;
	}
}
