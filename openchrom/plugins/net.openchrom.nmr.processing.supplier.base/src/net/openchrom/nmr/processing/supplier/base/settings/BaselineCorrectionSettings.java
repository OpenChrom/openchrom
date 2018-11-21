package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;

public class BaselineCorrectionSettings implements IProcessorSettings {

	private int polynomialOrder = 1;

	public BaselineCorrectionSettings() {

	}

	public int getPolynomialOrder() {

		return polynomialOrder;
	}

	public void setPolynomialOrder(int polynomialOrder) {

		this.polynomialOrder = polynomialOrder;
	}
}
