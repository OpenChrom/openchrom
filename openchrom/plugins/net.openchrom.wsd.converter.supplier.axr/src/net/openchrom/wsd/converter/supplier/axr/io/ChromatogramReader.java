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

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

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
		try (JsonParser jsonParser = new JsonFactory().createParser(file)) {
			if(jsonParser.nextToken() != JsonToken.START_OBJECT) {
				throw new FileIsNotReadableException("Invalid AXR JSON structure.");
			}

			while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String name = jsonParser.currentName();
				jsonParser.nextToken();

				switch(name) {
					case "user":
						metadata.operator = nextString(jsonParser);
						break;
					case "meta":
						readMeta(jsonParser, metadata);
						break;
					case "data":
						if(includeData) {
							dataPoints = readDataArray(jsonParser, chromatogram);
						} else {
							jsonParser.skipChildren();
						}
						break;
					default:
						jsonParser.skipChildren();
				}
			}
		} catch(JsonParseException e) {
			throw new FileIsNotReadableException("Invalid AXR JSON structure.");
		}
		applyMetadata(chromatogram, metadata);
		if(includeData && dataPoints == 0) {
			throw new FileIsNotReadableException("No AXR chromatogram data points found.");
		}
		return chromatogram;
	}

	private void readMeta(JsonParser jsonParser, Metadata metadata) throws IOException {

		if(jsonParser.currentToken() != JsonToken.START_OBJECT) {
			jsonParser.skipChildren();
			return;
		}
		while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String key = jsonParser.currentName();
			jsonParser.nextToken();
			switch(key) {
				case "sample":
					metadata.sampleName = nextString(jsonParser);
					break;
				case "file":
					metadata.fileName = nextString(jsonParser);
					break;
				case "note":
					metadata.note = nextString(jsonParser);
					break;
				default:
					jsonParser.skipChildren();
			}
		}
	}

	private int readDataArray(JsonParser jsonParser, IVendorChromatogram chromatogram) throws IOException {

		if(jsonParser.currentToken() != JsonToken.START_ARRAY) {
			jsonParser.skipChildren();
			return 0;
		}
		int scans = 0;
		int firstRetentionTime = -1;
		int previousRetentionTime = -1;
		int scanInterval = 0;

		while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
			Double signal = null;
			Double retentionMinutes = null;

			if(jsonParser.currentToken() != JsonToken.START_OBJECT) {
				jsonParser.skipChildren();
				continue;
			}

			while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String name = jsonParser.currentName();
				jsonParser.nextToken(); // move to value
				switch(name) {
					case "x":
						retentionMinutes = nextDouble(jsonParser);
						break;
					case "y":
						signal = nextDouble(jsonParser);
						break;
					default:
						jsonParser.skipChildren();
				}
			}

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

	private String nextString(JsonParser jsonParser) throws IOException {

		JsonToken token = jsonParser.currentToken();
		switch(token) {
			case VALUE_STRING:
				return jsonParser.getText();
			case VALUE_NUMBER_INT:
			case VALUE_NUMBER_FLOAT:
				return jsonParser.getNumberValue().toString();
			case VALUE_NULL:
				return null;
			default:
				jsonParser.skipChildren();
				return null;
		}
	}

	private Double nextDouble(JsonParser jsonParser) throws IOException {

		JsonToken token = jsonParser.currentToken();
		switch(token) {
			case VALUE_NUMBER_INT:
			case VALUE_NUMBER_FLOAT:
				return jsonParser.getDoubleValue();
			case VALUE_STRING:
				String value = jsonParser.getText();
				if(value == null || value.isBlank()) {
					return null;
				}
				try {
					return Double.parseDouble(value);
				} catch(NumberFormatException e) {
					return null;
				}
			case VALUE_NULL:
				return null;
			default:
				jsonParser.skipChildren();
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
