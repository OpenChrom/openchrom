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

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class IdentifierComparator implements Comparator<IdentifierSetting> {

	@Override
	public int compare(IdentifierSetting setting1, IdentifierSetting setting2) {

		return Double.compare(setting1.getPositionStart(), setting2.getPositionStart());
	}
}
