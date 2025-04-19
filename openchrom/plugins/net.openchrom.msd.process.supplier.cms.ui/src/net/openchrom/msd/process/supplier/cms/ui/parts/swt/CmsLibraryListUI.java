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
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class CmsLibraryListUI extends Composite {

	public CmsLibraryListUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {

		this.setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, true);
		composite.setLayout(gridLayout);
	}
}
