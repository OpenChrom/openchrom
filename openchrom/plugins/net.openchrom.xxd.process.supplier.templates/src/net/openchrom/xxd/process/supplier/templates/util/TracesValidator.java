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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class TracesValidator implements IValidator<Object> {

	private static final String SEPARATOR_TRACE_ITEM = AbstractTemplateListUtil.SEPARATOR_TRACE_ITEM;
	private static final String SEPARATOR_TRACE_RANGE = AbstractTemplateListUtil.SEPARATOR_TRACE_RANGE;
	private static final int TRACE_ERROR = AbstractTemplateListUtil.TRACE_ERROR;
	//
	private Set<Integer> traceSet = new HashSet<>();

	@Override
	public IStatus validate(Object value) {

		traceSet.clear();
		String message = null;
		//
		if(value == null) {
			message = "Invalid input.";
		} else if(value instanceof String && !"".equals(value)) {
			/*
			 * Preparations
			 */
			String traces = ((String)value).trim();
			String[] traceValues;
			if(traces.contains(SEPARATOR_TRACE_ITEM) || traces.contains(SEPARATOR_TRACE_RANGE)) {
				/*
				 * 18, 28, 32, 18 - 32
				 */
				traceValues = traces.split(SEPARATOR_TRACE_ITEM);
			} else {
				/*
				 * 18 28 32
				 */
				traceValues = traces.split(AbstractTemplateListUtil.WHITE_SPACE);
			}
			/*
			 * Parsing
			 */
			exitloop:
			for(String traceValue : traceValues) {
				if(traceValue.contains(SEPARATOR_TRACE_RANGE)) {
					/*
					 * Trace range, e.g.: 32 - 400
					 */
					String[] rangeParts = traceValue.split(SEPARATOR_TRACE_RANGE);
					if(rangeParts.length == 2) {
						int startTrace = getTrace(rangeParts[0]);
						int stopTrace = getTrace(rangeParts[1]);
						//
						if(startTrace == TRACE_ERROR) {
							message = "The start trace value is invalid: " + traceValue;
							break exitloop;
						} else if(stopTrace == TRACE_ERROR) {
							message = "The stop trace value is invalid: " + traceValue;
							break exitloop;
						} else if(stopTrace < startTrace) {
							message = "The start trace value must be lower than the stop value: " + traceValue;
							break exitloop;
						} else {
							for(int trace = startTrace; trace <= stopTrace; trace++) {
								traceSet.add(trace);
							}
						}
					} else {
						message = "Only start - stop is allowed.";
					}
				} else {
					/*
					 * Single trace, e.g.: 18
					 */
					int trace = getTrace(traceValue);
					if(trace == TRACE_ERROR) {
						message = "The trace value is invalid: " + traceValue;
						break exitloop;
					} else {
						traceSet.add(trace);
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

	public Set<Integer> getTraces() {

		return traceSet;
	}

	public int getTrace(String value) {

		int trace = TRACE_ERROR;
		if(value != null) {
			try {
				int val = Integer.parseInt(value.trim());
				if(val > 0) {
					trace = val;
				}
			} catch(NumberFormatException e) {
				//
			}
		}
		//
		return trace;
	}
}
