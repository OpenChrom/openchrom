/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Lorenz Gerber - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.services;

import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.ranges.TimeRanges;
import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkbenchPreferencePage;

public interface IDeconvolutionBatchService {

	String getName();

	String getDescription();

	String getVersion();

	DataType getDataType();

	IProcessingInfo<?> calculate(List<IChromatogram> chromatograms, TimeRanges timeRanges, IProgressMonitor monitor);

	Class<? extends IWorkbenchPreferencePage> getPreferencePage();
}
