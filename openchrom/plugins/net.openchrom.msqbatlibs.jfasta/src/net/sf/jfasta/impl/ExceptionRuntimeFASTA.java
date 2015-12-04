package net.sf.jfasta.impl;

public class ExceptionRuntimeFASTA extends RuntimeException {

	/**
     *
     */
	private static final long serialVersionUID = -5681123013659877669L;

	public ExceptionRuntimeFASTA() {

	}

	public ExceptionRuntimeFASTA(final String message) {

		super(message);
	}

	public ExceptionRuntimeFASTA(final String message, final Throwable cause) {

		super(message, cause);
	}

	public ExceptionRuntimeFASTA(final Throwable cause) {

		super(cause);
	}
}
