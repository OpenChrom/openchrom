/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.gaml.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.core.AbstractMeasurement;
import org.eclipse.chemclipse.nmr.model.core.AcquisitionParameter;
import org.eclipse.chemclipse.nmr.model.core.DataDimension;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementFID;
import org.eclipse.chemclipse.nmr.model.core.ISignalFID;

public final class VendorFIDMeasurement extends AbstractMeasurement implements IMeasurementFID, AcquisitionParameter {

	private static final long serialVersionUID = 1L;
	private final List<VendorFIDSignal> signals = new ArrayList<>();
	private BigDecimal spectralWidth;
	private BigDecimal spectrometerFrequency;
	private BigDecimal carrierFrequency;

	@Override
	public List<? extends ISignalFID> getSignals() {

		return Collections.unmodifiableList(signals);
	}

	public void addSignal(VendorFIDSignal signal) {

		signals.add(signal);
	}

	@Override
	public BigDecimal getSpectralWidth() {

		return spectralWidth;
	}

	public void setSpectralWidth(double value) {

		spectralWidth = new BigDecimal(value);
	}

	@Override
	public int getNumberOfPoints() {

		return signals.size();
	}

	@Override
	public BigDecimal getSpectrometerFrequency() {

		return spectrometerFrequency;
	}

	public void setSpectrometerFrequency(double value) {

		spectrometerFrequency = new BigDecimal(value);
	}

	@Override
	public BigDecimal getCarrierFrequency() {

		return carrierFrequency;
	}

	public void setCarrierFrequency(double value) {

		carrierFrequency = new BigDecimal(value);
	}

	@Override
	public AcquisitionParameter getAcquisitionParameter() {

		return this;
	}

	@Override
	public DataDimension getDataDimension() {

		return DataDimension.ONE_DIMENSIONAL;
	}
}
