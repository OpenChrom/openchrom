/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.io;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.Acquisition1DType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.AcquisitionDimensionParameterSetType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.AcquisitionParameterSet1DType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.BinaryDataArrayType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.CVParamType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.FirstDimensionProcessingParameterSetType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.FirstDimensionProcessingParameterSetType.WindowFunction;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.NmrMLType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.ObjectFactory;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.SpectrumListType;
import net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model.ValueWithUnitType;
import net.openchrom.nmr.converter.supplier.nmrml.model.VendorFIDMeasurement;
import net.openchrom.nmr.converter.supplier.nmrml.model.VendorFIDSignal;
import net.openchrom.nmr.processing.supplier.base.settings.ExponentialApodizationSettings;
import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ScanReaderVersion100 {

	private static final Logger logger = Logger.getLogger(ScanReaderVersion100.class);

	public Collection<IComplexSignalMeasurement<?>> read(File file, IProgressMonitor monitor) throws IOException {

		List<IComplexSignalMeasurement<?>> measurements = new ArrayList<>();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName("nmrML");
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			NmrMLType nmrML = (NmrMLType)unmarshaller.unmarshal(nodeList.item(0));
			VendorFIDMeasurement measurement = new VendorFIDMeasurement();
			measurement.setDataName(nmrML.getId());
			measurements.add(measurement);
			Acquisition1DType acquisition1D = nmrML.getAcquisition().getAcquisition1D();
			BinaryDataArrayType fidData = acquisition1D.getFidData();
			ByteBuffer byteBuffer = ByteBuffer.wrap(fidData.getValue());
			if(fidData.isCompressed()) {
				Inflater inflater = new Inflater();
				inflater.setInput(fidData.getValue());
				byte[] byteArray = new byte[fidData.getEncodedLength().intValue()];
				byteBuffer = ByteBuffer.wrap(byteArray, 0, inflater.inflate(byteArray));
			}
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			double[] buffer = null;
			if(fidData.getByteFormat().equals("Complex128")) {
				DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
				buffer = new double[doubleBuffer.capacity()];
				for(int index = 0; index < doubleBuffer.capacity(); index++) {
					buffer[index] = doubleBuffer.get(index);
				}
			} else if(fidData.getByteFormat().equals("Complex64")) {
				FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
				buffer = new double[floatBuffer.capacity()];
				for(int index = 0; index < floatBuffer.capacity(); index++) {
					buffer[index] = floatBuffer.get(index);
				}
			}
			AcquisitionParameterSet1DType acquisitionParameterSet = acquisition1D.getAcquisitionParameterSet();
			AcquisitionDimensionParameterSetType directDimensionParameterSet = acquisitionParameterSet.getDirectDimensionParameterSet();
			//
			double irradiationFrequency = Double.parseDouble(directDimensionParameterSet.getIrradiationFrequency().getValue());
			measurement.setCarrierFrequency(irradiationFrequency);
			//
			double sweepWidth = Double.parseDouble(directDimensionParameterSet.getSweepWidth().getValue());
			measurement.setSpectralWidth(sweepWidth);
			//
			double pulseWidth = Double.parseDouble(directDimensionParameterSet.getPulseWidth().getValue());
			int dataPoints = directDimensionParameterSet.getNumberOfDataPoints().intValue();
			measurement.setNumberOfPoints(dataPoints);
			double timeStep = pulseWidth / dataPoints;
			double time = 0d;
			for(int b = 0; b < buffer.length; b += 2) {
				VendorFIDSignal signal = new VendorFIDSignal(time, buffer[b], buffer[b + 1]);
				measurement.addSignal(signal);
				time += timeStep;
			}
			// TODO: apply those settings
			SpectrumListType spectrumList = nmrML.getSpectrumList();
			if(spectrumList != null) {
				PhaseCorrectionSettings phaseCorrectionSettings = new PhaseCorrectionSettings();
				FirstDimensionProcessingParameterSetType processingParameters = spectrumList.getSpectrum1D().get(0).getFirstDimensionProcessingParameterSet();
				ValueWithUnitType zeroPhaseCorrection = processingParameters.getZeroOrderPhaseCorrection();
				if(zeroPhaseCorrection != null) {
					phaseCorrectionSettings.setZeroOrderPhaseCorrection(Double.parseDouble(zeroPhaseCorrection.getValue()));
				}
				ValueWithUnitType firstOrderPhaseCorrection = processingParameters.getFirstOrderPhaseCorrection();
				if(firstOrderPhaseCorrection != null) {
					phaseCorrectionSettings.setFirstOrderPhaseCorrection(Double.parseDouble(firstOrderPhaseCorrection.getValue()));
				}
				ExponentialApodizationSettings exponentialApodizationSettings = new ExponentialApodizationSettings();
				for(WindowFunction windowFunction : processingParameters.getWindowFunction()) {
					for(CVParamType windowFunctionParameter : windowFunction.getWindowFunctionParameter()) {
						if(windowFunctionParameter.getAccession().equals("NMR:1400097") && windowFunctionParameter.getName().equals("Line Broadening")) {
							exponentialApodizationSettings.setExponentialLineBroadeningFactor(Double.parseDouble(windowFunctionParameter.getValue()));
						}
					}
				}
			}
		} catch(IOException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		} catch(SAXException e) {
			logger.warn(e);
		} catch(DataFormatException e) {
			logger.warn(e);
		}
		if(measurements.isEmpty()) {
			return null;
		}
		return measurements;
	}
}
