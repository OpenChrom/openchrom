/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.comparator;

import java.util.Comparator;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;

public class ReferenceComparator implements Comparator<AssignerReference> {

	@Override
	public int compare(AssignerReference setting1, AssignerReference setting2) {

		int result = Double.compare(setting1.getPositionStart(), setting2.getPositionStart());
		if(result == 0) {
			result = setting1.getInternalStandard().compareTo(setting2.getInternalStandard());
		}
		return result;
	}
}
