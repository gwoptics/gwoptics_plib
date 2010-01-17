package org.gwoptics;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 *  Small class that reads the manifest file and gets the Implementation-Version of the Jar file and returns as a string.
 *  
 * @author Daniel Brown 17/6/09	
 * @since 0.2.3
 *
 */
public final class Version {
	public String getVersion() {
		try {
			InputStream stream = this.getClass().getResourceAsStream("/META-INF/MANIFEST.MF");
			
			if (stream == null) {
				System.out.println("Couldn't find manifest.");
			}
			
			Manifest manifest = new Manifest(stream);
			Attributes attributes = manifest.getMainAttributes();
			return attributes.getValue("Implementation-Version");
		} catch (IOException e) {
			System.out.println("Couldn't read manifest.");
		}
		return null;
	}
}
