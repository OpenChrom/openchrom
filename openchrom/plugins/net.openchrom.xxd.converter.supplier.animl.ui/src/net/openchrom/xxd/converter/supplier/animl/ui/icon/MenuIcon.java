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
 * Matthias Mailänder - support process method resume option
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.ui.icon;

import org.eclipse.chemclipse.xxd.process.ui.menu.IMenuIcon;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.converter.supplier.animl.ui.Activator;

public class MenuIcon implements IMenuIcon {

	@Override
	public Image getImage() {

		return Activator.getDefault().getImage(Activator.ICON_IMAGE);
	}
}