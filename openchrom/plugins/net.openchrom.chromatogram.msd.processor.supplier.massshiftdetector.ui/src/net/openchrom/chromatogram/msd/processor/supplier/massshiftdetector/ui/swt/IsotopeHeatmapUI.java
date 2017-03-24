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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
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
import org.eclipse.swt.widgets.Text;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ValueRange;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.MassShiftDetectorRunnable;

public class IsotopeHeatmapUI extends Composite {

	private static final Logger logger = Logger.getLogger(IsotopeHeatmapUI.class);
	//
	private static final String MASSSHIFT_COLOR_MAP = "Mass Shift Spectrum";
	private static final String DEFAULT_COLOR_MAP = MASSSHIFT_COLOR_MAP;
	//
	private Combo comboMassShift;
	private Combo comboColorMap;
	private Text textNegativeDelta;
	private Text textPositiveDelta;
	private Scale scaleNegativeDelta;
	private Scale scalePositiveDelta;
	private LightweightSystem lightweightSystem;
	private IntensityGraphFigure intensityGraphFigure;
	//
	private ProcessorData processorData;
	private Map<String, ColorMap> colorMaps;

	public IsotopeHeatmapUI(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	public void update(ProcessorData processorData) {

		this.processorData = processorData;
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
			if(processorData.getMassShifts() == null) {
				Shell shell = Display.getCurrent().getActiveShell();
				MassShiftDetectorRunnable runnable = new MassShiftDetectorRunnable(processorData);
				ProgressMonitorDialog monitor = new ProgressMonitorDialog(shell);
				//
				try {
					monitor.run(true, true, runnable);
					processorData.setMassShifts(runnable.getMassShifts());
				} catch(InterruptedException e) {
					logger.warn(e);
				} catch(InvocationTargetException e) {
					logger.warn(e);
				}
			}
			/*
			 * Adjust the scale.
			 */
			showData(0);
		} else {
			/*
			 * Clear the items.
			 */
			clearRange();
		}
	}

	private void initialize(Composite parent) {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.FILL);
		composite.setLayout(new GridLayout(5, false));
		/*
		 * Color Map
		 */
		ColorMap colorMap = new ColorMap();
		colorMap.getMap().put(0.0d, new RGB(255, 255, 255)); // - 100%
		colorMap.getMap().put(0.2d, new RGB(255, 255, 255)); // - 60%
		colorMap.getMap().put(0.3d, new RGB(255, 255, 0)); // - 40%
		colorMap.getMap().put(0.4d, new RGB(0, 255, 0)); // - 20%
		colorMap.getMap().put(0.4874d, new RGB(0, 255, 0)); // 0
		colorMap.getMap().put(0.4875d, new RGB(0, 255, 0)); // - 2.5%
		colorMap.getMap().put(0.5d, new RGB(0, 0, 255)); // 0%
		colorMap.getMap().put(0.5125d, new RGB(0, 255, 0)); // + 2.5%
		colorMap.getMap().put(0.5126d, new RGB(0, 255, 0)); // 0
		colorMap.getMap().put(0.6d, new RGB(0, 255, 0)); // + 20%
		colorMap.getMap().put(0.7d, new RGB(255, 255, 0)); // + 40%
		colorMap.getMap().put(0.8d, new RGB(255, 255, 255)); // + 60%
		colorMap.getMap().put(1.0d, new RGB(255, 255, 255)); // + 100%
		//
		colorMaps = new HashMap<String, ColorMap>();
		colorMaps.put(MASSSHIFT_COLOR_MAP, colorMap);
		colorMaps.put(PredefinedColorMap.ColorSpectrum.toString(), new ColorMap(PredefinedColorMap.ColorSpectrum, true, true));
		colorMaps.put(PredefinedColorMap.Cool.toString(), new ColorMap(PredefinedColorMap.Cool, true, true));
		colorMaps.put(PredefinedColorMap.GrayScale.toString(), new ColorMap(PredefinedColorMap.GrayScale, true, true));
		colorMaps.put(PredefinedColorMap.Hot.toString(), new ColorMap(PredefinedColorMap.Hot, true, true));
		colorMaps.put(PredefinedColorMap.JET.toString(), new ColorMap(PredefinedColorMap.JET, true, true));
		colorMaps.put(PredefinedColorMap.Shaded.toString(), new ColorMap(PredefinedColorMap.Shaded, true, true));
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

				intensityGraphFigure.setColorMap(colorMaps.get(comboColorMap.getText()));
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
		intensityGraphFigure.getXAxis().setTitle("Scan Number");
		intensityGraphFigure.getXAxis().setFormatPattern("0");
		intensityGraphFigure.getYAxis().setTitle("m/z");
		intensityGraphFigure.getYAxis().setFormatPattern("0");
		intensityGraphFigure.setColorMap(colorMaps.get(DEFAULT_COLOR_MAP));
	}

	private void createThresholdSlider(Composite parent) {

		createTextNegativeDelta(parent);
		createSliderInfo(parent);
		createTextPositiveDelta(parent);
		//
		createSliderLabel(parent, "UNSPECIFIC");
		createSliderNegativeDelta(parent);
		createSliderLabel(parent, "SPECIFIC");
		createSliderPositiveDelta(parent);
		createSliderLabel(parent, "UNSPECIFIC");
	}

	private void createSliderLabel(Composite parent, String text) {

		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
	}

	private void createTextNegativeDelta(Composite parent) {

		textNegativeDelta = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		textNegativeDelta.setText(Integer.toString(-MassShiftDetector.SCALE_SELECTION));
		textNegativeDelta.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				try {
					double min = Double.valueOf(textNegativeDelta.getText().trim());
					if(min >= -MassShiftDetector.SCALE_MAX && min <= MassShiftDetector.SCALE_MIN) {
						scaleNegativeDelta.setSelection((int)Math.abs(min));
						setMinValue(min);
					} else {
						textNegativeDelta.setText(Integer.toString(-scaleNegativeDelta.getSelection()));
					}
				} catch(NumberFormatException e1) {
					logger.warn(e1);
					textNegativeDelta.setText(Integer.toString(-scaleNegativeDelta.getSelection()));
				}
			}
		});
	}

	private void createSliderInfo(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Define the granularity of the mass shift detection algorithm.");
		GridData gridDataLabelSlider = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabelSlider.horizontalSpan = 3;
		gridDataLabelSlider.horizontalAlignment = SWT.CENTER;
		label.setLayoutData(gridDataLabelSlider);
	}

	private void createTextPositiveDelta(Composite parent) {

		textPositiveDelta = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		textPositiveDelta.setText(Integer.toString(MassShiftDetector.SCALE_SELECTION));
		textPositiveDelta.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				try {
					double max = Double.valueOf(textPositiveDelta.getText().trim());
					if(max >= MassShiftDetector.SCALE_MIN && max <= MassShiftDetector.SCALE_MAX) {
						scalePositiveDelta.setSelection((int)Math.abs(max));
						setMaxValue(max);
					} else {
						textPositiveDelta.setText(Integer.toString(scalePositiveDelta.getSelection()));
					}
				} catch(NumberFormatException e1) {
					logger.warn(e1);
					textPositiveDelta.setText(Integer.toString(scalePositiveDelta.getSelection()));
				}
			}
		});
	}

	private void createSliderNegativeDelta(Composite parent) {

		scaleNegativeDelta = new Scale(parent, SWT.BORDER);
		scaleNegativeDelta.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scaleNegativeDelta.setOrientation(SWT.RIGHT_TO_LEFT);
		scaleNegativeDelta.setMinimum(MassShiftDetector.SCALE_MIN);
		scaleNegativeDelta.setMaximum(MassShiftDetector.SCALE_MAX);
		scaleNegativeDelta.setPageIncrement(MassShiftDetector.SCALE_INCREMENT);
		scaleNegativeDelta.setSelection(MassShiftDetector.SCALE_SELECTION);
		scaleNegativeDelta.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				double min = -scaleNegativeDelta.getSelection();
				textNegativeDelta.setText(Integer.toString((int)min));
				setMinValue(min);
			}
		});
	}

	private void createSliderPositiveDelta(Composite parent) {

		scalePositiveDelta = new Scale(parent, SWT.BORDER);
		scalePositiveDelta.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scalePositiveDelta.setOrientation(SWT.LEFT_TO_RIGHT);
		scalePositiveDelta.setMinimum(MassShiftDetector.SCALE_MIN);
		scalePositiveDelta.setMaximum(MassShiftDetector.SCALE_MAX);
		scalePositiveDelta.setPageIncrement(MassShiftDetector.SCALE_INCREMENT);
		scalePositiveDelta.setSelection(MassShiftDetector.SCALE_SELECTION);
		scalePositiveDelta.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				double max = scalePositiveDelta.getSelection();
				textPositiveDelta.setText(Integer.toString((int)max));
				setMaxValue(max);
			}
		});
	}

	private void showData(int level) {

		if(processorData.getMassShifts() != null && processorData.getMassShifts().get(level) != null) {
			//
			// Map<Integer, Map<Integer, Map<Integer, Double>>>: ShiftLevel, Scan, m/z, Intensity
			//
			Map<Integer, Map<Integer, Double>> shiftLevelData = processorData.getMassShifts().get(level);
			int startScan = Collections.min(shiftLevelData.keySet());
			int stopScan = Collections.max(shiftLevelData.keySet());
			//
			ValueRange minMaxMZ = getMinMaxMZ(shiftLevelData);
			int startIon = (int)minMaxMZ.getMin();
			int stopIon = (int)minMaxMZ.getMax();
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
			double[] heatmapData = new double[dataWidth * dataHeight * 2];
			/*
			 * Y-Axis: Scans
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				int xScan = scan - startScan; // xScan as zero based heatmap scan array index
				Map<Integer, Double> values = shiftLevelData.get(scan);
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
			 * Set the range and min/max values and the heatmap data.
			 */
			intensityGraphFigure.getXAxis().setRange(new Range(startScan, stopScan));
			intensityGraphFigure.getYAxis().setRange(new Range(stopIon, startIon));
			intensityGraphFigure.setDataHeight(dataHeight);
			intensityGraphFigure.setDataWidth(dataWidth);
			intensityGraphFigure.setMin(MassShiftDetector.MIN_VALUE);
			intensityGraphFigure.setMax(MassShiftDetector.MAX_VALUE);
			lightweightSystem.setContents(intensityGraphFigure);
			intensityGraphFigure.setDataArray(heatmapData);
			//
			adjustRange(level);
		} else {
			clearRange();
		}
	}

	private ValueRange getMinMaxMZ(Map<Integer, Map<Integer, Double>> shiftLevel) {

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
		return new ValueRange(start, stop);
	}

	private void clearRange() {

		String[] items = new String[]{};
		comboMassShift.setItems(items);
		// processorData.setMassShifts(null);
		intensityGraphFigure.getGraphArea().erase();
		textNegativeDelta.setText(Integer.toString(-MassShiftDetector.SCALE_SELECTION));
		textPositiveDelta.setText(Integer.toString(MassShiftDetector.SCALE_SELECTION));
		scaleNegativeDelta.setSelection(MassShiftDetector.SCALE_SELECTION);
		scalePositiveDelta.setSelection(MassShiftDetector.SCALE_SELECTION);
	}

	private void adjustRange(int level) {

		Map<Integer, ValueRange> levelGranularity = processorData.getLevelGranularity();
		ValueRange valueRange = levelGranularity.get(level);
		if(valueRange != null) {
			int min = (int)valueRange.getMin();
			textNegativeDelta.setText(Integer.toString(min));
			scaleNegativeDelta.setSelection(Math.abs(min));
			setMinValue(min);
			//
			int max = (int)valueRange.getMax();
			textPositiveDelta.setText(Integer.toString(max));
			scalePositiveDelta.setSelection(Math.abs(max));
			setMaxValue(max);
		} else {
			textNegativeDelta.setText(Integer.toString(-MassShiftDetector.SCALE_SELECTION));
			textPositiveDelta.setText(Integer.toString(MassShiftDetector.SCALE_SELECTION));
			scaleNegativeDelta.setSelection(MassShiftDetector.SCALE_SELECTION);
			scalePositiveDelta.setSelection(MassShiftDetector.SCALE_SELECTION);
		}
	}

	private void setMinValue(double min) {

		intensityGraphFigure.setMin(min - MassShiftDetector.SCALE_OFFSET);
		intensityGraphFigure.getGraphArea().getUpdateManager().performUpdate();
		//
		if(processorData != null) {
			Map<Integer, ValueRange> levelGranularity = processorData.getLevelGranularity();
			int level = comboMassShift.getSelectionIndex();
			ValueRange valueRange = levelGranularity.get(level);
			if(valueRange == null) {
				valueRange = new ValueRange(0.0d, 0.0d);
			}
			valueRange.setMin(min);
			levelGranularity.put(level, valueRange);
		}
	}

	private void setMaxValue(double max) {

		intensityGraphFigure.setMax(max + MassShiftDetector.SCALE_OFFSET);
		intensityGraphFigure.getGraphArea().getUpdateManager().performUpdate();
		//
		if(processorData != null) {
			Map<Integer, ValueRange> levelGranularity = processorData.getLevelGranularity();
			int level = comboMassShift.getSelectionIndex();
			ValueRange valueRange = levelGranularity.get(level);
			if(valueRange == null) {
				valueRange = new ValueRange(0.0d, 0.0d);
			}
			valueRange.setMax(max);
			levelGranularity.put(level, valueRange);
		}
	}
}
