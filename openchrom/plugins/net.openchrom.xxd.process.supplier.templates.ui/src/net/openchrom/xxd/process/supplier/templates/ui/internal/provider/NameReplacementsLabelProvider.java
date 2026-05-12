/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacement;

public class NameReplacementsLabelProvider extends ColumnLabelProvider implements ITableLabelProvider {

	public static final String NAME = "Name";
	public static final String SYNONYM = "Synonym";

	public static final String[] TITLES = { //
			NAME, //
			SYNONYM //
	};

	public static final int[] BOUNDS = { //
			200, //
			200 //
	};

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof NameReplacement setting) {
			switch(columnIndex) {
				case 0:
					text = setting.getName();
					break;
				case 1:
					text = setting.getSynonym();
					break;
				default:
					text = "n.v.";
			}
		}
		return text;
	}

	@Override
	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_TAG, IApplicationImageProvider.SIZE_16x16);
	}
}