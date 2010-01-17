package org.gwoptics;

/**
 * ArgumentException is an throwable object which represents a problem with one of the passed arguments to a function.
 * A message must be supplied to extrapolate on the issue.
 * 
 * @author Daniel Brown 18/6/09
 * @since 0.2.4
 */

@SuppressWarnings("serial")
public class ArgumentException extends RuntimeException {
	public ArgumentException(String message){
		super(message);
	}
}
