/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.axr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import net.openchrom.wsd.converter.supplier.axr.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.axr.model.VendorChromatogram;
import net.openchrom.wsd.converter.supplier.axr.model.VendorScan;

public class ChromatogramReader extends AbstractChromatogramWSDReader {

	private static final double MINUTES_TO_MILLISECONDS = 60_000d;

	@Override
	public IChromatogramWSD read(File file, IProgressMonitor monitor) throws IOException {

		return parse(file, true);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return parse(file, false);
	}

	private IChromatogramWSD parse(File file, boolean includeData) throws IOException {

		IVendorChromatogram chromatogram = new VendorChromatogram();
		chromatogram.setFile(file);
		Metadata metadata = new Metadata();
		int dataPoints = 0;
		try (JsonReader jsonReader = new JsonReader(new BufferedReader(new FileReader(file)))) {
			jsonReader.beginObject();
			while(jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				switch(name) {
					case "user":
						metadata.operator = nextString(jsonReader);
						break;
					case "meta":
						readMeta(jsonReader, metadata);
						break;
					case "data":
						if(includeData) {
							dataPoints = readDataArray(jsonReader, chromatogram);
						} else {
							jsonReader.skipValue();
						}
						break;
					default:
						jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
		} catch(IllegalStateException e) {
			throw new FileIsNotReadableException("Invalid AXR JSON structure.");
		}
		applyMetadata(chromatogram, metadata);
		if(includeData && dataPoints == 0) {
			throw new FileIsNotReadableException("No AXR chromatogram data points found.");
		}
		return chromatogram;
	}

	private void readMeta(JsonReader jsonReader, Metadata metadata) throws IOException {

		if(jsonReader.peek() != JsonToken.BEGIN_OBJECT) {
			jsonReader.skipValue();
			return;
		}
		jsonReader.beginObject();
		while(jsonReader.hasNext()) {
			String key = jsonReader.nextName();
			switch(key) {
				case "sample":
					metadata.sampleName = nextString(jsonReader);
					break;
				case "file":
					metadata.fileName = nextString(jsonReader);
					break;
				case "note":
					metadata.note = nextString(jsonReader);
					break;
				default:
					jsonReader.skipValue();
			}
		}
		jsonReader.endObject();
	}

	private int readDataArray(JsonReader jsonReader, IVendorChromatogram chromatogram) throws IOException {

		if(jsonReader.peek() != JsonToken.BEGIN_ARRAY) {
			jsonReader.skipValue();
			return 0;
		}
		int scans = 0;
		int firstRetentionTime = -1;
		int previousRetentionTime = -1;
		int scanInterval = 0;
		jsonReader.beginArray();
		while(jsonReader.hasNext()) {
			Double signal = null;
			Double retentionMinutes = null;
			if(jsonReader.peek() != JsonToken.BEGIN_OBJECT) {
				jsonReader.skipValue();
				continue;
			}
			jsonReader.beginObject();
			while(jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				switch(name) {
					case "x":
						retentionMinutes = nextDouble(jsonReader);
						break;
					case "y":
						signal = nextDouble(jsonReader);
						break;
					default:
						jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
			if(signal == null) {
				continue;
			}
			int retentionTime = previousRetentionTime;
			if(retentionMinutes != null) {
				retentionTime = (int)Math.round(retentionMinutes * MINUTES_TO_MILLISECONDS);
			} else if(retentionTime < 0) {
				retentionTime = 0;
			} else if(scanInterval > 0) {
				retentionTime += scanInterval;
			}
			VendorScan scan = new VendorScan(signal.floatValue());
			scan.setRetentionTime(retentionTime);
			chromatogram.addScan(scan);
			if(firstRetentionTime < 0) {
				firstRetentionTime = retentionTime;
			}
			if(previousRetentionTime >= 0 && scanInterval <= 0) {
				int delta = retentionTime - previousRetentionTime;
				if(delta > 0) {
					scanInterval = delta;
				}
			}
			previousRetentionTime = retentionTime;
			scans++;
		}
		jsonReader.endArray();
		if(firstRetentionTime >= 0) {
			chromatogram.setScanDelay(firstRetentionTime);
		}
		if(scanInterval > 0) {
			chromatogram.setScanInterval(scanInterval);
		}
		return scans;
	}

	private void applyMetadata(IVendorChromatogram chromatogram, Metadata metadata) {

		if(metadata.sampleName != null && !metadata.sampleName.isBlank()) {
			chromatogram.setSampleName(metadata.sampleName);
		}
		if(metadata.fileName != null && !metadata.fileName.isBlank()) {
			chromatogram.setDetailedInfo(metadata.fileName);
		}
		if(metadata.note != null && !metadata.note.isBlank()) {
			chromatogram.setMiscInfo(metadata.note);
		}
		if(metadata.operator != null && !metadata.operator.isBlank()) {
			chromatogram.setOperator(metadata.operator);
		}
	}

	private String nextString(JsonReader jsonReader) throws IOException {

		JsonToken token = jsonReader.peek();
		switch(token) {
			case STRING:
				return jsonReader.nextString();
			case NUMBER:
				return Double.toString(jsonReader.nextDouble());
			case NULL:
				jsonReader.nextNull();
				return null;
			default:
				jsonReader.skipValue();
				return null;
		}
	}

	private Double nextDouble(JsonReader jsonReader) throws IOException {

		JsonToken token = jsonReader.peek();
		switch(token) {
			case NUMBER:
				return jsonReader.nextDouble();
			case STRING:
				String value = jsonReader.nextString();
				if(value == null || value.isBlank()) {
					return null;
				}
				try {
					return Double.parseDouble(value);
				} catch(NumberFormatException e) {
					return null;
				}
			case NULL:
				jsonReader.nextNull();
				return null;
			default:
				jsonReader.skipValue();
				return null;
		}
	}

	private static class Metadata {

		private String sampleName;
		private String fileName;
		private String note;
		private String operator;
	}
}
