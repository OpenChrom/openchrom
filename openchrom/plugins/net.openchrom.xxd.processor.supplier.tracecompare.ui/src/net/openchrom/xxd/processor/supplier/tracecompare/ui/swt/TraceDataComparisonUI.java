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
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.eavp.service.swtchart.menu.ImageSupplier;
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

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;

public class TraceDataComparisonUI extends Composite {

	private static final int HORIZONTAL_INDENT = 15;
	//
	private EditorProcessor editorProcessor;
	//
	private Label labelTrack;
	private Combo comboReferenceTracks;
	private Button buttonIsEvaluated;
	private Button buttonIsMatched;
	private Button buttonIsSkipped;
	private Button buttonCreateSnapshot;
	private Text notesText;
	//
	private TraceDataUI sampleDataUI;
	private TraceDataUI referenceDataUI;
	//
	private IProcessorModel processorModel;
	private ITrackModel trackModel;
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

	public void setData(EditorProcessor editorProcessor, IProcessorModel processorModel, ITrackModel trackModel, String referenceGroup, Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData, Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData) {

		this.editorProcessor = editorProcessor;
		this.processorModel = processorModel;
		this.trackModel = trackModel;
		this.sampleGroup = processorModel.getSampleGroup();
		this.referenceGroup = referenceGroup;
		this.sampleMeasurementsData = sampleMeasurementsData;
		this.referenceMeasurementsData = referenceMeasurementsData;
	}

	public void loadData() {

		sampleDataUI.getBaseChart().suspendUpdate(true);
		referenceDataUI.getBaseChart().suspendUpdate(true);
		int sampleTrack = trackModel.getSampleTrack();
		setSampleData(sampleTrack);
		setReferenceData(sampleTrack);
		trackModel.setReferenceTrack(sampleTrack);
		initializeReferenceTrackComboItems(sampleTrack, referenceMeasurementsData.keySet().size());
		sampleDataUI.getBaseChart().suspendUpdate(false);
		referenceDataUI.getBaseChart().suspendUpdate(false);
		//
		notesText.setText(trackModel.getNotes());
		setSampleTrack(trackModel.getSampleTrack(), sampleGroup);
		//
		String imageMatched = (trackModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
		String imageSkipped = (trackModel.isSkipped()) ? IApplicationImage.IMAGE_SKIPPED : IApplicationImage.IMAGE_SKIP;
		buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(imageSkipped, IApplicationImage.SIZE_16x16));
		String imageEvaluated = (trackModel.isEvaluated()) ? IApplicationImage.IMAGE_EVALUATED : IApplicationImage.IMAGE_EVALUATE;
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(imageEvaluated, IApplicationImage.SIZE_16x16));
		//
		setElementStatus(trackModel);
		editorProcessor.setDirty(true);
	}

	private void setSampleData(int track) {

		if(sampleMeasurementsData != null) {
			sampleDataUI.addSeriesData(getLineSeriesDataList(sampleMeasurementsData, track), LineChart.MEDIUM_COMPRESSION);
		}
	}

	private void setReferenceData(int track) {

		if(referenceMeasurementsData != null) {
			referenceDataUI.addSeriesData(getLineSeriesDataList(referenceMeasurementsData, track), LineChart.MEDIUM_COMPRESSION);
		}
	}

	private void setSampleTrack(int track, String sample) {

		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		labelTrack.setFont(font);
		labelTrack.setText(sample + " > Track " + Integer.toString(track));
		font.dispose();
	}

	private List<ILineSeriesData> getLineSeriesDataList(Map<Integer, Map<String, ISeriesData>> measurementsData, int track) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		//
		if(measurementsData != null) {
			if(measurementsData.containsKey(track)) {
				Map<String, ISeriesData> wavelengthData = measurementsData.get(track);
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

		labelTrack = new Label(parent, SWT.NONE);
		labelTrack.setText("");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalIndent = HORIZONTAL_INDENT;
		labelTrack.setLayoutData(gridDataLabel);
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

		createComboReferenceTracks(parent);
		createButtonFlipComments(parent);
		createButtonCreateSnapshot(parent);
		createButtonIsMatched(parent);
		createButtonIsSkipped(parent);
		createButtonIsEvaluated(parent);
	}

	private void createComboReferenceTracks(Composite parent) {

		comboReferenceTracks = new Combo(parent, SWT.READ_ONLY);
		GridData gridData = new GridData();
		gridData.widthHint = 220;
		comboReferenceTracks.setLayoutData(gridData);
		initializeReferenceTrackComboItems(-1, 0);
		comboReferenceTracks.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int track = comboReferenceTracks.getSelectionIndex() + 1;
				trackModel.setReferenceTrack(track);
				setReferenceData(track);
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
				String fileNameSample = getImageName("Sample", sampleGroup, trackModel.getSampleTrack());
				ImageData imageDataSample = imageSupplier.getImageData(sampleDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataSample, fileNameSample, SWT.IMAGE_PNG);
				trackModel.setPathSnapshotSample(fileNameSample);
				//
				String fileNameReference = getImageName("Reference", referenceGroup, trackModel.getReferenceTrack());
				ImageData imageDataReference = imageSupplier.getImageData(referenceDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataReference, fileNameReference, SWT.IMAGE_PNG);
				trackModel.setPathSnapshotReference(fileNameReference);
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

				setMatched(!trackModel.isMatched());
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

				setSkipped(!trackModel.isSkipped());
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

				setEvaluated(!trackModel.isEvaluated());
			}
		});
	}

	private void setMatched(boolean isMatched) {

		trackModel.setMatched(isMatched);
		String imageMatched = (trackModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
		editorProcessor.setDirty(true);
	}

	private void setSkipped(boolean isSkipped) {

		trackModel.setSkipped(isSkipped);
		String imageSkipped = (trackModel.isSkipped()) ? IApplicationImage.IMAGE_SKIPPED : IApplicationImage.IMAGE_SKIP;
		buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(imageSkipped, IApplicationImage.SIZE_16x16));
		//
		if(isSkipped) {
			setEvaluated(false);
		}
		//
		setElementStatus(trackModel);
		editorProcessor.setDirty(true);
	}

	private void setEvaluated(boolean isEvaluated) {

		trackModel.setEvaluated(isEvaluated);
		String imageEvaluated = (trackModel.isEvaluated()) ? IApplicationImage.IMAGE_EVALUATED : IApplicationImage.IMAGE_EVALUATE;
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
		setElementStatus(trackModel);
		editorProcessor.setDirty(true);
	}

	private void setSelectedRange(Range rangeX, Range rangeY) {

		trackModel.setStartRetentionTime((rangeX != null) ? rangeX.lower : 0.0d);
		trackModel.setStopRetentionTime((rangeX != null) ? rangeX.upper : 0.0d);
		trackModel.setStartIntensity((rangeY != null) ? rangeY.lower : 0.0d);
		trackModel.setStopIntensity((rangeY != null) ? rangeY.upper : 0.0d);
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

				trackModel.setNotes(notesText.getText().trim());
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

	private String getImageName(String type, String group, int track) {

		StringBuilder builder = new StringBuilder();
		builder.append(processorModel.getImageDirectory());
		builder.append(File.separator);
		builder.append(type);
		builder.append("_");
		builder.append(group);
		builder.append("_");
		builder.append(track);
		builder.append(".png");
		return builder.toString();
	}

	private void setElementStatus(ITrackModel trackModel) {

		if(trackModel.isEvaluated() || trackModel.isSkipped()) {
			if(trackModel.isEvaluated()) {
				trackModel.setSkipped(false);
				comboReferenceTracks.setEnabled(false);
				buttonCreateSnapshot.setEnabled(false);
				buttonIsMatched.setEnabled(false);
				buttonIsSkipped.setEnabled(false);
				buttonIsEvaluated.setEnabled(true);
			} else {
				trackModel.setEvaluated(false);
				comboReferenceTracks.setEnabled(false);
				buttonCreateSnapshot.setEnabled(false);
				buttonIsMatched.setEnabled(false);
				buttonIsSkipped.setEnabled(true);
				buttonIsEvaluated.setEnabled(false);
			}
		} else {
			comboReferenceTracks.setEnabled(true);
			buttonCreateSnapshot.setEnabled(true);
			buttonIsMatched.setEnabled(true);
			buttonIsSkipped.setEnabled(true);
			buttonIsEvaluated.setEnabled(true);
		}
	}

	private void initializeReferenceTrackComboItems(int selectedTrack, int numberTracks) {

		comboReferenceTracks.removeAll();
		//
		List<String> tracks = new ArrayList<String>();
		for(int i = 1; i <= numberTracks; i++) {
			tracks.add("Reference Track " + i);
		}
		comboReferenceTracks.setItems(tracks.toArray(new String[tracks.size()]));
		//
		int size = comboReferenceTracks.getItemCount();
		if(size > 0) {
			if(selectedTrack > 0 && selectedTrack <= size) {
				comboReferenceTracks.select(selectedTrack - 1);
			} else {
				comboReferenceTracks.select(0);
			}
		}
	}
}
