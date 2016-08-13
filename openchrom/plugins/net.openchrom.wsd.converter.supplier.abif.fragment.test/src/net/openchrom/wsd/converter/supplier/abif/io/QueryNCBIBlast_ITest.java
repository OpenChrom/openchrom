/*******************************************************************************
 * Copyright (c) 2016 Matthias Mailänder.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.BlastOutputFormatEnum;
import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;

import net.openchrom.wsd.converter.supplier.abif.ABIF;
import net.openchrom.wsd.converter.supplier.abif.ChromatogramReaderTestCase;

public class QueryNCBIBlast_ITest extends ChromatogramReaderTestCase {

	@Override
	protected void setUp() throws Exception {

		extensionPointId = ABIF.EXTENSION_POINT_ID;
		pathImport = ABIF.getAbsolutePath(ABIF.TESTFILE_IMPORT_3100_AB1);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testBlast() {

		String sequence = chromatogram.getMiscInfo();
		NCBIQBlastService service = new NCBIQBlastService();
		NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
		props.setBlastProgram(BlastProgramEnum.blastn);
		props.setBlastDatabase("rRNA_typestrains/prokaryotic_16S_ribosomal_RNA");
		NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();
		outputProps.setAlignmentNumber(20);
		outputProps.setOutputFormat(BlastOutputFormatEnum.HTML);
		String rid = null; // blast request ID
		FileWriter writer = null;
		BufferedReader reader = null;
		try {
			// send blast request and save request id
			rid = service.sendAlignmentRequest(sequence, props);
			// wait until results become available. Alternatively, one can do other computations/send other alignment requests
			while(!service.isReady(rid)) {
				System.out.println("Waiting for results. Sleeping for 5 seconds");
				Thread.sleep(5000);
			}
			// read results when they are ready
			InputStream in = service.getAlignmentResults(rid, outputProps);
			reader = new BufferedReader(new InputStreamReader(in));
			// write blast output to specified file
			File f = File.createTempFile("blastOutput.html", Long.toString(System.nanoTime()));
			System.out.println("Saving query results in file " + f.getAbsolutePath());
			writer = new FileWriter(f);
			String line;
			while((line = reader.readLine()) != null) {
				writer.write(line + System.getProperty("line.separator"));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			// clean up
			IOUtils.close(writer);
			IOUtils.close(reader);
			// delete given alignment results from blast server (optional operation)
			service.sendDeleteRequest(rid);
		}
	}
}