/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
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
