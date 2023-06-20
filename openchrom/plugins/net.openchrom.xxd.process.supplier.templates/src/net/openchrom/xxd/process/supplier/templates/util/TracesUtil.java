/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 * Christoph Läubrich - adjust naming of methods
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.core.runtime.IStatus;

public class TracesUtil {

	private static final Logger logger = Logger.getLogger(TracesUtil.class);

	/**
	 * If traces is empty: true
	 * MSD, WSD is checked
	 * CSD always true
	 *
	 * @return boolean
	 */
	public boolean isTraceMatch(IPeak peak, String traces) {

		boolean traceMatch = false;
		/*
		 * If traces is empty, then TIC: always true.
		 */
		if(peak != null) {
			if(traces == null || traces.isEmpty()) {
				traceMatch = true;
			} else {
				if(peak instanceof IPeakCSD) {
					traceMatch = true;
				} else {
					/*
					 * MSD, WSD
					 */
					IScan scan = peak.getPeakModel().getPeakMaximum();
					Set<Integer> traceSet = extractTraces(traces);
					int detected = 0;
					for(int trace : traceSet) {
						if(isTraceContained(scan, trace)) {
							detected++;
						}
					}
					//
					if(detected == traceSet.size()) {
						traceMatch = true;
					}
				}
			}
		}
		//
		return traceMatch;
	}

	private boolean isTraceContained(IScan scan, int trace) {

		boolean isTraceContained = false;
		//
		if(scan instanceof IScanMSD) {
			try {
				IScanMSD scanMSD = (IScanMSD)scan;
				IIon ion = scanMSD.getIon(trace);
				if(ion != null) {
					isTraceContained = true;
				}
			} catch(Exception e) {
				logger.warn(e);
			}
		} else if(scan instanceof IScanWSD) {
			IScanWSD scanWSD = (IScanWSD)scan;
			Optional<IScanSignalWSD> optional = scanWSD.getScanSignal((float)trace);
			isTraceContained = optional.isPresent();
		}
		//
		return isTraceContained;
	}

	private Set<Integer> extractTraces(String traces) {

		TracesValidator tracesValidator = new TracesValidator();
		IStatus status = tracesValidator.validate(traces);
		Set<Integer> traceSet = status.isOK() ? tracesValidator.getTraces() : new HashSet<>();
		return traceSet;
	}
}