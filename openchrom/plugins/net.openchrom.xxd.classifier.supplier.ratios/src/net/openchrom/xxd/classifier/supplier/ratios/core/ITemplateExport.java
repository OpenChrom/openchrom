/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.core;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

public interface ITemplateExport {

	default String getName(IPeak peak) {

		return getName(IIdentificationTarget.getLibraryInformation(peak));
	}

	default String getName(IScan scan) {

		return getName(IIdentificationTarget.getLibraryInformation(scan));
	}

	default String getName(ILibraryInformation libraryInformation) {

		return (libraryInformation != null) ? libraryInformation.getName() : "";
	}
}