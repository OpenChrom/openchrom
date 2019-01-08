/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.HashSet;
import java.util.Set;

public class PeakDetectorListUtil extends AbstractListUtil<PeakDetectorValidator> {

	public static final String EXAMPLE_SINGLE = "10.52 | 10.63 | VV | 103, 104, 108-110";
	public static final String EXAMPLE_MULTIPLE = "10.52 | 10.63 | VV | 103, 104, 108-110 ; 10.71 | 10.76 | BB | 105, 106";
	//
	public static final String SEPARATOR_TRACE_ITEM = ",";
	public static final String SEPARATOR_TRACE_RANGE = "-";
	public static final int TRACE_ERROR = -1;

	public PeakDetectorListUtil() {
		super(new PeakDetectorValidator());
	}

	public Set<Integer> extractTraces(String traces) {

		PeakDetectorValidator validator = getValidator();
		//
		Set<Integer> traceSet = new HashSet<>();
		String[] values = traces.split(SEPARATOR_TRACE_ITEM);
		for(String value : values) {
			if(value.contains(SEPARATOR_TRACE_RANGE)) {
				String[] parts = value.split(SEPARATOR_TRACE_RANGE);
				if(parts.length == 2) {
					int startTrace = validator.getTrace(parts[0]);
					int stopTrace = validator.getTrace(parts[1]);
					if(startTrace != TRACE_ERROR && stopTrace != TRACE_ERROR) {
						if(startTrace <= stopTrace) {
							for(int trace = startTrace; trace <= stopTrace; trace++) {
								traceSet.add(trace);
							}
						}
					}
				}
			} else {
				int trace = validator.getTrace(value);
				if(trace != TRACE_ERROR) {
					traceSet.add(trace);
				}
			}
		}
		return traceSet;
	}
}
