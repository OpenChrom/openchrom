/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

public class TableElement extends AbstractElement<TableElement> {

	private PDFTable pdfTable;

	public TableElement(float x, float y) {
		setX(x);
		setY(y);
	}

	public PDFTable getPdfTable() {

		return pdfTable;
	}

	public TableElement setPdfTable(PDFTable pdfTable) {

		this.pdfTable = pdfTable;
		return this;
	}
}
