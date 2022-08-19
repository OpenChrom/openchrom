/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
