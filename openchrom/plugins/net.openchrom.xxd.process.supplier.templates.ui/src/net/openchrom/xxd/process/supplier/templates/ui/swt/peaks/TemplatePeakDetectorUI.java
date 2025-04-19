/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;

public class TemplatePeakDetectorUI extends Composite {

	private PeakDetectorControl peakDetectorControl;

	public TemplatePeakDetectorUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setInput(ProcessDetectorSettings peakProcessSettings) {

		peakDetectorControl.setInput(peakProcessSettings);
	}

	private void createControl() {

		setLayout(new FillLayout());
		peakDetectorControl = new PeakDetectorControl(this, SWT.NONE);
	}
}
