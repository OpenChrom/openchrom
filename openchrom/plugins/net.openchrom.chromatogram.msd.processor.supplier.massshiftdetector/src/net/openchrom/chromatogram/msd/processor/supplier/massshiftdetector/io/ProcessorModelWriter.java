/*******************************************************************************
 * Copyright (c) 2017, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.io;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.MassShift_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ProcessorModel_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ProcessorSettings_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.v1000.ScanMarker_v1000;

public class ProcessorModelWriter {

	public void write(File file, IProcessorModel processorModel, IProgressMonitor monitor) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{ProcessorModel_v1000.class, ScanMarker_v1000.class, MassShift_v1000.class, ProcessorSettings_v1000.class});
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(processorModel, file);
	}
}
