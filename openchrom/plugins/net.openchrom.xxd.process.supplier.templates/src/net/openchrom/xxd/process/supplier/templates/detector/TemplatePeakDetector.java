/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.detector;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.processing.DataCategory;
import org.eclipse.chemclipse.processing.ProcessorCategory;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;

public class TemplatePeakDetector implements ITemplatePeakDetector<Void> {

	private static final PeakSupport PEAK_SUPPORT = new PeakSupport();

	@Override
	public String getName() {

		return "Template Peak Detector";
	}

	@Override
	public Class<Void> getConfigClass() {

		return null;
	}

	@Override
	public <TPT extends ITemplatePeak> Map<TPT, IPeakModel> detectPeaks(IChromatogram<?> chromatogram, Collection<TPT> templates, Void configuration, MessageConsumer messages, IProgressMonitor progressMonitor) {

		LinkedHashMap<TPT, IPeakModel> result = new LinkedHashMap<>();
		for(TPT templatePeak : templates) {
			boolean includeBackground = templatePeak.gePeakType() == PeakType.VV ? true : false;
			IPeak peak = PEAK_SUPPORT.extractPeakByScanRange(chromatogram, templatePeak.getStartScan(), templatePeak.getStopScan(), includeBackground, templatePeak.isOptimizeRange(), templatePeak.getIonTraces().getIonsNominal());
			if(peak != null) {
				result.put(templatePeak, peak.getPeakModel());
			}
		}
		return result;
	}

	@Override
	public DataCategory[] getDataCategories() {

		return new DataCategory[]{DataCategory.MSD, DataCategory.CSD};
	}

	@Override
	public ProcessorCategory[] getProcessorCategories() {

		return ITemplatePeakDetector.super.getProcessorCategories();
	}

	@Override
	public PeakType[] getSupportedPeakTypes() {

		return new PeakType[]{PeakType.VV, PeakType.BB};
	}

	@Override
	public boolean isDefaultFor(PeakType peakType) {

		return peakType == PeakType.VV || peakType == PeakType.BB;
	}
}
