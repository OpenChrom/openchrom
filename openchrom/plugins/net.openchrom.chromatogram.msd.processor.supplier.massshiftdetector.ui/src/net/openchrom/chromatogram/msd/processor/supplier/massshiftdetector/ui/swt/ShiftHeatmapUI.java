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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.widgets.figures.IntensityGraphFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

public class ShiftHeatmapUI extends Composite {

	private static final Logger logger = Logger.getLogger(ShiftHeatmapUI.class);

	public ShiftHeatmapUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update() {

		logger.info("Update the Mass Shift Map");
		System.out.println("TODO");
	}

	private void initialize(Composite parent) {

		Display display = Display.getCurrent();
		//
		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.FILL);
		composite.setLayout(new GridLayout(3, false));
		/*
		 * Header
		 */
		Combo comboMassShift = new Combo(composite, SWT.READ_ONLY);
		GridData gridDataCombo = new GridData(GridData.FILL_HORIZONTAL);
		gridDataCombo.horizontalSpan = 3;
		comboMassShift.setLayoutData(gridDataCombo);
		comboMassShift.setItems(new String[]{"Mass Shift (0)", "Mass Shift (+1)", "Mass Shift (+2)", "Mass Shift (+3)"});
		comboMassShift.select(0);
		/*
		 * Heatmap
		 */
		Canvas canvas = new Canvas(composite, SWT.FILL | SWT.BORDER);
		GridData gridDataCanvas = new GridData(GridData.FILL_BOTH);
		gridDataCanvas.horizontalSpan = 3;
		canvas.setLayoutData(gridDataCanvas);
		canvas.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		//
		LightweightSystem lightweightSystem = new LightweightSystem(canvas);
		lightweightSystem.getRootFigure().setBackgroundColor(display.getSystemColor(SWT.COLOR_WHITE));
		//
		IntensityGraphFigure intensityGraphFigure = new IntensityGraphFigure();
		intensityGraphFigure.setForegroundColor(display.getSystemColor(SWT.COLOR_BLACK));
		intensityGraphFigure.getXAxis().setTitle("Retention Time (milliseconds)");
		intensityGraphFigure.getYAxis().setTitle("m/z");
		/*
		 * Footer
		 */
		Label labelSlider = new Label(composite, SWT.NONE);
		labelSlider.setText("Set the threshold to detect mass shifts.");
		GridData gridDataLabelSlider = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabelSlider.horizontalSpan = 3;
		gridDataLabelSlider.horizontalAlignment = SWT.CENTER;
		labelSlider.setLayoutData(gridDataLabelSlider);
		//
		Label labelLeft = new Label(composite, SWT.NONE);
		labelLeft.setText("LESS");
		//
		Scale scale = new Scale(composite, SWT.BORDER);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scale.setMinimum(0); // 0
		scale.setMaximum(1000); // 1
		scale.setPageIncrement(1); // 0.001
		//
		Label labelRight = new Label(composite, SWT.NONE);
		labelRight.setText("MORE");
	}
}
