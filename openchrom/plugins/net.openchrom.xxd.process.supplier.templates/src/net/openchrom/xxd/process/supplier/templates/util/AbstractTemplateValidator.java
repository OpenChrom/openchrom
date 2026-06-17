/*******************************************************************************
 * Copyright (c) 2019, 2026 Lablicate GmbH.
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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.support.util.ValueParserSupport;
import org.eclipse.chemclipse.support.validators.TraceValidator;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.ReportStrategy;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public abstract class AbstractTemplateValidator extends ValueParserSupport implements ITemplateValidator {

	private TraceValidator traceValidator = new TraceValidator();

	@Override
	public IStatus validateTraces(String traces) {

		return traceValidator.validate(traces);
	}

	@Override
	public Set<Integer> extractTraces(String traces) {

		IStatus status = traceValidator.validate(traces);
		return status.isOK() ? new HashSet<>(traceValidator.getTracesAsInteger()) : new HashSet<>();
	}

	@Override
	public PositionDirective parsePositionDirective(String value) {

		if(value != null && !value.isBlank()) {
			try {
				return PositionDirective.valueOf(value.toUpperCase());
			} catch(RuntimeException e) {
			}
		}

		return PositionDirective.RETENTION_TIME_MIN;
	}

	@Override
	public ReportStrategy parseReportStrategy(String value) {

		if(value != null) {
			try {
				return ReportStrategy.valueOf(value.toUpperCase());
			} catch(RuntimeException e) {
			}
		}

		return ReportStrategy.ALL;
	}

	@Override
	public PeakType parsePeakType(String value) {

		if(value != null) {
			try {
				PeakType peakType = PeakType.valueOf(value.toUpperCase());
				if(PreferenceSupplier.DETECTOR_TYPES.contains(peakType)) {
					return peakType;
				}
			} catch(RuntimeException e) {
			}
		}

		return null; // On purpose
	}

	protected String validateRetentionTime(String referenceIdentifier, double positionStart, double positionStop) {

		if(positionStart >= 0 && positionStop >= 0) {
			return validateRetentionTime(positionStart, positionStop);
		} else {
			if(referenceIdentifier.isEmpty()) {
				return "A negative start/stop position is only allowed if the reference identifier is set.";
			}
		}

		return null;
	}

	private String validateRetentionTime(double positionStart, double positionStop) {

		if(positionStop <= positionStart) {
			return "The stop position must be greater than the start position.";
		}

		return null;
	}
}