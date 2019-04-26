package net.openchrom.nmr.processing.supplier.base.core;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.FIDSignal;

final class ComplexFIDSignal implements FIDSignal, Serializable {

	private static final long serialVersionUID = -561512031857962638L;
	private Complex complex;
	private BigDecimal time;

	public ComplexFIDSignal(BigDecimal time, Complex complex) {
		this.time = time;
		this.complex = complex;
	}

	@Override
	public BigDecimal getSignalTime() {

		return time;
	}

	@Override
	public Number getRealComponent() {

		return complex.getReal();
	}

	@Override
	public Number getImaginaryComponent() {

		return complex.getImaginary();
	}
}