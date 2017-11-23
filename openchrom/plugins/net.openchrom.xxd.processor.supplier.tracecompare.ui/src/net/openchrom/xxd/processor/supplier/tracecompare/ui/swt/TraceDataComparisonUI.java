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
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.eavp.service.swtchart.core.BaseChart;
import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.ISeriesModificationListener;
import org.eclipse.eavp.service.swtchart.images.ImageSupplier;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import net.openchrom.xxd.processor.supplier.tracecompare.model.IReferenceModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support.DataProcessorUI;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support.MeasurementModelData;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.preferences.PreferencePage;

public class TraceDataComparisonUI extends Composite {

	private static final int HORIZONTAL_INDENT = 15;
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
	private Label labelDataStatus;
	//
	private TraceDataUI traceDataUI;
	//
	private String analysisType = "";
	private String sampleGroup = ""; // e.g. "0196"
	private String referenceGroup = ""; // e.g. "0236"
	private String sampleGroupSelection = "";
	private String referenceGroupSelection = "";
	//
	private IProcessorModel processorModel;
	private ITrackModel trackModel;
	private DataProcessorUI dataProcessorUI;
	private MeasurementModelData measurementModelData;

	public TraceDataComparisonUI(Composite parent, int style, String analysisType) {
		super(parent, style);
		this.analysisType = analysisType;
		dataProcessorUI = new DataProcessorUI();
		measurementModelData = new MeasurementModelData();
		initialize();
	}

	public void setData(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		this.processorModel = editorProcessor.getProcessorModel();
		/*
		 * Refresh the combo items
		 */
		setSampleMeasurementComboBoxItems();
		setReferenceMeasurementComboBoxItems();
		//
		initializeSampleAndReferenceModelData();
	}

	private void initializeSampleAndReferenceModelData() {

		/*
		 * Get the sample and reference.
		 */
		String fileExtension = PreferenceSupplier.getFileExtension();
		String sampleDirectory = processorModel.getSampleDirectory();
		String referenceDirectory = processorModel.getReferenceDirectory();
		//
		List<File> sampleFiles = dataProcessorUI.getMeasurementFileList(processorModel, fileExtension, sampleDirectory, sampleGroup, sampleGroupSelection);
		List<File> referenceFiles = dataProcessorUI.getMeasurementFileList(processorModel, fileExtension, referenceDirectory, referenceGroup, referenceGroupSelection);
		/*
		 * Refresh the combo items
		 */
		setSampleMeasurementComboBoxItems();
		setReferenceMeasurementComboBoxItems();
		/*
		 * Track Model
		 */
		IReferenceModel referenceModel = measurementModelData.loadModelData(processorModel, sampleFiles, referenceFiles, sampleGroup, referenceGroup);
		trackModel = measurementModelData.loadTrackModel(referenceModel);
	}

	public void loadSampleAndReferenceModelData() {

		initializeSampleAndReferenceModelData();
		//
		setTrackComboItems(comboSampleTracks, trackModel.getSampleTrack(), measurementModelData.getMeasurementDataSize(DataProcessorUI.MEASUREMENT_SAMPLE));
		setTrackComboItems(comboReferenceTracks, trackModel.getReferenceTrack(), measurementModelData.getMeasurementDataSize(DataProcessorUI.MEASUREMENT_REFERENCE));
		/*
		 * Update the chart and combo boxes.
		 */
		traceDataUI.getBaseChart().suspendUpdate(true);
		traceDataUI.deleteSeries();
		setTrackData(trackModel.getSampleTrack(), DataProcessorUI.MEASUREMENT_SAMPLE);
		setTrackData(trackModel.getReferenceTrack(), DataProcessorUI.MEASUREMENT_REFERENCE);
		traceDataUI.getBaseChart().suspendUpdate(false);
		/*
		 * Update the label and notes.
		 */
		notesText.setText(trackModel.getNotes());
		String title = analysisType + ": " + sampleGroup + " vs. " + referenceGroup;
		IChartSettings chartSettings = traceDataUI.getChartSettings();
		chartSettings.setTitle(title);
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
		modifyDataStatusLabel();
	}

	private void setTrackData(int track) {

		setTrackData(track, DataProcessorUI.MEASUREMENT_SAMPLE);
		setTrackData(track, DataProcessorUI.MEASUREMENT_REFERENCE);
	}

	private void setTrackData(int track, String type) {

		Map<Integer, Map<String, ISeriesData>> measurementsData = measurementModelData.getMeasurementData(type);
		if(measurementsData != null) {
			String wavelengthSelection = comboWavelengths.getText();
			traceDataUI.addSeriesData(dataProcessorUI.getLineSeriesDataList(measurementsData, wavelengthSelection, track), LineChart.MEDIUM_COMPRESSION);
		}
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

				sampleGroupSelection = comboSampleMeasurements.getText();
				loadSampleAndReferenceModelData();
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
				setTrackData(track, DataProcessorUI.MEASUREMENT_SAMPLE);
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

				referenceGroupSelection = comboReferenceMeasurements.getText();
				loadSampleAndReferenceModelData();
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
				setTrackData(track, DataProcessorUI.MEASUREMENT_REFERENCE);
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

				int sizeTracks = measurementModelData.getMeasurementDataSize(DataProcessorUI.MEASUREMENT_SAMPLE);
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
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(10, false));
		//
		createDataStatusLabel(composite);
		createComboWavelengths(composite);
		createButtonFlipComments(composite);
		createButtonCreateSnapshot(composite);
		createButtonIsMatched(composite);
		createButtonIsSkipped(composite);
		createButtonIsEvaluated(composite);
		createButtonToggleLegend(composite);
		createButtonSettings(composite);
		createButtonReset(composite);
	}

	private void createDataStatusLabel(Composite parent) {

		labelDataStatus = new Label(parent, SWT.NONE);
		labelDataStatus.setToolTipText("Indicates whether the data has been modified or not.");
		labelDataStatus.setText("");
		labelDataStatus.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void modifyDataStatusLabel() {

		if(traceDataUI.getBaseChart().isDataShifted()) {
			labelDataStatus.setText("Shifted Data");
			labelDataStatus.setBackground(Colors.YELLOW);
		} else {
			labelDataStatus.setText("");
			labelDataStatus.setBackground(null);
		}
	}

	private void createComboWavelengths(Composite parent) {

		comboWavelengths = new Combo(parent, SWT.READ_ONLY);
		comboWavelengths.setToolTipText("Selected Wavelength");
		GridData gridData = new GridData();
		gridData.widthHint = 150;
		comboWavelengths.setLayoutData(gridData);
		comboWavelengths.setItems(dataProcessorUI.getWavelengthItems());
		comboWavelengths.select(0);
		comboWavelengths.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				loadSampleAndReferenceModelData();
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

	private void createButtonSettings(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Open the Settings");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IPreferencePage preferencePage = new PreferencePage();
				preferencePage.setTitle("Trace Compare");
				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", preferencePage));
				//
				PreferenceDialog preferenceDialog = new PreferenceDialog(Display.getDefault().getActiveShell(), preferenceManager);
				preferenceDialog.create();
				preferenceDialog.setMessage("Settings");
				if(preferenceDialog.open() == PreferenceDialog.OK) {
					try {
						dataProcessorUI.reloadColors();
						loadSampleAndReferenceModelData();
					} catch(Exception e1) {
						MessageDialog.openError(Display.getDefault().getActiveShell(), "Settings", "Something has gone wrong to apply the chart settings.");
					}
				}
			}
		});
	}

	private void createButtonReset(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Reset the chart");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_RESET, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				loadSampleAndReferenceModelData();
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
		chartSettings.getRangeRestriction().setZeroY(false);
		chartSettings.setSupportDataShift(true);
		chartSettings.setTitle("");
		chartSettings.setTitleVisible(true);
		chartSettings.setTitleColor(Colors.BLACK);
		chartSettings.setCreateMenu(true);
		traceDataUI.applySettings(chartSettings);
		//
		BaseChart baseChart = traceDataUI.getBaseChart();
		baseChart.addSeriesModificationListener(new ISeriesModificationListener() {

			@Override
			public void handleSeriesModificationEvent() {

				modifyDataStatusLabel();
			}
		});
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

	private void setSampleMeasurementComboBoxItems() {

		String fileExtension = PreferenceSupplier.getFileExtension();
		String sampleDirectory = processorModel.getSampleDirectory();
		//
		List<String> samplePatterns = dataProcessorUI.getMeasurementPatterns(sampleDirectory, fileExtension);
		setMeasurementComboItems(comboSampleMeasurements, samplePatterns, sampleGroupSelection);
		sampleGroup = comboSampleMeasurements.getText();
	}

	private void setReferenceMeasurementComboBoxItems() {

		String fileExtension = PreferenceSupplier.getFileExtension();
		String referenceDirectory = processorModel.getReferenceDirectory();
		//
		List<String> referencePatterns = dataProcessorUI.getMeasurementPatterns(referenceDirectory, fileExtension);
		setMeasurementComboItems(comboReferenceMeasurements, referencePatterns, referenceGroupSelection);
		referenceGroup = comboReferenceMeasurements.getText();
	}

	private void setMeasurementComboItems(Combo combo, List<String> patterns, String selectedGroup) {

		combo.setItems(patterns.toArray(new String[patterns.size()]));
		if(combo.getItemCount() > 0 && "".equals(selectedGroup)) {
			combo.select(0);
		} else {
			if(patterns.contains(selectedGroup)) {
				combo.setText(selectedGroup);
			} else {
				combo.select(0);
			}
		}
	}
}
