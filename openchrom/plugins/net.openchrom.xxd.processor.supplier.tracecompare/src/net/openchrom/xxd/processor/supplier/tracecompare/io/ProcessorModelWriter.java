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
package net.openchrom.xxd.processor.supplier.tracecompare.io;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ProcessorModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ReferenceModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.SampleModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.TrackModel_v1000;

public class ProcessorModelWriter {

	public void write(File file, IProcessorModel processorModel, IProgressMonitor monitor) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(ProcessorModel_v1000.class, ReferenceModel_v1000.class, SampleModel_v1000.class, TrackModel_v1000.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(processorModel, file);
	}
}
