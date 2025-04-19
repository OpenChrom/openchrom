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

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;

public class IntegratorComparator implements Comparator<IntegratorSetting> {

	@Override
	public int compare(IntegratorSetting setting1, IntegratorSetting setting2) {

		return setting1.getIdentifier().compareTo(setting2.getIdentifier());
	}
}
