/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.support.text.ILabel;
import org.eclipse.jface.viewers.LabelProvider;

public class EnumLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {

		if(element instanceof ILabel labelledEnum) {
			return labelledEnum.label();
		}
		return "";
	}
}
