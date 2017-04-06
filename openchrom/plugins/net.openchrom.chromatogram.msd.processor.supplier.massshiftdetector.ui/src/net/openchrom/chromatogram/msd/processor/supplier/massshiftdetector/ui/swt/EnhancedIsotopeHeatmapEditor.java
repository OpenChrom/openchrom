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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.listener.AbstractControllerComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class EnhancedIsotopeHeatmapEditor extends AbstractControllerComposite {

	private Button buttonCalculate;
	private Button buttonPrevious;
	private Button buttonNext;
	private List<Button> buttons;
	//
	private IsotopeHeatmapUI shiftHeatmapUI;
	private ProcessorData processorData;

	public EnhancedIsotopeHeatmapEditor(Composite parent, int style) {
		super(parent, style);
		buttons = new ArrayList<Button>();
		createControl();
	}

	@Override
	public boolean setFocus() {

		plotData();
		return super.setFocus();
	}

	@Override
	public void setStatus(boolean readOnly) {

		for(Button button : buttons) {
			button.setEnabled(false);
		}
		/*
		 * Defaults when editable.
		 */
		if(!readOnly) {
			buttonCalculate.setEnabled(true);
			buttonPrevious.setEnabled(true);
		} else {
			/*
			 * If the project is read only, next shall be enabled.
			 */
			buttonNext.setEnabled(true);
		}
	}

	/**
	 * Sets the table viewer input.
	 * 
	 * @param input
	 */
	public void setInput(Object input) {

		if(input instanceof ProcessorData) {
			this.processorData = (ProcessorData)input;
		} else {
			processorData = null;
		}
	}

	/*
	 * Plot the data if there is no validation error.
	 */
	private void plotData() {

		shiftHeatmapUI.update(processorData);
		buttonNext.setEnabled(true);
	}

	private void createControl() {

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Standards Table
		 */
		Composite chartComposite = new Composite(composite, SWT.NONE);
		chartComposite.setLayout(new GridLayout(1, true));
		chartComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Heatmap
		 */
		shiftHeatmapUI = new IsotopeHeatmapUI(chartComposite, SWT.NONE);
		shiftHeatmapUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Button Bar
		 */
		Composite compositeButtons = new Composite(composite, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(1, true));
		compositeButtons.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		//
		GridData gridDataButtons = new GridData(GridData.FILL_HORIZONTAL);
		gridDataButtons.minimumWidth = 150;
		//
		buttons.add(buttonCalculate = createCalculateButton(compositeButtons, gridDataButtons));
		buttons.add(buttonPrevious = createPreviousButton(compositeButtons, gridDataButtons));
		buttons.add(buttonNext = createNextButton(compositeButtons, gridDataButtons));
	}

	private Button createCalculateButton(Composite parent, GridData gridData) {

		Shell shell = Display.getDefault().getActiveShell();
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Calculate");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CALCULATE, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(processorData != null && processorData.getMassShifts() != null) {
					MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setText("Calculate Heatmap");
					messageBox.setMessage("Current results are overwritten when doing a new calculation.");
					if(messageBox.open() == SWT.YES) {
						processAction();
						plotData();
					}
				} else {
					processAction();
					plotData();
				}
			}
		});
		return button;
	}

	private Button createNextButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Next");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_FORWARD, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.setEnabled(false);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				fireUpdateNext();
			}
		});
		return button;
	}

	private Button createPreviousButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Previous");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				fireUpdatePrevious();
			}
		});
		return button;
	}
}
