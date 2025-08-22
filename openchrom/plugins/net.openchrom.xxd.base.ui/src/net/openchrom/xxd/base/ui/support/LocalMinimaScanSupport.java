/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.types.DataType;

import net.openchrom.xxd.base.ui.Activator;
import net.openchrom.xxd.base.ui.services.ILocalMinimaScanService;

public class LocalMinimaScanSupport {

	public static ILocalMinimaScanService getLocalMinimaScanService(DataType dataType) {

		Object[] services = Activator.getDefault().getLocalMinimaScanServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof ILocalMinimaScanService localMinimaScanService) {
					if(dataType.equals(localMinimaScanService.getDataType())) {
						return localMinimaScanService;
					}
				}
			}
		}

		return null;
	}
}