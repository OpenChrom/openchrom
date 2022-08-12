/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.PeakLibraryInformation;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.btmsp.converter.model.MainSpectraProjection;

public class MainSpectraProjectionReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MainSpectraProjectionReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.setName(file.getName());
		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
			while(zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = zipEntries.nextElement(); // UTF8, no BOM
				if(zipEntry.getName().equals("[Content_Types].xml")) {
					continue;
				}
				MainSpectraProjection mainSpectrum = readSpectrum(zipFile, zipEntry);
				massSpectra.addMassSpectrum(mainSpectrum);
			}
		}
		return massSpectra;
	}

	private MainSpectraProjection readSpectrum(ZipFile zipFile, ZipEntry zipEntry) {

		ILibraryInformation libraryInformation = new PeakLibraryInformation();
		MainSpectraProjection mainSpectrum = new MainSpectraProjection();
		try {
			readMainSpectrum(zipFile, zipEntry, libraryInformation);
			readSample(zipFile, zipEntry, libraryInformation);
			mainSpectrum = readPeakData(zipFile, zipEntry);
			mainSpectrum.setLibraryInformation(libraryInformation);
		} catch(XMLStreamException | AbundanceLimitExceededException
				| IonLimitExceededException | IOException e) {
			logger.warn(e);
		}
		return mainSpectrum;
	}

	private void readMainSpectrum(ZipFile zipFile, ZipEntry zipEntry, ILibraryInformation libraryInformation) throws XMLStreamException, IOException {

		InputStream dataXML = zipFile.getInputStream(zipEntry);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(dataXML);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(bufferedInputStream);
		EventFilter eventFilter = new EventFilterMainSpectrum();
		XMLEventReader filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
		XMLEvent xmlEvent = filteredEventReader.nextEvent();
		Iterator<? extends Attribute> mainSpectrumAttributes = xmlEvent.asStartElement().getAttributes();
		while(mainSpectrumAttributes.hasNext()) {
			Attribute attribute = mainSpectrumAttributes.next();
			String attributeName = attribute.getName().getLocalPart();
			if(attributeName.equals("name")) {
				libraryInformation.setName(attribute.getValue());
			}
		}
	}

	private void readSample(ZipFile zipFile, ZipEntry zipEntry, ILibraryInformation libraryInformation) throws XMLStreamException, IOException {

		InputStream dataXML = zipFile.getInputStream(zipEntry);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(dataXML);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(bufferedInputStream);
		EventFilterSample eventFilter = new EventFilterSample();
		XMLEventReader filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
		if(filteredEventReader.hasNext()) {
			XMLEvent xmlEvent = filteredEventReader.nextEvent();
			Iterator<? extends Attribute> sampleAttributes = xmlEvent.asStartElement().getAttributes();
			while(sampleAttributes.hasNext()) {
				Attribute attribute = sampleAttributes.next();
				String attributeName = attribute.getName().getLocalPart();
				if(attributeName.equals("providedBy")) {
					libraryInformation.setContributor(attribute.getValue());
				} else if(attributeName.equals("comment")) {
					libraryInformation.setComments(attribute.getValue());
				} else if(attributeName.equals("growingConditions")) {
					libraryInformation.setMiscellaneous(attribute.getValue());
				}
			}
		}
	}

	private MainSpectraProjection readPeakData(ZipFile zipFile, ZipEntry zipEntry) throws XMLStreamException, AbundanceLimitExceededException, IonLimitExceededException, IOException {

		InputStream dataXML = zipFile.getInputStream(zipEntry);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(dataXML);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(bufferedInputStream);
		EventFilterPeakData eventFilter = new EventFilterPeakData();
		XMLEventReader filteredEventReader = inputFactory.createFilteredReader(eventReader, eventFilter);
		MainSpectraProjection mainSpectrum = new MainSpectraProjection();
		while(filteredEventReader.hasNext()) {
			XMLEvent xmlEvent = filteredEventReader.nextEvent();
			Iterator<? extends Attribute> peakAttributes = xmlEvent.asStartElement().getAttributes();
			float abundance = 0;
			double ion = 0;
			while(peakAttributes.hasNext()) {
				Attribute attribute = peakAttributes.next();
				String attributeName = attribute.getName().getLocalPart();
				if(attributeName.equals("relativeIntensity")) {
					abundance = Float.parseFloat(attribute.getValue());
				}
				if(attributeName.equals("mass")) {
					ion = Double.parseDouble(attribute.getValue());
				}
				if(abundance > 0 && ion > 0) {
					mainSpectrum.addIon(new Ion(ion, abundance));
					abundance = 0;
					ion = 0;
				}
			}
		}
		return mainSpectrum;
	}
}
