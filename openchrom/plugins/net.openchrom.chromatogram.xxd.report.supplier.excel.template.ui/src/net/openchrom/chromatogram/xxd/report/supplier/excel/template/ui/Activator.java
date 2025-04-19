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
 * Matthias Mailänder - data update support
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.ui;

import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.eclipse.chemclipse.ux.extension.ui.support.DataUpdateSupport;
import org.osgi.framework.BundleContext;

import net.openchrom.chromatogram.xxd.report.supplier.excel.template.preferences.PreferenceSupplier;

public class Activator extends AbstractActivatorUI {

	/*
	 * Instance
	 */
	private static Activator plugin;
	//
	private DataUpdateSupport dataUpdateSupport;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		initializePreferenceStore(PreferenceSupplier.INSTANCE());
		dataUpdateSupport = new DataUpdateSupport(getEventBroker());
		initialize(dataUpdateSupport);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {

		return plugin;
	}

	public DataUpdateSupport getDataUpdateSupport() {

		return dataUpdateSupport;
	}

	private void initialize(DataUpdateSupport dataUpdateSupport) {

		dataUpdateSupport.subscribe(IChemClipseEvents.TOPIC_CHROMATOGRAM_XXD_UPDATE_SELECTION, IChemClipseEvents.EVENT_BROKER_DATA);
	}
}
