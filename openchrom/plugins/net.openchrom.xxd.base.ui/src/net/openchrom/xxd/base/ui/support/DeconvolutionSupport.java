/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.support;

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
		//
		return null;
	}
}