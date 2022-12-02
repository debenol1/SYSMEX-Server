package ch.framsteg.hl7.sysmex.server.properties;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import ch.framsteg.hl7.sysmex.server.interfaces.Validator;

public class PropertiesValidator implements Validator<Properties> {

	@Override
	public boolean validate(Properties properties) {
		boolean validPort = false;
		boolean validLogDir = false;
		boolean validOutput = false;
		
		int port = Integer.parseInt(properties.getProperty("port"));
		validPort = (port > 100 && port < 65535) ? true : false;
		validOutput = validate(properties.getProperty("output.dir"));
		validLogDir = validate(properties.getProperty("log.dir"));

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

