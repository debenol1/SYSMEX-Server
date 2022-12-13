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
package ch.framsteg.hl7.sysmex.server;

import java.util.Properties;

import ch.framsteg.hl7.sysmex.server.interfaces.Loader;
import ch.framsteg.hl7.sysmex.server.interfaces.Validator;
import ch.framsteg.hl7.sysmex.server.properties.PropertiesLoader;
import ch.framsteg.hl7.sysmex.server.properties.PropertiesValidator;
import ch.framsteg.hl7.sysmex.server.server.CommonSocketServer;

/* Launcher class which reads the application.properties and starts the CommonSocketServer */
public class Launcher {

	private final static String ERR_PROPERTIES_INVALID = "Properties invalid";
	private final static String ERR_PARAMETERS_MISSING = "Missing parameter";

	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				Loader<Properties> loader = new PropertiesLoader();
				// Tries to load the application.properties
				Properties properties = loader.load(args[0]);
				Validator<Properties> validator = new PropertiesValidator();
				// The necessary properties are being tested
				if (validator.validate(properties)) {
					// The commonSocketServer is being created/started
					CommonSocketServer commonSocketServer = new CommonSocketServer(
							Integer.parseInt(properties.getProperty("port")), properties);
					commonSocketServer.run();
				} else {
					// Properties invalid
					System.out.println(ERR_PROPERTIES_INVALID);
				}
			} else {
				// Parameter not entered
				System.out.println(ERR_PARAMETERS_MISSING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
