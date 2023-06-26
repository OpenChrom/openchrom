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
package net.openchrom.xxd.identifier.supplier.cas.identifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.targets.ITargetIdentifierSupplier;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

public class CommonChemistryTargetIdentifier implements ITargetIdentifierSupplier {

	private static final Logger logger = Logger.getLogger(CommonChemistryTargetIdentifier.class);
	private static final String CAS_DETAIL_URL = "https://commonchemistry.cas.org/detail?cas_rn={0}";
	private static final String QUERY_RESULTS_URL = "https://commonchemistry.cas.org/results?q={0}";

	@Override
	public String getId() {

		return "net.openchrom.xxd.identifier.supplier.cas.common.chemistry.identifier";
	}

	@Override
	public String getDescription() {

		return "Click to open the corresponding CAS Common Chemistry database entry in a web browser.";
	}

	@Override
	public String getIdentifierName() {

		return "CAS Common Chemistry";
	}

	@Override
	public Class<? extends IIdentifierSettings> getSettingsClass() {

		return null;
	}

	@Override
	public URL getURL(ILibraryInformation libraryInformation) {

		URL url = null;
		try {
			String cas = libraryInformation.getCasNumber();
			if(cas != null && !cas.isEmpty() && !cas.equals("0-00-0")) {
				url = new URL(MessageFormat.format(CAS_DETAIL_URL, cas));
			} else {
				String name = libraryInformation.getName();
				url = new URL(MessageFormat.format(QUERY_RESULTS_URL, name));
			}
		} catch(MalformedURLException e) {
			logger.warn(e);
		}
		return url;
	}
}
