/*******************************************************************************
 * Copyright (c) 2016, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - improvements
 *******************************************************************************/
package net.openchrom.wsd.identifier.supplier.geneident.ui.internal.runnables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import org.biojava.nbio.core.sequence.io.util.IOUtils;
import org.biojava.nbio.ws.alignment.qblast.BlastOutputFormatEnum;
import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.progress.core.InfoType;
import org.eclipse.chemclipse.progress.core.StatusLineLogger;
import org.eclipse.chemclipse.support.editor.SystemEditor;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import net.openchrom.wsd.identifier.supplier.geneident.ui.handlers.NCBIqueuedBLASThandler;

public class NCBIqueuedBLASTRunnable implements IRunnableWithProgress {

	private static final String DESCRIPTION = "Queued Web Nucleotide BLAST Search";
	// private static final String IDENTIFIER_ID = "net.openchrom.wsd.identifier.supplier.geneident.qblast";
	private IChromatogramSelectionWSD chromatogramSelection;
	private static final Logger logger = Logger.getLogger(NCBIqueuedBLASThandler.class);

	public NCBIqueuedBLASTRunnable(IChromatogramSelectionWSD chromatogramSelection) {

		this.chromatogramSelection = chromatogramSelection;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask(DESCRIPTION, IProgressMonitor.UNKNOWN);
			//
			IChromatogramWSD chromtogram = chromatogramSelection.getChromatogram();
			String sequence = chromtogram.getMiscInfo();
			NCBIQBlastService service = new NCBIQBlastService();
			NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
			props.setBlastProgram(BlastProgramEnum.megablast); // TODO: make this configurable
			props.setBlastDatabase("nt"); // nucleotide collection
			NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();
			outputProps.setAlignmentNumber(0); // TODO: make this configurable
			outputProps.setDescriptionNumber(10);
			outputProps.setOutputFormat(BlastOutputFormatEnum.Text); // TODO in BioJava: actually there is also hittable, ASN.1 and CSV output
			String requestID = null;
			FileWriter writer = null;
			BufferedReader reader = null;
			try {
				// Send BLAST request and save request id.
				requestID = service.sendAlignmentRequest(sequence, props);
				// Wait until results become available.
				while(!service.isReady(requestID)) {
					StatusLineLogger.setInfo(InfoType.MESSAGE, "Waiting for results.");
					Thread.sleep(NCBIQBlastService.WAIT_INCREMENT);
				}
				// Read results when they are ready.
				InputStream in = service.getAlignmentResults(requestID, outputProps);
				reader = new BufferedReader(new InputStreamReader(in));
				// Write BLAST output to specified file.
				String sampleName = chromtogram.getSampleName();
				File file = File.createTempFile(sampleName + "_" + requestID, ".txt");
				StatusLineLogger.setInfo(InfoType.MESSAGE, "Saving query results in file " + file.getAbsolutePath());
				writer = new FileWriter(file);
				String line;
				while((line = reader.readLine()) != null) {
					writer.write(line + System.getProperty("line.separator"));
				}
				// Open the report:
				SystemEditor.open(file);
			} catch(Exception e) {
				logger.warn(e);
			} finally {
				// Clean up:
				IOUtils.close(writer);
				IOUtils.close(reader);
				// Delete given alignment results from blast server (optional operation).
				service.sendDeleteRequest(requestID);
			}
		} finally {
			monitor.done();
		}
	}
}
