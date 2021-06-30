/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirstDimensionProcessingParameterSetType", propOrder = {"zeroOrderPhaseCorrection", "firstOrderPhaseCorrection", "calibrationReferenceShift", "spectralDenoisingMethod", "windowFunction", "baselineCorrectionMethod"})
@XmlSeeAlso({HigherDimensionProcessingParameterSetType.class})
public class FirstDimensionProcessingParameterSetType {

	protected ValueWithUnitType zeroOrderPhaseCorrection;
	protected ValueWithUnitType firstOrderPhaseCorrection;
	protected ValueWithUnitType calibrationReferenceShift;
	protected CVTermType spectralDenoisingMethod;
	protected List<FirstDimensionProcessingParameterSetType.WindowFunction> windowFunction;
	protected CVTermType baselineCorrectionMethod;

	public ValueWithUnitType getZeroOrderPhaseCorrection() {

		return zeroOrderPhaseCorrection;
	}

	public void setZeroOrderPhaseCorrection(ValueWithUnitType value) {

		this.zeroOrderPhaseCorrection = value;
	}

	public ValueWithUnitType getFirstOrderPhaseCorrection() {

		return firstOrderPhaseCorrection;
	}

	public void setFirstOrderPhaseCorrection(ValueWithUnitType value) {

		this.firstOrderPhaseCorrection = value;
	}

	public ValueWithUnitType getCalibrationReferenceShift() {

		return calibrationReferenceShift;
	}

	public void setCalibrationReferenceShift(ValueWithUnitType value) {

		this.calibrationReferenceShift = value;
	}

	public CVTermType getSpectralDenoisingMethod() {

		return spectralDenoisingMethod;
	}

	public void setSpectralDenoisingMethod(CVTermType value) {

		this.spectralDenoisingMethod = value;
	}

	public List<FirstDimensionProcessingParameterSetType.WindowFunction> getWindowFunction() {

		if(windowFunction == null) {
			windowFunction = new ArrayList<FirstDimensionProcessingParameterSetType.WindowFunction>();
		}
		return this.windowFunction;
	}

	public CVTermType getBaselineCorrectionMethod() {

		return baselineCorrectionMethod;
	}

	public void setBaselineCorrectionMethod(CVTermType value) {

		this.baselineCorrectionMethod = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"windowFunctionMethod", "windowFunctionParameter"})
	public static class WindowFunction {

		@XmlElement(required = true)
		protected CVTermType windowFunctionMethod;
		@XmlElement(required = true)
		protected List<CVParamType> windowFunctionParameter;

		public CVTermType getWindowFunctionMethod() {

			return windowFunctionMethod;
		}

		public void setWindowFunctionMethod(CVTermType value) {

			this.windowFunctionMethod = value;
		}

		public List<CVParamType> getWindowFunctionParameter() {

			if(windowFunctionParameter == null) {
				windowFunctionParameter = new ArrayList<CVParamType>();
			}
			return this.windowFunctionParameter;
		}
	}
}
