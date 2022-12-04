/*******************************************************************************
 * Copyright (c) 2020-2022,  Olivier Debenath
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Olivier Debenath <olivier@debenath.ch> - initial implementation
 *    
 *******************************************************************************/
package ch.framsteg.hl7.sysmex.server.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import ch.framsteg.hl7.sysmex.server.interfaces.Loader;

public class PropertiesLoader implements Loader<Properties> {

	@Override
	public Properties load(String path) throws FileNotFoundException, IOException {
		Properties properties = new Properties();

		if (Files.exists(Paths.get(path))) {
			if (Files.isRegularFile(Paths.get(path))) {
				if (Files.isReadable(Paths.get(path))) {
					properties.load(new FileInputStream(path));
				}
			}
		}
		return properties;
	}
}
