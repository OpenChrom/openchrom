/*******************************************************************************
 * Copyright (c) 2012, 2025 Lablicate GmbH.
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
package net.openchrom.rcp.compilation.community.ui.splash;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.branding.IProductConstants;
import org.eclipse.ui.splash.BasicSplashHandler;

public class EnhancedSplashHandler extends BasicSplashHandler {

	@Override
	public void init(Shell splash) {

		super.init(splash);
		String progressString = null;
		String messageString = null;
		/*
		 * Try to get the progress bar and message updater.
		 */
		IProduct product = Platform.getProduct();
		if(product != null) {
			progressString = product.getProperty(IProductConstants.STARTUP_PROGRESS_RECT);
			messageString = product.getProperty(IProductConstants.STARTUP_MESSAGE_RECT);
		}
		Rectangle progressRect = StringConverter.asRectangle(progressString, new Rectangle(5, 275, 445, 15));
		setProgressRect(progressRect);
		Rectangle messageRect = StringConverter.asRectangle(messageString, new Rectangle(7, 252, 445, 20));
		setMessageRect(messageRect);
	}
}
