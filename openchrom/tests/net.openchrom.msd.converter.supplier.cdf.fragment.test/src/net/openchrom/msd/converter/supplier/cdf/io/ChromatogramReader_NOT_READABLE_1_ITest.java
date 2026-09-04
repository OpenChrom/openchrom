/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.IProcessingMessage;
import org.eclipse.chemclipse.processing.core.MessageType;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import net.openchrom.msd.converter.supplier.cdf.converter.ChromatogramImportConverter;

@Disabled("Does not work in CI.")
public class ChromatogramReader_NOT_READABLE_1_ITest {

	private IChromatogramMSD chromatogram;

	@Test
	public void testImport() {

		File file = new File("testData/NOT_READABLE.CDF");
		assertTrue(file.setReadable(false));
		ChromatogramImportConverter importConverter = new ChromatogramImportConverter();
		IProcessingInfo<IChromatogramMSD> processingInfo = importConverter.convert(file, new NullProgressMonitor());

		assertTrue(!processingInfo.getMessages().isEmpty());
		for(IProcessingMessage message : processingInfo.getMessages()) {
			assertEquals(MessageType.ERROR, message.getMessageType());
			assertEquals("The given file is not readable: " + file.getAbsolutePath(), message.getMessage());
		}

		chromatogram = processingInfo.getProcessingResult();
		assertNull(chromatogram);
		assertTrue(file.setReadable(true));
	}
}
