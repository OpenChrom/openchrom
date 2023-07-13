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
package net.openchrom.xxd.identifier.supplier.foodb.identifier;

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

public class ScanIdentifier implements IScanIdentifierSupplier {

	private static final Logger logger = Logger.getLogger(ScanIdentifier.class);
	private static final String GC_MS_SEARCH_URL = "https://foodb.ca/spectra/c_ms/search?utf8=%E2%9C%93&peaks={0}&c_ms_retention_value=&c_ms_retention_type=ri_semistdnp&mass_charge_tolerence=0.5&commit=Search";
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish();

	@Override
	public String getId() {

		return "net.openchrom.xxd.identifier.supplier.foodb.scan.identifier";
	}

	@Override
	public String getDescription() {

		return "Click to open the corresponding FooDB entry in a web browser.";
	}

	@Override
	public String getIdentifierName() {

		return "FooDB";
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
				url = new URL(MessageFormat.format(GC_MS_SEARCH_URL, extractTracesIntensity(scanMSD, 10)));
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
