package net.openchrom.nmr.processing.supplier.base.settings.support;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DigitalFilterTreatmentOptions {
	SHIFT_ONLY("Left shift group delay only"), //

	SUBSTITUTE_WITH_ZERO("Left shift group delay and overwrite with zeros"), //

	SUBSTITUTE_WITH_NOISE("Left shift group delay and overwrite with noise");

	@JsonValue
	private String digitalFilterTreatmentOption;

	private DigitalFilterTreatmentOptions(String digitalFilterTreatmentOption){

		this.digitalFilterTreatmentOption = digitalFilterTreatmentOption;
	}

	@Override
	public String toString() {

		return digitalFilterTreatmentOption;
	}
}
