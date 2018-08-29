/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.editors;

import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;

import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class PeakDetectorListEditor extends ListEditor {

	public PeakDetectorListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		initialize(parent);
	}

	@Override
	protected String createList(String[] items) {

		PeakDetectorListUtil util = new PeakDetectorListUtil();
		return util.createList(items);
	}

	@Override
	protected String getNewInputObject() {

		List list = getList();
		InputDialog dialog = new InputDialog(getShell(), "Peak Detector Template", "You can create a new peak detector range here.", "10.52 | 10.63 | VV", new PeakDetectorInputValidator(list));
		dialog.create();
		if(dialog.open() == Dialog.OK) {
			String range = dialog.getValue();
			return addRange(range, list);
		}
		return null;
	}

	private void initialize(Composite parent) {

		Composite composite = getButtonBoxControl(parent);
		Button button = new Button(composite, SWT.PUSH);
		button.setText("Clear Ranges");
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button, IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				List list = getList();
				if(list != null) {
					MessageBox messageBox = new MessageBox(e.widget.getDisplay().getActiveShell(), SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setText("Delete Range(s)");
					messageBox.setMessage("Would you like to delete the selected range(s) from the list?");
					int decision = messageBox.open();
					if(decision == SWT.YES) {
						list.removeAll();
					}
				}
			}
		});
	}

	private String addRange(String range, List list) {

		String[] items = list.getItems();
		if(!itemExistsInList(range, items)) {
			return range;
		}
		return null;
	}

	private boolean itemExistsInList(String range, String[] list) {

		for(String item : list) {
			if(item.equals(range)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String[] parseString(String stringList) {

		PeakDetectorListUtil util = new PeakDetectorListUtil();
		String[] ranges = util.parseString(stringList);
		Arrays.sort(ranges);
		return ranges;
	}
}
