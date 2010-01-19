package org.gwoptics.graphics.colourmap;

/**
 * This exception is thrown by a colourmap when the user attempts to read a value from the map without generating it beforehand.
 * 
 * @author Daniel 17/6/09
 * @since 0.2.2
 */
@SuppressWarnings("serial")
public class MapNeedsGeneratingException extends RuntimeException {

	public MapNeedsGeneratingException() {
		super("Colourmap needs to be generated before values are read from it.");
	}
}
