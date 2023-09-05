/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.massbank.identifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.scan.IScanIdentifierSupplier;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.msd.identifier.supplier.massbank.preferences.PreferenceSupplier;

public class ScanIdentifier implements IScanIdentifierSupplier {

	private static final Logger logger = Logger.getLogger(ScanIdentifier.class);
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();
	private static final String PEAK_SEARCH_URL = "QpeakResult.jsp?type=quick&searchType=peak&sortKey=not&sortAction=1&pageNo=1&qpeak={0}&CUTOFF=5&num=20";

	@Override
	public String getId() {

		return "net.openchrom.xxd.identifier.supplier.massbank.scan.identifier";
	}

	@Override
	public String getDescription() {

		return "Click to open the corresponding MassBank entry in a web browser.";
	}

	@Override
	public String getIdentifierName() {

		return "MassBank";
	}

	@Override
	public Class<? extends IIdentifierSettings> getSettingsClass() {

		return null;
	}

	@Override
	public URL getURL(IScan scan) {

		URL url = null;
		try {
			if(scan != null && scan instanceof IScanMSD scanMSD) {
				String combined = PreferenceSupplier.getDomain() + PEAK_SEARCH_URL;
				url = new URL(MessageFormat.format(combined, extractTracesIntensity(scanMSD, 10)));
			}
		} catch(MalformedURLException e) {
			logger.warn(e);
		}
		return url;
	}

	private String extractTracesIntensity(IScanMSD scanMSD, int max) {

		StringBuilder stringBuilder = new StringBuilder();
		float maxIntensity = scanMSD.getHighestAbundance().getAbundance();
		float relativeScalingFactor = 1000.0f / maxIntensity;
		List<IIon> ions = new ArrayList<>(scanMSD.getIons());
		Collections.sort(ions, (i1, i2) -> Float.compare(i2.getAbundance(), i1.getAbundance()));
		int i = 0;
		for(IIon ion : ions) {
			if(i > max) {
				break;
			}
			float mz = Math.round(AbstractIon.getIon(ion.getIon(), 1));
			float relativeIntensity = ion.getAbundance() * relativeScalingFactor;
			stringBuilder.append(MessageFormat.format("{0}%20{1}%0D%0A", decimalFormat.format(mz), decimalFormat.format(relativeIntensity)));
			i++;
		}
		return stringBuilder.toString();
	}
}
