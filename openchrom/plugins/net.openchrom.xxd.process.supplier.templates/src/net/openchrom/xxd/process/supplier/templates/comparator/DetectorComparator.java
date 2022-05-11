/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class DetectorComparator implements Comparator<DetectorSetting> {

	@Override
	public int compare(DetectorSetting setting1, DetectorSetting setting2) {

		return Double.compare(setting1.getPositionStart(), setting2.getPositionStart());
	}
}