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
 * Lorenz Gerber - get all available Deconvolution Services
 *******************************************************************************/
package net.openchrom.xxd.base.ui.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.types.DataType;

import net.openchrom.xxd.base.ui.Activator;
import net.openchrom.xxd.base.ui.services.IDeconvolutionService;

public class DeconvolutionSupport {

	public static IDeconvolutionService getDeconvolutionService(DataType dataType) {

		Object[] services = Activator.getDefault().getDeconvolutionServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof IDeconvolutionService deconvolutionService) {
					if(dataType.equals(deconvolutionService.getDataType())) {
						return deconvolutionService;
					}
				}
			}
		}

		return null;
	}

	public static List<IDeconvolutionService> getDeconvolutionServices(DataType dataType) {

		List<IDeconvolutionService> result = new ArrayList<>();
		Object[] services = Activator.getDefault().getDeconvolutionServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof IDeconvolutionService deconvolutionService) {
					if(dataType.equals(deconvolutionService.getDataType())) {
						result.add(deconvolutionService);
					}
				}
			}
		}

		return result;
	}
}