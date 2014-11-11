/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui;

import org.osgi.framework.BundleContext;

import net.chemclipse.support.ui.activator.AbstractActivatorUI;
import net.openchrom.keys.preferences.IBundleProductPreferences;
import net.openchrom.keys.preferences.IProductPreferences;
import net.openchrom.keys.validator.ProductValidator;
import net.openchrom.msd.converter.supplier.pdf.preferences.BundleProductPreferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivatorUI {

	/*
	 * Instance
	 */
	private static Activator plugin;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		/*
		 * The key will be checked each time the plugin gets activated.<br/> The
		 * user can select the trial (if not expired) or full option.
		 */
		IBundleProductPreferences bundleProductPreferences = new BundleProductPreferences();
		IProductPreferences productPreferences = bundleProductPreferences.getProductPreferences();
		ProductValidator.isValidVersion(productPreferences, true, false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static AbstractActivatorUI getDefault() {

		return plugin;
	}
}