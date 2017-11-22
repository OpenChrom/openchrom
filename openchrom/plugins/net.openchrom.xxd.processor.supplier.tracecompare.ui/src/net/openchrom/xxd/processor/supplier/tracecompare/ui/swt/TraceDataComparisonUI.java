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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.eavp.service.swtchart.core.BaseChart;
import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;
import org.eclipse.eavp.service.swtchart.images.ImageSupplier;
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
import org.swtchart.LineStyle;
import org.swtchart.Range;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;

public class TraceDataComparisonUI extends Composite {

	public static final String SAMPLE = "Sample";
	public static final String REFERENCE = "Reference";
	//
	private static final int HORIZONTAL_INDENT = 15;
	private static final String SHOW_ALL_WAVELENGTHS = "Show All";
	//
	private EditorProcessor editorProcessor;
	private int previousTrack;
	private int nextTrack;
	//
	private Label labelTrack;
	private Combo comboReferenceTracks;
	private Combo comboWavelengths;
	private Button buttonIsEvaluated;
	private Button buttonIsMatched;
	private Button buttonIsSkipped;
	private Button buttonCreateSnapshot;
	private Text notesText;
	//
	private TraceDataUI traceDataUI;
	//
	private IProcessorModel processorModel;
	private ITrackModel trackModel;
	private String type;
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
		colorMap.put("190", Colors.getColor(255, 255, 0));
		colorMap.put("200", Colors.getColor(0, 0, 255));
		colorMap.put("220", Colors.getColor(0, 255, 255));
		colorMap.put("240", Colors.getColor(0, 255, 0));
		colorMap.put("260", Colors.getColor(0, 0, 0));
		colorMap.put("280", Colors.getColor(255, 0, 0));
		colorMap.put("300", Colors.getColor(185, 0, 127));
		colorDefault = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		//
		initialize();
	}

	public void setData(EditorProcessor editorProcessor, IProcessorModel processorModel, ITrackModel trackModel, String referenceGroup, Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData, Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData) {

		this.editorProcessor = editorProcessor;
		this.processorModel = processorModel;
		this.trackModel = trackModel;
		this.referenceGroup = referenceGroup;
		this.sampleMeasurementsData = sampleMeasurementsData;
		this.referenceMeasurementsData = referenceMeasurementsData;
	}

	public void setTrackInformation(int previousTrack, int nextTrack) {

		this.previousTrack = previousTrack;
		this.nextTrack = nextTrack;
	}

	public void loadData(String type, String sampleGroup) {

		this.type = type;
		this.sampleGroup = sampleGroup;
		//
		int sampleTrack = trackModel.getSampleTrack();
		initializeWavelengthsComboItems(sampleMeasurementsData.get(sampleTrack));
		//
		reloadData();
	}

	public void reloadData() {

		/*
		 * Update the chart and combo boxes.
		 */
		traceDataUI.getBaseChart().suspendUpdate(true);
		traceDataUI.deleteSeries();
		int sampleTrack = trackModel.getSampleTrack();
		initializeReferenceTrackComboItems(sampleTrack, referenceMeasurementsData.keySet().size());
		setSampleData(sampleTrack);
		setReferenceData(sampleTrack);
		trackModel.setReferenceTrack(sampleTrack);
		traceDataUI.getBaseChart().suspendUpdate(false);
		/*
		 * Update the notes.
		 */
		notesText.setText(trackModel.getNotes());
		setSampleTrack(type, sampleGroup, sampleTrack);
		/*
		 * Update the buttons.
		 */
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
			traceDataUI.addSeriesData(getLineSeriesDataList(sampleMeasurementsData, track), LineChart.MEDIUM_COMPRESSION);
		}
	}

	private void setReferenceData(int track) {

		if(referenceMeasurementsData != null) {
			traceDataUI.addSeriesData(getLineSeriesDataList(referenceMeasurementsData, track), LineChart.MEDIUM_COMPRESSION);
		}
	}

	private void setSampleTrack(String type, String sample, int track) {

		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		labelTrack.setFont(font);
		labelTrack.setText(type + ": " + sample + " > Track " + Integer.toString(track));
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

		String wavelengthSelection = comboWavelengths.getText();
		if(wavelengthSelection.equals(SHOW_ALL_WAVELENGTHS)) {
			for(String wavelength : wavelengthData.keySet()) {
				addWavelengthData(lineSeriesDataList, wavelengthData, wavelength);
			}
		} else {
			addWavelengthData(lineSeriesDataList, wavelengthData, wavelengthSelection);
		}
	}

	private void addWavelengthData(List<ILineSeriesData> lineSeriesDataList, Map<String, ISeriesData> wavelengthData, String wavelength) {

		ISeriesData seriesData = wavelengthData.get(wavelength);
		boolean isReference = seriesData.getId().startsWith(REFERENCE);
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(false);
		if(isReference) {
			lineSerieSettings.setLineStyle(LineStyle.DASH);
			lineSerieSettings.setLineWidth(2);
		} else {
			lineSerieSettings.setLineStyle(LineStyle.SOLID);
			lineSerieSettings.setLineWidth(1);
		}
		lineSerieSettings.setLineColor((colorMap.containsKey(wavelength)) ? colorMap.get(wavelength) : colorDefault);
		lineSeriesDataList.add(lineSeriesData);
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

		setLayout(new GridLayout(1, true));
		//
		createLabelSection(this);
		createButtonSection(this);
		createCommentsSection(this);
		createTraceDataSection(this);
		//
		showComments(false);
	}

	private void createLabelSection(Composite parent) {

		labelTrack = new Label(parent, SWT.NONE);
		labelTrack.setText("");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalIndent = HORIZONTAL_INDENT;
		labelTrack.setLayoutData(gridDataLabel);
	}

	private void createButtonSection(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		gridDataComposite.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(10, false));
		//
		createButtonPreviousTrack(composite);
		createComboReferenceTracks(composite);
		createComboWavelengths(composite);
		createButtonFlipComments(composite);
		createButtonCreateSnapshot(composite);
		createButtonIsMatched(composite);
		createButtonIsSkipped(composite);
		createButtonIsEvaluated(composite);
		createButtonToggleLegend(composite);
		createButtonNextTrack(composite);
	}

	private void createButtonPreviousTrack(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Select the previous track.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PREVIOUS_YELLOW, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("Previous Track: " + previousTrack);
			}
		});
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

	private void createComboWavelengths(Composite parent) {

		comboWavelengths = new Combo(parent, SWT.READ_ONLY);
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		comboWavelengths.setLayoutData(gridData);
		initializeWavelengthsComboItems(null);
		comboWavelengths.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				reloadData();
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
				String fileNameSample = getImageName("Sample+Reference", sampleGroup + "+" + referenceGroup, trackModel.getSampleTrack());
				ImageData imageDataSample = imageSupplier.getImageData(traceDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataSample, fileNameSample, SWT.IMAGE_PNG);
				trackModel.setPathSnapshotSample(fileNameSample);
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

	private void createButtonToggleLegend(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Toggle the chart legend");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_TAG, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				traceDataUI.toggleSeriesLegendVisibility();
			}
		});
	}

	private void createButtonNextTrack(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Select the next track.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_NEXT_YELLOW, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("Next Track: " + nextTrack);
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
			BaseChart baseChart = traceDataUI.getBaseChart();
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
		GridData gridData = new GridData(GridData.FILL_BOTH);
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

		traceDataUI = new TraceDataUI(parent, SWT.NONE);
		traceDataUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		IChartSettings chartSettings = traceDataUI.getChartSettings();
		chartSettings.setEnableRangeSelector(true);
		chartSettings.setShowRangeSelectorInitially(false);
		chartSettings.setRangeSelectorDefaultAxisX(1); // Distance [mm]
		chartSettings.setRangeSelectorDefaultAxisY(1); // Relative Intensity [%]
		chartSettings.setHorizontalSliderVisible(true);
		chartSettings.setVerticalSliderVisible(false);
		chartSettings.getRangeRestriction().setZeroX(true);
		chartSettings.getRangeRestriction().setZeroY(true);
		chartSettings.setCreateMenu(true);
		traceDataUI.applySettings(chartSettings);
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
		//
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

	private void initializeWavelengthsComboItems(Map<String, ISeriesData> wavelengthData) {

		comboWavelengths.removeAll();
		//
		List<String> wavelenghts = new ArrayList<String>();
		wavelenghts.add(SHOW_ALL_WAVELENGTHS);
		//
		if(wavelengthData != null) {
			List<String> wavelengthList = new ArrayList<>(wavelengthData.keySet());
			Collections.sort(wavelengthList);
			for(String wavelength : wavelengthList) {
				wavelenghts.add(wavelength);
			}
		}
		//
		comboWavelengths.setItems(wavelenghts.toArray(new String[wavelenghts.size()]));
		//
		int size = comboWavelengths.getItemCount();
		if(size > 0 && comboWavelengths.getSelectionIndex() == -1) {
			comboWavelengths.select(0);
		}
	}
}
