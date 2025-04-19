/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
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
public class DetectorExportProcessSupplier extends AbstractSystemProcessSettings {

	private static final String ID = "net.openchrom.xxd.process.supplier.templates.system.peakdetectorexport";
	private static final String NAME = "Peak Detector Export";
	private static final String DESCRIPTION = "Peak detector export template system settings.";

	@Override
	public Collection<IProcessSupplier<?>> getProcessorSuppliers() {

		return Collections.singleton(new ProcessSupplier(this));
	}

	private static final class ProcessSupplier extends AbstractSystemProcessSupplier<DetectorExportProcessSettings> {

		public ProcessSupplier(IProcessTypeSupplier parent) {

			super(ID, NAME, DESCRIPTION, DetectorExportProcessSettings.class, parent);
		}

		@Override
		public void executeUserSettings(ISystemProcessSettings settings, ProcessExecutionContext context) throws Exception {

			if(settings instanceof DetectorExportProcessSettings processSettings) {
				PreferenceSupplier.setExportNumberTracesDetector(processSettings.getNumberTraces());
				PreferenceSupplier.setExportPeakTypeDetector(processSettings.getPeakType());
				PreferenceSupplier.setExportOptimizeRangeDetector(processSettings.isOptimizeRange());
				PreferenceSupplier.setExportDeltaLeftPositionDetector(processSettings.getPositionDeltaLeft());
				PreferenceSupplier.setExportDeltaRightPositionDetector(processSettings.getPositionDeltaRight());
				PreferenceSupplier.setExportPositionDirectiveDetector(processSettings.getPositionDirective());
			}
		}
	}
}