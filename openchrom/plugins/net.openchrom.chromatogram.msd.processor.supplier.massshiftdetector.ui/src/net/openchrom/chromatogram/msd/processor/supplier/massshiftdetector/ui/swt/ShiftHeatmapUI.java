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

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
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
import org.eclipse.swt.widgets.Shell;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.MassShiftDetectorRunnable;

public class ShiftHeatmapUI extends Composite {

	private static final Logger logger = Logger.getLogger(ShiftHeatmapUI.class);
	//
	private static final int SCALE_MIN = 0; // 0
	private static final int SCALE_MAX = 1000; // 1
	private static final int SCALE_INCREMENT = 1; // 0.001
	private static final int SCALE_SELECTION = 1000;
	//
	private static final String DEFAULT_COLOR_MAP = PredefinedColorMap.JET.toString();
	//
	private Combo comboMassShift;
	private Combo comboColorMap;
	private Scale scaleNegativeDelta;
	private Scale scalePositiveDelta;
	private LightweightSystem lightweightSystem;
	private IntensityGraphFigure intensityGraphFigure;
	//
	private Map<String, ColorMap.PredefinedColorMap> colorMaps;
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
			int level = processorData.getProcessorModel().getLevel();
			String[] items = new String[level + 1];
			for(int i = 0; i <= level; i++) {
				items[i] = "Mass Shift (+" + i + ")";
			}
			comboMassShift.setItems(items);
			comboMassShift.select(0);
			/*
			 * Calculate the shifts.
			 */
			if(this.massShifts == null) {
				Shell shell = Display.getCurrent().getActiveShell();
				MassShiftDetectorRunnable runnable = new MassShiftDetectorRunnable(processorData);
				ProgressMonitorDialog monitor = new ProgressMonitorDialog(shell);
				//
				try {
					monitor.run(true, true, runnable);
					this.massShifts = runnable.getMassShifts();
				} catch(InterruptedException e) {
					logger.warn(e);
				} catch(InvocationTargetException e) {
					logger.warn(e);
				}
			}
			/*
			 * Adjust the scale.
			 */
			scaleNegativeDelta.setSelection(SCALE_SELECTION);
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

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.FILL);
		composite.setLayout(new GridLayout(5, false));
		/*
		 * Color Map
		 */
		colorMaps = new HashMap<String, ColorMap.PredefinedColorMap>();
		colorMaps.put(PredefinedColorMap.ColorSpectrum.toString(), PredefinedColorMap.ColorSpectrum);
		colorMaps.put(PredefinedColorMap.Cool.toString(), PredefinedColorMap.Cool);
		colorMaps.put(PredefinedColorMap.GrayScale.toString(), PredefinedColorMap.GrayScale);
		colorMaps.put(PredefinedColorMap.Hot.toString(), PredefinedColorMap.Hot);
		colorMaps.put(PredefinedColorMap.JET.toString(), PredefinedColorMap.JET);
		colorMaps.put(PredefinedColorMap.Shaded.toString(), PredefinedColorMap.Shaded);
		/*
		 * Elements
		 */
		createMassShiftCombo(composite);
		createColorMapCombo(composite);
		createHeatmapComposite(composite);
		createThresholdSlider(composite);
	}

	private void createMassShiftCombo(Composite parent) {

		comboMassShift = new Combo(parent, SWT.READ_ONLY);
		GridData gridDataCombo = new GridData(GridData.FILL_HORIZONTAL);
		gridDataCombo.horizontalSpan = 5;
		comboMassShift.setLayoutData(gridDataCombo);
		comboMassShift.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				showData(comboMassShift.getSelectionIndex());
			}
		});
	}

	private void createColorMapCombo(Composite parent) {

		comboColorMap = new Combo(parent, SWT.READ_ONLY);
		GridData gridDataCombo = new GridData(GridData.FILL_HORIZONTAL);
		gridDataCombo.horizontalSpan = 5;
		comboColorMap.setLayoutData(gridDataCombo);
		comboColorMap.setItems(colorMaps.keySet().toArray(new String[colorMaps.size()]));
		comboColorMap.setText(DEFAULT_COLOR_MAP);
		comboColorMap.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				intensityGraphFigure.setColorMap(new ColorMap(colorMaps.get(comboColorMap.getText()), true, true));
			}
		});
	}

	private void createHeatmapComposite(Composite parent) {

		Display display = Display.getCurrent();
		//
		Canvas canvas = new Canvas(parent, SWT.FILL | SWT.BORDER);
		GridData gridDataCanvas = new GridData(GridData.FILL_BOTH);
		gridDataCanvas.horizontalSpan = 5;
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
		intensityGraphFigure.getYAxis().setFormatPattern("0.000");
		intensityGraphFigure.setColorMap(new ColorMap(PredefinedColorMap.JET, true, true)); // Default
	}

	private void createThresholdSlider(Composite parent) {

		Label labelSlider = new Label(parent, SWT.NONE);
		labelSlider.setText("Set the threshold to detect mass shifts. 'LESS' means, that the most important ones and 'MORE' that less important shifts are selected.");
		GridData gridDataLabelSlider = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabelSlider.horizontalSpan = 5;
		gridDataLabelSlider.horizontalAlignment = SWT.CENTER;
		labelSlider.setLayoutData(gridDataLabelSlider);
		//
		createSliderLabel(parent, "MORE");
		createSliderNegativeDelta(parent);
		createSliderLabel(parent, "LESS");
		createSliderPositiveDelta(parent);
		createSliderLabel(parent, "MORE");
	}

	private void createSliderLabel(Composite parent, String text) {

		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
	}

	private void createSliderNegativeDelta(Composite parent) {

		scaleNegativeDelta = new Scale(parent, SWT.BORDER);
		scaleNegativeDelta.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scaleNegativeDelta.setOrientation(SWT.RIGHT_TO_LEFT);
		scaleNegativeDelta.setMinimum(SCALE_MIN);
		scaleNegativeDelta.setMaximum(SCALE_MAX);
		scaleNegativeDelta.setPageIncrement(SCALE_INCREMENT);
		scaleNegativeDelta.setSelection(SCALE_SELECTION);
		scaleNegativeDelta.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				// double minIntensity = minValue / SCALE_MAX * scaleNegativeDelta.getSelection();
				// double maxIntensity = maxValue / SCALE_MAX * scaleNegativeDelta.getSelection();
				// intensityGraphFigure.setMin(minIntensity);
				// intensityGraphFigure.setMax(maxIntensity);
				// intensityGraphFigure.getGraphArea().getUpdateManager().performUpdate();
				//
				// System.out.println(scaleNegativeDelta.getSelection());
			}
		});
	}

	private void createSliderPositiveDelta(Composite parent) {

		scalePositiveDelta = new Scale(parent, SWT.BORDER);
		scalePositiveDelta.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scalePositiveDelta.setOrientation(SWT.LEFT_TO_RIGHT);
		scalePositiveDelta.setMinimum(SCALE_MIN);
		scalePositiveDelta.setMaximum(SCALE_MAX);
		scalePositiveDelta.setPageIncrement(SCALE_INCREMENT);
		scalePositiveDelta.setSelection(SCALE_SELECTION);
		scalePositiveDelta.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				// System.out.println(scalePositiveDelta.getSelection() + ": " + minIntensity + " - " + maxIntensity + "(" + minValue + " - " + maxValue + ")");
			}
		});
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
