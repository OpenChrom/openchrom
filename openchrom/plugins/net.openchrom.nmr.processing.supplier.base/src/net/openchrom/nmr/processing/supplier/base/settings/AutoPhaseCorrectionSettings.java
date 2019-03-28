package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;

public class AutoPhaseCorrectionSettings implements IProcessorSettings {

	// user might provide better values
	private double penaltyFactor = 1E-9 / 5;

	public double getPenaltyFactor() {

		return penaltyFactor;
	}

	public void setPenaltyFactor(double penaltyFactor) {

		this.penaltyFactor = penaltyFactor;
	}
}
