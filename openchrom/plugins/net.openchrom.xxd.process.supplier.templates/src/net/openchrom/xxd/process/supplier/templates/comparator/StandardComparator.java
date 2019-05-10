/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.comparator;

import java.util.Comparator;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;

public class StandardComparator implements Comparator<AssignerStandard> {

	@Override
	public int compare(AssignerStandard setting1, AssignerStandard setting2) {

		int result = Double.compare(setting1.getStartRetentionTime(), setting2.getStartRetentionTime());
		if(result == 0) {
			result = setting1.getName().compareTo(setting2.getName());
		}
		return result;
	}
}
