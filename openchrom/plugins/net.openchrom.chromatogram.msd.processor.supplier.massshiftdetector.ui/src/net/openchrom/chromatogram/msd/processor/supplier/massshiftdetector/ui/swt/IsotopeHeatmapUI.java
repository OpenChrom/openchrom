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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.widgets.datadefinition.ColorMap;
import org.eclipse.nebula.visualization.widgets.figures.IntensityGraphFigure;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.CalculatedIonCertainties;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class IsotopeHeatmapUI extends Composite {

	private Combo comboIsotopeLevel;
	private Text textCertaintyThreshold;
	private Button buttonDecreaseThreshold;
	private Button buttonIncreaseThreshold;
	private Scale scaleThreshold;
	private LightweightSystem lightweightSystem;
	private IntensityGraphFigure intensityGraphFigure;
	//
	private ProcessorData processorData;
	private Map<Integer, ColorMap> colorMaps;

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
			IProcessorModel processorModel = processorData.getProcessorModel();
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			//
			int startShiftLevel = processorSettings.getStartShiftLevel();
			int stopShiftLevel = processorSettings.getStopShiftLevel();
			//
			int size = stopShiftLevel - startShiftLevel + 1;
			String[] items = new String[size];
			for(int i = 0; i < size; i++) {
				items[i] = "Mass Shift (+" + (i + startShiftLevel) + ")";
			}
			comboIsotopeLevel.setItems(items);
			comboIsotopeLevel.select(0);
			showData();
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
		composite.setLayout(new GridLayout(3, false));
		//
		colorMaps = new HashMap<Integer, ColorMap>();
		for(int i = MassShiftDetector.SCALE_CERTAINTY_MIN; i <= MassShiftDetector.SCALE_CERTAINTY_MAX; i++) {
			ColorMap colorMap = new ColorMap();
			double threshold = 1.0d / MassShiftDetector.SCALE_CERTAINTY_MAX * i;
			colorMap.getMap().put(0.0d, new RGB(128, 0, 0));
			colorMap.getMap().put(threshold * 0.2d, new RGB(255, 0, 0));
			colorMap.getMap().put(threshold * 0.4d, new RGB(255, 255, 0));
			colorMap.getMap().put(threshold * 0.6d, new RGB(0, 255, 255));
			colorMap.getMap().put(threshold * 0.8d, new RGB(0, 0, 255));
			colorMap.getMap().put(threshold, new RGB(0, 0, 143));
			if(threshold < 1.0d) {
				colorMap.getMap().put(1.0d, new RGB(0, 0, 143));
			}
			colorMaps.put(i, colorMap);
		}
		/*
		 * Elements
		 */
		createMassShiftCombo(composite);
		createHeatmapComposite(composite);
		//
		Composite compositeThreshold = new Composite(composite, SWT.NONE);
		GridData gridDataThreshold = new GridData(GridData.FILL_HORIZONTAL);
		gridDataThreshold.horizontalSpan = 3;
		compositeThreshold.setLayoutData(gridDataThreshold);
		compositeThreshold.setLayout(new GridLayout(5, false));
		createThresholdSlider(compositeThreshold);
	}

	private void createMassShiftCombo(Composite parent) {

		comboIsotopeLevel = new Combo(parent, SWT.READ_ONLY);
		comboIsotopeLevel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboIsotopeLevel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				showData();
			}
		});
		//
		Label label = new Label(parent, SWT.NONE);
		label.setText("Threshold:");
		//
		createTextThreshold(parent);
	}

	private void createTextThreshold(Composite parent) {

		textCertaintyThreshold = new Text(parent, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		textCertaintyThreshold.setLayoutData(gridData);
		textCertaintyThreshold.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				try {
					int threshold = Integer.parseInt(textCertaintyThreshold.getText().trim());
					if(threshold > MassShiftDetector.SCALE_CERTAINTY_MIN && threshold <= MassShiftDetector.SCALE_CERTAINTY_MAX) {
						scaleThreshold.setSelection(threshold);
						setThreshold(threshold);
					} else {
						textCertaintyThreshold.setText(Integer.toString(scaleThreshold.getSelection()));
					}
				} catch(NumberFormatException e1) {
					textCertaintyThreshold.setText(Integer.toString(scaleThreshold.getSelection()));
				}
			}
		});
	}

	private void createHeatmapComposite(Composite parent) {

		Display display = Display.getDefault();
		//
		Canvas canvas = new Canvas(parent, SWT.FILL | SWT.BORDER);
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
		intensityGraphFigure.getXAxis().setTitle("Retention Time (Minutes)");
		intensityGraphFigure.getXAxis().setFormatPattern("0");
		intensityGraphFigure.getYAxis().setTitle("m/z");
		intensityGraphFigure.getYAxis().setFormatPattern("0");
		intensityGraphFigure.setColorMap(colorMaps.get(MassShiftDetector.SCALE_CERTAINTY_SELECTION));
	}

	private void createThresholdSlider(Composite parent) {

		createSliderLabel(parent, "UNCERTAIN");
		createButtonDecreaseThreshold(parent);
		createSliderCertainty(parent);
		createButtonIncreaseThreshold(parent);
		createSliderLabel(parent, "CERTAIN");
	}

	private void createSliderLabel(Composite parent, String text) {

		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
	}

	private void createButtonDecreaseThreshold(Composite parent) {

		buttonDecreaseThreshold = new Button(parent, SWT.PUSH);
		buttonDecreaseThreshold.setText("");
		buttonDecreaseThreshold.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		buttonDecreaseThreshold.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int threshold = scaleThreshold.getSelection() - MassShiftDetector.SCALE_CERTAINTY_INCREMENT;
				threshold = (threshold < MassShiftDetector.SCALE_CERTAINTY_MIN) ? MassShiftDetector.SCALE_CERTAINTY_MIN : threshold;
				//
				setThreshold(threshold);
			}
		});
	}

	private void createSliderCertainty(Composite parent) {

		scaleThreshold = new Scale(parent, SWT.BORDER);
		scaleThreshold.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		scaleThreshold.setOrientation(SWT.LEFT_TO_RIGHT);
		scaleThreshold.setMinimum(MassShiftDetector.SCALE_CERTAINTY_MIN);
		scaleThreshold.setMaximum(MassShiftDetector.SCALE_CERTAINTY_MAX);
		scaleThreshold.setPageIncrement(MassShiftDetector.SCALE_CERTAINTY_INCREMENT);
		scaleThreshold.setSelection(MassShiftDetector.SCALE_CERTAINTY_SELECTION);
		scaleThreshold.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setThreshold(scaleThreshold.getSelection());
			}
		});
	}

	private void createButtonIncreaseThreshold(Composite parent) {

		buttonIncreaseThreshold = new Button(parent, SWT.PUSH);
		buttonIncreaseThreshold.setText("");
		buttonIncreaseThreshold.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		buttonIncreaseThreshold.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int threshold = scaleThreshold.getSelection() + MassShiftDetector.SCALE_CERTAINTY_INCREMENT;
				threshold = (threshold > MassShiftDetector.SCALE_CERTAINTY_MAX) ? MassShiftDetector.SCALE_CERTAINTY_MAX : threshold;
				//
				setThreshold(threshold);
			}
		});
	}

	private void showData() {

		int massShiftLevel = getMassShiftLevel();
		if(processorData != null && processorData.getCalculatedIonCertainties() != null) {
			//
			// Map<Integer, Map<Integer, Map<Integer, Double>>>: ShiftLevel, Scan, m/z, Intensity
			//
			CalculatedIonCertainties calculatedIonCertainties = processorData.getCalculatedIonCertainties();
			if(calculatedIonCertainties.getMap().get(massShiftLevel) != null) {
				//
				int startScan = calculatedIonCertainties.getStartScan();
				int stopScan = calculatedIonCertainties.getStopScan();
				double startRetentionTimeMinutes = calculatedIonCertainties.getStartRetentionTime() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR;
				double stopRetentionTimeMinutes = calculatedIonCertainties.getStopRetentionTime() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR;
				//
				int startIon = calculatedIonCertainties.getShiftLevelStartIonMap().get(massShiftLevel);
				int stopIon = calculatedIonCertainties.getShiftLevelStopIonMap().get(massShiftLevel);
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
				Map<Integer, Map<Integer, Double>> shiftLevelMap = processorData.getCalculatedIonCertainties().getMap().get(massShiftLevel);
				/*
				 * Y-Axis: Scans
				 */
				for(int scan = startScan; scan <= stopScan; scan++) {
					int xScan = scan - startScan; // xScan as zero based heatmap scan array index
					Map<Integer, Double> ionSignalCertainties = shiftLevelMap.get(scan);
					//
					if(ionSignalCertainties != null) {
						for(int ion = startIon; ion <= stopIon; ion++) {
							/*
							 * X-Axis: m/z values
							 */
							int xIon = ion - startIon; // xIon as zero based heatmap ion array index
							double abundance;
							if(ionSignalCertainties.containsKey(ion)) {
								abundance = ionSignalCertainties.get(ion);
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
				intensityGraphFigure.getXAxis().setRange(new Range(startRetentionTimeMinutes, stopRetentionTimeMinutes));
				intensityGraphFigure.getYAxis().setRange(new Range(stopIon, startIon));
				intensityGraphFigure.setDataHeight(dataHeight);
				intensityGraphFigure.setDataWidth(dataWidth);
				intensityGraphFigure.setMin(MassShiftDetector.SCALE_CERTAINTY_MIN);
				intensityGraphFigure.setMax(MassShiftDetector.SCALE_CERTAINTY_MAX);
				lightweightSystem.setContents(intensityGraphFigure);
				intensityGraphFigure.setDataArray(heatmapData);
				intensityGraphFigure.getUpdateManager().performUpdate();
				adjustRange(massShiftLevel);
			}
		} else {
			clearRange();
		}
	}

	private void clearRange() {

		String[] items = new String[]{};
		comboIsotopeLevel.setItems(items);
		/*
		 * Clear the heatmap.
		 */
		intensityGraphFigure.setDataHeight(2);
		intensityGraphFigure.setDataWidth(2);
		intensityGraphFigure.setDataArray(new double[]{0, 0, 0, 0});
		intensityGraphFigure.setMin(0);
		intensityGraphFigure.setMax(0);
		//
		scaleThreshold.setSelection(MassShiftDetector.SCALE_CERTAINTY_SELECTION);
		textCertaintyThreshold.setText(Integer.toString(MassShiftDetector.SCALE_CERTAINTY_SELECTION));
	}

	private void adjustRange(int massShiftLevel) {

		Map<Integer, Integer> levelCertainty = processorData.getLevelCertainty();
		Integer threshold = levelCertainty.get(massShiftLevel);
		if(threshold != null) {
			scaleThreshold.setSelection(threshold);
			textCertaintyThreshold.setText(Integer.toString(threshold));
		} else {
			scaleThreshold.setSelection(MassShiftDetector.SCALE_CERTAINTY_SELECTION);
			textCertaintyThreshold.setText(Integer.toString(MassShiftDetector.SCALE_CERTAINTY_SELECTION));
		}
		/*
		 * Set the color map.
		 */
		ColorMap colorMap = colorMaps.get(threshold);
		intensityGraphFigure.setColorMap(colorMap);
	}

	private void setThreshold(int threshold) {

		scaleThreshold.setSelection(threshold);
		textCertaintyThreshold.setText(Integer.toString(threshold));
		//
		int massShiftLevel = getMassShiftLevel();
		Map<Integer, Integer> levelCertainty = processorData.getLevelCertainty();
		levelCertainty.put(massShiftLevel, threshold);
		/*
		 * Set the color map.
		 */
		ColorMap colorMap = colorMaps.get(threshold);
		intensityGraphFigure.setColorMap(colorMap);
	}

	private int getMassShiftLevel() {

		int selection = comboIsotopeLevel.getSelectionIndex();
		int massShiftLevel = 0;
		if(processorData != null) {
			IProcessorModel processorModel = processorData.getProcessorModel();
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			massShiftLevel = selection + processorSettings.getStartShiftLevel();
		}
		return massShiftLevel;
	}
}
