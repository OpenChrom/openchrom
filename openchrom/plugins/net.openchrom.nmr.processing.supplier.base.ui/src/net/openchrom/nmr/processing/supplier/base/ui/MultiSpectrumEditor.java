/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.ui;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ExtendedNMROverlayUI;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class MultiSpectrumEditor {

	private ExtendedNMROverlayUI extendedNMROverlayUI;

	@Inject
	public MultiSpectrumEditor() {
	}

	@PostConstruct
	public void postConstruct(Composite parent, EPartService partservice) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		extendedNMROverlayUI = new ExtendedNMROverlayUI(parent, partservice, Activator.getDefault().getPreferenceStore());
	}

	@PreDestroy
	public void preDestroy() {

	}

	@Persist
	public void save() {

	}

	@Focus
	public void onFocus() {

		extendedNMROverlayUI.update();
	}
}