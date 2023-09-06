/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
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
import net.openchrom.xxd.base.ui.services.IAlignmentService;

public class AlignmentSupport {

	public static IAlignmentService getAlignmentService(DataType dataType) {

		Object[] services = Activator.getDefault().getAlignmentServices();
		for(Object service : services) {
			if(service instanceof IAlignmentService alignmentService) {
				if(dataType.equals(alignmentService.getDataType())) {
					return alignmentService;
				}
			}
		}
		//
		return null;
	}
}
