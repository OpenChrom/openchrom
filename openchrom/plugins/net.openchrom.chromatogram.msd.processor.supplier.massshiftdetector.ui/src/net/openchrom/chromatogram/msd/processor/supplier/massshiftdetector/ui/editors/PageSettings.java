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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
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
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorModel_v1000;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.ChromatogramImportRunnable;

public class PageSettings extends AbstractExtendedEditorPage implements IExtendedEditorPage {

	private static final Logger logger = Logger.getLogger(PageSettings.class);
	//
	private EditorProcessor editorProcessor;
	//
	private Text referenceChromatogramText;
	private Text isotopeChromatogramText;
	private Spinner startShiftLevelSpinner;
	private Spinner stopShiftLevelSpinner;
	private Label labelNotes;
	private Text descriptionText;
	//
	private ImageHyperlink referenceChromatogramHyperlink;
	private ImageHyperlink isotopeChromatogramHyperlink;
	private ImageHyperlink calculateHyperlink;

	public PageSettings(EditorProcessor editorProcessor, Composite container) {
		super("Settings", container, true);
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
		createPropertiesSection(body);
		createDescriptionSection(body);
		createProcessSection(body);
	}

	public void setFocus() {

		if(editorProcessor != null && editorProcessor.getProcessorData().getProcessorModel() != null) {
			ProcessorData processorData = editorProcessor.getProcessorData();
			ProcessorModel_v1000 processorModel = processorData.getProcessorModel();
			//
			referenceChromatogramText.setText(processorModel.getReferenceChromatogramPath());
			isotopeChromatogramText.setText(processorModel.getIsotopeChromatogramPath());
			startShiftLevelSpinner.setSelection(processorModel.getStartShiftLevel());
			stopShiftLevelSpinner.setSelection(processorModel.getStopShiftLevel());
			labelNotes.setText(processorModel.getNotes());
			descriptionText.setText(processorModel.getDescription());
			//
			IChromatogramMSD referenceChromatogram = processorData.getReferenceChromatogram();
			IChromatogramMSD isotopeChromatogram = processorData.getIsotopeChromatogram();
			//
			if(referenceChromatogram != null) {
				referenceChromatogramHyperlink.setEnabled(true);
			}
			//
			if(isotopeChromatogram != null) {
				isotopeChromatogramHyperlink.setEnabled(true);
			}
			//
			if(referenceChromatogram != null && isotopeChromatogram != null) {
				calculateHyperlink.setEnabled(true);
			}
		} else {
			referenceChromatogramText.setText("");
			isotopeChromatogramText.setText("");
			startShiftLevelSpinner.setSelection(0);
			stopShiftLevelSpinner.setSelection(0);
			labelNotes.setText("");
			descriptionText.setText("");
			referenceChromatogramHyperlink.setEnabled(false);
			isotopeChromatogramHyperlink.setEnabled(false);
			calculateHyperlink.setEnabled(false);
		}
	}

	private void createPropertiesSection(Composite parent) {

		Section section = createSection(parent, 3, "Settings", "The selected settings will be used for the current analysis.");
		Composite client = createClient(section, 3);
		//
		createReferenceChromatogramText(client);
		createIsotopeChromatogramText(client);
		createStartShiftLevelSpinner(client);
		createStopShiftLevelSpinner(client);
		createNotesLabel(client);
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
		//
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 50;
		gridData.heightHint = 20;
		startShiftLevelSpinner.setLayoutData(gridData);
		startShiftLevelSpinner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(startShiftLevelSpinner.getSelection() <= stopShiftLevelSpinner.getSelection()) {
					ProcessorData processorData = editorProcessor.getProcessorData();
					processorData.getProcessorModel().setStartShiftLevel(startShiftLevelSpinner.getSelection());
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
		//
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 50;
		gridData.heightHint = 20;
		stopShiftLevelSpinner.setLayoutData(gridData);
		stopShiftLevelSpinner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(stopShiftLevelSpinner.getSelection() >= startShiftLevelSpinner.getSelection()) {
					ProcessorData processorData = editorProcessor.getProcessorData();
					processorData.getProcessorModel().setStopShiftLevel(stopShiftLevelSpinner.getSelection());
				} else {
					stopShiftLevelSpinner.setSelection(stopShiftLevelSpinner.getSelection() + 1);
				}
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
		createCheckHyperlink(client, "Check Selected Chromatograms");
		referenceChromatogramHyperlink = createReferenceChromatogramHyperlink(client, "Open Reference Chromatogram");
		isotopeChromatogramHyperlink = createIsotopeChromatogramHyperlink(client, "Open Isotope Chromatogram");
		calculateHyperlink = createCalculateHyperlink(client, "Calculate Mass Shifts");
		/*
		 * Add the client to the section.
		 */
		section.setClient(client);
	}

	private ImageHyperlink createCheckHyperlink(Composite client, String text) {

		Shell shell = Display.getCurrent().getActiveShell();
		ImageHyperlink imageHyperlink = getFormToolkit().createImageHyperlink(client, SWT.NONE);
		imageHyperlink.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CHECK, IApplicationImage.SIZE_16x16));
		imageHyperlink.setText(text);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		imageHyperlink.setLayoutData(gridData);
		imageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

			public void linkActivated(HyperlinkEvent e) {

				List<IChromatogramMSD> chromatograms = new ArrayList<IChromatogramMSD>();
				String pathChromatogramReference = referenceChromatogramText.getText().trim();
				String pathChromatogramIsotope = isotopeChromatogramText.getText().trim();
				ChromatogramImportRunnable runnable = new ChromatogramImportRunnable(pathChromatogramReference, pathChromatogramIsotope);
				ProgressMonitorDialog monitor = new ProgressMonitorDialog(shell);
				//
				try {
					monitor.run(true, true, runnable);
					chromatograms = runnable.getChromatograms();
				} catch(InterruptedException e1) {
					logger.warn(e1);
				} catch(InvocationTargetException e1) {
					logger.warn(e1);
				}
				//
				updateChromatogramSelections(chromatograms);
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
					editorProcessor.focusPage(EditorProcessor.PAGE_INDEX_SHIFT_HEATMAP);
				} else {
					MessageDialog.openWarning(shell, "Chromatogram Selection", "Please select a reference and a shifted chromatogram.");
				}
			}
		});
		return imageHyperlink;
	}

	private void updateChromatogramSelections(List<IChromatogramMSD> chromatograms) {

		if(chromatograms.size() != 2) {
			// Feedback
			// labelChromatogramInfo.setText("Please select two reference chromatograms.");
			// labelChromatogramInfo.setForeground(Colors.WHITE);
			// labelChromatogramInfo.setBackground(Colors.RED);
		} else {
			IChromatogramMSD referenceChromatogram = chromatograms.get(0);
			IChromatogramMSD isotopeChromatogram = chromatograms.get(1);
			int numberOfScans1 = referenceChromatogram.getNumberOfScans();
			int numberOfScans2 = isotopeChromatogram.getNumberOfScans();
			//
			ProcessorData processorRawData = editorProcessor.getProcessorData();
			processorRawData.setReferenceChromatogram(referenceChromatogram);
			processorRawData.setIsotopeChromatogram(isotopeChromatogram);
			//
			referenceChromatogramHyperlink.setEnabled(true);
			isotopeChromatogramHyperlink.setEnabled(true);
			calculateHyperlink.setEnabled(true);
			//
			if(numberOfScans1 != numberOfScans2) {
				// Feedback
				// labelChromatogramInfo.setText("The selected chromatograms have a different number of scans (" + numberOfScans1 + " vs. " + numberOfScans2 + ").");
				// labelChromatogramInfo.setForeground(Colors.BLACK);
				// labelChromatogramInfo.setBackground(Colors.YELLOW);
			} else {
				// Feedback
				// labelChromatogramInfo.setText("The selected chromatograms are valid.");
				// labelChromatogramInfo.setForeground(Colors.BLACK);
				// labelChromatogramInfo.setBackground(Colors.GREEN);
			}
		}
		// Update Overlay?
	}
}
