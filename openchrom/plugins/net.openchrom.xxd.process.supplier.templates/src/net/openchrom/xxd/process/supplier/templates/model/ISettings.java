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
package net.openchrom.xxd.process.supplier.templates.model;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.support.text.ValueFormat;

public interface ISettings {

	default String getFormattedRetentionTime(double retentionTimeMinutes) {

		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
		return decimalFormat.format(retentionTimeMinutes);
	}
}
