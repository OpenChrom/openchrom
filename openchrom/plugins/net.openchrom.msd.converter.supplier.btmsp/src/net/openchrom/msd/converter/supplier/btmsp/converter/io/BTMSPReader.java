/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.btmsp.converter.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.btmsp.converter.model.BTMSPMassSpectrum;

public class BTMSPReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(BTMSPReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectra massSpectra = new MassSpectra();
		ZipFile zipFile = new ZipFile(file);
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		ZipEntry zipData = null;
		while(zipEntries.hasMoreElements()) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			if(!name.equals("[Content_Types].xml"))
				zipData = zipEntry;
		}
		if(zipData == null) {
			zipFile.close();
			throw new FileIsNotReadableException();
		}
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream dataXML = zipFile.getInputStream(zipData); // UTF8, no BOM
			BufferedInputStream bufferedInputStream = new BufferedInputStream(dataXML);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(bufferedInputStream);
			EventFilter eventFilter = new EventFilterMainSpectrum();
			XMLEventReader filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
			XMLEvent xmlEvent = filteredEventReader.nextEvent();
			@SuppressWarnings("unchecked")
			Iterator<? extends Attribute> mainSpectrumAttributes = xmlEvent.asStartElement().getAttributes();
			while(mainSpectrumAttributes.hasNext()) {
				Attribute attribute = mainSpectrumAttributes.next();
				String attributeName = attribute.getName().getLocalPart();
				if(attributeName.equals("name")) {
					massSpectra.setName(attribute.getValue()); // TODO: add more metadata
				}
			}
			dataXML = zipFile.getInputStream(zipData); // TODO: Do I really need to reload the whole file?
			BufferedInputStream newBufferedInputStream = new BufferedInputStream(dataXML);
			eventReader = inputFactory.createXMLEventReader(newBufferedInputStream);
			eventFilter = new EventFilterPeakData();
			filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
			BTMSPMassSpectrum mainSpectrum = new BTMSPMassSpectrum(); // TODO this only parses the MSP, but the source spectra are saved in here as well
			while(filteredEventReader.hasNext()) {
				xmlEvent = filteredEventReader.nextEvent();
				@SuppressWarnings("unchecked")
				Iterator<? extends Attribute> peakAttributes = xmlEvent.asStartElement().getAttributes();
				float abundance = 0;
				double ion = 0;
				while(peakAttributes.hasNext()) {
					Attribute attribute = peakAttributes.next();
					String attributeName = attribute.getName().getLocalPart();
					if(attributeName.equals("relativeIntensity"))
						abundance = Float.parseFloat(attribute.getValue());
					if(attributeName.equals("mass"))
						ion = Double.parseDouble(attribute.getValue());
					if(abundance > 0 && ion > 0) {
						mainSpectrum.addIon(new Ion(ion, abundance));
						abundance = 0;
						ion = 0;
					}
				}
			}
			massSpectra.addMassSpectrum(mainSpectrum);
		} catch(XMLStreamException | AbundanceLimitExceededException
				| IonLimitExceededException e) {
			logger.warn(e);
		}
		zipFile.close();
		return massSpectra;
	}
}
