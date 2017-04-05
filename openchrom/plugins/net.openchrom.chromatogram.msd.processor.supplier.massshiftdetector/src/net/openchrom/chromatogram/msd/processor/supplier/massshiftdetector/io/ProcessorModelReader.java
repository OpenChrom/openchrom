/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.MassShift_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorModel_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorSettings_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ScanMarker_v1000;

public class ProcessorModelReader {

	public IProcessorModel read(File file, IProgressMonitor monitor) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{ProcessorModel_v1000.class, ScanMarker_v1000.class, MassShift_v1000.class, ProcessorSettings_v1000.class});
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (IProcessorModel)unmarshaller.unmarshal(file);
	}
}
