/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.support.util.ValueParserSupport;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.ReportStrategy;

public abstract class AbstractTemplateValidator extends ValueParserSupport implements ITemplateValidator {

	public static final Set<PeakType> DETECTOR_TYPES = Collections.unmodifiableSet(EnumSet.of(PeakType.VV, PeakType.BB, PeakType.MM));
	//
	private TracesValidator tracesValidator = new TracesValidator();

	@Override
	public IStatus validateTraces(String traces) {

		return tracesValidator.validate(traces);
	}

	@Override
	public Set<Integer> extractTraces(String traces) {

		IStatus status = tracesValidator.validate(traces);
		return status.isOK() ? tracesValidator.getTraces() : new HashSet<>();
	}

	@Override
	public PositionDirective parsePositionDirective(String value) {

		if(value != null) {
			try {
				return PositionDirective.valueOf(value.toUpperCase());
			} catch(RuntimeException e) {
				// default
			}
		}
		//
		return PositionDirective.RETENTION_TIME_MIN;
	}

	@Override
	public ReportStrategy parseReportStrategy(String value) {

		if(value != null) {
			try {
				return ReportStrategy.valueOf(value.toUpperCase());
			} catch(RuntimeException e) {
				// default
			}
		}
		//
		return ReportStrategy.ALL;
	}

	@Override
	public PeakType parsePeakType(String value) {

		if(value != null) {
			try {
				PeakType peakType = PeakType.valueOf(value.toUpperCase());
				if(DETECTOR_TYPES.contains(peakType)) {
					return peakType;
				}
			} catch(RuntimeException e) {
				// default
			}
		}
		//
		return null; // On purpose
	}
}