/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
import net.openchrom.xxd.base.ui.services.IAlignmentService;

public class AlignmentSupport {

	public static IAlignmentService getAlignmentService(DataType dataType) {

		Object[] services = Activator.getDefault().getAlignmentServices();
		if(services != null) {
			for(Object service : services) {
				if(service instanceof IAlignmentService alignmentService) {
					if(dataType.equals(alignmentService.getDataType())) {
						return alignmentService;
					}
				}
			}
		}
		//
		return null;
	}
}