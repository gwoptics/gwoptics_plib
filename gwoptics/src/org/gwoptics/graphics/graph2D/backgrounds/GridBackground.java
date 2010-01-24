package org.gwoptics.graphics.graph2D.backgrounds;

import org.gwoptics.graphics.Colour;

public class GridBackground extends SolidColourBackground {
	protected Colour _gridColour;
	
	/** Sets the colour of the major grid lines **/
	public void setGridColour(int R, int G, int B){_gridColour = new Colour(R, G, B);}
	/** Removes major grid lines **/
	public void setNoGrid(){_gridColour = null;}
	
	public GridBackground(Colour gridColour, Colour background){
		super(background);
		_gridColour = gridColour;
	}
	
	public void draw() {
		super.draw();
		
		if(_parent != null && _gridColour != null){	
			_parent.stroke(_gridColour.toInt());
			
			if(_ax.getMajorTickPositions() != null){
				for (Integer i : _ax.getMajorTickPositions()) {
					_parent.line(i, 0, i, -_ay.getLength());
				}
			}
			
			if(_ay.getMajorTickPositions() != null){
				for (Integer i : _ay.getMajorTickPositions()) {
					_parent.line(0, -i, _ax.getLength(), -i);
				}
			}
		}
	}
}