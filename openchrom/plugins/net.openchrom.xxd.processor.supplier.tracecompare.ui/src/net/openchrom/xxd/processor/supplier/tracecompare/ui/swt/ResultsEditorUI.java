/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ResultsEditorUI extends Composite {

	private Combo referenceCombo;
	private Composite compositeMain;

	public ResultsEditorUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(Object object) {

		// TODO
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.FILL);
		composite.setLayout(new GridLayout(1, false));
		/*
		 * Elements
		 */
		createReferenceCombo(composite);
		createResultsList(composite);
	}

	private void createReferenceCombo(Composite parent) {

		referenceCombo = new Combo(parent, SWT.READ_ONLY);
		referenceCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		referenceCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				// TODO
			}
		});
	}

	private void createResultsList(Composite parent) {

		compositeMain = new Composite(parent, SWT.NONE);
		compositeMain.setLayoutData(new GridData(GridData.FILL_BOTH));
		compositeMain.setBackground(Colors.DARK_CYAN);
	}
}
