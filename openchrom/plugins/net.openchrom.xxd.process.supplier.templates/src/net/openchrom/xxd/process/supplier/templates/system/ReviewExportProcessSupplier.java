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
public class ReviewExportProcessSupplier extends AbstractSystemProcessSettings {

	private static final String ID = "net.openchrom.xxd.process.supplier.templates.system.reviewexport";
	private static final String NAME = "Review Export";
	private static final String DESCRIPTION = "Review export template system settings.";

	@Override
	public Collection<IProcessSupplier<?>> getProcessorSuppliers() {

		return Collections.singleton(new ProcessSupplier(this));
	}

	private static final class ProcessSupplier extends AbstractSystemProcessSupplier<ReviewExportProcessSettings> {

		public ProcessSupplier(IProcessTypeSupplier parent) {

			super(ID, NAME, DESCRIPTION, ReviewExportProcessSettings.class, parent);
		}

		@Override
		public void executeUserSettings(ISystemProcessSettings settings, ProcessExecutionContext context) throws Exception {

			if(settings instanceof ReviewExportProcessSettings) {
				ReviewExportProcessSettings processSettings = (ReviewExportProcessSettings)settings;
				PreferenceSupplier.setExportNumberTracesReview(processSettings.getNumberTraces());
				PreferenceSupplier.setExportOptimizeRangeReview(processSettings.isOptimizeRange());
				PreferenceSupplier.setExportDeltaLeftMillisecondsReview(processSettings.getRetentionTimeDeltaLeft());
				PreferenceSupplier.setExportDeltaRightMillisecondsReview(processSettings.getRetentionTimeDeltaRight());
			}
		}
	}
}