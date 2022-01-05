/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.adapter;

import java.io.IOException;

import org.eclipse.chemclipse.processing.supplier.ProcessorPreferences;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.SettingsUIProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.classifier.supplier.ratios.settings.QuantRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.ui.swt.QuantRatioListEditor;

public class QuantRatioSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof QuantRatioSettings) {
			QuantRatioSettings settings = (QuantRatioSettings)adaptableObject;
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<QuantRatioSettings> createSettingsUIProvider(QuantRatioSettings adaptedSettings) {

		return new SettingsUIProvider<QuantRatioSettings>() {

			@Override
			public SettingsUIProvider.SettingsUIControl createUI(Composite parent, ProcessorPreferences<QuantRatioSettings> preferences, boolean showProfileToolbar) throws IOException {

				QuantRatioSettings userSettings = preferences.getUserSettings();
				return new QuantRatioListEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
			}
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}