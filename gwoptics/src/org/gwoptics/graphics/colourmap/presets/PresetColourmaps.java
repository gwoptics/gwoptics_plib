package org.gwoptics.graphics.colourmap.presets;

import org.gwoptics.ArgumentException;
import org.gwoptics.graphics.colourmap.IColourmap;

/**
 * This class defines only one static member to retrieve 
 * various presets.
 * 
 * @author Daniel Brown 18/6/09
 * @since 0.2.4
 */
public final class PresetColourmaps {
	/**
	 * This function excepts a type of IColourmap to return, this is done
	 * via the Presets enumerator. See Presets for list of possible inputs.
	 * 
	 * @param type 
	 * @see Presets
	 */
	public static IColourmap getColourmap(Presets type){
		switch (type) {
		case COOL:
			return new CoolColourmap(true);
		case FLIP:
			return new FlipColourmap(true);
		case GRAY:
			return new GrayScaleColourmap(true);
		case HOT:
			return new HotColourmap(true);			
		case WARM:
			return new WarmColourmap(true);

		default:
			throw new ArgumentException("type of preset can not be resolved.");
		}
	}
}
