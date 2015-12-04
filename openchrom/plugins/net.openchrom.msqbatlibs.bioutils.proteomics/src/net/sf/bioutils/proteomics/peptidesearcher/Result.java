package net.sf.bioutils.proteomics.peptidesearcher;

import java.util.ArrayList;
import java.util.List;

public class Result {

	public static enum Type {
		DEGEN, PROTEOTYPIC, NOT_FOUND, NOT_SEARCHED
	}

	public static Result buildDegen(final List<String> headers) {

		return new Result(headers, Type.DEGEN);
	}

	public static Result buildNotFound() {

		return new Result(new ArrayList<String>(0), Type.NOT_FOUND);
	}

	public static Result buildNotSearched() {

		return new Result();
	}

	public static Result buildProteotypic(final List<String> headers) {

		return new Result(headers, Type.PROTEOTYPIC);
	}

	public List<String> headers;
	public final Type type;

	private Result() {

		this(new ArrayList<String>(0), Type.NOT_SEARCHED);
	}

	public Result(final List<String> headers, final Type type) {

		this.headers = headers;
		this.type = type;
	}

	public synchronized void limitMatches(final int limitMatches) {

		headers = headers.subList(0, limitMatches);
	}

	@Override
	public String toString() {

		if(!headers.isEmpty())
			return headers.get(0) + " " + type.toString();
		return type.toString();
	}
}
