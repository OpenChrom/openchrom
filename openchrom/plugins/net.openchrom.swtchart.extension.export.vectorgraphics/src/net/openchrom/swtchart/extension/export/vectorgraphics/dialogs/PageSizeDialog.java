/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.dialogs;

import org.eclipse.chemclipse.support.ui.provider.AbstractLabelProvider;
import org.eclipse.chemclipse.support.ui.swt.EnhancedComboViewer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.preferences.PreferenceSupplier;

public class PageSizeDialog extends Dialog {

	private PageSizeOption pageSizeOption = PreferenceSupplier.getPageSizeOption();

	public PageSizeDialog(Shell parentShell) {

		super(parentShell);
	}

	public PageSizeOption getPageSizeOption() {

		return pageSizeOption;
	}

	@Override
	protected void configureShell(Shell shell) {

		super.configureShell(shell);
		shell.setText("Page Size");
	}

	@Override
	protected boolean isResizable() {

		return false;
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite composite = (Composite)super.createDialogArea(parent);
		composite.setLayout(new FillLayout());
		//
		createComboViewer(composite);
		//
		return composite;
	}

	private void createComboViewer(Composite parent) {

		ComboViewer comboViewer = new EnhancedComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof PageSizeOption pageSizeOption) {
					return pageSizeOption.label();
				}
				return null;
			}
		});
		//
		combo.setToolTipText("Select a page size.");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof PageSizeOption option) {
					pageSizeOption = option;
				}
			}
		});
		//
		comboViewer.setInput(PageSizeOption.values());
		comboViewer.setSelection(new StructuredSelection(pageSizeOption));
	}
}
