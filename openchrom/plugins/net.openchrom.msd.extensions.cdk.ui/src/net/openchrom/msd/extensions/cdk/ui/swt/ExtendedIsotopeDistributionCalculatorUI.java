/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.extensions.cdk.ui.swt;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.ux.extension.ui.swt.IExtendedPartUI;
import org.eclipse.chemclipse.ux.extension.ui.swt.ISettingsHandler;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ScanChartUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.openscience.cdk.formula.IsotopePattern;

import net.openchrom.msd.extensions.cdk.calculator.IsotopePatternCalculator;
import net.openchrom.msd.extensions.cdk.ui.preferences.PreferencePage;

public class ExtendedIsotopeDistributionCalculatorUI extends Composite implements IExtendedPartUI {

	private static final String MOLECULAR_FORMULA_CAFFEINE = "C8H10N4O2";

	private AtomicReference<Text> textInputControl = new AtomicReference<>();
	private AtomicReference<ScanChartUI> chartControl = new AtomicReference<>();

	private IsotopePatternCalculator calculator = new IsotopePatternCalculator();

	public ExtendedIsotopeDistributionCalculatorUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginRight = 0;
		setLayout(gridLayout);

		createToolbarMain(this);
		createScanChart(this);

		initialize();
	}

	private void initialize() {

		updateInput();
	}

	private void updateInput() {

		String formula = textInputControl.get().getText();
		if(formula == null || formula.isBlank()) {
			chartControl.get().setInput(null);
			return;
		}

		IsotopePattern isotopePattern = calculator.getIsotopePatternCalculator(formula);
		IScanMSD scanMSD = new ScanMSD();
		isotopePattern.getIsotopes().forEach(isotope -> {
			scanMSD.addIon(new Ion(isotope.getMass(), (float)isotope.getIntensity()));
		});
		chartControl.get().setInput(scanMSD);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(4, false));

		createLabel(composite);
		createTextInput(composite);
		createButtonCalculate(composite);
		createButtonSettings(composite);
	}

	private void createLabel(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Molecular Formula:");
	}

	private void createTextInput(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText(MOLECULAR_FORMULA_CAFFEINE);
		text.setToolTipText("Enter a sum formula and press enter.");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				if(e.keyCode == SWT.LF || e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					updateInput();
				}
			}
		});

		textInputControl.set(text);
	}

	private void createButtonCalculate(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Calculate the isotopic pattern.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXECUTE, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateInput();
			}
		});
	}

	private void createButtonSettings(Composite parent) {

		createSettingsButton(parent, Arrays.asList(PreferencePage.class), new ISettingsHandler() {

			@Override
			public void apply(Display display) {

				applySettings();
			}
		});
	}

	private void createScanChart(Composite parent) {

		ScanChartUI scanChartUI = new ScanChartUI(parent, SWT.BORDER);
		scanChartUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		scanChartUI.setDataType(DataType.MSD_TANDEM); // HACK: disables (overlapping) labels

		chartControl.set(scanChartUI);
	}

	private void applySettings() {

		updateInput();
	}
}