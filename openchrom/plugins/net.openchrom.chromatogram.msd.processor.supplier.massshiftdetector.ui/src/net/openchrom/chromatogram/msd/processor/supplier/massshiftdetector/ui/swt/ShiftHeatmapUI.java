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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class ShiftHeatmapUI extends Composite {

	private static final Logger logger = Logger.getLogger(ShiftHeatmapUI.class);
	private LightweightSystem lightweightSystem;
	private IntensityGraphFigure intensityGraphFigure;
	private Label heatmapTitel;
	private Composite header;

	public ShiftHeatmapUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update() {

		logger.info("Update the Mass Shift Map");
		System.out.println("TODO");
	}

	private void initialize(Composite parent) {

		GridData layoutData;
		Display display = Display.getCurrent();
		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.FILL);
		composite.setLayout(new GridLayout(1, true));
		/*
		 * Header
		 */
		layoutData = new GridData(GridData.FILL_HORIZONTAL);
		header = new Composite(composite, SWT.NONE);
		header.setLayout(new GridLayout(1, true));
		header.setLayoutData(layoutData);
		//
		layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.grabExcessHorizontalSpace = true;
		heatmapTitel = new Label(header, SWT.CENTER);
		heatmapTitel.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		heatmapTitel.setLayoutData(layoutData);
		Font font = new Font(display, "Arial", 12, SWT.BOLD);
		heatmapTitel.setFont(font);
		heatmapTitel.setText("Mass Shift +1");
		font.dispose();
		/*
		 * Heatmap
		 */
		layoutData = new GridData(GridData.FILL_BOTH);
		Canvas canvas = new Canvas(composite, SWT.FILL | SWT.BORDER);
		canvas.setLayoutData(layoutData);
		canvas.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		//
		lightweightSystem = new LightweightSystem(canvas);
		lightweightSystem.getRootFigure().setBackgroundColor(display.getSystemColor(SWT.COLOR_WHITE));
		//
		intensityGraphFigure = new IntensityGraphFigure();
		intensityGraphFigure.setForegroundColor(display.getSystemColor(SWT.COLOR_BLACK));
		intensityGraphFigure.getXAxis().setTitle("m/z");
		intensityGraphFigure.getYAxis().setTitle("scan");
	}
}
