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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.sf.bioutils.proteomics.peak.FactoryPeak;
import net.sf.bioutils.proteomics.peak.FactoryPeakImpl;
import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.jmgf.MGFFileReader;
import net.sf.kerner.utils.io.buffered.AbstractBufferedReader;
import net.sf.kerner.utils.progress.ProgressMonitor;

public class MGFFileReaderImpl extends AbstractBufferedReader implements MGFFileReader {

	private FactoryPeak factoryPeak;
	private ProgressMonitor monitor;
	protected MGFElementIterator iterator;

	public MGFFileReaderImpl(final BufferedReader reader) {
		super(reader);
	}

	public MGFFileReaderImpl(final File file) throws FileNotFoundException {
		super(file);
	}

	public MGFFileReaderImpl(final InputStream stream) {
		super(stream);
	}

	public MGFFileReaderImpl(final Reader reader) {
		super(reader);
	}

	protected synchronized MGFFile buildNewMGFFile(final List<? extends MGFElement> elements) {

		return new MGFFileBean(elements);
	}

	public synchronized FactoryPeak getFactoryPeak() {

		if(factoryPeak == null) {
			factoryPeak = new FactoryPeakImpl();
		}
		return factoryPeak;
	}

	public synchronized MGFElementIterator getIterator() throws IOException {

		if(iterator == null) {
			iterator = new MGFElementIterator(super.reader);
		}
		iterator.setMonitor(monitor);
		return iterator;
	}

	public synchronized ProgressMonitor getMonitor() {

		return monitor;
	}

	public synchronized MGFFile read() throws IOException {

		final List<MGFElement> result = new ArrayList<MGFElement>();
		final MGFElementIterator it = getIterator();
		it.setFactoryPeak(getFactoryPeak());
		// try {
		while(it.hasNext()) {
			result.add(it.next());
		}
		return buildNewMGFFile(result);
		// } finally {
		// it.close();
		// }
	}

	public synchronized void setFactoryPeak(final FactoryPeak factoryPeak) {

		this.factoryPeak = factoryPeak;
	}

	public synchronized void setMonitor(final ProgressMonitor monitor) {

		this.monitor = monitor;
	}
}
