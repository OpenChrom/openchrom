/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.adapter;

import java.io.IOException;

import org.eclipse.chemclipse.processing.supplier.ProcessorPreferences;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.SettingsUIProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.settings.PeakIdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.ui.swt.TemplatePeakIdentifierEditor;

public class PeakIdentifierSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof PeakIdentifierSettings) {
			PeakIdentifierSettings settings = (PeakIdentifierSettings)adaptableObject;
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<PeakIdentifierSettings> createSettingsUIProvider(PeakIdentifierSettings adaptedSettings) {

		return new SettingsUIProvider<PeakIdentifierSettings>() {

			@Override
			public SettingsUIProvider.SettingsUIControl createUI(Composite parent, ProcessorPreferences<PeakIdentifierSettings> preferences) throws IOException {

				PeakIdentifierSettings userSettings = preferences.getUserSettings();
				return new TemplatePeakIdentifierEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
			}
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}
