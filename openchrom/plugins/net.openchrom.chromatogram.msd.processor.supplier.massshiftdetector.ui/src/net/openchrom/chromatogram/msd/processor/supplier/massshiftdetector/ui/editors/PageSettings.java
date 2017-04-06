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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editors;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
import org.eclipse.chemclipse.rcp.app.ui.addons.ModelSupportAddon;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.editors.AbstractExtendedEditorPage;
import org.eclipse.chemclipse.support.ui.editors.IExtendedEditorPage;
import org.eclipse.chemclipse.support.ui.wizards.ChromatogramWizardElements;
import org.eclipse.chemclipse.support.ui.wizards.IChromatogramWizardElements;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.msd.ui.support.ChromatogramSupport;
import org.eclipse.chemclipse.ux.extension.msd.ui.wizards.ChromatogramInputEntriesWizard;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.ChromatogramImportRunnable;

public class PageSettings extends AbstractExtendedEditorPage implements IExtendedEditorPage {

	private static final Logger logger = Logger.getLogger(PageSettings.class);
	//
	private static final String DESCRIPTION = "MASS SHIFT DETECTOR";
	//
	private EditorProcessor editorProcessor;
	//
	private Text referenceChromatogramText;
	private Text isotopeChromatogramText;
	private Spinner startShiftLevelSpinner;
	private Spinner stopShiftLevelSpinner;
	private Button normalizeDataCheckBox;
	private Combo ionSelectionStrategyCombo;
	private Text numberHighestIntensityMZText;
	private Button usePeaksCheckBox;
	private Label labelNotes;
	private Text descriptionText;
	//
	private ImageHyperlink referenceChromatogramHyperlink;
	private ImageHyperlink isotopeChromatogramHyperlink;
	private ImageHyperlink calculateHyperlink;
	//
	private String[] ionSelectionStrategies;

	public PageSettings(Composite container) {
		super("Settings", container, true);
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
	}

	@Override
	public void fillBody(ScrolledForm scrolledForm) {

		Composite body = scrolledForm.getBody();
		body.setLayout(new TableWrapLayout());
		body.setLayout(createFormTableWrapLayout(true, 3));
		/*
		 * 3 column layout
		 */
		createFileSelectionSection(body);
		createSettingsSection(body);
		createDescriptionSection(body);
		createProcessSection(body);
	}

	public void setFocus() {

		if(editorProcessor != null && editorProcessor.getProcessorData().getProcessorModel() != null) {
			ProcessorData processorData = editorProcessor.getProcessorData();
			IProcessorModel processorModel = processorData.getProcessorModel();
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			//
			referenceChromatogramText.setText(processorModel.getReferenceChromatogramPath());
			isotopeChromatogramText.setText(processorModel.getIsotopeChromatogramPath());
			startShiftLevelSpinner.setSelection(processorSettings.getStartShiftLevel());
			stopShiftLevelSpinner.setSelection(processorSettings.getStopShiftLevel());
			normalizeDataCheckBox.setSelection(processorSettings.isNormalizeData());
			ionSelectionStrategyCombo.setText(processorSettings.getIonSelectionStrategy());
			numberHighestIntensityMZText.setText(Integer.toString(processorSettings.getNumberHighestIntensityMZ()));
			usePeaksCheckBox.setSelection(processorSettings.isUsePeaks());
			labelNotes.setText(processorModel.getNotes());
			descriptionText.setText(processorModel.getDescription());
			//
			if(processorSettings.getIonSelectionStrategy().equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
				numberHighestIntensityMZText.setEnabled(true);
			} else {
				numberHighestIntensityMZText.setEnabled(false);
			}
			//
			IProcessingInfo processingInfo = validateSettings();
			boolean hasErrorMessage = processingInfo.hasErrorMessages();
			referenceChromatogramHyperlink.setEnabled(!hasErrorMessage);
			isotopeChromatogramHyperlink.setEnabled(!hasErrorMessage);
			calculateHyperlink.setEnabled(!hasErrorMessage);
			//
		} else {
			referenceChromatogramText.setText("");
			isotopeChromatogramText.setText("");
			startShiftLevelSpinner.setSelection(0);
			stopShiftLevelSpinner.setSelection(0);
			normalizeDataCheckBox.setSelection(false);
			ionSelectionStrategyCombo.setText(IProcessorSettings.STRATEGY_SELECT_ALL);
			numberHighestIntensityMZText.setText("1");
			usePeaksCheckBox.setSelection(false);
			labelNotes.setText("");
			descriptionText.setText("");
			referenceChromatogramHyperlink.setEnabled(false);
			isotopeChromatogramHyperlink.setEnabled(false);
			calculateHyperlink.setEnabled(false);
		}
	}

	private void createFileSelectionSection(Composite parent) {

		Section section = createSection(parent, 3, "File Selection", "The selected files are used for the current analysis.");
		Composite client = createClient(section, 3);
		//
		createReferenceChromatogramText(client);
		createIsotopeChromatogramText(client);
		createNotesLabel(client);
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private void createSettingsSection(Composite parent) {

		Section section = createSection(parent, 3, "Settings", "The selected settings are used for the current analysis.");
		section.setExpanded(false);
		Composite client = createClient(section, 2);
		//
		createStartShiftLevelSpinner(client);
		createStopShiftLevelSpinner(client);
		createNormalizeDataCheckBox(client);
		createIonSelectionStrategyCombo(client);
		createNumberHighestIntensityText(client);
		createUsePeaksCheckBox(client);
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private void createReferenceChromatogramText(Composite client) {

		createLabel(client, "Reference - Chromatogram:");
		//
		referenceChromatogramText = createText(client, SWT.BORDER, "");
		referenceChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(client, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Reference - Chromatogram", "Select the reference chromatogram.", PreferenceSupplier.getFilterPathReferenceChromatogram());
				WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						referenceChromatogramText.setText(selectedChromatogram);
						ProcessorData processorData = editorProcessor.getProcessorData();
						processorData.getProcessorModel().setReferenceChromatogramPath(selectedChromatogram);
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathReferenceChromatogram(file.getParentFile().toString());
						}
					}
				}
			}
		});
	}

	private void createIsotopeChromatogramText(Composite client) {

		createLabel(client, "Isotope - Chromatogram:");
		//
		isotopeChromatogramText = createText(client, SWT.BORDER, "");
		isotopeChromatogramText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Button button = new Button(client, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IChromatogramWizardElements chromatogramWizardElements = new ChromatogramWizardElements();
				ChromatogramInputEntriesWizard inputWizard = new ChromatogramInputEntriesWizard(chromatogramWizardElements, "Isotope - Chromatogram", "Select the isotope chromatogram.", PreferenceSupplier.getFilterPathIsotopeChromatogram());
				WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), inputWizard);
				wizardDialog.create();
				//
				if(wizardDialog.open() == WizardDialog.OK) {
					List<String> selectedChromatograms = chromatogramWizardElements.getSelectedChromatograms();
					if(selectedChromatograms.size() > 0) {
						String selectedChromatogram = chromatogramWizardElements.getSelectedChromatograms().get(0);
						isotopeChromatogramText.setText(selectedChromatogram);
						ProcessorData processorData = editorProcessor.getProcessorData();
						processorData.getProcessorModel().setIsotopeChromatogramPath(selectedChromatogram);
						//
						File file = new File(selectedChromatogram);
						if(file.exists()) {
							PreferenceSupplier.setFilterPathIsotopeChromatogram(file.getParentFile().toString());
						}
					}
				}
			}
		});
	}

	private void createStartShiftLevelSpinner(Composite client) {

		createLabel(client, "Start Shift Level:");
		//
		startShiftLevelSpinner = new Spinner(client, SWT.BORDER);
		startShiftLevelSpinner.setMinimum(MassShiftDetector.MIN_ISOTOPE_LEVEL);
		startShiftLevelSpinner.setMaximum(MassShiftDetector.MAX_ISOTOPE_LEVEL);
		startShiftLevelSpinner.setIncrement(MassShiftDetector.INCREMENT_ISOTOPE_LEVEL);
		GridData gridData = new GridData();
		gridData.widthHint = 50;
		gridData.heightHint = 20;
		startShiftLevelSpinner.setLayoutData(gridData);
		startShiftLevelSpinner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(startShiftLevelSpinner.getSelection() <= stopShiftLevelSpinner.getSelection()) {
					ProcessorData processorData = editorProcessor.getProcessorData();
					IProcessorModel processorModel = processorData.getProcessorModel();
					IProcessorSettings processorSettings = processorModel.getProcessorSettings();
					processorSettings.setStartShiftLevel(startShiftLevelSpinner.getSelection());
				} else {
					startShiftLevelSpinner.setSelection(startShiftLevelSpinner.getSelection() - 1);
				}
			}
		});
	}

	private void createStopShiftLevelSpinner(Composite client) {

		createLabel(client, "Stop Shift Level:");
		//
		stopShiftLevelSpinner = new Spinner(client, SWT.BORDER);
		stopShiftLevelSpinner.setMinimum(MassShiftDetector.MIN_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.setMaximum(MassShiftDetector.MAX_ISOTOPE_LEVEL);
		stopShiftLevelSpinner.setIncrement(MassShiftDetector.INCREMENT_ISOTOPE_LEVEL);
		GridData gridData = new GridData();
		gridData.widthHint = 50;
		gridData.heightHint = 20;
		stopShiftLevelSpinner.setLayoutData(gridData);
		stopShiftLevelSpinner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(stopShiftLevelSpinner.getSelection() >= startShiftLevelSpinner.getSelection()) {
					ProcessorData processorData = editorProcessor.getProcessorData();
					IProcessorModel processorModel = processorData.getProcessorModel();
					IProcessorSettings processorSettings = processorModel.getProcessorSettings();
					processorSettings.setStopShiftLevel(stopShiftLevelSpinner.getSelection());
				} else {
					stopShiftLevelSpinner.setSelection(stopShiftLevelSpinner.getSelection() + 1);
				}
			}
		});
	}

	private void createNormalizeDataCheckBox(Composite client) {

		normalizeDataCheckBox = new Button(client, SWT.CHECK);
		normalizeDataCheckBox.setText("Normalize Data");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		normalizeDataCheckBox.setLayoutData(gridData);
		normalizeDataCheckBox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ProcessorData processorData = editorProcessor.getProcessorData();
				IProcessorModel processorModel = processorData.getProcessorModel();
				IProcessorSettings processorSettings = processorModel.getProcessorSettings();
				processorSettings.setNormalizeData(normalizeDataCheckBox.getSelection());
			}
		});
	}

	private void createIonSelectionStrategyCombo(Composite client) {

		createLabel(client, "Ion Selection Strategy:");
		//
		ionSelectionStrategies = new String[]{IProcessorSettings.STRATEGY_SELECT_ALL, IProcessorSettings.STRATEGY_ABOVE_MEAN, IProcessorSettings.STRATEGY_ABOVE_MEDIAN, IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY};
		ionSelectionStrategyCombo = new Combo(client, SWT.READ_ONLY);
		ionSelectionStrategyCombo.setItems(ionSelectionStrategies);
		ionSelectionStrategyCombo.select(0);
		ionSelectionStrategyCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ionSelectionStrategyCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String ionSelectionStrategy = ionSelectionStrategyCombo.getText().trim();
				if(isValidStrategy(ionSelectionStrategy)) {
					ProcessorData processorData = editorProcessor.getProcessorData();
					IProcessorModel processorModel = processorData.getProcessorModel();
					IProcessorSettings processorSettings = processorModel.getProcessorSettings();
					processorSettings.setIonSelectionStrategy(ionSelectionStrategy);
				}
				//
				if(ionSelectionStrategy.equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
					numberHighestIntensityMZText.setEnabled(true);
				} else {
					numberHighestIntensityMZText.setEnabled(false);
				}
			}
		});
	}

	private void createNumberHighestIntensityText(Composite client) {

		createLabel(client, "Number n Highest Intesity m/z:");
		//
		numberHighestIntensityMZText = new Text(client, SWT.BORDER);
		numberHighestIntensityMZText.setText("");
		numberHighestIntensityMZText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		numberHighestIntensityMZText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				ProcessorData processorData = editorProcessor.getProcessorData();
				IProcessorModel processorModel = processorData.getProcessorModel();
				IProcessorSettings processorSettings = processorModel.getProcessorSettings();
				//
				if(processorSettings.getIonSelectionStrategy().equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
					try {
						int numberHighestIntensityMZ = Integer.parseInt(numberHighestIntensityMZText.getText().trim());
						processorSettings.setNumberHighestIntensityMZ(numberHighestIntensityMZ);
					} catch(NumberFormatException e1) {
						//
					}
				}
			}
		});
	}

	private void createUsePeaksCheckBox(Composite client) {

		usePeaksCheckBox = new Button(client, SWT.CHECK);
		usePeaksCheckBox.setText("Use Peaks");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		usePeaksCheckBox.setLayoutData(gridData);
		usePeaksCheckBox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ProcessorData processorData = editorProcessor.getProcessorData();
				IProcessorModel processorModel = processorData.getProcessorModel();
				IProcessorSettings processorSettings = processorModel.getProcessorSettings();
				processorSettings.setUsePeaks(usePeaksCheckBox.getSelection());
			}
		});
	}

	private void createNotesLabel(Composite client) {

		labelNotes = createLabel(client, "");
		labelNotes.setBackground(Colors.YELLOW);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		labelNotes.setLayoutData(gridData);
	}

	private void createDescriptionSection(Composite parent) {

		Section section = createSection(parent, 3, "Description", "A description of the current project is listed.");
		section.setExpanded(false);
		Composite client = createClient(section);
		/*
		 * Description
		 */
		descriptionText = createText(client, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL, "");
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 300;
		descriptionText.setLayoutData(gridData);
		descriptionText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				ProcessorData processorData = editorProcessor.getProcessorData();
				processorData.getProcessorModel().setDescription(descriptionText.getText().trim());
			}
		});
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private void createProcessSection(Composite parent) {

		Section section = createSection(parent, 3, "Process", "The selected chromatograms are processed with the given settings to detect mass shifts.");
		Composite client = createClient(section);
		/*
		 * Process
		 */
		createCheckHyperlink(client, "Check Selected Chromatograms and Settings");
		referenceChromatogramHyperlink = createReferenceChromatogramHyperlink(client, "Open Reference Chromatogram");
		isotopeChromatogramHyperlink = createIsotopeChromatogramHyperlink(client, "Open Isotope Chromatogram");
		calculateHyperlink = createCalculateHyperlink(client, "Calculate Mass Shifts");
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private ImageHyperlink createCheckHyperlink(Composite client, String text) {

		Display display = Display.getDefault();
		ImageHyperlink imageHyperlink = getFormToolkit().createImageHyperlink(client, SWT.NONE);
		imageHyperlink.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CHECK, IApplicationImage.SIZE_16x16));
		imageHyperlink.setText(text);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		imageHyperlink.setLayoutData(gridData);
		imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(HyperlinkEvent e) {

				String pathChromatogramReference = referenceChromatogramText.getText().trim();
				String pathChromatogramIsotope = isotopeChromatogramText.getText().trim();
				ChromatogramImportRunnable runnable = new ChromatogramImportRunnable(pathChromatogramReference, pathChromatogramIsotope);
				ProgressMonitorDialog monitor = new ProgressMonitorDialog(display.getActiveShell());
				//
				try {
					monitor.run(true, true, runnable);
				} catch(InterruptedException e1) {
					logger.warn(e1);
				} catch(InvocationTargetException e1) {
					logger.warn(e1);
				}
				//
				List<IChromatogramSelectionMSD> chromatogramSelections = runnable.getChromatogramSelections();
				ProcessorData processorRawData = editorProcessor.getProcessorData();
				processorRawData.setReferenceChromatogramSelection(chromatogramSelections.get(0));
				processorRawData.setIsotopeChromatogramSelection(chromatogramSelections.get(1));
				//
				display.asyncExec(new Runnable() {

					@Override
					public void run() {

						updateChromatogramSelections();
					}
				});
			}
		});
		return imageHyperlink;
	}

	private ImageHyperlink createReferenceChromatogramHyperlink(Composite client, String text) {

		ImageHyperlink imageHyperlink = getFormToolkit().createImageHyperlink(client, SWT.NONE);
		imageHyperlink.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		imageHyperlink.setText(text);
		imageHyperlink.setEnabled(false); // Default disabled
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		imageHyperlink.setLayoutData(gridData);
		imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(HyperlinkEvent e) {

				/*
				 * Opens the editor.
				 */
				ProcessorData processorData = editorProcessor.getProcessorData();
				if(processorData.getReferenceChromatogram() != null) {
					IChromatogramMSD chromatogram = processorData.getReferenceChromatogram();
					EModelService modelService = ModelSupportAddon.getModelService();
					MApplication application = ModelSupportAddon.getApplication();
					EPartService partService = ModelSupportAddon.getPartService();
					ChromatogramSupport.getInstanceEditorSupport().openEditor(chromatogram, modelService, application, partService);
				}
			}
		});
		return imageHyperlink;
	}

	private ImageHyperlink createIsotopeChromatogramHyperlink(Composite client, String text) {

		ImageHyperlink imageHyperlink = getFormToolkit().createImageHyperlink(client, SWT.NONE);
		imageHyperlink.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		imageHyperlink.setText(text);
		imageHyperlink.setEnabled(false); // Default disabled
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		imageHyperlink.setLayoutData(gridData);
		imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(HyperlinkEvent e) {

				/*
				 * Opens the editor.
				 */
				ProcessorData processorData = editorProcessor.getProcessorData();
				if(processorData.getIsotopeChromatogram() != null) {
					IChromatogramMSD chromatogram = processorData.getIsotopeChromatogram();
					EModelService modelService = ModelSupportAddon.getModelService();
					MApplication application = ModelSupportAddon.getApplication();
					EPartService partService = ModelSupportAddon.getPartService();
					ChromatogramSupport.getInstanceEditorSupport().openEditor(chromatogram, modelService, application, partService);
				}
			}
		});
		return imageHyperlink;
	}

	private ImageHyperlink createCalculateHyperlink(Composite client, String text) {

		Shell shell = Display.getCurrent().getActiveShell();
		ImageHyperlink imageHyperlink = getFormToolkit().createImageHyperlink(client, SWT.NONE);
		imageHyperlink.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXECUTE, IApplicationImage.SIZE_16x16));
		imageHyperlink.setText(text);
		imageHyperlink.setEnabled(false); // Default disabled
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		imageHyperlink.setLayoutData(gridData);
		imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(HyperlinkEvent e) {

				ProcessorData processorRawData = editorProcessor.getProcessorData();
				if(processorRawData.getReferenceChromatogram() != null && processorRawData.getIsotopeChromatogram() != null) {
					editorProcessor.setActivePage(EditorProcessor.PAGE_INDEX_SHIFT_HEATMAP);
				} else {
					MessageDialog.openWarning(shell, "Chromatogram Selection", "Please select a reference and a shifted chromatogram.");
				}
			}
		});
		return imageHyperlink;
	}

	private void updateChromatogramSelections() {

		/*
		 * Display display = Display.getDefault();
		 * instead of
		 * Display display = Display.getCurrent();
		 */
		editorProcessor.setDirty(true);
		IProcessingInfo processingInfo = validateSettings();
		boolean hasErrorMessage = processingInfo.hasErrorMessages();
		referenceChromatogramHyperlink.setEnabled(!hasErrorMessage);
		isotopeChromatogramHyperlink.setEnabled(!hasErrorMessage);
		calculateHyperlink.setEnabled(!hasErrorMessage);
		ProcessingInfoViewSupport.updateProcessingInfo(processingInfo, true);
	}

	private IProcessingInfo validateSettings() {

		IProcessingInfo processingInfo = new ProcessingInfo();
		ProcessorData processorData = editorProcessor.getProcessorData();
		IProcessorModel processorModel = processorData.getProcessorModel();
		IProcessorSettings processorSettings = processorModel.getProcessorSettings();
		//
		if(processorData.getReferenceChromatogram() == null || processorData.getIsotopeChromatogram() == null) {
			processingInfo.addErrorMessage(DESCRIPTION, "Please select a reference and an isotope chromatogram.");
		} else {
			int numberOfScans1 = processorData.getReferenceChromatogram().getNumberOfScans();
			int numberOfScans2 = processorData.getIsotopeChromatogram().getNumberOfScans();
			//
			if(numberOfScans1 != numberOfScans2) {
				processingInfo.addWarnMessage(DESCRIPTION, "The selected chromatograms have a different number of scans (" + numberOfScans1 + " vs. " + numberOfScans2 + ").");
			} else {
				processingInfo.addInfoMessage(DESCRIPTION, "The selected chromatograms are valid.");
			}
			//
			if(processorSettings.getStartShiftLevel() > processorSettings.getStopShiftLevel()) {
				processingInfo.addErrorMessage(DESCRIPTION, "The start shift level must be <= stop shift level.");
			}
			//
			String ionSelectionStrategy = processorSettings.getIonSelectionStrategy();
			if(!isValidStrategy(ionSelectionStrategy)) {
				processingInfo.addErrorMessage(DESCRIPTION, "Please select a valid ion selection strategy.");
			}
			//
			if(ionSelectionStrategy.equals(IProcessorSettings.STRATEGY_N_HIGHEST_INTENSITY)) {
				int numberHighestIntensityMZ = processorSettings.getNumberHighestIntensityMZ();
				if(numberHighestIntensityMZ < IProcessorSettings.MIN_N_HIGHEST_INTENSITY || numberHighestIntensityMZ > IProcessorSettings.MAX_N_HIGHEST_INTENSITY) {
					processingInfo.addErrorMessage(DESCRIPTION, "Allowed range highest intensity m/z values (" + IProcessorSettings.MIN_N_HIGHEST_INTENSITY + " - " + IProcessorSettings.MAX_N_HIGHEST_INTENSITY + ").");
				}
			}
		}
		//
		return processingInfo;
	}

	private boolean isValidStrategy(String ionSelectionStrategy) {

		for(String strategy : ionSelectionStrategies) {
			if(strategy.equals(ionSelectionStrategy)) {
				return true;
			}
		}
		return false;
	}
}
