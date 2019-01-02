package ch.bibbias.logging;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * 
 * @author Marco Bibbia
 * 
 *         This class provides methods to get stacktrace as a string.
 *
 */
public class ExceptionWriter extends PrintWriter {

	public ExceptionWriter(Writer writer) {
		super(writer);
	}

	private String wrapAroundWithNewlines(String stringWithoutNewlines) {
		return ("\n" + stringWithoutNewlines + "\n");
	}

	/*
	 * Convert a stacktrace into a string
	 */
	public String getExceptionAsString(Throwable throwable) {
		throwable.printStackTrace(this);

		String exception = super.out.toString();

		return (wrapAroundWithNewlines(exception));
	}

}
