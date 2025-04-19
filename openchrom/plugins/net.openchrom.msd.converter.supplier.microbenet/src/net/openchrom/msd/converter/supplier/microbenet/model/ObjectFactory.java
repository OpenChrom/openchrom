/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.microbenet.model;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public MspMatchResult createMspMatchResult() {

		return new MspMatchResult();
	}

	public Analytes createMspMatchResultAnalytes() {

		return new Analytes();
	}

	public Analytes.Analyte createMspMatchResultAnalytesAnalyte() {

		return new Analytes.Analyte();
	}

	public Peaklist createMspMatchResultAnalytesAnalytePeaklist() {

		return new Peaklist();
	}

	public Peaks createMspMatchResultAnalytesAnalytePeaklistPeaks() {

		return new Peaks();
	}

	public ProjectInfo createMspMatchResultProjectInfo() {

		return new ProjectInfo();
	}

	public Peak createMspMatchResultAnalytesAnalytePeaklistPeaksPeak() {

		return new Peak();
	}
}
