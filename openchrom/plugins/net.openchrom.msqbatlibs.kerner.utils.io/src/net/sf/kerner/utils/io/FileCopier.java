/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileCopier {

	private final InputStream inStream;
	private final File outFile;

	public FileCopier(File in, File out) throws IOException {
		if(!UtilFile.fileCheck(in, false))
			throw new FileNotFoundException("cannot read " + in);
		this.inStream = UtilIO.getInputStreamFromFile(in);
		this.outFile = out;
	}

	public FileCopier(String inFilePath, String outFilePath) throws IOException {
		final File in = new File(inFilePath);
		if(!UtilFile.fileCheck(in, false))
			throw new FileNotFoundException("cannot read " + in);
		this.inStream = UtilIO.getInputStreamFromFile(in);
		this.outFile = new File(outFilePath);
	}

	public FileCopier(File inFile, File outDir, String outFileName) throws IOException {
		if(!UtilFile.fileCheck(inFile, false))
			throw new FileNotFoundException("cannot read " + inFile);
		this.inStream = UtilIO.getInputStreamFromFile(inFile);
		this.outFile = new File(outDir, outFileName);
	}

	public long copy() throws IOException {

		final InputStreamReader reader = new InputStreamReader(inStream);
		final BufferedReader br = new BufferedReader(reader);
		final FileWriter writer = new FileWriter(outFile);
		final BufferedWriter bw = new BufferedWriter(writer);
		long r = 0;
		try {
			r = UtilIO.readerToWriter(br, bw);
		} finally {
			bw.flush();
			bw.close();
			br.close();
		}
		return r;
	}
}
