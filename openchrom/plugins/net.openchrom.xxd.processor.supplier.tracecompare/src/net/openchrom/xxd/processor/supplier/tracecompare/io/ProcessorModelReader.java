/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.io;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.TraceModel;

public class ProcessorModelReader {

	public ProcessorModel read(File file, IProgressMonitor monitor) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{ProcessorModel.class, ReferenceModel.class, TraceModel.class});
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (ProcessorModel)unmarshaller.unmarshal(file);
	}
}
