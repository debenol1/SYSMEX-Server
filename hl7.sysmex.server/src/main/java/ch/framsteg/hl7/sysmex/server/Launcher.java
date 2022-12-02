package ch.framsteg.hl7.sysmex.server;

import java.util.Properties;

import ch.framsteg.hl7.sysmex.server.interfaces.Loader;
import ch.framsteg.hl7.sysmex.server.interfaces.Validator;
import ch.framsteg.hl7.sysmex.server.properties.PropertiesLoader;
import ch.framsteg.hl7.sysmex.server.properties.PropertiesValidator;
import ch.framsteg.hl7.sysmex.server.server.CommonSocketServer;

public class Launcher {
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				Loader<Properties> loader = new PropertiesLoader();
				Properties properties = loader.load(args[0]);

				Validator<Properties> validator = new PropertiesValidator();
				if (validator.validate(properties)) {
					CommonSocketServer commonSocketServer = new CommonSocketServer(Integer.parseInt(properties.getProperty("port")), properties);

				} else {
					// Properties invalid
					System.out.println("Properties invalid");
				}
			} else {
				// Parameter not entered
				System.out.println("Missing parameter");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
