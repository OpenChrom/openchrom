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
package net.openchrom.xxd.process.supplier.templates.model;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.xxd.process.supplier.templates.util.AbstractTemplateListUtil;

public interface ISettings {

	default String getFormattedPosition(double position) {

		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
		return decimalFormat.format(position);
	}

	/**
	 * Replaces the separator chars.
	 * 
	 * @param value
	 * @return String
	 */
	default String getFormattedString(String value) {

		return value.replaceAll("\\" + AbstractTemplateListUtil.SEPARATOR_TOKEN, AbstractTemplateListUtil.WHITE_SPACE).replaceAll("\\" + AbstractTemplateListUtil.SEPARATOR_ENTRY, AbstractTemplateListUtil.WHITE_SPACE);
	}

	default void compile(StringBuilder builder, List<String> entries) {

		Iterator<String> iterator = entries.iterator();
		while(iterator.hasNext()) {
			builder.append(iterator.next());
			if(iterator.hasNext()) {
				builder.append(AbstractTemplateListUtil.WHITE_SPACE);
				builder.append(AbstractTemplateListUtil.SEPARATOR_ENTRY);
				builder.append(AbstractTemplateListUtil.WHITE_SPACE);
			}
		}
	}
}