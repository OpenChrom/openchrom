/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.system;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.chemclipse.processing.supplier.IProcessSupplier;
import org.eclipse.chemclipse.processing.supplier.IProcessTypeSupplier;
import org.eclipse.chemclipse.processing.supplier.ProcessExecutionContext;
import org.eclipse.chemclipse.processing.system.AbstractSystemProcessSettings;
import org.eclipse.chemclipse.processing.system.AbstractSystemProcessSupplier;
import org.eclipse.chemclipse.processing.system.ISystemProcessSettings;
import org.osgi.service.component.annotations.Component;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

@Component(service = {IProcessTypeSupplier.class})
public class IdentifierExportProcessSupplier extends AbstractSystemProcessSettings {

	private static final String ID = "net.openchrom.xxd.process.supplier.templates.system.peakidentifierexport";
	private static final String NAME = "Peak Identifier Export";
	private static final String DESCRIPTION = "Peak identifier export template system settings.";

	@Override
	public Collection<IProcessSupplier<?>> getProcessorSuppliers() {

		return Collections.singleton(new ProcessSupplier(this));
	}

	private static final class ProcessSupplier extends AbstractSystemProcessSupplier<IdentifierExportProcessSettings> {

		public ProcessSupplier(IProcessTypeSupplier parent) {

			super(ID, NAME, DESCRIPTION, IdentifierExportProcessSettings.class, parent);
		}

		@Override
		public void executeUserSettings(ISystemProcessSettings settings, ProcessExecutionContext context) throws Exception {

			if(settings instanceof IdentifierExportProcessSettings) {
				IdentifierExportProcessSettings processSettings = (IdentifierExportProcessSettings)settings;
				PreferenceSupplier.setExportNumberTracesIdentifier(processSettings.getNumberTraces());
				PreferenceSupplier.setExportDeltaLeftMillisecondsIdentifier(processSettings.getRetentionTimeDeltaLeft());
				PreferenceSupplier.setExportDeltaRightMillisecondsIdentifier(processSettings.getRetentionTimeDeltaRight());
			}
		}
	}
}