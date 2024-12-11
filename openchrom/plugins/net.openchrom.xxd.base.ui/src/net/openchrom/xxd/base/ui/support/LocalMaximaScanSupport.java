/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Lorenz Gerber - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.ui.support;

import org.eclipse.chemclipse.model.types.DataType;

import net.openchrom.xxd.base.ui.Activator;
import net.openchrom.xxd.base.ui.services.ILocalMaximaScanService;

public class LocalMaximaScanSupport {

	public static ILocalMaximaScanService getLocalMaximaScanService(DataType dataType) {

		Object[] services = Activator.getDefault().getLocalMaximaScanServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof ILocalMaximaScanService localMaximaScanService) {
					if(dataType.equals(localMaximaScanService.getDataType())) {
						return localMaximaScanService;
					}
				}
			}
		}
		//
		return null;
	}
}
