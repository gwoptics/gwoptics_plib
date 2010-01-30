package org.gwoptics.MathUtils;
/**
 * This class provides access to a much faster version of the cos and sin functions.
 * It generates a table of sin values from 0 to 90 to an accuracy of 0.01 degrees. 
 * As the first 90 degrees is just mirrored in the 3 other sections. 90 to 180 is
 * 0 to 90 reversed and 180 to 360 is 0 to 180 inverted.
 * 
 * Having a small table is important for performance. If the table is too large it
 * will unlikely be stored in the faster CPU cache and instead in RAM. Although this
 * is still relatively fast. Cosine values are generated by the addition of 90 degrees
 * to sin values.
 * 
 * This class should be used when accuracy in cos and sin is not imperative.
 * 
 * @author Daniel Brown 28/6/09
 * @since 0.3.4
 */
public final class TrigLookup {

	private static final int _accuracy = 100;
	private static final double[] _sinTable = new double[90 * _accuracy + 1];

	private static final double DegtoRadian = Math.PI/180f; 
	private static final double RadianToDeg = 180f / Math.PI; 
	
	static{
		//generate the values to fill the sin table
		for (int i = 0; i < _sinTable.length; i++) {
			_sinTable[i] = Math.sin(DegtoRadian * (i)/_accuracy);
		}
	}
	
	/**
	 * Computes a fast cosine of an angle
	 * @param value
	 * @return
	 */
	public static double cos(float value){
		return sin(value + (float)Math.PI/2);
	}
	
	/**
	 * Computes a fast sin of an angle
	 * @param value
	 * @return
	 */
	public static double sin(float a){
		float angle = (float) (a * RadianToDeg);
		int sign;
		
		//by using the mod functionality we can
		//limit our range to 0 to 360
		if(angle >= 360)
			angle %= 360;
		
		if(angle < 0){
			sign = -1;
			angle *= -1;
		}else
			sign = 1;
			
		int ix = (int)(angle * _accuracy);
		
		//deals with determining the area of the period we are in
		//and then the relation to the 0 to 90 degrees section
		if(ix < (180 * _accuracy)){
			if(ix < ((90 * _accuracy) + 1))
				return sign * _sinTable[ix];
			else
				return sign * _sinTable[(180*_accuracy) - ix];			
		}else{
			if(ix < 270 * _accuracy + 1)
				return -sign * _sinTable[ix - (180 * _accuracy)];
			else
				return -sign * _sinTable[(360 * _accuracy) - ix];   
		}
	}
	
}
