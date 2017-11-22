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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.xwc.ExtractedWavelengthSignalExtractor;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignals;
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
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.swtchart.IAxis;
import org.swtchart.LineStyle;
import org.swtchart.Range;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.ReferenceModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.model.v1000.TrackModel_v1000;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables.MeasurementImportRunnable;

public class TraceDataComparisonUI extends Composite {

	public static final String SAMPLE = "Sample";
	public static final String REFERENCE = "Reference";
	//
	public static final String TYPE_QUALIFICATION = "Qualification";
	public static final String TYPE_VALIDATION = "Validation";
	//
	private static final Logger logger = Logger.getLogger(TraceDataComparisonUI.class);
	//
	private static final int HORIZONTAL_INDENT = 15;
	private static final String SHOW_ALL_WAVELENGTHS = "Show All";
	//
	private EditorProcessor editorProcessor;
	//
	private Combo comboSampleMeasurements;
	private Combo comboReferenceMeasurements;
	private Combo comboSampleTracks;
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
	private String sampleGroup = ""; // "0196"
	private String referenceGroup = ""; // "0236"
	private Map<Integer, Map<String, ISeriesData>> sampleMeasurementsData;
	private Map<Integer, Map<String, ISeriesData>> referenceMeasurementsData;
	private Map<String, Map<Integer, Map<String, ISeriesData>>> modelData = new HashMap<String, Map<Integer, Map<String, ISeriesData>>>();
	//
	private Map<String, Color> colorMap;
	private Color colorDefault;
	//
	private DataProcessor dataProcessor;

	public TraceDataComparisonUI(Composite parent, int style, String type) {
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
		colorDefault = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		//
		this.type = type;
		dataProcessor = new DataProcessor();
		initialize();
	}

	public void setData(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		this.processorModel = editorProcessor.getProcessorModel();
		//
		initializeModelData();
	}

	private void initializeModelData() {

		/*
		 * Get the sample and reference.
		 */
		List<File> sampleFiles = getSampleFiles();
		List<File> referenceFiles = getReferenceFiles();
		/*
		 * Get the model.
		 */
		ReferenceModel_v1000 referenceModel = processorModel.getReferenceModels().get(referenceGroup);
		if(referenceModel == null) {
			referenceModel = new ReferenceModel_v1000();
			referenceModel.setReferenceGroup(referenceGroup);
			referenceModel.setReferencePath(PreferenceSupplier.getReferenceDirectory());
			processorModel.getReferenceModels().put(referenceGroup, referenceModel);
		}
		//
		sampleGroup = comboSampleMeasurements.getText();
		referenceGroup = comboReferenceMeasurements.getText();
		/*
		 * Extract the data.
		 * 0196 [Group]
		 * -> 1 [Track] -> 190 [nm], ISeriesData
		 * -> 1 [Track] -> 200 [nm], ISeriesData
		 * ...
		 * -> 18 [Track] -> 190 [nm], ISeriesData
		 * -> 18 [Track] -> 200 [nm], ISeriesData
		 * 0197 [Group]
		 * -> 1 [Track] -> 190 [nm], ISeriesData
		 * -> 1 [Track] -> 200 [nm], ISeriesData
		 * ...
		 * -> 18 [Track] -> 190 [nm], ISeriesData
		 * -> 18 [Track] -> 200 [nm], ISeriesData
		 */
		sampleMeasurementsData = modelData.get(sampleGroup);
		if(sampleMeasurementsData == null) {
			sampleMeasurementsData = extractMeasurementsData(sampleFiles, SAMPLE);
			modelData.put(sampleGroup, sampleMeasurementsData);
		}
		//
		referenceMeasurementsData = modelData.get(referenceGroup);
		if(referenceMeasurementsData == null) {
			referenceMeasurementsData = extractMeasurementsData(referenceFiles, REFERENCE);
			modelData.put(referenceGroup, referenceMeasurementsData);
		}
		/*
		 * Track #
		 */
		int track = 1;
		trackModel = referenceModel.getTrackModels().get(track);
		if(trackModel == null) {
			trackModel = new TrackModel_v1000();
			trackModel.setSampleTrack(track);
			referenceModel.getTrackModels().put(track, (TrackModel_v1000)trackModel);
		}
		/*
		 * Set the current velocity and the reference track.
		 * Set the reference track only if it is 0.
		 * The user may have selected another reference track.
		 */
		trackModel.setScanVelocity(PreferenceSupplier.getScanVelocity());
		if(trackModel.getReferenceTrack() == 0) {
			trackModel.setReferenceTrack(track);
		}
	}

	private List<File> getSampleFiles() {

		List<File> sampleFiles = new ArrayList<File>();
		if(processorModel != null) {
			/*
			 * Sample files.
			 */
			if("".equals(sampleGroup) || !sampleGroup.equals(comboSampleMeasurements.getText())) {
				String fileExtension = PreferenceSupplier.getFileExtension();
				String sampleDirectory = PreferenceSupplier.getSampleDirectory();
				List<String> samplePatterns = dataProcessor.getMeasurementPatterns(sampleDirectory, fileExtension);
				setMeasurementComboItems(comboSampleMeasurements, samplePatterns);
				sampleGroup = comboSampleMeasurements.getText();
				sampleFiles = dataProcessor.getMeasurementFiles(sampleDirectory, fileExtension, sampleGroup);
			}
		}
		//
		return sampleFiles;
	}

	private List<File> getReferenceFiles() {

		List<File> referenceFiles = new ArrayList<File>();
		if(processorModel != null) {
			/*
			 * Reference files.
			 */
			if("".equals(referenceGroup) || !referenceGroup.equals(comboReferenceMeasurements.getText())) {
				String fileExtension = PreferenceSupplier.getFileExtension();
				String referenceDirectory = PreferenceSupplier.getReferenceDirectory();
				List<String> referencePatterns = dataProcessor.getMeasurementPatterns(referenceDirectory, fileExtension);
				setMeasurementComboItems(comboReferenceMeasurements, referencePatterns);
				referenceGroup = comboReferenceMeasurements.getText();
				referenceFiles = dataProcessor.getMeasurementFiles(referenceDirectory, fileExtension, referenceGroup);
			}
		}
		//
		return referenceFiles;
	}

	public void loadData() {

		int sampleTrack = trackModel.getSampleTrack();
		initializeWavelengthsComboItems(sampleMeasurementsData.get(sampleTrack));
		reloadData();
	}

	public void reloadData() {

		initializeModelData();
		/*
		 * Update the chart and combo boxes.
		 */
		traceDataUI.getBaseChart().suspendUpdate(true);
		traceDataUI.deleteSeries();
		int sampleTrack = trackModel.getSampleTrack();
		setTrackComboItems(comboSampleTracks, sampleTrack, sampleMeasurementsData.keySet().size());
		setSampleData(sampleTrack);
		trackModel.setSampleTrack(sampleTrack);
		int referenceTrack = trackModel.getReferenceTrack();
		setTrackComboItems(comboReferenceTracks, referenceTrack, referenceMeasurementsData.keySet().size());
		setReferenceData(sampleTrack);
		trackModel.setReferenceTrack(referenceTrack);
		traceDataUI.getBaseChart().suspendUpdate(false);
		/*
		 * Update the label and notes.
		 */
		notesText.setText(trackModel.getNotes());
		String title = type + ": " + sampleGroup;
		IChartSettings chartSettings = traceDataUI.getChartSettings();
		chartSettings.setTitle(title);
		chartSettings.setTitleVisible(true);
		chartSettings.setTitleColor(Colors.BLACK);
		traceDataUI.applySettings(chartSettings);
		traceDataUI.redraw();
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

	private void setTrackData(int track) {

		setSampleData(track);
		setReferenceData(track);
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
		createButtonSectionTracks(this);
		createButtonSectionIdentify(this);
		createCommentsSection(this);
		createTraceDataSection(this);
		//
		showComments(false);
	}

	private void createButtonSectionTracks(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(6, false));
		//
		createButtonPreviousTrack(composite);
		createComboSampleMeasurements(composite);
		createComboSampleTracks(composite);
		createComboReferenceMeasurements(composite);
		createComboReferenceTracks(composite);
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

				int track = comboSampleTracks.getSelectionIndex(); // no -1 as the index is 0 based
				track = (track <= 0) ? 1 : track;
				trackModel.setSampleTrack(track);
				int selection = track - 1;
				comboSampleTracks.select(selection);
				comboReferenceTracks.select(selection);
				setTrackData(track);
			}
		});
	}

	private void createComboSampleMeasurements(Composite parent) {

		comboSampleMeasurements = new Combo(parent, SWT.READ_ONLY);
		comboSampleMeasurements.setToolTipText("Sample Measurements");
		comboSampleMeasurements.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboSampleMeasurements.setItems(new String[]{});
		comboSampleMeasurements.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				sampleGroup = comboSampleMeasurements.getText();
				reloadData();
			}
		});
	}

	private void createComboSampleTracks(Composite parent) {

		comboSampleTracks = new Combo(parent, SWT.READ_ONLY);
		comboSampleTracks.setToolTipText("Sample Track");
		comboSampleTracks.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setTrackComboItems(comboSampleTracks, -1, 0);
		comboSampleTracks.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int track = comboSampleTracks.getSelectionIndex() + 1;
				trackModel.setSampleTrack(track);
				setSampleData(track);
				setEvaluated(false);
			}
		});
	}

	private void createComboReferenceMeasurements(Composite parent) {

		comboReferenceMeasurements = new Combo(parent, SWT.READ_ONLY);
		comboReferenceMeasurements.setToolTipText("Reference Measurements");
		comboReferenceMeasurements.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboReferenceMeasurements.setItems(new String[]{});
		comboReferenceMeasurements.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				referenceGroup = comboReferenceMeasurements.getText();
				reloadData();
			}
		});
	}

	private void createComboReferenceTracks(Composite parent) {

		comboReferenceTracks = new Combo(parent, SWT.READ_ONLY);
		comboReferenceTracks.setToolTipText("Reference Track");
		comboReferenceTracks.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setTrackComboItems(comboReferenceTracks, -1, 0);
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

	private void createButtonNextTrack(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Select the next track.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_NEXT_YELLOW, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				int sizeTracks = sampleMeasurementsData.keySet().size();
				int track = comboSampleTracks.getSelectionIndex() + 2;
				track = (track > sizeTracks) ? sizeTracks : track;
				trackModel.setSampleTrack(track);
				int selection = track - 1;
				comboSampleTracks.select(selection);
				comboReferenceTracks.select(selection);
				setTrackData(track);
			}
		});
	}

	private void createButtonSectionIdentify(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		gridDataComposite.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(7, false));
		//
		createComboWavelengths(composite);
		createButtonFlipComments(composite);
		createButtonCreateSnapshot(composite);
		createButtonIsMatched(composite);
		createButtonIsSkipped(composite);
		createButtonIsEvaluated(composite);
		createButtonToggleLegend(composite);
	}

	private void createComboWavelengths(Composite parent) {

		comboWavelengths = new Combo(parent, SWT.READ_ONLY);
		comboWavelengths.setToolTipText("Selected Wavelength");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
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
				trackModel.setPathSnapshots(fileNameSample);
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
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 150;
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

	private void setTrackComboItems(Combo combo, int selectedTrack, int numberTracks) {

		/*
		 * Remove and set the items.
		 */
		combo.removeAll();
		List<String> tracks = new ArrayList<String>();
		for(int i = 1; i <= numberTracks; i++) {
			tracks.add("Track " + i);
		}
		combo.setItems(tracks.toArray(new String[tracks.size()]));
		/*
		 * Select the default item.
		 */
		int size = combo.getItemCount();
		if(size > 0) {
			if(selectedTrack > 0 && selectedTrack <= size) {
				combo.select(selectedTrack - 1);
			} else {
				combo.select(0);
			}
		}
	}

	private void setMeasurementComboItems(Combo combo, List<String> samplePatterns) {

		combo.setItems(samplePatterns.toArray(new String[samplePatterns.size()]));
		if(combo.getItemCount() > 0 && combo.getSelectionIndex() == -1) {
			combo.select(0);
		}
	}

	private void initializeWavelengthsComboItems(Map<String, ISeriesData> wavelengthData) {

		comboWavelengths.removeAll();
		//
		List<String> wavelenghts = new ArrayList<String>();
		wavelenghts.add(SHOW_ALL_WAVELENGTHS);
		//
		if(wavelengthData != null) {
			List<String> wavelengthList = new ArrayList<String>(wavelengthData.keySet());
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

	// ----------------------------------------------------------------------------------------------
	private Map<Integer, Map<String, ISeriesData>> extractMeasurementsData(List<File> measurementFiles, String type) {

		Map<Integer, Map<String, ISeriesData>> measurementsData = new HashMap<Integer, Map<String, ISeriesData>>();
		//
		MeasurementImportRunnable runnable = new MeasurementImportRunnable(measurementFiles);
		ProgressMonitorDialog monitor = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
		try {
			monitor.run(true, true, runnable);
		} catch(InterruptedException e1) {
			logger.warn(e1);
		} catch(InvocationTargetException e1) {
			logger.warn(e1);
		}
		//
		ISeriesData seriesData;
		String wavelength;
		//
		List<IChromatogramWSD> measurements = runnable.getMeasurements();
		for(IChromatogram measurement : measurements) {
			/*
			 * Track 1
			 */
			int index = 1;
			seriesData = extractMeasurement(measurement, type);
			wavelength = Integer.toString(getWavelength(measurement));
			addMeasurementData(measurementsData, wavelength, seriesData, index++);
			/*
			 * Track 2 ... n
			 */
			for(IChromatogram additionalMeasurement : measurement.getReferencedChromatograms()) {
				seriesData = extractMeasurement(additionalMeasurement, type);
				wavelength = Integer.toString(getWavelength(additionalMeasurement));
				addMeasurementData(measurementsData, wavelength, seriesData, index++);
			}
		}
		//
		return measurementsData;
	}

	private void addMeasurementData(Map<Integer, Map<String, ISeriesData>> measurementsData, String wavelength, ISeriesData seriesData, int index) {

		Map<String, ISeriesData> wavelengthData = measurementsData.get(index);
		if(wavelengthData == null) {
			wavelengthData = new HashMap<String, ISeriesData>();
			measurementsData.put(index, wavelengthData);
		}
		wavelengthData.put(wavelength, seriesData);
	}

	private ISeriesData extractMeasurement(IChromatogram measurement, String type) {

		List<IScan> scans = measurement.getScans();
		double[] xSeries = new double[scans.size()];
		double[] ySeries = new double[scans.size()];
		int wavelength = getWavelength(measurement);
		//
		int index = 0;
		ExtractedWavelengthSignalExtractor signalExtractor = new ExtractedWavelengthSignalExtractor((IChromatogramWSD)measurement);
		IExtractedWavelengthSignals extractedWavelengthSignals = signalExtractor.getExtractedWavelengthSignals();
		for(IExtractedWavelengthSignal extractedWavelengthSignal : extractedWavelengthSignals.getExtractedWavelengthSignals()) {
			xSeries[index] = extractedWavelengthSignal.getRetentionTime();
			ySeries[index] = extractedWavelengthSignal.getAbundance(wavelength);
			index++;
		}
		/*
		 * Sample 210 nm
		 * ...
		 */
		return new SeriesData(xSeries, ySeries, type + " " + Integer.toString(wavelength) + " nm");
	}

	private int getWavelength(IChromatogram measurement) {

		for(IScan scan : measurement.getScans()) {
			if(scan instanceof IScanWSD) {
				IScanWSD scanWSD = (IScanWSD)scan;
				for(IScanSignalWSD signal : scanWSD.getScanSignals()) {
					int wavelength = (int)signal.getWavelength();
					return wavelength;
				}
			}
		}
		//
		return 0;
	}
}
