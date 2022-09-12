/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - enable profiles
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.adapter;

import java.io.IOException;

import org.eclipse.chemclipse.processing.supplier.IProcessorPreferences;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.SettingsUIProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.settings.CompensationQuantifierSettings;
import net.openchrom.xxd.process.supplier.templates.ui.swt.CompensationQuantifierEditor;

public class CompensationQuantifierSettingsAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {

		if(adaptableObject instanceof CompensationQuantifierSettings settings) {
			if(adapterType == SettingsUIProvider.class) {
				return adapterType.cast(createSettingsUIProvider(settings));
			}
		}
		return null;
	}

	private static SettingsUIProvider<CompensationQuantifierSettings> createSettingsUIProvider(CompensationQuantifierSettings adaptedSettings) {

		return new SettingsUIProvider<CompensationQuantifierSettings>() {

			@Override
			public SettingsUIProvider.SettingsUIControl createUI(Composite parent, IProcessorPreferences<CompensationQuantifierSettings> preferences, boolean showProfileToolbar) throws IOException {

				CompensationQuantifierSettings userSettings = preferences.getUserSettings();
				return new CompensationQuantifierEditor(parent, preferences, userSettings == null ? adaptedSettings : userSettings);
			}
		};
	}

	@Override
	public Class<?>[] getAdapterList() {

		return new Class<?>[]{SettingsUIProvider.class};
	}
}