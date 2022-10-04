/*******************************************************************************
 * Copyright (c) 2017, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
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
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.clipboard.ImageSupplier;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.eclipse.swtchart.extensions.core.ISeriesModificationListener;
import org.eclipse.swtchart.extensions.linecharts.LineChart;

import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support.DataProcessorUI;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.support.MeasurementModelData;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.preferences.PreferencePage;

public class TraceDataComparisonUI extends Composite {

	private static final Logger logger = Logger.getLogger(TraceDataComparisonUI.class);
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
	private String analysisType = ""; // Qualification, Validation
	private String sampleGroup = ""; // e.g. "0196"
	private String referenceGroup = ""; // e.g. "0236"
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

	@Override
	public boolean setFocus() {

		boolean focus = super.setFocus();
		loadSampleAndReferenceModelData();
		return focus;
	}

	public void setData(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		this.processorModel = editorProcessor.getProcessorModel();
		/*
		 * Refresh the combo items
		 */
		setSampleMeasurementComboBoxItems();
		setReferenceMeasurementComboBoxItems();
	}

	public void loadSampleAndReferenceModelData() {

		int track = getTrack();
		trackModel = retrieveTrackModel(track);
		//
		if(trackModel != null) {
			/*
			 * Set the data.
			 */
			setTrackComboItems(comboSampleTracks, trackModel.getSampleTrack(), measurementModelData.getMeasurementDataSize(analysisType, DataProcessorUI.MEASUREMENT_SAMPLE));
			setTrackComboItems(comboReferenceTracks, trackModel.getReferenceTrack(), measurementModelData.getMeasurementDataSize(analysisType, DataProcessorUI.MEASUREMENT_REFERENCE));
			/*
			 * Update the chart and combo boxes.
			 */
			traceDataUI.getBaseChart().suspendUpdate(true);
			traceDataUI.deleteSeries();
			setTrackData(trackModel.getSampleTrack(), DataProcessorUI.MEASUREMENT_SAMPLE);
			setTrackData(trackModel.getReferenceTrack(), DataProcessorUI.MEASUREMENT_REFERENCE);
			traceDataUI.getBaseChart().suspendUpdate(false);
			/*
			 * Update the widgets.
			 */
			updateChartTitle();
			setElementStatusAndValues();
			editorProcessor.setDirty(true);
			modifyDataStatusLabel();
		}
	}

	private ITrackModel retrieveTrackModel(int track) {

		/*
		 * Get the track model.
		 */
		ITrackModel trackModel = null;
		if(sampleGroup != null && !"".equals(sampleGroup) && referenceGroup != null && !"".equals(referenceGroup)) {
			trackModel = measurementModelData.loadTrackModel(processorModel, track, analysisType, sampleGroup, referenceGroup);
		}
		return trackModel;
	}

	private int getTrack() {

		if(comboSampleTracks.getSelectionIndex() == -1) {
			return 1; // Initialize
		} else {
			return comboSampleTracks.getSelectionIndex() + 1;
		}
	}

	private void setTrackData(int track, String type) {

		Map<Integer, Map<String, ISeriesData>> measurementsData = measurementModelData.getMeasurementData(analysisType, type);
		if(measurementsData != null) {
			String wavelengthSelection = comboWavelengths.getText();
			traceDataUI.addSeriesData(dataProcessorUI.getLineSeriesDataList(measurementsData, wavelengthSelection, track), LineChart.MEDIUM_COMPRESSION);
			updateChartTitle();
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

				loadPreviousTrack();
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
				if(comboReferenceMeasurements.getSelectionIndex() == -1) {
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Trace Compare", "Please select next a reference to analyze.");
				} else {
					loadSampleAndReferenceModelData();
				}
			}
		});
	}

	private void createComboSampleTracks(Composite parent) {

		comboSampleTracks = new Combo(parent, SWT.READ_ONLY);
		comboSampleTracks.setToolTipText("Sample Track");
		comboSampleTracks.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboSampleTracks.setItems(new String[]{});
		comboSampleTracks.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				loadSampleTrack();
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
				if(comboSampleMeasurements.getSelectionIndex() == -1) {
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Trace Compare", "Please select next a sample to analyze.");
				} else {
					loadSampleAndReferenceModelData();
				}
			}
		});
	}

	private void createComboReferenceTracks(Composite parent) {

		comboReferenceTracks = new Combo(parent, SWT.READ_ONLY);
		comboReferenceTracks.setToolTipText("Reference Track");
		comboReferenceTracks.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboReferenceTracks.setItems(new String[]{});
		comboReferenceTracks.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				loadReferenceTrack();
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

				loadNextTrack();
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
		createButtonToggleComments(composite);
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
			labelDataStatus.setBackground(Colors.getColor(Colors.LIGHT_YELLOW));
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

	private void createButtonToggleComments(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Show/Hide Comments");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT_DEFAULT, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isVisible = !notesText.isVisible();
				showComments(isVisible);
				//
				if(isVisible) {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_COLLAPSE_ALL, IApplicationImage.SIZE_16x16));
				} else {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT_DEFAULT, IApplicationImage.SIZE_16x16));
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

				if(trackModel != null) {
					/*
					 * Create a chart image.
					 */
					ImageSupplier imageSupplier = new ImageSupplier();
					String imagePath = dataProcessorUI.getImageName(processorModel, sampleGroup, referenceGroup, trackModel.getSampleTrack(), trackModel.getReferenceTrack());
					ImageData imageDataSample = imageSupplier.getImageData(traceDataUI.getBaseChart());
					imageSupplier.saveImage(imageDataSample, imagePath, SWT.IMAGE_PNG);
					trackModel.setPathSnapshot(imagePath);
					File imageFile = new File(imagePath);
					if(imageFile.exists()) {
						/*
						 * Refresh the workspace folder.
						 */
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IPath location = Path.fromOSString(imageFile.getAbsolutePath());
						IFile file = workspace.getRoot().getFileForLocation(location);
						try {
							file.refreshLocal(IResource.DEPTH_ZERO, null);
						} catch(CoreException e1) {
							logger.warn(e1);
						}
						//
						MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Save Image", "A screenshot of the sample and reference has been saved.");
						editorProcessor.setDirty(true);
					}
				}
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

				if(trackModel != null) {
					setMatched(!trackModel.isMatched());
					if(trackModel.isMatched()) {
						setEvaluated(true);
					}
				}
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

				if(trackModel != null) {
					setSkipped(!trackModel.isSkipped());
				}
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

				if(trackModel != null) {
					setEvaluated(!trackModel.isEvaluated());
				}
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
				if(preferenceDialog.open() == Window.OK) {
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

	private void createCommentsSection(Composite parent) {

		notesText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		notesText.setText("");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 80;
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		notesText.setLayoutData(gridData);
		notesText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(trackModel != null) {
					trackModel.setNotes(notesText.getText().trim());
				}
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

	private void loadPreviousTrack() {

		int track = comboSampleTracks.getSelectionIndex(); // no -1 as the index is 0 based
		track = (track <= 0) ? 1 : track;
		trackModel = retrieveTrackModel(track);
		updateTrackModels(track);
	}

	private void loadSampleTrack() {

		if(trackModel != null) {
			int track = comboSampleTracks.getSelectionIndex() + 1;
			trackModel.setSampleTrack(track);
			setTrackData(track, DataProcessorUI.MEASUREMENT_SAMPLE);
			setEvaluated(false);
		}
	}

	private void loadReferenceTrack() {

		if(trackModel != null) {
			int track = comboReferenceTracks.getSelectionIndex() + 1;
			trackModel.setReferenceTrack(track);
			setTrackData(track, DataProcessorUI.MEASUREMENT_REFERENCE);
			setEvaluated(false);
		}
	}

	private void loadNextTrack() {

		int sizeTracks = measurementModelData.getMeasurementDataSize(analysisType, DataProcessorUI.MEASUREMENT_SAMPLE);
		int track = comboSampleTracks.getSelectionIndex() + 2;
		track = (track > sizeTracks) ? sizeTracks : track;
		trackModel = retrieveTrackModel(track);
		updateTrackModels(track);
	}

	private void updateTrackModels(int track) {

		if(trackModel != null) {
			/*
			 * Sample Track
			 */
			int selection = track - 1;
			comboSampleTracks.select(selection);
			setTrackData(track, DataProcessorUI.MEASUREMENT_SAMPLE);
			/*
			 * Reference Track
			 */
			int referenceTrack = trackModel.getReferenceTrack();
			if(referenceTrack > 0) {
				comboReferenceTracks.select(referenceTrack - 1);
			} else {
				referenceTrack = track;
				comboReferenceTracks.select(selection);
			}
			setTrackData(referenceTrack, DataProcessorUI.MEASUREMENT_REFERENCE);
			setElementStatusAndValues();
		}
	}

	private void setElementStatusAndValues() {

		/*
		 * Button images.
		 */
		if(trackModel != null) {
			//
			notesText.setText(trackModel.getNotes());
			setButtonIcons();
			/*
			 * Enable/Disable buttons
			 */
			if(trackModel.isEvaluated() || trackModel.isSkipped()) {
				enableWidgets(false);
				if(trackModel.isEvaluated()) {
					buttonIsEvaluated.setEnabled(true);
				} else {
					buttonIsSkipped.setEnabled(true);
				}
			} else {
				enableWidgets(true);
			}
		} else {
			enableWidgets(false);
			comboSampleMeasurements.setEnabled(true);
			comboReferenceMeasurements.setEnabled(true);
			notesText.setText("");
		}
	}

	private void setButtonIcons() {

		String imageMatched = (trackModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
		String imageSkipped = (trackModel.isSkipped()) ? IApplicationImage.IMAGE_SKIPPED : IApplicationImage.IMAGE_SKIP;
		buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(imageSkipped, IApplicationImage.SIZE_16x16));
		String imageEvaluated = (trackModel.isEvaluated()) ? IApplicationImage.IMAGE_EVALUATED : IApplicationImage.IMAGE_EVALUATE;
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(imageEvaluated, IApplicationImage.SIZE_16x16));
	}

	private void enableWidgets(boolean enabled) {

		comboSampleMeasurements.setEnabled(enabled);
		comboReferenceMeasurements.setEnabled(enabled);
		comboSampleTracks.setEnabled(enabled);
		comboReferenceTracks.setEnabled(enabled);
		comboWavelengths.setEnabled(enabled);
		buttonCreateSnapshot.setEnabled(enabled);
		buttonIsMatched.setEnabled(enabled);
		buttonIsSkipped.setEnabled(enabled);
		buttonIsEvaluated.setEnabled(enabled);
	}

	private void setMatched(boolean isMatched) {

		if(trackModel != null) {
			trackModel.setMatched(isMatched);
			String imageMatched = (trackModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
			buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
			editorProcessor.setDirty(true);
		}
	}

	private void setSkipped(boolean isSkipped) {

		if(trackModel != null) {
			trackModel.setSkipped(isSkipped);
			String imageSkipped = (trackModel.isSkipped()) ? IApplicationImage.IMAGE_SKIPPED : IApplicationImage.IMAGE_SKIP;
			buttonIsSkipped.setImage(ApplicationImageFactory.getInstance().getImage(imageSkipped, IApplicationImage.SIZE_16x16));
			//
			if(isSkipped) {
				setEvaluated(false);
			}
			//
			setElementStatusAndValues();
			editorProcessor.setDirty(true);
		}
	}

	private void setEvaluated(boolean isEvaluated) {

		if(trackModel != null) {
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
			setElementStatusAndValues();
			editorProcessor.setDirty(true);
		}
	}

	private void setSelectedRange(Range rangeX, Range rangeY) {

		if(trackModel != null) {
			trackModel.setStartRetentionTime((rangeX != null) ? rangeX.lower : 0.0d);
			trackModel.setStopRetentionTime((rangeX != null) ? rangeX.upper : 0.0d);
			trackModel.setStartIntensity((rangeY != null) ? rangeY.lower : 0.0d);
			trackModel.setStopIntensity((rangeY != null) ? rangeY.upper : 0.0d);
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
		setMeasurementComboItems(comboSampleMeasurements, samplePatterns, sampleGroup);
		sampleGroup = comboSampleMeasurements.getText();
	}

	private void setReferenceMeasurementComboBoxItems() {

		String fileExtension = PreferenceSupplier.getFileExtension();
		String referenceDirectory = processorModel.getReferenceDirectory();
		//
		List<String> referencePatterns = dataProcessorUI.getMeasurementPatterns(referenceDirectory, fileExtension);
		setMeasurementComboItems(comboReferenceMeasurements, referencePatterns, referenceGroup);
		referenceGroup = comboReferenceMeasurements.getText();
	}

	private void setMeasurementComboItems(Combo combo, List<String> patterns, String selectedGroup) {

		combo.setItems(patterns.toArray(new String[patterns.size()]));
	}

	private void updateChartTitle() {

		StringBuilder builder = new StringBuilder();
		if(trackModel != null) {
			builder.append(analysisType);
			builder.append(": ");
			builder.append(sampleGroup);
			builder.append("-");
			builder.append(trackModel.getSampleTrack());
			builder.append(" vs. ");
			builder.append(referenceGroup);
			builder.append("-");
			builder.append(trackModel.getReferenceTrack());
		} else {
			builder.append(analysisType);
		}
		//
		String title = builder.toString();
		IChartSettings chartSettings = traceDataUI.getChartSettings();
		chartSettings.setTitle(title);
		traceDataUI.applySettings(chartSettings);
		traceDataUI.redraw();
	}
}
