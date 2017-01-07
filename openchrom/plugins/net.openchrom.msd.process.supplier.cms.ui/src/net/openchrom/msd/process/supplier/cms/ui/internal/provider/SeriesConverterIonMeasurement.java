/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.util.List;

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.swt.ui.series.ISeries;
import org.eclipse.chemclipse.swt.ui.series.Series;
import org.eclipse.chemclipse.swt.ui.support.Sign;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class SeriesConverterIonMeasurement {

	public static ISeries convertNominalIonMeasurement(IScanMSD massSpectrum, Sign sign) {

		ISeries massSpectrumSeries = null;
		if(massSpectrum instanceof ICalibratedVendorMassSpectrum && massSpectrum.getTotalSignal() > 0.0f) {
			/*
			 * Cast to the vendor mass spectrum.
			 */
			ICalibratedVendorMassSpectrum calibratedVendorMassSpectrum = (ICalibratedVendorMassSpectrum)massSpectrum;
			List<IIonMeasurement> ionMeasurements = calibratedVendorMassSpectrum.getIonMeasurements();
			double[] xSeries = new double[ionMeasurements.size()];
			double[] ySeries = new double[ionMeasurements.size()];
			/*
			 * Get the abundance for each ion and check if the values should be
			 * negative
			 */
			int x = 0;
			int y = 0;
			for(IIonMeasurement ionMeasurement : ionMeasurements) {
				xSeries[x++] = ionMeasurement.getMZ();
				double signal = ionMeasurement.getSignal();
				if(sign == Sign.NEGATIVE) {
					signal *= -1;
				}
				ySeries[y++] = signal;
			}
			//
			String label = "Ion Measurement";
			massSpectrumSeries = new Series(xSeries, ySeries, label);
		} else {
			massSpectrumSeries = getZeroIonMeasurementSeries();
		}
		return massSpectrumSeries;
	}

	private static ISeries getZeroIonMeasurementSeries() {

		double[] xSeries = {0, 0};
		double[] ySeries = {0, 0};
		return new Series(xSeries, ySeries, "No Ion Measurement");
	}
}
