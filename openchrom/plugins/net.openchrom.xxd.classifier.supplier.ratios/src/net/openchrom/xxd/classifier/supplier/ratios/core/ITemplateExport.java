/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

import java.util.Set;

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

public interface ITemplateExport {

	default String getName(IPeak peak, IdentificationTargetComparator comparator) {

		return getName(peak.getTargets(), comparator);
	}

	default String getName(IScan scan, IdentificationTargetComparator comparator) {

		return getName(scan.getTargets(), comparator);
	}

	default String getName(Set<IIdentificationTarget> targets, IdentificationTargetComparator comparator) {

		ILibraryInformation libraryInformation = IIdentificationTarget.getBestLibraryInformation(targets, comparator);
		return (libraryInformation != null) ? libraryInformation.getName() : "";
	}
}