/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Alexander Stark - re-factoring
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.ui;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Observable;

import org.eclipse.chemclipse.ux.extension.xxd.ui.editors.EditorExtension;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings;
import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings.PivotPointSelection;

public class PhaseCorrectionSettingsEditorExtension implements EditorExtension {

	private static final int FRACTIONS = 0;// 2;
	private static final int FIRST_ORDER_RANGE = 1000;// 1000000;
	private static final int ZERO_ORDER_RANGE = 180;
	private static final int ZERO_ORDER_INCREMENT = 90;
	private PhaseCorrectionSettings settings;
	//
	private static final char DEGREE = 0x00B0;

	public PhaseCorrectionSettingsEditorExtension(PhaseCorrectionSettings settings){
		this.settings = settings;
	}

	@Override
	public Observable createExtension(Composite parent) {

		new PhaseCorrectionSettingsExtension(parent);
		return settings;
	}

	private class PhaseCorrectionSettingsExtension {

		public PhaseCorrectionSettingsExtension(Composite parent){
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout(1, false));
			{
				Label label = new Label(composite, SWT.NONE);
				label.setText("Zero order phase correction [" + DEGREE + "]:");
				GridData labelLayout = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
				label.setLayoutData(labelLayout);
			}
			createZeroOrderSlider(composite);
			{
				Label label = new Label(composite, SWT.NONE);
				label.setText("First order phase correction [" + DEGREE + "]:");
				GridData labelLayout = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
				label.setLayoutData(labelLayout);
			}
			createFirstOrderSlider(composite);
			{
				Label label = new Label(composite, SWT.NONE);
				label.setText("Pivot point [ppm]:");
				GridData labelLayout = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
				label.setLayoutData(labelLayout);
			}
			createPivotSelection(composite);
		}

		private void createPivotSelection(Composite parent) {

			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout(2, false));
			Text textField = new Text(composite, SWT.BORDER);
			ComboViewer pivotCombo = new ComboViewer(composite);
			//
			pivotCombo.setContentProvider(ArrayContentProvider.getInstance());
			pivotCombo.setInput(PivotPointSelection.values());
			pivotCombo.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			GridData layoutData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
			layoutData.widthHint = 80;
			textField.setLayoutData(layoutData);
			pivotCombo.addSelectionChangedListener(new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {

					PivotPointSelection selection = (PivotPointSelection) pivotCombo.getStructuredSelection().getFirstElement();
					if(selection != settings.getPivotPointSelection()) {
						settings.setPivotPointSelection(selection);
					}
					textField.setEnabled(selection == PivotPointSelection.USER_DEFINED);
				}
			});
			NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
			textField.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent se) {

					try {
						settings.setUserDefinedPivotPointValue(numberFormat.parse(textField.getText()).doubleValue());
					} catch (ParseException | RuntimeException e) {
						// TODO show a decorator!
					}
				}
			});
			textField.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {

				}

				@Override
				public void focusGained(FocusEvent e) {

					Display.getCurrent().asyncExec(textField::selectAll);
				}
			});
			PivotPointSelection pivotPointSelection = settings.getPivotPointSelection();
			pivotCombo.setSelection(new StructuredSelection(pivotPointSelection));
			textField.setText(numberFormat.format(settings.getUserDefinedPivotPointValue()));
			textField.setEnabled(pivotPointSelection == PivotPointSelection.USER_DEFINED);
		}

		private SliderUI createFirstOrderSlider(Composite composite) {

			SliderUI ui = new SliderUI(composite, FRACTIONS);
			ui.setRange(-FIRST_ORDER_RANGE, FIRST_ORDER_RANGE);
			ui.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			ui.setValue(settings.getFirstOrderPhaseCorrection());
			ui.addConsumer(settings::setFirstOrderPhaseCorrection);
			return ui;
		}

		private SliderUI createZeroOrderSlider(Composite composite) {

			SliderUI ui = new SliderUI(composite, FRACTIONS);
			ui.setRange(-ZERO_ORDER_RANGE, ZERO_ORDER_RANGE);
			ui.setPageIncrement(ZERO_ORDER_INCREMENT);
			ui.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			Composite buttons = new Composite(composite, SWT.NONE);
			GridData ldButtons = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
			ldButtons.horizontalSpan = 2;
			buttons.setLayoutData(ldButtons);
			buttons.setLayout(new GridLayout(4, true));
			createButton("+ 90" + DEGREE, 1, ui, buttons);
			createButton("- 90" + DEGREE, -1, ui, buttons);
			createButton("+/- 180" + DEGREE, 2, ui, buttons);
			ui.setValue(settings.getZeroOrderPhaseCorrection());
			ui.addConsumer(settings::setZeroOrderPhaseCorrection);
			return ui;
		}

		private Button createButton(String text, int increment, SliderUI ui, Composite buttons) {

			Button button = new Button(buttons, SWT.PUSH);
			GridData buttonData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			buttonData.widthHint = 70;
			button.setLayoutData(buttonData);
			button.setText(text);
			button.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					ui.increment(increment);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {

				}
			});
			return button;
		}
	}
}
