package org.gwoptics;
/**
 * This enum is used by the gwSlider object to state what type of number the slider should represent.
 * Decimal and exponentional both make the slider represent a floating point number, displayed as 0.00
 * and 0E00 respectively on the labels. Integer mode makes the slider store and return integer values.
 * 
 * @author Daniel Brown
 */
public enum ValueType {
	INTEGER,
	DECIMAL,
	EXPONENT
}
