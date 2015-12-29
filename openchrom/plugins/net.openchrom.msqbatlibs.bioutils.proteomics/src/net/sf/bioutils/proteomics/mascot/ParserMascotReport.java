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

import java.io.File;
import java.io.IOException;

import net.sf.jtables.io.reader.ReaderTableString;
import net.sf.jtables.io.reader.VisitorFirstLine;
import net.sf.jtables.io.transformer.TransformerRowToObject;
import net.sf.jtables.table.Row;
import net.sf.kerner.utils.io.buffered.IOIterator;

public class ParserMascotReport implements IOIterator<MascotReportRow>, TransformerRowToObject<String, MascotReportRow> {

	private ReaderTableString reader;

	public ParserMascotReport(File file) throws IOException {
		reader = new ReaderTableString(file, true, false);
		reader.addVisitorFirstLine(new VisitorFirstLine(FileFormatMascotReport.PROT_HIT_NUM));
	}

	public void close() {

		synchronized(reader) {
			reader.close();
		}
	}

	public boolean hasNext() throws IOException {

		synchronized(reader) {
			return reader.hasNext();
		}
	}

	public MascotReportRow next() throws IOException {

		synchronized(reader) {
			Row<String> nextRow = reader.next();
			return transform(nextRow);
		}
	}

	public MascotReportRow transform(Row<String> element) {

		synchronized(reader) {
			MascotReportRowImpl result = new MascotReportRowImpl(element);
			return result;
		}
	}
}
