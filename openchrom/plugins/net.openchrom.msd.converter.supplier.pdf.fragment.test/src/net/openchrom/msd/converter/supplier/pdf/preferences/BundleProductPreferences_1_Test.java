/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import net.openchrom.keys.preferences.IProductPreferences;
import net.openchrom.keys.validator.ProductValidator;

import junit.framework.TestCase;

public class BundleProductPreferences_1_Test extends TestCase {

	private BundleProductPreferences bundleProductPreferences;
	private IProductPreferences productPreferences;
	private Preferences preferences;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		//
		bundleProductPreferences = new BundleProductPreferences();
		productPreferences = bundleProductPreferences.getProductPreferences();
		preferences = InstanceScope.INSTANCE.getNode(productPreferences.getProductId());
		preferences.clear();
		preferences.flush();
		//
		productPreferences.setProductSerial("8klat6Tz-AaIn2o6A-lal2WFMZ");
		productPreferences.setTrial(false);
	}

	@Override
	protected void tearDown() throws Exception {

		preferences.clear();
		preferences.flush();
		super.tearDown();
	}

	public void test1() {

		assertTrue(ProductValidator.isValidVersion(productPreferences, false, false));
	}
}
