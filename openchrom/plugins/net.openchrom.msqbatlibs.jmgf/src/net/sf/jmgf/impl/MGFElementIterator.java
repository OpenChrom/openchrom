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

import org.apache.log4j.Logger;

public class MGFElementIterator extends AbstractIOIterator<MGFElement> {

	private final static Logger logger = Logger.getLogger(MGFElementIterator.class);
	private ProgressMonitor monitor;
	private FactoryPeak factoryPeak;

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

	protected synchronized MGFElement doRead() throws IOException {

		if(monitor != null) {
			monitor.started(ProgressMonitor.UNKNOWN_WORKLOAD);
		}
		try {
			String line = reader.readLine();
			if(line == null) {
				// nothing left to read
				return null;
			}
			if(line.toUpperCase().trim().equals(MGFFile.FIRST_LINE)) {
			} else {
				throw new ExceptionFileFormat("Invalid first line " + line);
			}
			final Map<String, String> elements = new LinkedHashMap<String, String>();
			final List<Peak> peaks = new ArrayList<Peak>();
			while(line != null) {
				line = reader.readLine();
				if(line == null) {
					logger.error("Unexpected end of file");
					final MGFElementBean result = new MGFElementBean();
					result.setPeaks(peaks);
					result.setElements(elements);
					return result;
				}
				if(line.toUpperCase().trim().equals(MGFFile.LAST_LINE)) {
					final MGFElementBean result = new MGFElementBean();
					result.setPeaks(peaks);
					result.setElements(elements);
					return result;
				}
				// Assume that lines starting numerically always describe an ion
				if(isIonBlock(line)) {
					peaks.add(readPeak(line));
				} else {
					final String[] arr = line.split("=", 2);
					if(arr.length != 2) {
						throw new ExceptionFileFormat("Invalid line " + line);
					}
					elements.put(arr[0], arr[1]);
				}
			}
		} finally {
			if(monitor != null) {
				monitor.finished();
			}
		}
		throw new RuntimeException("Something buggy here..");
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

		try {
			Double.parseDouble(secondLine.split("\\s")[0]);
		} catch(final NumberFormatException e) {
			return false;
		}
		return true;
	}

	protected synchronized Peak readPeak(final String line) throws ExceptionFileFormat {

		final String[] arr = line.split("\\s");
		if(arr.length < 1) {
			throw new ExceptionFileFormat("invalid peak line " + line);
		}
		final double mz = Double.parseDouble(arr[0]);
		final double intensity;
		final double charge;
		if(arr.length > 1) {
			intensity = Double.parseDouble(arr[1]);
		} else {
			intensity = -1;
		}
		if(arr.length > 2) {
			charge = Double.parseDouble(arr[2]);
		} else {
			charge = -1;
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
