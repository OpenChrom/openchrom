/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.preferences;

import net.openchrom.keys.preferences.IProductPreferences;
import net.openchrom.keys.preferences.ProductPreferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

public class BundleProductPreferences {

	/*
	 * Trial version (only read option). The key generator (php) needs to have
	 * the same product id (converter excel index.php $pluginIdentifier).
	 * Don't forget to disable the write extension and to include the classes.
	 * CONVERTER_ID is the id used in the declaration of the converter extension
	 * (MANIFEST.MF). It is needed to save the chromatogram when the user calls
	 * "save" or CTRL+S on an open chromatogram editor. The editor itself has a
	 * method "doSave(...)" which will be called.
	 */
	/*
	 * Full version (read/write option).
	 */
	private static final int P_TRIAL_DAYS = 30;
	public static final String P_PRODUCT_ID = "net.openchrom.chromatogram.msd.converter.supplier.pdf";
	private static final String PRODUCT_INFO = "Contact me, if you have further questions.";
	public static final String CONVERTER_ID = "net.openchrom.chromatogram.msd.converter.supplier.pdf";
	/*
	 * The keys are needed for the preference store.
	 */
	public static final String P_TRIAL_KEY = "productTrialKey";
	public static final String P_TRIAL_START_DATE_KEY = "productTrialStartDateKey";
	public static final String P_PRODUCT_SERIAL_KEY = "productSerialKey";
	/*
	 * The name, info and web site url will be shown in the product serial key
	 * dialog.
	 */
	private static final String PRODUCT_NAME = "PDF MS Chromatogram Converter";
	private static final String PRODUCT_WEBSITE = "http://www.openchrom.net";

	/**
	 * Returns an instance of the product preferences with the given information
	 * of trial days etc.
	 * 
	 * @return {@link IProductPreferences}
	 */
	public static IProductPreferences getProductPreferences() {

		/*
		 * Use the same product id as the PLUGIN_ID in the ui module of the
		 * plugin (class Activator extends AbstractUIPlugin).<br/> The product
		 * serial key shall also be editable in the preference page of the ui
		 * module. The ui preference page can not write into another preference
		 * store, so a unique product id from the model plugin (e.g. this
		 * P_PRODUCT_ID) can't be used.
		 */
		@SuppressWarnings("deprecation")
		Preferences preferences = new InstanceScope().getNode("net.openchrom.chromatogram.msd.converter.supplier.pdf.ui");
		IProductPreferences productPreferences = new ProductPreferences(preferences, P_TRIAL_KEY, P_TRIAL_START_DATE_KEY, P_PRODUCT_SERIAL_KEY, P_TRIAL_DAYS);
		productPreferences.setProductName(PRODUCT_NAME);
		productPreferences.setProductId(P_PRODUCT_ID);
		productPreferences.setProductInfo(PRODUCT_INFO);
		productPreferences.setProductWebsite(PRODUCT_WEBSITE);
		return productPreferences;
	}
}
