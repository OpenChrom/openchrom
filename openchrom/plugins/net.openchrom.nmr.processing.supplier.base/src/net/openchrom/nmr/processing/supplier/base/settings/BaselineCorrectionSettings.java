package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.support.settings.IntSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaselineCorrectionSettings implements IProcessorSettings {

	@JsonProperty(value = "Polynomial Order", defaultValue = "1")
	@IntSettingsProperty(minValue = 0)
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
