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
import java.text.MessageFormat;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.targets.ITargetIdentifierSupplier;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

public class TargetIdentifier implements ITargetIdentifierSupplier {

	private static final Logger logger = Logger.getLogger(TargetIdentifier.class);
	private static final String SEARCH_URL = "https://massbank.eu/MassBank/Result.jsp?inchikey={0}&type=inchikey&searchType=inchikey";

	@Override
	public String getId() {

		return "net.openchrom.xxd.identifier.supplier.massbank.target.identifier";
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
	public URL getURL(ILibraryInformation libraryInformation) {

		URL url = null;
		try {
			String inchiKey = libraryInformation.getInChIKey();
			if(inchiKey != null && !inchiKey.isEmpty()) {
				url = new URL(MessageFormat.format(SEARCH_URL, inchiKey));
			}
		} catch(MalformedURLException e) {
			logger.warn(e);
		}
		return url;
	}
}
