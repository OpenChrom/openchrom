/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import net.openchrom.chromatogram.msd.converter.supplier.pdf.preferences.BundleProductPreferences;
import net.openchrom.keys.preferences.IBundleProductPreferences;
import net.openchrom.keys.preferences.IProductPreferences;
import net.openchrom.keys.validator.ProductValidator;

public class Activator implements BundleActivator {

	private static BundleContext context;
	// The plug-in ID
	public static final String PLUGIN_ID = "net.openchrom.chromatogram.msd.converter.supplier.pdf";

	static BundleContext getContext() {

		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {

		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {

		Activator.context = null;
	}

	/**
	 * Returns whether this is a valid version or not.
	 * 
	 * @return boolean
	 */
	public static boolean isValidVersion() {

		IBundleProductPreferences bundleProductPreferences = new BundleProductPreferences();
		IProductPreferences productPreferences = bundleProductPreferences.getProductPreferences();
		return ProductValidator.isValidVersion(productPreferences, false, false);
	}
}
