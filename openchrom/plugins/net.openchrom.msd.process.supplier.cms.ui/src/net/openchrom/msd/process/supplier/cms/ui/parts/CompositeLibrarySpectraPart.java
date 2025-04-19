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
package net.openchrom.msd.process.supplier.cms.ui.parts;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.process.supplier.cms.ui.parts.swt.CompositeLibrarySpectraUI;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

public class CompositeLibrarySpectraPart {

	@Inject
	private Composite parent;
	private CompositeLibrarySpectraUI compositeLibrarySpectraUI;

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		compositeLibrarySpectraUI = new CompositeLibrarySpectraUI(parent, SWT.NONE);
	}

	@PreDestroy
	private void preDestroy() {

	}

	@Focus
	public void setFocus() {

		compositeLibrarySpectraUI.setFocus();
	}
}
