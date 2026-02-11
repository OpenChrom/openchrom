/*******************************************************************************
 * Copyright (c) 2009, 2026 Tasktop Technologies, Polarion Software and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.swt;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

import net.openchrom.installer.model.Overview;

/**
 * @author David Green
 * @author Igor Burilo
 */
public class OverviewToolTip extends ToolTip {

	private final Overview overview;

	public OverviewToolTip(Control control, Overview overview) {

		super(control, ToolTip.RECREATE, true);
		if(overview == null) {
			throw new IllegalArgumentException();
		}
		this.overview = overview;
		setHideOnMouseDown(false); // required for links to work
	}

	@Override
	protected Composite createToolTipContentArea(Event event, Composite parent) {

		GridLayoutFactory.fillDefaults().applyTo(parent);
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(null);
		final int borderWidth = 1;
		final int fixedImageHeight = 240;
		final int heightHint = fixedImageHeight + (borderWidth * 2);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(5, 5).spacing(3, 0).applyTo(container);
		String summary = overview.getSummary();
		Composite summaryContainer = new Composite(container, SWT.NULL);
		summaryContainer.setBackground(null);
		GridLayoutFactory.fillDefaults().applyTo(summaryContainer);
		GridDataFactory gridDataFactory = GridDataFactory.fillDefaults().grab(true, true).span(2 , 1);
		gridDataFactory.applyTo(summaryContainer);
		Label summaryLabel = new Label(summaryContainer, SWT.WRAP);
		summaryLabel.setText(summary);
		summaryLabel.setBackground(null);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.BEGINNING, SWT.BEGINNING).applyTo(summaryLabel);
			// prevent overviews with no image from providing unlimited text.
		Point optimalSize = summaryContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		if(optimalSize.y > (heightHint + 10)) {
			((GridData)summaryContainer.getLayoutData()).heightHint = heightHint;
			container.layout(true);
		}
		return container;
	}

}
