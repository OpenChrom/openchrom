/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.services;

import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.IPeakIdentificationResults;
import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Shell;

public interface IIdentificationService {

	String getName();

	String getDescription();

	String getVersion();

	DataType getDataType();

	IProcessingInfo<IPeakIdentificationResults> identify(List<? extends IPeak> peaks, IIdentifierSettings identifierSettings, IProgressMonitor monitor);

	IIdentifierSettings getIdentifierSettings(Shell shell);
}