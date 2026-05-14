/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.base.ui.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.types.DataType;

import net.openchrom.xxd.base.ui.Activator;
import net.openchrom.xxd.base.ui.services.IAnalysisSegmentService;

public class AnalysisSegmentSupport {

	public static IAnalysisSegmentService getAnalysisSegmentService(DataType dataType) {

		Object[] services = Activator.getDefault().getAnalysisSegmentServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof IAnalysisSegmentService analysisSegmentService) {
					if(dataType.equals(analysisSegmentService.getDataType())) {
						return analysisSegmentService;
					}
				}
			}
		}

		return null;
	}

	public static List<IAnalysisSegmentService> getAnalysisSegmentServices(DataType dataType) {

		List<IAnalysisSegmentService> result = new ArrayList<>();

		Object[] services = Activator.getDefault().getAnalysisSegmentServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof IAnalysisSegmentService analysisSegmentService) {
					if(dataType.equals(analysisSegmentService.getDataType())) {
						result.add(analysisSegmentService);
					}
				}
			}
		}

		return result;
	}
}
