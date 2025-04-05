/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.support.ui.provider.AbstractLabelProvider;
import org.eclipse.chemclipse.support.ui.swt.EnhancedComboViewer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

import net.openchrom.swtchart.extension.export.vectorgraphics.model.ChartSettings;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageUnit;
import net.openchrom.swtchart.extension.export.vectorgraphics.preferences.PreferenceSupplier;

public class PageSizeDialog extends Dialog {

	private AtomicReference<Text> textWidthControl = new AtomicReference<>();
	private AtomicReference<Text> textHeightControl = new AtomicReference<>();
	private AtomicReference<ComboViewer> comboViewerUnitControl = new AtomicReference<>();
	private AtomicReference<Text> textGraphicsFactorControl = new AtomicReference<>();
	private AtomicReference<Text> textFontFactorControl = new AtomicReference<>();
	private AtomicReference<Spinner> spinnerTicsControl = new AtomicReference<>();

	private PageSizeOption pageSizeOption = PreferenceSupplier.getPageSizeOption();
	private PageUnit pageUnit = PreferenceSupplier.getPageUnit();

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

		return true;
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite composite = (Composite)super.createDialogArea(parent);
		composite.setLayout(new GridLayout(4, false));
		//
		createComboViewerPageSize(composite);
		createPageSizeSection(composite);
		createGraphicsFactorSection(composite);
		createFontFactorSection(composite);
		createNumberTicsSection(composite);
		//
		initialize();
		return composite;
	}

	private void initialize() {

		updatePageSize();
		updateSettings();
		updateWidgets();
	}

	private void createComboViewerPageSize(Composite parent) {

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
		combo.setToolTipText("Select a page size option.");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof PageSizeOption option) {
					pageSizeOption = option;
					PreferenceSupplier.setPageSizeOption(pageSizeOption);
					updatePageSize();
					updateSettings();
					updateWidgets();
				}
			}
		});
		//
		comboViewer.setInput(PageSizeOption.values());
		comboViewer.setSelection(new StructuredSelection(pageSizeOption));
	}

	private void createPageSizeSection(Composite parent) {

		createLabel(parent, "Page Size");
		createTextPageWidth(parent);
		createTextPageHeight(parent);
		createComboViewerUnit(parent);
	}

	private void createTextPageWidth(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Page Width");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				try {
					double input = Double.parseDouble(text.getText().trim());
					PageSize pageSize = pageSizeOption.pageSize();
					double width = convert(pageUnit, PageUnit.MM, input);
					pageSize.setWidth(width);
				} catch(NumberFormatException e1) {
				}
			}
		});

		textWidthControl.set(text);
	}

	private void createTextPageHeight(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Page Height");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				try {
					double input = Double.parseDouble(text.getText().trim());
					PageSize pageSize = pageSizeOption.pageSize();
					double height = convert(pageUnit, PageUnit.MM, input);
					pageSize.setHeight(height);
				} catch(NumberFormatException e1) {
				}
			}
		});

		textHeightControl.set(text);
	}

	private void createComboViewerUnit(Composite parent) {

		ComboViewer comboViewer = new EnhancedComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof PageUnit pageUnit) {
					return pageUnit.label();
				}
				return null;
			}
		});
		//
		combo.setToolTipText("Select a page unit.");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof PageUnit unit) {
					pageUnit = unit;
					PreferenceSupplier.setPageUnit(pageUnit);
					updatePageSize();
				}
			}
		});
		//
		comboViewer.setInput(PageUnit.values());
		comboViewer.setSelection(new StructuredSelection(pageUnit));
		comboViewerUnitControl.set(comboViewer);
	}

	private void createGraphicsFactorSection(Composite parent) {

		createLabel(parent, "Graphics Factor");
		createTextGraphicsFactor(parent);
	}

	private void createTextGraphicsFactor(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Graphics Factor (1 = 100%)");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		text.setLayoutData(gridData);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				try {
					float factor = Float.parseFloat(text.getText().trim());
					ChartSettings chartSettings = pageSizeOption.chartSettings();
					chartSettings.setFactorGraphics(factor);
				} catch(NumberFormatException e1) {
				}
			}
		});

		textGraphicsFactorControl.set(text);
	}

	private void createFontFactorSection(Composite parent) {

		createLabel(parent, "Font Factor");
		createTextFontFactor(parent);
	}

	private void createTextFontFactor(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Font Factor (1 = 100%)");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		text.setLayoutData(gridData);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				try {
					float factor = Float.parseFloat(text.getText().trim());
					ChartSettings chartSettings = pageSizeOption.chartSettings();
					chartSettings.setFactorFont(factor);
				} catch(NumberFormatException e1) {
				}
			}
		});

		textFontFactorControl.set(text);
	}

	private void createNumberTicsSection(Composite parent) {

		createLabel(parent, "Number Tics");
		createSpinnerNumberTics(parent);
	}

	private void createSpinnerNumberTics(Composite parent) {

		Spinner spinner = new Spinner(parent, SWT.BORDER);
		spinner.setMinimum(1);
		spinner.setMaximum(50);
		spinner.setIncrement(1);
		spinner.setSelection(20);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		spinner.setLayoutData(gridData);
		//
		spinner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ChartSettings chartSettings = pageSizeOption.chartSettings();
				chartSettings.setNumberTics(spinner.getSelection());
			}
		});

		spinnerTicsControl.set(spinner);
	}

	private void createLabel(Composite parent, String text) {

		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
	}

	private double convert(PageUnit unitSource, PageUnit unitSink, double input) {

		double value = input;
		if(!unitSource.equals(unitSink)) {
			double factor = 1.0d;
			switch(unitSource) {
				case INCH:
					switch(unitSink) {
						case MM:
							factor = PageSize.MM_PER_INCH;
							break;
						default:
							break;
					}
					break;
				case MM:
					switch(unitSink) {
						case INCH:
							factor = PageSize.INCH_PER_MM;
							break;
						default:
							break;
					}
					break;
				default:
					break;
			}
			value *= factor;
		}

		return value;
	}

	private void updatePageSize() {

		PageSize pageSize = pageSizeOption.pageSize();
		textWidthControl.get().setText(Double.toString(convert(PageUnit.MM, pageUnit, pageSize.getWidth())));
		textHeightControl.get().setText(Double.toString(convert(PageUnit.MM, pageUnit, pageSize.getHeight())));
	}

	private void updateSettings() {

		ChartSettings chartSettings = pageSizeOption.chartSettings();
		textGraphicsFactorControl.get().setText(Double.toString(chartSettings.getFactorGraphics()));
		textFontFactorControl.get().setText(Double.toString(chartSettings.getFactorFont()));
		spinnerTicsControl.get().setSelection(chartSettings.getNumberTics());
	}

	private void updateWidgets() {

		boolean enabled = PageSizeOption.CUSTOM_SIZE.equals(pageSizeOption);
		textWidthControl.get().setEnabled(enabled);
		textHeightControl.get().setEnabled(enabled);
		comboViewerUnitControl.get().getCombo().setEnabled(enabled);
	}
}