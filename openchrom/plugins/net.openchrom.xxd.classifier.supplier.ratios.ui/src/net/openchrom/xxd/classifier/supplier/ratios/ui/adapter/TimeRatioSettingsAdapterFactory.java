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

import net.openchrom.xxd.classifier.supplier.ratios.settings.TimeRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.ui.swt.TimeRatioListEditor;

public class TimeRatioSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof TimeRatioSettings settings) {
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<TimeRatioSettings> createSettingsUIProvider(TimeRatioSettings adaptedSettings) {

		return (parent, preferences, showProfileToolbar) -> {

			TimeRatioSettings userSettings = preferences.getUserSettings();
			return new TimeRatioListEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}
