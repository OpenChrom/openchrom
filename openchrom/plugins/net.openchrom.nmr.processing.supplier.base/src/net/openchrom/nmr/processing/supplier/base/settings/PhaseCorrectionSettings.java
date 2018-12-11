package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;

public class PhaseCorrectionSettings implements IProcessorSettings {

	public enum PIVOT_POINT_SELECTION {
		LEFT("pivot @ far left end of the spectrum"), //
		MIDDLE("pivot @ middle of the spectrum"), //
		PEAK_MAX("pivot @ biggest peak of the spectrum"), //
		USER_DEFINED("pivot @ user defined position"), //
		NOT_DEFINED("phasing without specified pivot point");//

		private String pivotPosition = "";

		private PIVOT_POINT_SELECTION(String pivotPosition) {
			this.pivotPosition = pivotPosition;
		}

		@Override
		public String toString() {

			return pivotPosition;
		}
	}

	private PIVOT_POINT_SELECTION pivotPointSelection = PIVOT_POINT_SELECTION.PEAK_MAX;
	private double zeroOrderPhaseCorrection = 0.0;
	private double firstOrderPhaseCorrection = 0.0;
	private double userDefinedPivotPointValue = 0.0;
	private double dspPhaseFactor = 0.0;

	public double getDspPhaseFactor() {

		return dspPhaseFactor;
	}

	public void setDspPhaseFactor(double dspPhaseFactor) {

		this.dspPhaseFactor = dspPhaseFactor;
	}

	public PIVOT_POINT_SELECTION getPivotPointSelection() {

		return pivotPointSelection;
	}

	public void setPivotPointSelection(PIVOT_POINT_SELECTION pivotPointSelection) {

		this.pivotPointSelection = pivotPointSelection;
	}

	public double getZeroOrderPhaseCorrection() {

		return zeroOrderPhaseCorrection;
	}

	public void setZeroOrderPhaseCorrection(double zeroOrderPhaseCorrection) {

		this.zeroOrderPhaseCorrection = zeroOrderPhaseCorrection;
	}

	public double getFirstOrderPhaseCorrection() {

		return firstOrderPhaseCorrection;
	}

	public void setFirstOrderPhaseCorrection(double firstOrderPhaseCorrection) {

		this.firstOrderPhaseCorrection = firstOrderPhaseCorrection;
	}

	public double getUserDefinedPivotPointValue() {

		return userDefinedPivotPointValue;
	}

	public void setUserDefinedPivotPointValue(double userDefinedPivotPointValue) {

		this.userDefinedPivotPointValue = userDefinedPivotPointValue;
	}
}
