/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.math3.util.Precision;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.IStandaloneMassSpectrum;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.microbenet.model.Analytes;
import net.openchrom.msd.converter.supplier.microbenet.model.Analytes.Analyte;
import net.openchrom.msd.converter.supplier.microbenet.model.MspMatchResult;
import net.openchrom.msd.converter.supplier.microbenet.model.ObjectFactory;
import net.openchrom.msd.converter.supplier.microbenet.model.Peak;
import net.openchrom.msd.converter.supplier.microbenet.model.Peaklist;
import net.openchrom.msd.converter.supplier.microbenet.model.Peaks;
import net.openchrom.msd.converter.supplier.microbenet.model.ProjectInfo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class MicrobeNetWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	private static final Logger logger = Logger.getLogger(MicrobeNetWriter.class);
	//
	private double scale = 1d;

	@Override
	public void write(File file, IScanMSD scanMSD, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try (FileWriter fileWriter = new FileWriter(file, append)) {
			writeMassSpectrum(fileWriter, scanMSD, monitor);
		}
	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		for(IScanMSD scan : massSpectra.getList()) {
			write(file, scan, append, monitor);
		}
	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD scanMSD, IProgressMonitor monitor) throws IOException {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			//
			MspMatchResult mspMatchResult = new MspMatchResult();
			mspMatchResult.setAnalytes(createAnalytes(scanMSD));
			mspMatchResult.setProjectInfo(createProjectInfo(scanMSD));
			marshaller.marshal(mspMatchResult, fileWriter);
		} catch(JAXBException e) {
			logger.warn(e);
		}
	}

	private Analytes createAnalytes(IScanMSD scanMSD) {

		Analytes analytes = new Analytes();
		analytes.getAnalyte().add(createAnalyte(scanMSD));
		return analytes;
	}

	private Analyte createAnalyte(IScanMSD scanMSD) {

		Analyte analyte = new Analyte();
		analyte.setTypeName("Sample");
		if(scanMSD instanceof IStandaloneMassSpectrum standaloneMassSpectrum) {
			analyte.setTimestamp(createTimestamp(standaloneMassSpectrum));
			analyte.setName(standaloneMassSpectrum.getName());
		}
		analyte.setInternId(UUID.randomUUID().toString());
		analyte.setExternId("Unknown");
		analyte.setPeaklist(createPeaklist(scanMSD));
		return analyte;
	}

	private Peaklist createPeaklist(IScanMSD scanMSD) {

		Peaklist peaklist = new Peaklist();
		scale = scanMSD.getHighestAbundance().getAbundance();
		peaklist.setIntensityScale(scale);
		peaklist.setUserCrafted(true);
		peaklist.setPeaks(createPeaks(scanMSD));
		peaklist.setUuid(UUID.randomUUID().toString());
		return peaklist;
	}

	private Peaks createPeaks(IScanMSD scanMSD) {

		Peaks peaks = new Peaks();
		for(IIon ion : scanMSD.getIons()) {
			peaks.getPeak().add(createPeak(ion));
		}
		return peaks;
	}

	private Peak createPeak(IIon ion) {

		Peak peak = new Peak();
		peak.setIntensity(Precision.round(ion.getAbundance() / scale, 3));
		peak.setMass(Precision.round(ion.getIon(), 2));
		return peak;
	}

	private ProjectInfo createProjectInfo(IScanMSD scanMSD) {

		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setName(createProjectName());
		projectInfo.setTotalSampleNumber(1);
		projectInfo.setUuid(UUID.randomUUID().toString());
		if(scanMSD instanceof IStandaloneMassSpectrum standaloneMassSpectrum) {
			projectInfo.setInstrumentId(standaloneMassSpectrum.getInstrument());
			projectInfo.setComment(standaloneMassSpectrum.getDescription());
			projectInfo.setCreator(standaloneMassSpectrum.getOperator());
			projectInfo.setTimestamp(createTimestamp(standaloneMassSpectrum));
			projectInfo.setValidationResult(false);
		}
		return projectInfo;
	}

	private String createProjectName() {

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd-HHmm");
		return localDateTime.format(formatter);
	}

	private XMLGregorianCalendar createTimestamp(IStandaloneMassSpectrum standaloneMassSpectrum) {

		Date date = standaloneMassSpectrum.getDate();
		if(date == null) {
			return null;
		}
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch(DatatypeConfigurationException e) {
			logger.warn(e);
		}
		return null;
	}
}
