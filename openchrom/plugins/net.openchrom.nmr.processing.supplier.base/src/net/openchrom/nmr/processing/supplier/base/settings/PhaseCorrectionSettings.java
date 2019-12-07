/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Kerner - initial API and implementation
 * Jan Holy - refactoring
 * Alexander Stark - refactoring
 * Christoph LÃ¤ubrich - refactoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings;

import java.util.Observable;

import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * this class is final, synchronized, {@link Cloneable} and {@link Observable}
 * since it participates in the dynamic settings framework
 *
 */
@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public final class PhaseCorrectionSettings extends Observable implements Cloneable {

	public enum PivotPointSelection {
		LEFT("pivot @ far left end of the spectrum"), //
		MIDDLE("pivot @ middle of the spectrum"), //
		PEAK_MAX("pivot @ biggest peak of the spectrum"), //
		USER_DEFINED("pivot @ user defined position"), //
		NOT_DEFINED("phasing without specified pivot point");//

		@JsonValue
		private String pivotPosition = "";

		private PivotPointSelection(String pivotPosition){
			this.pivotPosition = pivotPosition;
		}

		@Override
		public String toString() {

			return pivotPosition;
		}
	}

	@JsonProperty("0th order correction [°]")
	private double zeroOrderPhaseCorrection = 0.0;
	@JsonProperty("1st order correction [°]")
	private double firstOrderPhaseCorrection = 0.0;
	@JsonProperty("Position of pivot point")
	private PivotPointSelection pivotPointSelection = PivotPointSelection.PEAK_MAX;
	@JsonProperty("User defined pivot point value")
	private double userDefinedPivotPointValue = 0.0;
	@JsonProperty("User defined phase correction factor")
	private double dspPhaseFactor = 0.0;

	public synchronized double getDspPhaseFactor() {

		return dspPhaseFactor;
	}

	public synchronized void setDspPhaseFactor(double dspPhaseFactor) {

		this.dspPhaseFactor = dspPhaseFactor;
		fireUpdate();
	}

	public synchronized PivotPointSelection getPivotPointSelection() {

		return pivotPointSelection;
	}

	public synchronized void setPivotPointSelection(PivotPointSelection pivotPointSelection) {

		this.pivotPointSelection = pivotPointSelection;
		fireUpdate();
	}

	public synchronized double getZeroOrderPhaseCorrection() {

		return zeroOrderPhaseCorrection;
	}

	public synchronized void setZeroOrderPhaseCorrection(double zeroOrderPhaseCorrection) {

		this.zeroOrderPhaseCorrection = zeroOrderPhaseCorrection;
		fireUpdate();
	}

	public synchronized double getFirstOrderPhaseCorrection() {

		return firstOrderPhaseCorrection;
	}

	public synchronized void setFirstOrderPhaseCorrection(double firstOrderPhaseCorrection) {

		this.firstOrderPhaseCorrection = firstOrderPhaseCorrection;
		fireUpdate();
	}

	public synchronized double getUserDefinedPivotPointValue() {

		return userDefinedPivotPointValue;
	}

	public synchronized void setUserDefinedPivotPointValue(double userDefinedPivotPointValue) {

		this.userDefinedPivotPointValue = userDefinedPivotPointValue;
		fireUpdate();
	}

	private synchronized void fireUpdate() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				setChanged();
				notifyObservers(this);
			}
		}).start();
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("PhaseCorrectionSettings [zeroOrderPhaseCorrection=");
		builder.append(zeroOrderPhaseCorrection);
		builder.append(", firstOrderPhaseCorrection=");
		builder.append(firstOrderPhaseCorrection);
		builder.append(", pivotPointSelection=");
		builder.append(pivotPointSelection);
		if(pivotPointSelection == PivotPointSelection.USER_DEFINED) {
			builder.append(", userDefinedPivotPointValue=");
			builder.append(userDefinedPivotPointValue);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public synchronized PhaseCorrectionSettings clone() {

		PhaseCorrectionSettings settings = new PhaseCorrectionSettings();
		settings.dspPhaseFactor = dspPhaseFactor;
		settings.firstOrderPhaseCorrection = firstOrderPhaseCorrection;
		settings.pivotPointSelection = pivotPointSelection;
		settings.userDefinedPivotPointValue = userDefinedPivotPointValue;
		settings.zeroOrderPhaseCorrection = zeroOrderPhaseCorrection;
		return settings;
	}
}
