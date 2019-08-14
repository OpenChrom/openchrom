package net.sf.kerner.utils.collections.list;

import java.text.NumberFormat;

import net.sf.kerner.utils.transformer.TransformerToString;

public class TransformerNumberToString implements TransformerToString<Number> {

	private final NumberFormat numberFormat;

	public TransformerNumberToString(NumberFormat numberFormat) {
		super();
		this.numberFormat = numberFormat;
	}

	public TransformerNumberToString() {
		this(NumberFormat.getInstance());
	}

	@Override
	public String transform(Number element) {

		return numberFormat.format(element);
	}
}
