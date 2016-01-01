/*******************************************************************************
 * Copyright (c) 2014, 2016 Dr. Philip Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractExtendedPreferenceInitializer;

public class PreferenceInitializer extends AbstractExtendedPreferenceInitializer {

	public PreferenceInitializer() {
		super(PreferenceSupplier.INSTANCE());
	}
}