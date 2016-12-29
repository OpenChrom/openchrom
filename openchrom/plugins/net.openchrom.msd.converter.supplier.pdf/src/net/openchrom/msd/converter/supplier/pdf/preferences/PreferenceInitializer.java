/*******************************************************************************
 * Copyright (c) 2014, 2016 Lablicate GmbH.
 * 
 * All rights reserved.
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractExtendedPreferenceInitializer;

public class PreferenceInitializer extends AbstractExtendedPreferenceInitializer {

	public PreferenceInitializer() {
		super(PreferenceSupplier.INSTANCE());
	}
}