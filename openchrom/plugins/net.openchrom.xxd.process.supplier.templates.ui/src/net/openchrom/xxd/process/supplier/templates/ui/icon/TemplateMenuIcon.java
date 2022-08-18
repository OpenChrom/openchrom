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
package net.openchrom.xxd.process.supplier.templates.ui.icon;

import org.eclipse.chemclipse.xxd.process.ui.menu.IMenuIcon;
import org.eclipse.swt.graphics.Image;

import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class TemplateMenuIcon implements IMenuIcon {

	@Override
	public Image getImage() {

		return Activator.getDefault().getImage(Icon.TEMPLATE);
	}
}