/*******************************************************************************
 * Copyright (c) 2022, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.adapter;

import org.eclipse.chemclipse.ux.extension.ui.methods.SettingsUIProvider;
import org.eclipse.core.runtime.IAdapterFactory;

import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.ui.swt.TraceRatioListEditor;

public class TraceRatioSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof TraceRatioSettings settings) {
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<TraceRatioSettings> createSettingsUIProvider(TraceRatioSettings adaptedSettings) {

		return (parent, preferences, _) -> {

			TraceRatioSettings userSettings = preferences.getUserSettings();
			return new TraceRatioListEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}
