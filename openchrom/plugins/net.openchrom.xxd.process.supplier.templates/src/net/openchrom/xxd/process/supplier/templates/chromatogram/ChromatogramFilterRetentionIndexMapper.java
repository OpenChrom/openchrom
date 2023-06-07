/*******************************************************************************
 * Copyright (c) 2022, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import org.eclipse.chemclipse.chromatogram.filter.core.chromatogram.AbstractChromatogramFilter;
import org.eclipse.chemclipse.chromatogram.filter.result.ChromatogramFilterResult;
import org.eclipse.chemclipse.chromatogram.filter.result.IChromatogramFilterResult;
import org.eclipse.chemclipse.chromatogram.filter.result.ResultStatus;
import org.eclipse.chemclipse.chromatogram.filter.settings.IChromatogramFilterSettings;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;
import org.eclipse.chemclipse.msd.model.exceptions.FilterException;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.MessageType;
import org.eclipse.chemclipse.processing.core.ProcessingMessage;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.FilterSettingsRetentionIndexMapper;

public class ChromatogramFilterRetentionIndexMapper extends AbstractChromatogramFilter {

	@Override
	public IProcessingInfo<IChromatogramFilterResult> applyFilter(IChromatogramSelection<?, ?> chromatogramSelection, IChromatogramFilterSettings chromatogramFilterSettings, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramFilterResult> processingInfo = validate(chromatogramSelection, chromatogramFilterSettings);
		if(!processingInfo.hasErrorMessages()) {
			try {
				applyChromatogramCleanerFilter(chromatogramSelection, monitor);
				processingInfo.addMessage(new ProcessingMessage(MessageType.INFO, "Extract RI", "The retention indices have been extracted."));
				processingInfo.setProcessingResult(new ChromatogramFilterResult(ResultStatus.OK, "Retention indices have been extracted and mapped."));
				chromatogramSelection.getChromatogram().setDirty(true);
			} catch(FilterException e) {
				processingInfo.setProcessingResult(new ChromatogramFilterResult(ResultStatus.EXCEPTION, e.getMessage()));
			}
		}
		chromatogramSelection.getChromatogram().setDirty(true);
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IChromatogramFilterResult> applyFilter(IChromatogramSelection<?, ?> chromatogramSelection, IProgressMonitor monitor) {

		FilterSettingsRetentionIndexMapper filterSettings = PreferenceSupplier.getFilterSettings();
		return applyFilter(chromatogramSelection, filterSettings, monitor);
	}

	private void applyChromatogramCleanerFilter(IChromatogramSelection<?, ?> chromatogramSelection, IProgressMonitor monitor) throws FilterException {

		IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
		RetentionIndexMap retentionIndexMap = new RetentionIndexMap(chromatogram);
		chromatogram.setSeparationColumnIndices(retentionIndexMap.getSeparationColumnIndices());
	}
}