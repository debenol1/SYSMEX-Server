package ch.framsteg.hl7.sysmex.server.interfaces;

@FunctionalInterface
public interface Validator<T> {
	public boolean validate(T t);
}

