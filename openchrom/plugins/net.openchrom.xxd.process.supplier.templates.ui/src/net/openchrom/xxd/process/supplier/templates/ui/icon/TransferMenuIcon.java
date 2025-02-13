/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.xxd.process.ui.menu.IMenuIcon;
import org.eclipse.swt.graphics.Image;

public class TransferMenuIcon implements IMenuIcon {

	@Override
	public Image getImage() {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAKS, IApplicationImageProvider.SIZE_16x16);
	}
}