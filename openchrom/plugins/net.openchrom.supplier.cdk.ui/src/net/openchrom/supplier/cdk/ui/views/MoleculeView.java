/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class MoleculeView {

	private Label label;
	@Inject
	private Composite parent;

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		label = new Label(parent, SWT.NONE);
		label.setText("Hallo Marwin");
	}

	@PreDestroy
	private void preDestroy() {

	}

	@Focus
	public void setFocus() {

		label.setFocus();
	}
}
