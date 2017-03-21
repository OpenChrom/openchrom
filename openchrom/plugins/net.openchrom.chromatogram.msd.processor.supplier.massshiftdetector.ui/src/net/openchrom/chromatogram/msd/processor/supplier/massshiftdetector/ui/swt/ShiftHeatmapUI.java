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

import java.util.Collections;
import java.util.Map;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.widgets.datadefinition.ColorMap;
import org.eclipse.nebula.visualization.widgets.datadefinition.ColorMap.PredefinedColorMap;
import org.eclipse.nebula.visualization.widgets.figures.IntensityGraphFigure;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class ShiftHeatmapUI extends Composite {

	private static final int SCALE_MIN = 0; // 0
	private static final int SCALE_MAX = 1000; // 1
	private static final int SCALE_INCREMENT = 1; // 0.001
	private static final int SCALE_SELECTION = 1000;
	//
	private Combo comboMassShift;
	private Scale scale;
	private LightweightSystem lightweightSystem;
	private IntensityGraphFigure intensityGraphFigure;
	//
	private Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts;
	private double minValue;
	private double maxValue;

	public ShiftHeatmapUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(ProcessorData processorData) {

		if(processorData != null) {
			/*
			 * Set the combo items.
			 */
			String[] items = new String[processorData.getLevel() + 1];
			for(int i = 0; i <= processorData.getLevel(); i++) {
				items[i] = "Mass Shift (+" + i + ")";
			}
			comboMassShift.setItems(items);
			comboMassShift.select(0);
			/*
			 * Calculate the shifts.
			 */
			if(this.massShifts == null) {
				MassShiftDetector massShiftDetector = new MassShiftDetector();
				IChromatogramMSD reference = processorData.getChromatogramReference();
				IChromatogramMSD shifted = processorData.getChromatogramShifted();
				int level = processorData.getLevel();
				boolean useAbsoluteValues = processorData.isUseAbsoluteValues();
				Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts = massShiftDetector.detectMassShifts(reference, shifted, level, useAbsoluteValues);
				this.massShifts = massShifts;
			}
			/*
			 * Adjust the scale.
			 */
			scale.setSelection(SCALE_SELECTION);
			showData(0);
		} else {
			/*
			 * Clear the items.
			 */
			String[] items = new String[]{};
			comboMassShift.setItems(items);
			this.massShifts = null;
			intensityGraphFigure.getGraphArea().erase();
		}
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
		comboMassShift = new Combo(composite, SWT.READ_ONLY);
		GridData gridDataCombo = new GridData(GridData.FILL_HORIZONTAL);
		gridDataCombo.horizontalSpan = 3;
		comboMassShift.setLayoutData(gridDataCombo);
		comboMassShift.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				showData(comboMassShift.getSelectionIndex());
			}
		});
		/*
		 * Heatmap
		 */
		Canvas canvas = new Canvas(composite, SWT.FILL | SWT.BORDER);
		GridData gridDataCanvas = new GridData(GridData.FILL_BOTH);
		gridDataCanvas.horizontalSpan = 3;
		canvas.setLayoutData(gridDataCanvas);
		canvas.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		//
		lightweightSystem = new LightweightSystem(canvas);
		lightweightSystem.getRootFigure().setBackgroundColor(display.getSystemColor(SWT.COLOR_WHITE));
		//
		intensityGraphFigure = new IntensityGraphFigure();
		intensityGraphFigure.setForegroundColor(display.getSystemColor(SWT.COLOR_BLACK));
		intensityGraphFigure.getXAxis().setTitle("Scans");
		intensityGraphFigure.getXAxis().setFormatPattern("0");
		intensityGraphFigure.getYAxis().setTitle("Delta (m/z)");
		intensityGraphFigure.getYAxis().setFormatPattern("0.0E0");
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
		scale = new Scale(composite, SWT.BORDER);
		scale.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scale.setMinimum(SCALE_MIN);
		scale.setMaximum(SCALE_MAX);
		scale.setPageIncrement(SCALE_INCREMENT);
		scale.setSelection(SCALE_SELECTION);
		scale.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				double minIntensity = minValue / SCALE_MAX * scale.getSelection();
				double maxIntensity = maxValue / SCALE_MAX * scale.getSelection();
				//
				intensityGraphFigure.setMin(minIntensity);
				intensityGraphFigure.setMax(maxIntensity);
				intensityGraphFigure.getGraphArea().getUpdateManager().performUpdate();
			}
		});
		//
		Label labelRight = new Label(composite, SWT.NONE);
		labelRight.setText("MORE");
	}

	private void showData(int shiftLevel) {

		if(massShifts != null && massShifts.get(shiftLevel) != null) {
			//
			// Map<Integer, Map<Integer, Map<Integer, Double>>>: ShiftLevel, Scan, m/z, Intensity
			//
			Map<Integer, Map<Integer, Double>> shiftLevelData = massShifts.get(shiftLevel);
			int startScan = Collections.min(shiftLevelData.keySet());
			int stopScan = Collections.max(shiftLevelData.keySet());
			//
			Point minMaxMZ = getMinMaxMZ(shiftLevelData);
			int startIon = minMaxMZ.x;
			int stopIon = minMaxMZ.y;
			//
			int dataHeight = stopScan - startScan + 1; // y -> scans
			int dataWidth = stopIon - startIon + 1; // x -> m/z values
			/*
			 * The data height and width must be >= 1!
			 */
			if(dataHeight < 1 || dataWidth < 1) {
				return;
			}
			/*
			 * Parse the heatmap data
			 */
			double minIntensity = Double.MAX_VALUE;
			double maxIntensity = Double.MIN_VALUE;
			double[] heatmapData = new double[dataWidth * dataHeight * 2];
			/*
			 * Y-Axis: Scans
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				int xScan = scan - startScan; // xScan as zero based heatmap scan array index
				Map<Integer, Double> values = shiftLevelData.get(scan);
				//
				double min = Collections.min(values.values());
				double max = Collections.min(values.values());
				minIntensity = (min < minIntensity) ? min : minIntensity;
				maxIntensity = (max > maxIntensity) ? max : maxIntensity;
				//
				if(values != null) {
					for(int ion = startIon; ion <= stopIon; ion++) {
						/*
						 * X-Axis: m/z values
						 */
						int xIon = ion - startIon; // xIon as zero based heatmap ion array index
						double abundance;
						if(values.containsKey(ion)) {
							abundance = values.get(ion);
						} else {
							abundance = 0;
						}
						/*
						 * XY data
						 */
						heatmapData[xScan * dataWidth + xIon] = abundance;
					}
				}
			}
			/*
			 * Set the range and min/max values.
			 */
			intensityGraphFigure.getXAxis().setRange(new Range(startScan, stopScan));
			intensityGraphFigure.getYAxis().setRange(new Range(stopIon, startIon));
			//
			minValue = minIntensity;
			maxValue = maxIntensity;
			intensityGraphFigure.setMin(minValue);
			intensityGraphFigure.setMax(maxValue);
			//
			intensityGraphFigure.setDataHeight(dataHeight);
			intensityGraphFigure.setDataWidth(dataWidth);
			//
			intensityGraphFigure.setColorMap(new ColorMap(PredefinedColorMap.JET, true, true));
			/*
			 * Set the heatmap data
			 */
			lightweightSystem.setContents(intensityGraphFigure);
			intensityGraphFigure.setDataArray(heatmapData);
		} else {
			// Clear map
		}
	}

	private Point getMinMaxMZ(Map<Integer, Map<Integer, Double>> shiftLevel) {

		int start = Integer.MAX_VALUE;
		int stop = Integer.MIN_VALUE;
		//
		for(Map<Integer, Double> values : shiftLevel.values()) {
			//
			int startMZ = Collections.min(values.keySet());
			if(startMZ < start) {
				start = startMZ;
			}
			//
			int stopMZ = Collections.max(values.keySet());
			if(stopMZ > stop) {
				stop = stopMZ;
			}
		}
		//
		return new Point(start, stop);
	}
}
