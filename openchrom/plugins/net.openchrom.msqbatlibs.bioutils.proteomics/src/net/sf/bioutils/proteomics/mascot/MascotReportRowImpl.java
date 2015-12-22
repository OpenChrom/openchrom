/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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
import net.sf.jtables.table.impl.RowImpl;

public class MascotReportRowImpl implements MascotReportRow {

	private final Row<String> row;
	private final ParserFractionNumber parser = new ParserFractionNumber();

	public MascotReportRowImpl(Row<String> element) {

		this.row = element;
	}

	public int getFractionNumber() throws IOException {

		return parser.parseFractionNumber(row.get(FileFormatMascotReport.PEP_SCAN_TITLE));
	}

	public double getPeptideExpMr() {

		double result = Double.parseDouble(row.get(FileFormatMascotReport.PEP_EXP_MR));
		return result;
	}

	public double getPeptideExpMz() {

		double result = Double.parseDouble(row.get(FileFormatMascotReport.PEP_EXP_MZ));
		return result;
	}

	public String getProteinAccessionID() {

		String result = row.get(FileFormatMascotReport.PROT_ACC);
		return result;
	}

	public Row<String> toRow() {

		return new RowImpl<String>(row);
	}

	public String toString() {

		return row.toString();
	}
}
