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
import java.text.MessageFormat;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.targets.ITargetIdentifierSupplier;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.cas.CasSupport;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

public class TargetIdentifier implements ITargetIdentifierSupplier {

	private static final Logger logger = Logger.getLogger(TargetIdentifier.class);
	private static final String SEARCH_URL = "https://foodb.ca/unearth/q?query={0}&searcher=compounds";

	@Override
	public String getId() {

		return "net.openchrom.xxd.identifier.supplier.foodb.target.identifier";
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
	public URL getURL(ILibraryInformation libraryInformation) {

		URL url = null;
		try {
			String cas = libraryInformation.getCasNumber();
			if(cas != null && !cas.isEmpty() && !CasSupport.CAS_DEFAULT.equals(cas)) {
				url = new URL(MessageFormat.format(SEARCH_URL, cas));
			} else {
				String name = libraryInformation.getName();
				url = new URL(MessageFormat.format(SEARCH_URL, name));
			}
		} catch(MalformedURLException e) {
			logger.warn(e);
		}
		return url;
	}
}
