/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.Set;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.ReportStrategy;

public interface ITemplateValidator extends IValidator<Object> {

	IStatus validateTraces(String traces);

	Set<Integer> extractTraces(String traces);

	PositionDirective parsePositionDirective(String value);

	ReportStrategy parseReportStrategy(String value);

	PeakType parsePeakType(String value);
}