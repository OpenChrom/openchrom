/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.converter.supplier.rdx3.core;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.eclipse.chemclipse.converter.io.AbstractChromatogramWriter;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.core.runtime.IProgressMonitor;

public class ChromatogramWriter extends AbstractChromatogramWriter {

	public static final String FILE_EXTENSION = ".RData";

	private static final String MAGIC_CODE = "RDX3";
	private static final String ENCODING = "UTF-8";
	private static final String SECTION_DATA = "data";
	private static final String SECTION_CLASS = "class";
	private static final String SECTION_NAMES = "names";
	private static final String ID_TBL_DF = "tbl_df";
	private static final String ID_TBL = "tbl";
	private static final String ID_DATA_FRAME = "data.frame";
	private static final String ID_ROW_NAMES = "row.names";
	private static final String COLUMN_RT = "RT";
	private static final String COLUMN_RI = "RI";

	public void export(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws IOException {

		try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			String name = file.getName().replace(FILE_EXTENSION, "");
			writeEntry(gzipOutputStream, name, chromatogram, monitor);
			gzipOutputStream.flush();
		}

	}

	private void writeEntry(GZIPOutputStream gzipOutputStream, String name, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws IOException {

		try (DataOutputStream dataOutputStream = new DataOutputStream(gzipOutputStream)) {
			/*
			 * Settings
			 */
			int startMZ = AbstractIon.getIon(chromatogram.getStartIon());
			int stopMZ = AbstractIon.getIon(chromatogram.getStopIon());
			List<IScan> scans = new ArrayList<>(chromatogram.getScans());
			Collections.sort(scans, (s1, s2) -> Integer.compare(s1.getRetentionTime(), s2.getRetentionTime()));
			/*
			 * Export
			 */
			writeHeader(dataOutputStream);
			writeData(dataOutputStream, startMZ, stopMZ, scans, monitor);
			writeFooter(dataOutputStream);
			dataOutputStream.flush();
		}
	}

	private void writeHeader(DataOutputStream dataOutputStream) throws IOException {

		/*
		 * RDX3
		 * 2648, 2560, 0, 768, 1028, 256, 773, 0
		 * UTF-8
		 */
		dataOutputStream.write(MAGIC_CODE.getBytes());
		dataOutputStream.writeShort((short)2648);
		dataOutputStream.writeShort((short)2560);
		dataOutputStream.writeShort((short)0);
		dataOutputStream.writeShort((short)768);
		dataOutputStream.writeShort((short)1028);
		dataOutputStream.writeShort((short)256);
		dataOutputStream.writeShort((short)773);
		dataOutputStream.writeByte(0);
		writeString(dataOutputStream, ENCODING);
	}

	private void writeData(DataOutputStream dataOutputStream, int startMZ, int stopMZ, List<IScan> scans, IProgressMonitor monitor) throws IOException {

		writeSectionData(dataOutputStream, startMZ, stopMZ, scans, monitor);
		writeSectionClass(dataOutputStream, monitor);
		writeSectionEmpty(dataOutputStream, monitor);
		writeSectionNames(dataOutputStream, startMZ, stopMZ, monitor);
	}

	private void writeSectionData(DataOutputStream dataOutputStream, int startMZ, int stopMZ, List<IScan> scans, IProgressMonitor monitor) throws IOException {

		dataOutputStream.writeInt(1026);
		dataOutputStream.writeInt(1);
		dataOutputStream.writeShort((short)4);
		dataOutputStream.writeShort((short)9);
		writeString(dataOutputStream, SECTION_DATA);

		dataOutputStream.writeInt(787);
		dataOutputStream.writeInt(getColumnSize(startMZ, stopMZ));
		writeRetentionTime(dataOutputStream, scans, monitor);
		writeRetentionIndex(dataOutputStream, scans, monitor);

		List<IExtractedIonSignal> extractedIonSignals = getExtractedIonSignals(scans);
		for(int mz = startMZ; mz <= stopMZ; mz++) {
			writeIon(dataOutputStream, extractedIonSignals, mz, monitor);
		}
	}

	private int getColumnSize(int startMZ, int stopMZ) {

		/*
		 * RT
		 * RI
		 * 35
		 * ...
		 * 450
		 */
		return 2 + (stopMZ - startMZ) + 1;
	}

	private List<IExtractedIonSignal> getExtractedIonSignals(List<IScan> scans) {

		List<IExtractedIonSignal> extractedIonSignals = new ArrayList<>();
		for(IScan scan : scans) {
			if(scan instanceof IScanMSD scanMSD) {
				IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
				extractedIonSignals.add(extractedIonSignal);
			}
		}

		return extractedIonSignals;
	}

	private void writeRetentionTime(DataOutputStream dataOutputStream, List<IScan> scans, IProgressMonitor monitor) throws IOException {

		List<Double> data = new ArrayList<>();
		for(IScan scan : scans) {
			data.add(scan.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
		}
		writeDoubleList(dataOutputStream, 14, data);
	}

	private void writeRetentionIndex(DataOutputStream dataOutputStream, List<IScan> scans, IProgressMonitor monitor) throws IOException {

		List<Double> data = new ArrayList<>();
		for(IScan scan : scans) {
			data.add((double)scan.getRetentionIndex());
		}
		writeDoubleList(dataOutputStream, 14, data);
	}

	private void writeIon(DataOutputStream dataOutputStream, List<IExtractedIonSignal> extractedIonSignals, int mz, IProgressMonitor monitor) throws IOException {

		List<Double> data = new ArrayList<>();
		for(IExtractedIonSignal extractedIonSignal : extractedIonSignals) {
			data.add((double)extractedIonSignal.getAbundance(mz));
		}
		writeDoubleList(dataOutputStream, 14, data);
	}

	private void writeSectionClass(DataOutputStream dataOutputStream, IProgressMonitor monitor) throws IOException {

		dataOutputStream.writeInt(1026);
		dataOutputStream.writeInt(1);
		dataOutputStream.writeShort((short)4);
		dataOutputStream.writeShort((short)9);
		writeString(dataOutputStream, SECTION_CLASS);

		List<String> tableValues = new ArrayList<>();
		tableValues.add(ID_TBL_DF);
		tableValues.add(ID_TBL);
		tableValues.add(ID_DATA_FRAME);
		writeStringList(dataOutputStream, 16, tableValues);

		List<String> rowValues = new ArrayList<>();
		rowValues.add(ID_ROW_NAMES);
		writeStringList(dataOutputStream, 1026, rowValues);
	}

	private void writeSectionEmpty(DataOutputStream dataOutputStream, IProgressMonitor monitor) throws IOException {

		/*
		 * Needs further inspection.
		 */
		dataOutputStream.writeInt(13);
		dataOutputStream.writeInt(2);
		dataOutputStream.writeShort((short)32768);
		dataOutputStream.writeShort((short)0);
		dataOutputStream.writeByte(255);
		dataOutputStream.writeByte(255);
		dataOutputStream.writeByte(255);
		dataOutputStream.writeByte(247);
	}

	private void writeSectionNames(DataOutputStream dataOutputStream, int startMZ, int stopMZ, IProgressMonitor monitor) throws IOException {

		dataOutputStream.writeInt(1026);
		dataOutputStream.writeInt(1);
		dataOutputStream.writeShort((short)4);
		dataOutputStream.writeShort((short)9);
		writeString(dataOutputStream, SECTION_NAMES);

		List<String> names = new ArrayList<>();
		names.add(COLUMN_RT);
		names.add(COLUMN_RI);
		for(int mz = startMZ; mz <= stopMZ; mz++) {
			names.add(Integer.toString(mz));
		}
		writeStringList(dataOutputStream, 16, names);
	}

	private void writeFooter(DataOutputStream dataOutputStream) throws IOException {

		dataOutputStream.writeInt(254);
		dataOutputStream.writeInt(254);
	}

	private void writeString(DataOutputStream dataOutputStream, String value) throws IOException {

		byte[] section = value.getBytes();
		dataOutputStream.writeInt(section.length);
		dataOutputStream.write(section);
	}

	private void writeStringList(DataOutputStream dataOutputStream, int index, List<String> values) throws IOException {

		dataOutputStream.writeInt(index);
		dataOutputStream.writeInt(values.size());

		for(String value : values) {
			dataOutputStream.writeShort((short)4);
			dataOutputStream.writeShort((short)9);
			writeString(dataOutputStream, value);
		}
	}

	private void writeDoubleList(DataOutputStream dataOutputStream, int index, List<Double> values) throws IOException {

		dataOutputStream.writeInt(index);
		dataOutputStream.writeInt(values.size());

		for(Double value : values) {
			dataOutputStream.writeDouble(value);
		}
	}
}
