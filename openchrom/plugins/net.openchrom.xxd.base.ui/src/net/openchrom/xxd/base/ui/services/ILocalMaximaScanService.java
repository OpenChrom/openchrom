/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkbenchPreferencePage;

public interface ILocalMaximaScanService {

	String getName();

	String getDescription();

	String getVersion();

	DataType getDataType();

	IProcessingInfo<List<? extends IScan>> calculate(IChromatogramSelection chromatogram, IProgressMonitor monitor);

	Class<? extends IWorkbenchPreferencePage> getPreferencePage();
}
