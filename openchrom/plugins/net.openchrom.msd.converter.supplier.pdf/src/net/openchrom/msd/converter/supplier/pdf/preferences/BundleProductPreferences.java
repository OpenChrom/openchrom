/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.preferences;

import net.openchrom.keys.preferences.AbstractBundleProductPreferences;
import net.openchrom.keys.preferences.IBundleProductPreferences;

public class BundleProductPreferences extends AbstractBundleProductPreferences implements IBundleProductPreferences {

	public static final String PREFERENCE_NODE = "net.openchrom.msd.converter.supplier.pdf.ui";
	/*
	 * The key generator (php) needs to have
	 * the same product id (index.php $pluginIdentifier).
	 */
	private static final String PRODUCT_NAME = "PDF MS Chromatogram Converter";
	/*
	 * FULL VERSION
	 */
	private static final String PRODUCT_ID = "net.openchrom.msd.converter.supplier.pdf";
	public static final String CONVERTER_ID = "net.openchrom.msd.converter.supplier.pdf";
	private static final int TRIAL_DAYS = 30;

	public BundleProductPreferences() {

		super(PREFERENCE_NODE, PRODUCT_NAME, PRODUCT_ID, TRIAL_DAYS);
	}
}
