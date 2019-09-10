/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class TracesValidator implements IValidator {

	private static final String SEPARATOR_TRACE_ITEM = AbstractTemplateListUtil.SEPARATOR_TRACE_ITEM;
	private static final String SEPARATOR_TRACE_RANGE = AbstractTemplateListUtil.SEPARATOR_TRACE_RANGE;
	private static final int TRACE_ERROR = AbstractTemplateListUtil.TRACE_ERROR;

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value instanceof String && !"".equals(value)) {
			String traces = (String)value;
			String[] traceValues = traces.split(SEPARATOR_TRACE_ITEM);
			exitloop:
			for(String traceValue : traceValues) {
				if(traceValue.contains(SEPARATOR_TRACE_RANGE)) {
					String[] rangeParts = traceValue.split(SEPARATOR_TRACE_RANGE);
					if(rangeParts.length == 2) {
						int startTrace = getTrace(rangeParts[0]);
						int stopTrace = getTrace(rangeParts[1]);
						if(startTrace == TRACE_ERROR) {
							message = "The start trace value is invalid: " + traceValue;
							break exitloop;
						} else if(stopTrace == TRACE_ERROR) {
							message = "The stop trace value is invalid: " + traceValue;
							break exitloop;
						} else if(stopTrace < startTrace) {
							message = "The start trace value must be lower than the stop value: " + traceValue;
							break exitloop;
						}
					}
				} else {
					int trace = getTrace(traceValue);
					if(trace == TRACE_ERROR) {
						message = "The trace value is invalid: " + traceValue;
						break exitloop;
					}
				}
			}
		}
		//
		if(message != null) {
			return ValidationStatus.error(message);
		} else {
			return ValidationStatus.ok();
		}
	}

	public int getTrace(String value) {

		int trace;
		//
		try {
			trace = Integer.parseInt(value.trim());
			if(trace <= 0) {
				trace = TRACE_ERROR;
			}
		} catch(NumberFormatException e) {
			trace = TRACE_ERROR;
		}
		//
		return trace;
	}
}
