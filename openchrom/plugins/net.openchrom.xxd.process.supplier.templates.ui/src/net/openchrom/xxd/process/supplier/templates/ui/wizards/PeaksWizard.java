/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.jface.wizard.Wizard;

public class PeaksWizard extends Wizard {

	private PeaksPage page;
	private List<IPeak> peaks;

	public PeaksWizard(List<IPeak> peaks) {
		setNeedsProgressMonitor(true);
		setWindowTitle("Manual Peak Detector UI");
		this.peaks = peaks;
	}

	@Override
	public void addPages() {

		System.out.println(peaks);
		page = new PeaksPage("Peaks");
		addPage(page);
	}

	@Override
	public boolean performFinish() {

		return true;
	}
}
