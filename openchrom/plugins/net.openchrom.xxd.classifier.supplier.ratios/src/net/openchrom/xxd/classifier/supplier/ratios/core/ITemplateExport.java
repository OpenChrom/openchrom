/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
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