/*******************************************************************************
 * Copyright (c) 2013, 2014 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.rcp.ui.icons.core.ApplicationImageFactory;
import net.openchrom.rcp.ui.icons.core.IApplicationImage;

public class FormulaListLabelProvider extends LabelProvider implements ITableLabelProvider {

	private DecimalFormat decimalFormat;

	public FormulaListLabelProvider() {

		decimalFormat = new DecimalFormat("0.000000");
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		if(element instanceof NameAndRating) {
			NameAndRating nameAndRating = (NameAndRating)element;
			String text;
			switch(columnIndex) {
				case 0:
					text = nameAndRating.getName();
					break;
				case 1:
					text = decimalFormat.format(nameAndRating.getRating());
					break;
				default:
					text = "n/a";
			}
			return text;
		}
		return null;
	}

	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK, IApplicationImage.SIZE_16x16);
	}
}
