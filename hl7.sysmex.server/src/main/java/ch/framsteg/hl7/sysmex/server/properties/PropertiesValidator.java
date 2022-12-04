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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import ch.framsteg.hl7.sysmex.server.interfaces.Validator;

public class PropertiesValidator implements Validator<Properties> {

	private final static String PORT = "port";
	private final static String OUTPUt_DIR = "output.dir";
	private final static String LOG_DIR = "log.dir";
	
	@Override
	public boolean validate(Properties properties) {
		boolean validPort = false;
		boolean validLogDir = false;
		boolean validOutput = false;
		
		int port = Integer.parseInt(properties.getProperty(PORT));
		validPort = (port > 100 && port < 65535) ? true : false;
		validOutput = validate(properties.getProperty(OUTPUt_DIR));
		validLogDir = validate(properties.getProperty(LOG_DIR));
		return validPort && validOutput && validLogDir;
	}

	private boolean validate(String path) {
		boolean valid = false;
		if (Files.exists(Paths.get(path))) {
			if (Files.isDirectory(Paths.get(path))) {
				if (Files.isReadable(Paths.get(path))) {
					valid = true;
				}
			}
		}
		return valid;
	}
}

