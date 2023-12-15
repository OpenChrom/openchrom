/*******************************************************************************
 * Copyright (c) 2022, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.system;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.chemclipse.processing.supplier.IProcessSupplier;
import org.eclipse.chemclipse.processing.supplier.IProcessTypeSupplier;
import org.eclipse.chemclipse.processing.supplier.ProcessExecutionContext;
import org.eclipse.chemclipse.processing.system.AbstractSystemProcessSettings;
import org.eclipse.chemclipse.processing.system.AbstractSystemProcessSupplier;
import org.eclipse.chemclipse.processing.system.ISystemProcessSettings;
import org.osgi.service.component.annotations.Component;

import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioExportSettings;

@Component(service = {IProcessTypeSupplier.class})
public class TraceRatioExportProcessSupplier extends AbstractSystemProcessSettings {

	private static final String ID = "net.openchrom.xxd.classifier.supplier.system.traceratioexport";
	private static final String NAME = "Trace Ratio Export";
	private static final String DESCRIPTION = "Trace ratio export system settings.";

	@Override
	public Collection<IProcessSupplier<?>> getProcessorSuppliers() {

		return Collections.singleton(new ProcessSupplier(this));
	}

	private static final class ProcessSupplier extends AbstractSystemProcessSupplier<TraceRatioExportSettings> {

		public ProcessSupplier(IProcessTypeSupplier parent) {

			super(ID, NAME, DESCRIPTION, TraceRatioExportSettings.class, parent);
		}

		@Override
		public void executeUserSettings(ISystemProcessSettings settings, ProcessExecutionContext context) throws Exception {

			if(settings instanceof TraceRatioExportSettings processSettings) {
				PreferenceSupplier.setAllowedDeviationOk(processSettings.getAllowedDeviationOk());
				PreferenceSupplier.setAllowedDeviationWarn(processSettings.getAllowedDeviationWarn());
				PreferenceSupplier.setNumberTraces(processSettings.getNumberTraces());
			}
		}
	}
}
