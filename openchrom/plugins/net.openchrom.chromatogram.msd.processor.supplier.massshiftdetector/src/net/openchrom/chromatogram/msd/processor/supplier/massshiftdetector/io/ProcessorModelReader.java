/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.MassShift_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ProcessorModel_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ProcessorSettings_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ScanMarker_v1000;

public class ProcessorModelReader {

	public IProcessorModel read(File file, IProgressMonitor monitor) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(ProcessorModel_v1000.class, ScanMarker_v1000.class, MassShift_v1000.class, ProcessorSettings_v1000.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (IProcessorModel)unmarshaller.unmarshal(file);
	}
}
