/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core.formula;

import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * The GUI Dialog for choosing the settings (e.g. Isotopes and Threshold) of the File GenericMassToFormula.java.
 * The Elements of the PSE should be listed as CheckBoxes after the user choosed to manually configur
 * the Set of Isotopes to include in the calculation.
 * This PSE view should generally be shown in an external view => maybe choose Swing here ??
 * There should be an option for changing Threshold (when to stop calculation,e.g. when is result good enough)
 * and an IterationDepth (e.g. after how many isotopes to stop the calculation).
 * 
 * @author administrator_marwin
 * 
 */
public class UIIsotopeDeciderDialog {

	private Shell shell;
	private Display display;
	private boolean manualSelectionMode = false;

	public UIIsotopeDeciderDialog() {

		display = new Display();
		shell = new Shell(display);
		shell.setText("Mass to Formula Tool Settings");
		shell.setLayout(new FillLayout());
		Group manualSetting = new Group(shell, SWT.CENTER);
		manualSetting.setLayout(new FillLayout());
		final StyledText textEdit = new StyledText(manualSetting, SWT.NONE);
		final Button manualEdit = new Button(shell, SWT.CHECK);
		manualEdit.setText("Enable Manual Isotope Selection");
		manualEdit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				textEdit.setEnabled(true);
				manualSelectionMode = true;
				System.out.println("Komm an!");
				textEdit.setText("1H, 12C, 13C");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		manualSetting.setText("Manual Isotope Configuration");
		textEdit.setEnabled(false);
		shell.pack();
		shell.open();
		// Start the basic gui event loop
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		// Free resources
		display.dispose();
		shell.dispose();
	}

	public static void main(String[] args) {

		UIIsotopeDeciderDialog dialog = new UIIsotopeDeciderDialog();
	}
}
