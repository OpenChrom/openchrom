/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - icons
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.osgi.framework.BundleContext;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.icon.Icon;

public class Activator extends AbstractActivatorUI {

	private static Activator plugin;

	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		initializePreferenceStore(PreferenceSupplier.INSTANCE());
		initializeImageRegistry(getImageHashMap());
	}

	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	public static AbstractActivatorUI getDefault() {

		return plugin;
	}

	private Map<String, String> getImageHashMap() {

		Map<String, String> imageHashMap = new HashMap<>();
		imageHashMap.put(Icon.TEMPLATE, "icons/16x16/template.gif"); // $NON-NLS-1$
		return imageHashMap;
	}
}
