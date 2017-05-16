/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui;

import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.osgi.framework.BundleContext;

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class Activator extends AbstractActivatorUI {

	private static Activator plugin;

	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		initializePreferenceStore(PreferenceSupplier.INSTANCE());
	}

	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	public static AbstractActivatorUI getDefault() {

		return plugin;
	}
}
