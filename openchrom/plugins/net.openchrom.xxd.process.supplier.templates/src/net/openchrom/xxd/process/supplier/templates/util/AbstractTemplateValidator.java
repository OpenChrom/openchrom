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

import org.eclipse.chemclipse.support.util.ValueParserSupport;

public abstract class AbstractTemplateValidator extends ValueParserSupport implements ITemplateValidator {

	private static final String SEPARATOR_TRACE_ITEM = AbstractTemplateListUtil.SEPARATOR_TRACE_ITEM;
	private static final String SEPARATOR_TRACE_RANGE = AbstractTemplateListUtil.SEPARATOR_TRACE_RANGE;
	private static final int TRACE_ERROR = AbstractTemplateListUtil.TRACE_ERROR;

	@Override
	public String validateTraces(String traces) {

		String message = null;
		if(!"".equals(traces)) {
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
		return message;
	}

	@Override
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
