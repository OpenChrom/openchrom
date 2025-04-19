/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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

import java.io.IOException;

import org.eclipse.chemclipse.processing.supplier.IProcessorPreferences;
import org.eclipse.chemclipse.ux.extension.ui.methods.SettingsUIProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.settings.StandardsReferencerSettings;
import net.openchrom.xxd.process.supplier.templates.ui.swt.StandardsReferencerEditor;

public class StandardsReferencerSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof StandardsReferencerSettings settings) {
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<StandardsReferencerSettings> createSettingsUIProvider(StandardsReferencerSettings adaptedSettings) {

		return new SettingsUIProvider<StandardsReferencerSettings>() {

			@Override
			public SettingsUIProvider.SettingsUIControl createUI(Composite parent, IProcessorPreferences<StandardsReferencerSettings> preferences, boolean showProfileToolbar) throws IOException {

				StandardsReferencerSettings userSettings = preferences.getUserSettings();
				return new StandardsReferencerEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
			}
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}