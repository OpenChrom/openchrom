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
package net.sf.jmgf.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.bioutils.proteomics.peak.FactoryPeak;
import net.sf.bioutils.proteomics.peak.FactoryPeakImpl;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.jmgf.exception.ExceptionFileFormat;
import net.sf.kerner.utils.io.buffered.AbstractIOIterator;
import net.sf.kerner.utils.progress.ProgressMonitor;

public class MGFElementIterator extends AbstractIOIterator<MGFElement> {

	private FactoryPeak factoryPeak;
	private ProgressMonitor monitor;
	private long lineCnt = 0;

	private enum ReadState {
		MS1, MS2
	}

	private ReadState readState = ReadState.MS1;

	public MGFElementIterator(final BufferedReader reader) throws IOException {
		super(reader);
	}

	public MGFElementIterator(final File file) throws IOException {
		super(file);
	}

	public MGFElementIterator(final InputStream stream) throws IOException {
		super(stream);
	}

	public MGFElementIterator(final Reader reader) throws IOException {
		super(reader);
	}

	@Override
	protected synchronized MGFElement doRead() throws IOException {

		if(monitor != null) {
			monitor.started(ProgressMonitor.UNKNOWN_WORKLOAD);
		}
		try {
			String line;
			final Map<String, String> tags = new LinkedHashMap<String, String>();
			final List<Peak> peaks = new ArrayList<Peak>();
			while((line = reader.readLine()) != null) {
				lineCnt++;
				final String lineUpperTrim = line.toUpperCase().trim();
				if(lineUpperTrim.equals(MGFFile.Format.FIRST_LINE_MS2)) {
					// Switching from ReadState.MS1 to ReadState.MS2 or continuing in MS2 mode
					if(peaks.isEmpty()) {
						// continuing in MS2 mode
						readState = ReadState.MS2;
						continue;
					} else {
						// first build new element, then switch state
						MGFElement result = newMGFElement(tags, peaks, readState);
						readState = ReadState.MS2;
						return result;
					}
				}
				// Switching from ReadState.MS2 to ReadState.MS1 is not intended, instead a new MGFElement is created
				if(lineUpperTrim.equals(MGFFile.Format.LAST_LINE_MS2) && readState.equals(ReadState.MS2)) {
					// Finish ReadState.MS2
					return newMGFElement(tags, peaks, readState);
				}
				// Assume that lines starting numerically always describe an ion
				if(isIonBlock(line.trim())) {
					peaks.add(readPeak(line));
				} else {
					final String[] arr = line.split(MGFFile.Format.KEY_VALUE_SEPARATOR, 2);
					if(arr.length != 2) {
						throw new ExceptionFileFormat("Invalid line " + line);
					}
					tags.put(arr[0], arr[1]);
				}
			}
			if(tags != null && !tags.isEmpty()) {
				return newMGFElement(tags, peaks, readState);
			}
		} finally {
			if(monitor != null) {
				monitor.finished();
			}
		}
		// nothing left to read
		return null;
	}

	private MGFElement newMGFElement(Map<String, String> tags, List<Peak> peaks, ReadState readState) {

		if(peaks == null) {
			throw new IllegalArgumentException("peaks must not be null (line " + lineCnt + ")");
		}
		final MGFElementBean result = new MGFElementBean();
		switch(readState) {
			case MS1:
				result.setMSLevel((short)1);
				break;
			case MS2:
				result.setMSLevel((short)2);
				break;
			default:
				throw new IllegalArgumentException("Unknown read state " + readState);
		}
		result.setPeaks(new ArrayList<>(peaks));
		result.setTags(new LinkedHashMap<>(tags));
		peaks.clear();
		// Do not clear tags, use them for all MGFElements that come from the same file
		tags.clear();
		return result;
	}

	public synchronized FactoryPeak getFactoryPeak() {

		if(factoryPeak == null) {
			factoryPeak = new FactoryPeakImpl();
		}
		return factoryPeak;
	}

	public synchronized ProgressMonitor getMonitor() {

		return monitor;
	}

	protected synchronized boolean isIonBlock(final String secondLine) {

		// TODO: not optimal in terms of performance and elegance
		try {
			Double.parseDouble(secondLine.split("\\s")[0]);
		} catch(final NumberFormatException e) {
			return false;
		}
		return true;
	}

	protected synchronized Peak readPeak(final String line) throws ExceptionFileFormat {

		final String[] arr = line.trim().split("\\s++");
		if(arr.length < 1) {
			throw new ExceptionFileFormat("invalid peak line " + line);
		}
		final double mz = Double.parseDouble(arr[0]);
		final double intensity;
		if(arr.length > 1) {
			intensity = Double.parseDouble(arr[1]);
		} else {
			intensity = -1;
		}
		if(arr.length > 2) {
			Double.parseDouble(arr[2]);
		} else {
		}
		// TODO: charge currently not supported
		return getFactoryPeak().create(null, mz, intensity, -1);
	}

	public synchronized void setFactoryPeak(final FactoryPeak factoryPeak) {

		this.factoryPeak = factoryPeak;
	}

	public synchronized void setMonitor(final ProgressMonitor monitor) {

		this.monitor = monitor;
	}
}
