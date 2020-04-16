/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.comparator;

import java.util.Comparator;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;

public class ReportComparator implements Comparator<ReportSetting> {

	@Override
	public int compare(ReportSetting setting1, ReportSetting setting2) {

		return Integer.compare(setting1.getStartRetentionTime(), setting2.getStartRetentionTime());
	}
}
