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
package net.openchrom.xxd.converter.supplier.animl.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.history.EditHistory;
import org.eclipse.chemclipse.support.history.EditInformation;
import org.eclipse.chemclipse.support.history.IEditHistory;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.DependencyType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ObjectFactory;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.PlotScaleType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SIUnitType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SoftwareType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.UnitType;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class Common {

	private Common() {

		// only static access
	}

	public static AnIMLType getAnIML(File file) throws SAXException, IOException, JAXBException, ParserConfigurationException {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		NodeList topNode = document.getElementsByTagName(AnIMLType.NODE_NAME);
		//
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (AnIMLType)unmarshaller.unmarshal(topNode.item(0));
	}

	public static int getTimeMultiplicator(UnitType unit) {

		int multiplicator = 1;
		if(unit.getLabel().equals("ms")) {
			multiplicator = 1;
		}
		if(unit.getLabel().equals("s")) {
			multiplicator = 1000;
		}
		if(unit.getLabel().equals("min")) {
			multiplicator = 60 * 1000;
		}
		return multiplicator;
	}

	public static Collection<IEditInformation> readAuditTrail(AnIMLType animl) {

		IEditHistory editHistory = new EditHistory();
		for(AuditTrailEntryType entry : animl.getAuditTrailEntrySet().getAuditTrailEntry()) {
			Date date = entry.getTimestamp().toGregorianCalendar().getTime();
			String description = entry.getAction().value();
			if(entry.getReason() != null && !entry.getReason().isEmpty()) {
				description += ", " + entry.getReason();
			}
			editHistory.add(new EditInformation(date, description, entry.getAuthor().getName()));
		}
		return editHistory;
	}

	public static SoftwareType createSoftware() {

		SoftwareType software = new SoftwareType();
		software.setName("OpenChrom");
		IProduct product = Platform.getProduct();
		if(product != null) {
			Version version = product.getDefiningBundle().getVersion();
			software.setVersion(version.toString());
		}
		software.setManufacturer("Lablicate GmbH");
		software.setOperatingSystem(System.getProperty("os.name"));
		return software;
	}

	public static ResultType createPeaks(IChromatogram<?> chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setName("Peak Table");
		seriesSet.setLength(chromatogram.getNumberOfPeaks());
		//
		SeriesType numberSeries = new SeriesType();
		numberSeries.setName("Number");
		numberSeries.setSeriesType(ParameterTypeType.INT_32);
		numberSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType idSeries = new SeriesType();
		idSeries.setName("ID");
		idSeries.setSeriesType(ParameterTypeType.STRING);
		idSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType groupIdSeries = new SeriesType();
		groupIdSeries.setName("Group ID");
		groupIdSeries.setSeriesType(ParameterTypeType.STRING);
		groupIdSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType nameSeries = new SeriesType();
		nameSeries.setName("Name");
		nameSeries.setSeriesType(ParameterTypeType.STRING);
		nameSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType retentionTimeSeries = new SeriesType();
		retentionTimeSeries.setName("Retention Time");
		UnitType retentionTimeUnit = new UnitType();
		retentionTimeUnit.setLabel("Time");
		retentionTimeUnit.setQuantity("min");
		retentionTimeSeries.setUnit(retentionTimeUnit);
		retentionTimeSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		retentionTimeSeries.setDependency(DependencyType.INDEPENDENT);
		retentionTimeSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType adjustedRetentionTimeSeries = new SeriesType();
		adjustedRetentionTimeSeries.setName("Adjusted Retention Time");
		UnitType adjustedRetentionTimeUnit = new UnitType();
		adjustedRetentionTimeUnit.setLabel("Time");
		adjustedRetentionTimeUnit.setQuantity("min");
		adjustedRetentionTimeSeries.setUnit(adjustedRetentionTimeUnit);
		adjustedRetentionTimeSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		adjustedRetentionTimeSeries.setDependency(DependencyType.DEPENDENT);
		adjustedRetentionTimeSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType startTimeSeries = new SeriesType();
		startTimeSeries.setName("Start Time");
		UnitType startTimeUnit = new UnitType();
		startTimeUnit.setLabel("Time");
		startTimeUnit.setQuantity("min");
		startTimeSeries.setUnit(startTimeUnit);
		startTimeSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		startTimeSeries.setDependency(DependencyType.DEPENDENT);
		startTimeSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType endTimeSeries = new SeriesType();
		endTimeSeries.setName("End Time");
		UnitType endTimeUnit = new UnitType();
		endTimeUnit.setLabel("Time");
		endTimeUnit.setQuantity("min");
		endTimeSeries.setUnit(endTimeUnit);
		endTimeSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		endTimeSeries.setDependency(DependencyType.DEPENDENT);
		endTimeSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType peakHeightSeries = new SeriesType();
		peakHeightSeries.setName("Value");
		UnitType peakHeightUnit = new UnitType();
		peakHeightUnit.setLabel("Arbitrary");
		peakHeightUnit.setQuantity("arbitrary");
		peakHeightSeries.setUnit(peakHeightUnit);
		peakHeightSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		peakHeightSeries.setDependency(DependencyType.DEPENDENT);
		peakHeightSeries.setPlotScale(PlotScaleType.NONE);
		//
		IndividualValueSetType numbers = new IndividualValueSetType();
		IndividualValueSetType ids = new IndividualValueSetType();
		IndividualValueSetType groups = new IndividualValueSetType();
		IndividualValueSetType names = new IndividualValueSetType();
		IndividualValueSetType retentionTimes = new IndividualValueSetType();
		IndividualValueSetType adjustedRetentionTimes = new IndividualValueSetType();
		IndividualValueSetType startTimes = new IndividualValueSetType();
		IndividualValueSetType endTimes = new IndividualValueSetType();
		IndividualValueSetType peakHeights = new IndividualValueSetType();
		int i = 0;
		for(Object object : chromatogram.getPeaks()) {
			if(object instanceof IChromatogramPeak peak) {
				numbers.getI().add(i);
				Set<IIdentificationTarget> targets = peak.getTargets();
				if(!targets.isEmpty()) {
					IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC);
					List<IIdentificationTarget> peakTargetList = new ArrayList<>(targets);
					Collections.sort(peakTargetList, identificationTargetComparator);
					Optional<IIdentificationTarget> target = peakTargetList.stream().findFirst();
					if(target.isPresent()) {
						ILibraryInformation libraryInformation = target.get().getLibraryInformation();
						ids.getS().add(libraryInformation.getName());
						Optional<String> classifier = libraryInformation.getClassifier().stream().findFirst();
						if(classifier.isPresent()) {
							groups.getS().add(classifier.get());
						}
						names.getS().add(target.get().getLibraryInformation().getName());
					}
				}
				retentionTimes.getD().add(peak.getX() / 1000 / 60);
				float relativeRetentionTime = chromatogram.getScan(peak.getScanMax()).getRelativeRetentionTime() / 1000f / 60f;
				if(relativeRetentionTime > 0) {
					adjustedRetentionTimes.getF().add(relativeRetentionTime);
				}
				startTimes.getF().add(peak.getPeakModel().getStartRetentionTime() / 1000f / 60f);
				endTimes.getF().add(peak.getPeakModel().getStopRetentionTime() / 1000f / 60f);
				peakHeights.getF().add(peak.getPeakModel().getPeakMaximum().getTotalSignal());
				i++;
			}
		}
		numberSeries.getIndividualValueSet().add(numbers);
		idSeries.getIndividualValueSet().add(ids);
		groupIdSeries.getIndividualValueSet().add(groups);
		nameSeries.getIndividualValueSet().add(names);
		retentionTimeSeries.getIndividualValueSet().add(retentionTimes);
		adjustedRetentionTimeSeries.getIndividualValueSet().add(adjustedRetentionTimes);
		startTimeSeries.getIndividualValueSet().add(startTimes);
		endTimeSeries.getIndividualValueSet().add(endTimes);
		peakHeightSeries.getIndividualValueSet().add(peakHeights);
		//
		seriesSet.getSeries().add(numberSeries);
		seriesSet.getSeries().add(idSeries);
		seriesSet.getSeries().add(groupIdSeries);
		seriesSet.getSeries().add(nameSeries);
		seriesSet.getSeries().add(retentionTimeSeries);
		seriesSet.getSeries().add(adjustedRetentionTimeSeries);
		seriesSet.getSeries().add(startTimeSeries);
		seriesSet.getSeries().add(endTimeSeries);
		seriesSet.getSeries().add(peakHeightSeries);
		//
		ResultType result = new ResultType();
		result.setName("Peak Table");
		result.setSeriesSet(seriesSet);
		return result;
	}

	public static UnitType createMilisecond() {

		UnitType unit = new UnitType();
		unit.setLabel("ms");
		unit.setQuantity("Time");
		unit.getSIUnit().add(createMilisecondSI());
		return unit;
	}

	private static SIUnitType createMilisecondSI() {

		SIUnitType si = new SIUnitType();
		si.setExponent(-3d);
		si.setValue("s");
		return si;
	}
}
