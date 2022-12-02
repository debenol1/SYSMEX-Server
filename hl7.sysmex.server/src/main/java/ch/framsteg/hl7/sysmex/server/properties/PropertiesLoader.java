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
