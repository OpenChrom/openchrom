/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.internal.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;

import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.DependencyType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.PlotScaleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SoftwareType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.UnitType;

public class Common {

	private Common() {

		// only static access
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
		seriesSet.setId("PEAK_TABLE");
		seriesSet.setName("Peak Table");
		seriesSet.setLength(chromatogram.getNumberOfPeaks());
		//
		SeriesType numberSeries = new SeriesType();
		numberSeries.setSeriesID("NUMBER");
		numberSeries.setName("Number");
		numberSeries.setSeriesType(ParameterTypeType.INT_32);
		numberSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType idSeries = new SeriesType();
		idSeries.setSeriesID("ID");
		idSeries.setName("ID");
		idSeries.setSeriesType(ParameterTypeType.STRING);
		idSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType groupIdSeries = new SeriesType();
		groupIdSeries.setSeriesID("GROUP_ID");
		groupIdSeries.setName("Group ID");
		groupIdSeries.setSeriesType(ParameterTypeType.STRING);
		groupIdSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType nameSeries = new SeriesType();
		nameSeries.setSeriesID("NAME");
		nameSeries.setName("Name");
		nameSeries.setSeriesType(ParameterTypeType.STRING);
		nameSeries.setPlotScale(PlotScaleType.NONE);
		//
		SeriesType retentionTimeSeries = new SeriesType();
		retentionTimeSeries.setSeriesID("RETENTION_TIME");
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
		adjustedRetentionTimeSeries.setSeriesID("ADJUSTED_RETENTION_TIME");
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
		startTimeSeries.setSeriesID("START_TIME");
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
		result.setName("Peaks");
		result.setSeriesSet(seriesSet);
		return result;
	}
}
