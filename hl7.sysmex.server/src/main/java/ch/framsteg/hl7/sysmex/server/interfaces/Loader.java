package ch.framsteg.hl7.sysmex.server.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

@FunctionalInterface
public interface Loader<t> {
	public <T> t load(String path) throws FileNotFoundException, IOException;
}

