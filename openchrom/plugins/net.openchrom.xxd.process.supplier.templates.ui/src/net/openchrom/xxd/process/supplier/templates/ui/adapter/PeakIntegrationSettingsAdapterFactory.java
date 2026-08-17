/*******************************************************************************
 * Copyright (c) 2020, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - enable profiles
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.adapter;

import org.eclipse.chemclipse.ux.extension.ui.methods.SettingsUIProvider;
import org.eclipse.core.runtime.IAdapterFactory;

import net.openchrom.xxd.process.supplier.templates.settings.PeakIntegrationSettings;
import net.openchrom.xxd.process.supplier.templates.ui.swt.TemplatePeakIntegrationEditor;

public class PeakIntegrationSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof PeakIntegrationSettings settings) {
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<PeakIntegrationSettings> createSettingsUIProvider(PeakIntegrationSettings adaptedSettings) {

		return (parent, preferences, _) -> {

			PeakIntegrationSettings userSettings = preferences.getUserSettings();
			return new TemplatePeakIntegrationEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}