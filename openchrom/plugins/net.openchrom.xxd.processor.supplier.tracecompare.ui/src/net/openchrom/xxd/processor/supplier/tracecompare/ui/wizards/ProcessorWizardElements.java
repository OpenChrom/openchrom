/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.wizards;

import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ProcessorModel_v1000;

public class ProcessorWizardElements extends ChromatogramWizardElements implements IProcessorWizardElements {

	private IProcessorModel processorModel;

	public ProcessorWizardElements() {
		processorModel = new ProcessorModel_v1000();
	}

	@Override
	public IProcessorModel getProcessorModel() {

		return processorModel;
	}
}
