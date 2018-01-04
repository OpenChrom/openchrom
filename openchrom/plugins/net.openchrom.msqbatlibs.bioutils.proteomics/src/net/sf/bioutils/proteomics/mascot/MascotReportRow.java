/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.mascot;

import java.io.IOException;

import net.sf.jtables.table.Row;

public interface MascotReportRow {

	int getFractionNumber() throws IOException;

	double getPeptideExpMr() throws IOException;

	double getPeptideExpMz() throws IOException;

	String getProteinAccessionID() throws IOException;

	Row<String> toRow();
}
