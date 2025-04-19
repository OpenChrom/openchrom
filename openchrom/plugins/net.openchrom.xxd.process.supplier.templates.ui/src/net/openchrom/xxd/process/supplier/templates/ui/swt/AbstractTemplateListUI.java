/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.updates.IUpdateListener;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractTemplateListUI extends ExtendedTableViewer implements ITemplateListUI {

	private IUpdateListener updateListener;

	protected AbstractTemplateListUI(Composite parent, int style) {

		super(parent, style);
	}

	@Override
	public void setUpdateListener(IUpdateListener updateListener) {

		this.updateListener = updateListener;
	}

	@Override
	public void updateContent() {

		if(updateListener != null) {
			updateListener.update();
		}
	}

	@Override
	public void clear() {

		setInput(null);
	}
}