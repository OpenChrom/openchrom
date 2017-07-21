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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.eavp.service.swtchart.core.BaseChart;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;
import org.eclipse.eavp.service.swtchart.export.ImageSupplier;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.swtchart.IAxis;
import org.swtchart.Range;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.SampleLaneModel;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;

public class TraceDataComparisonUI extends Composite {

	private static final int HORIZONTAL_INDENT = 15;
	//
	private EditorProcessor editorProcessor;
	//
	private Label labelSampleLane;
	private Combo comboReferenceSampleLanes;
	private Button buttonIsEvaluated;
	private Button buttonIsMatched;
	private Button buttonIsSkipped;
	private Button buttonCreateSnapshot;
	private Text notesText;
	//
	private TraceDataUI sampleDataUI;
	private TraceDataUI referenceDataUI;
	//
	private ProcessorModel processorModel;
	private SampleLaneModel sampleLaneModel;
	private String sampleGroup;
	private String referenceGroup;
	private Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData;
	private Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData;
	//
	private Map<String, Color> colorMap;
	private Color colorDefault;

	public TraceDataComparisonUI(Composite parent, int style) {
		super(parent, style);
		//
		colorMap = new HashMap<String, Color>();
		colorMap.put("190", Colors.YELLOW);
		colorMap.put("200", Colors.BLUE);
		colorMap.put("220", Colors.CYAN);
		colorMap.put("240", Colors.GREEN);
		colorMap.put("260", Colors.BLACK);
		colorMap.put("280", Colors.RED);
		colorMap.put("300", Colors.DARK_RED);
		colorDefault = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		//
		initialize();
	}

	public void setData(EditorProcessor editorProcessor, ProcessorModel processorModel, SampleLaneModel sampleLaneModel, String referenceGroup, Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData, Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData) {

		this.editorProcessor = editorProcessor;
		this.processorModel = processorModel;
		this.sampleLaneModel = sampleLaneModel;
		this.sampleGroup = processorModel.getSampleGroup();
		this.referenceGroup = referenceGroup;
		this.sampleMeasurementsData = sampleMeasurementsData;
		this.referenceMeasurementsData = referenceMeasurementsData;
	}

	public void loadData() {

		int sampleLane = sampleLaneModel.getSampleLane();
		setSampleData(sampleLane);
		setReferenceData(sampleLane);
		sampleLaneModel.setReferenceLane(sampleLane);
		initializeReferenceSampleLaneComboItems(sampleLane, referenceMeasurementsData.keySet().size());
		//
		notesText.setText(sampleLaneModel.getNotes());
		setSampleLane(sampleLaneModel.getSampleLane(), sampleGroup);
		//
		String imageMatched = (sampleLaneModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
		String imageSkipped = (sampleLaneModel.isSkipped()) ? IApplicationImage.IMAGE_SKIPPED : IApplicationImage.IMAGE_SKIP;
		buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(imageSkipped, IApplicationImage.SIZE_16x16));
		String imageEvaluated = (sampleLaneModel.isEvaluated()) ? IApplicationImage.IMAGE_EVALUATED : IApplicationImage.IMAGE_EVALUATE;
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(imageEvaluated, IApplicationImage.SIZE_16x16));
		//
		setElementStatus(sampleLaneModel);
		editorProcessor.setDirty(true);
	}

	private void setSampleData(int sampleLane) {

		if(sampleMeasurementsData != null) {
			sampleDataUI.addSeriesData(getLineSeriesDataList(sampleMeasurementsData, sampleLane), LineChart.MEDIUM_COMPRESSION);
		}
	}

	private void setReferenceData(int sampleLane) {

		if(referenceMeasurementsData != null) {
			referenceDataUI.addSeriesData(getLineSeriesDataList(referenceMeasurementsData, sampleLane), LineChart.MEDIUM_COMPRESSION);
		}
	}

	private void setSampleLane(int sampleLane, String sample) {

		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		labelSampleLane.setFont(font);
		labelSampleLane.setText(sample + " > Sample Lane " + Integer.toString(sampleLane));
		font.dispose();
	}

	private List<ILineSeriesData> getLineSeriesDataList(Map<Integer, Map<String, ISeriesData>> measurementsData, int sampleLane) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		//
		if(measurementsData != null) {
			if(measurementsData.containsKey(sampleLane)) {
				Map<String, ISeriesData> wavelengthData = measurementsData.get(sampleLane);
				addLineSeriesData(lineSeriesDataList, wavelengthData);
			} else if(measurementsData.containsKey(1)) {
				Map<String, ISeriesData> wavelengthData = measurementsData.get(1);
				addLineSeriesData(lineSeriesDataList, wavelengthData);
			} else {
				ILineSeriesData lineSeriesData = createEmptyLineSeriesData();
				lineSeriesDataList.add(lineSeriesData);
			}
		} else {
			ILineSeriesData lineSeriesData = createEmptyLineSeriesData();
			lineSeriesDataList.add(lineSeriesData);
		}
		//
		return lineSeriesDataList;
	}

	private void addLineSeriesData(List<ILineSeriesData> lineSeriesDataList, Map<String, ISeriesData> wavelengthData) {

		for(String wavelength : wavelengthData.keySet()) {
			ISeriesData seriesData = wavelengthData.get(wavelength);
			ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
			ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
			lineSerieSettings.setDescription(wavelength + " nm");
			lineSerieSettings.setEnableArea(false);
			lineSerieSettings.setLineColor((colorMap.containsKey(wavelength)) ? colorMap.get(wavelength) : colorDefault);
			lineSeriesDataList.add(lineSeriesData);
		}
	}

	private ILineSeriesData createEmptyLineSeriesData() {

		double[] xSeries = new double[]{0, 1000};
		double[] ySeries = new double[]{0, 1000};
		ISeriesData seriesData = new SeriesData(xSeries, ySeries, "0");
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setDescription("0 nm");
		lineSerieSettings.setEnableArea(false);
		lineSerieSettings.setLineColor(colorDefault);
		return lineSeriesData;
	}

	private void initialize() {

		setLayout(new GridLayout(2, true));
		//
		createButtonSection(this);
		createCommentsSection(this);
		createTraceDataSection(this);
		//
		showComments(false);
	}

	private void createButtonSection(Composite parent) {

		labelSampleLane = new Label(parent, SWT.NONE);
		labelSampleLane.setText("");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalIndent = HORIZONTAL_INDENT;
		labelSampleLane.setLayoutData(gridDataLabel);
		//
		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		gridDataComposite.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(6, false));
		//
		createButtons(composite);
	}

	private void createButtons(Composite parent) {

		createComboReferenceSampleLanes(parent);
		createButtonFlipComments(parent);
		createButtonCreateSnapshot(parent);
		createButtonIsMatched(parent);
		createButtonIsSkipped(parent);
		createButtonIsEvaluated(parent);
	}

	private void createComboReferenceSampleLanes(Composite parent) {

		comboReferenceSampleLanes = new Combo(parent, SWT.READ_ONLY);
		GridData gridData = new GridData();
		gridData.widthHint = 220;
		comboReferenceSampleLanes.setLayoutData(gridData);
		initializeReferenceSampleLaneComboItems(-1, 0);
		comboReferenceSampleLanes.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int sampleLane = comboReferenceSampleLanes.getSelectionIndex() + 1;
				sampleLaneModel.setReferenceLane(sampleLane);
				setReferenceData(sampleLane);
				setEvaluated(false);
			}
		});
	}

	private void createButtonFlipComments(Composite parent) {

		Button buttonFlipComments = new Button(parent, SWT.PUSH);
		buttonFlipComments.setText("");
		buttonFlipComments.setToolTipText("Show/Hide Comments");
		buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
		buttonFlipComments.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isVisible = !notesText.isVisible();
				showComments(isVisible);
				//
				if(isVisible) {
					buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_COLLAPSE_ALL, IApplicationImage.SIZE_16x16));
				} else {
					buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
				}
				//
				editorProcessor.setDirty(true);
			}
		});
	}

	private void createButtonCreateSnapshot(Composite parent) {

		buttonCreateSnapshot = new Button(parent, SWT.PUSH);
		buttonCreateSnapshot.setText("");
		buttonCreateSnapshot.setToolTipText("Create a snapshot.");
		buttonCreateSnapshot.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CREATE_SNAPSHOT, IApplicationImage.SIZE_16x16));
		buttonCreateSnapshot.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ImageSupplier imageSupplier = new ImageSupplier();
				//
				String fileNameSample = getImageName("Sample", sampleGroup, sampleLaneModel.getSampleLane());
				ImageData imageDataSample = imageSupplier.getImageData(sampleDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataSample, fileNameSample, SWT.IMAGE_PNG);
				sampleLaneModel.setPathSnapshotSample(fileNameSample);
				//
				String fileNameReference = getImageName("Reference", referenceGroup, sampleLaneModel.getReferenceLane());
				ImageData imageDataReference = imageSupplier.getImageData(referenceDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataReference, fileNameReference, SWT.IMAGE_PNG);
				sampleLaneModel.setPathSnapshotReference(fileNameReference);
				//
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Save Image", "A screenshot of the sample and reference has been saved.");
				editorProcessor.setDirty(true);
			}
		});
	}

	private void createButtonIsMatched(Composite parent) {

		buttonIsMatched = new Button(parent, SWT.PUSH);
		buttonIsMatched.setText("");
		buttonIsMatched.setToolTipText("Flag as matched");
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED, IApplicationImage.SIZE_16x16));
		buttonIsMatched.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setMatched(!sampleLaneModel.isMatched());
			}
		});
	}

	private void createButtonIsSkipped(Composite parent) {

		buttonIsSkipped = new Button(parent, SWT.PUSH);
		buttonIsSkipped.setText("");
		buttonIsSkipped.setToolTipText("Flag as skipped.");
		buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SKIP, IApplicationImage.SIZE_16x16));
		buttonIsSkipped.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setSkipped(!sampleLaneModel.isSkipped());
			}
		});
	}

	private void createButtonIsEvaluated(Composite parent) {

		buttonIsEvaluated = new Button(parent, SWT.PUSH);
		buttonIsEvaluated.setText("");
		buttonIsEvaluated.setToolTipText("Flag as evaluated.");
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EVALUATE, IApplicationImage.SIZE_16x16));
		buttonIsEvaluated.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setEvaluated(!sampleLaneModel.isEvaluated());
			}
		});
	}

	private void setMatched(boolean isMatched) {

		sampleLaneModel.setMatched(isMatched);
		String imageMatched = (sampleLaneModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
		editorProcessor.setDirty(true);
	}

	private void setSkipped(boolean isSkipped) {

		sampleLaneModel.setSkipped(isSkipped);
		String imageSkipped = (sampleLaneModel.isSkipped()) ? IApplicationImage.IMAGE_SKIPPED : IApplicationImage.IMAGE_SKIP;
		buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(imageSkipped, IApplicationImage.SIZE_16x16));
		//
		if(isSkipped) {
			setEvaluated(false);
		}
		//
		setElementStatus(sampleLaneModel);
		editorProcessor.setDirty(true);
	}

	private void setEvaluated(boolean isEvaluated) {

		sampleLaneModel.setEvaluated(isEvaluated);
		String imageEvaluated = (sampleLaneModel.isEvaluated()) ? IApplicationImage.IMAGE_EVALUATED : IApplicationImage.IMAGE_EVALUATE;
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(imageEvaluated, IApplicationImage.SIZE_16x16));
		//
		if(isEvaluated) {
			setSkipped(false);
			BaseChart baseChart = sampleDataUI.getBaseChart();
			IAxis xAxis = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
			IAxis yAxis = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
			Range rangeX = xAxis.getRange();
			Range rangeY = yAxis.getRange();
			setSelectedRange(rangeX, rangeY);
		} else {
			setSelectedRange(null, null);
		}
		//
		setElementStatus(sampleLaneModel);
		editorProcessor.setDirty(true);
	}

	private void setSelectedRange(Range rangeX, Range rangeY) {

		sampleLaneModel.setStartRetentionTime((rangeX != null) ? rangeX.lower : 0.0d);
		sampleLaneModel.setStopRetentionTime((rangeX != null) ? rangeX.upper : 0.0d);
		sampleLaneModel.setStartIntensity((rangeY != null) ? rangeY.lower : 0.0d);
		sampleLaneModel.setStopIntensity((rangeY != null) ? rangeY.upper : 0.0d);
	}

	private void createCommentsSection(Composite parent) {

		notesText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		notesText.setText("");
		GridData gridData = getGridData();
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		notesText.setLayoutData(gridData);
		notesText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				sampleLaneModel.setNotes(notesText.getText().trim());
			}
		});
	}

	private void createTraceDataSection(Composite parent) {

		TraceDataSettings traceDataSettingsSample = new TraceDataSettings();
		traceDataSettingsSample.setEnableRangeSelector(true);
		traceDataSettingsSample.setShowAxisTitle(false);
		traceDataSettingsSample.setEnableHorizontalSlider(false);
		traceDataSettingsSample.setCreateMenu(true);
		sampleDataUI = new TraceDataUI(parent, SWT.NONE, traceDataSettingsSample);
		sampleDataUI.setLayoutData(getGridData());
		//
		TraceDataSettings traceDataSettingsReference = new TraceDataSettings();
		traceDataSettingsReference.setEnableRangeSelector(false);
		traceDataSettingsReference.setShowAxisTitle(true);
		traceDataSettingsReference.setEnableHorizontalSlider(true);
		traceDataSettingsReference.setCreateMenu(true);
		referenceDataUI = new TraceDataUI(parent, SWT.NONE, traceDataSettingsReference);
		referenceDataUI.setLayoutData(getGridData());
		/*
		 * Link both charts.
		 */
		sampleDataUI.addLinkedScrollableChart(referenceDataUI);
		referenceDataUI.addLinkedScrollableChart(sampleDataUI);
	}

	private GridData getGridData() {

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		return gridData;
	}

	private void showComments(boolean isVisible) {

		GridData gridData = (GridData)notesText.getLayoutData();
		gridData.exclude = !isVisible;
		notesText.setVisible(isVisible);
		Composite parent = notesText.getParent();
		parent.layout(false);
		parent.redraw();
	}

	private String getImageName(String type, String group, int lane) {

		StringBuilder builder = new StringBuilder();
		builder.append(processorModel.getImageDirectory());
		builder.append(File.separator);
		builder.append(type);
		builder.append("_");
		builder.append(group);
		builder.append("_");
		builder.append(lane);
		builder.append(".png");
		return builder.toString();
	}

	private void setElementStatus(SampleLaneModel sampleLaneModel) {

		if(sampleLaneModel.isEvaluated() || sampleLaneModel.isSkipped()) {
			if(sampleLaneModel.isEvaluated()) {
				sampleLaneModel.setSkipped(false);
				comboReferenceSampleLanes.setEnabled(false);
				buttonCreateSnapshot.setEnabled(false);
				buttonIsMatched.setEnabled(false);
				buttonIsSkipped.setEnabled(false);
				buttonIsEvaluated.setEnabled(true);
			} else {
				sampleLaneModel.setEvaluated(false);
				comboReferenceSampleLanes.setEnabled(false);
				buttonCreateSnapshot.setEnabled(false);
				buttonIsMatched.setEnabled(false);
				buttonIsSkipped.setEnabled(true);
				buttonIsEvaluated.setEnabled(false);
			}
		} else {
			comboReferenceSampleLanes.setEnabled(true);
			buttonCreateSnapshot.setEnabled(true);
			buttonIsMatched.setEnabled(true);
			buttonIsSkipped.setEnabled(true);
			buttonIsEvaluated.setEnabled(true);
		}
	}

	private void initializeReferenceSampleLaneComboItems(int sampleLane, int numberOfSampleLanes) {

		comboReferenceSampleLanes.removeAll();
		//
		List<String> sampleLanes = new ArrayList<String>();
		for(int i = 1; i <= numberOfSampleLanes; i++) {
			sampleLanes.add("Reference Sample Lane " + i);
		}
		comboReferenceSampleLanes.setItems(sampleLanes.toArray(new String[sampleLanes.size()]));
		//
		int size = comboReferenceSampleLanes.getItemCount();
		if(size > 0) {
			if(sampleLane > 0 && sampleLane <= size) {
				comboReferenceSampleLanes.select(sampleLane - 1);
			} else {
				comboReferenceSampleLanes.select(0);
			}
		}
	}
}
