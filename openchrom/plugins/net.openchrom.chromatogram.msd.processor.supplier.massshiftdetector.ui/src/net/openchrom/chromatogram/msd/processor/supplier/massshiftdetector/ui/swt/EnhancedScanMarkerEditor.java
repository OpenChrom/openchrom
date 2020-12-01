/*******************************************************************************
 * Copyright (c) 2017, 2020 Lablicate GmbH.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.ui.Activator;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.listener.AbstractControllerComposite;
import org.eclipse.chemclipse.swt.ui.notifier.UpdateNotifierUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.editors.ChromatogramEditorMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editors.EditorProcessor;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.ScanMarkerDetectorRunnable;

public class EnhancedScanMarkerEditor extends AbstractControllerComposite {

	private static final Logger logger = Logger.getLogger(EnhancedScanMarkerEditor.class);
	//
	private EditorProcessor editorProcessor;
	//
	private Button buttonCalculate;
	private Button buttonPrevious;
	private Button buttonExport;
	private List<Button> buttons;
	//
	private ScanMarkerListUI scanMarkerListUI;
	private MassShiftListUI massShiftListUI;
	private Label scanMarkerInfoLabel;

	public EnhancedScanMarkerEditor(Composite parent, int style) {

		super(parent, style);
		buttons = new ArrayList<Button>();
		createControl();
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		editorProcessor.setDirty(true);
		/*
		 * Try to set the loaded list.
		 */
		try {
			if(scanMarkerListUI.getTable().getItemCount() == 0) {
				List<IScanMarker> scanMarkerList = editorProcessor.getProcessorData().getProcessorModel().getScanMarker();
				scanMarkerListUI.setInput(scanMarkerList);
				if(scanMarkerList.size() > 0) {
					scanMarkerListUI.getTable().select(0);
					updateMassShiftList();
					updateComparisonViews(getDisplay());
					setScanMarkerInfoLabel(scanMarkerList.size());
				}
			}
		} catch(Exception e) {
			//
		}
	}

	@Override
	public boolean setFocus() {

		return super.setFocus();
	}

	@Override
	public void setStatus(boolean readOnly) {

		for(Button button : buttons) {
			button.setEnabled(false);
		}
		/*
		 * Defaults when editable.
		 */
		if(!readOnly) {
			buttonCalculate.setEnabled(true);
			buttonPrevious.setEnabled(true);
			buttonExport.setEnabled(true);
		}
	}

	private void createControl() {

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		Composite compositeMain = new Composite(composite, SWT.NONE);
		compositeMain.setLayout(new GridLayout(2, true));
		compositeMain.setLayoutData(new GridData(GridData.FILL_BOTH));
		createListComposite(compositeMain);
		/*
		 * Button Bar
		 */
		Composite compositeButtons = new Composite(composite, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(1, true));
		compositeButtons.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		//
		GridData gridDataButtons = new GridData(GridData.FILL_HORIZONTAL);
		gridDataButtons.minimumWidth = 150;
		//
		buttons.add(buttonCalculate = createCalculateButton(compositeButtons, gridDataButtons));
		buttons.add(buttonPrevious = createPreviousButton(compositeButtons, gridDataButtons));
		buttons.add(createSaveButton(compositeButtons, gridDataButtons));
		buttons.add(buttonExport = createExportButton(compositeButtons, gridDataButtons));
		//
		createInfoLabel(compositeButtons);
	}

	private void createListComposite(Composite parent) {

		scanMarkerListUI = new ScanMarkerListUI(parent, SWT.BORDER);
		scanMarkerListUI.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		scanMarkerListUI.getTable().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateMassShiftList();
				updateComparisonViews(e.display);
			}
		});
		//
		massShiftListUI = new MassShiftListUI(parent, SWT.BORDER);
		massShiftListUI.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		scanMarkerInfoLabel = new Label(parent, SWT.NONE);
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalSpan = 2;
		scanMarkerInfoLabel.setLayoutData(gridDataLabel);
	}

	private Button createCalculateButton(Composite parent, GridData gridData) {

		Display display = Display.getDefault();
		Shell shell = display.getActiveShell();
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Calculate");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CALCULATE, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				editorProcessor.setDirty(true);
				if(scanMarkerListUI.getTable().getItemCount() == 0) {
					calculateScanMarker(e.display);
				} else {
					MessageBox messageBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setText("Calculate Scan List");
					messageBox.setMessage("Current results are overwritten when doing a new calculation.");
					if(messageBox.open() == SWT.YES) {
						calculateScanMarker(e.display);
					}
				}
			}
		});
		return button;
	}

	private void calculateScanMarker(Display display) {

		ProcessorData processorData = editorProcessor.getProcessorData();
		ScanMarkerDetectorRunnable runnable = new ScanMarkerDetectorRunnable(processorData);
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
		List<IScanMarker> scanMarker = runnable.getScanMarker();
		processorData.getProcessorModel().setScanMarker(scanMarker);
		scanMarkerListUI.setInput(scanMarker);
		if(scanMarker.size() > 0) {
			scanMarkerListUI.getTable().select(0);
		}
		updateMassShiftList();
		updateComparisonViews(display);
		setScanMarkerInfoLabel(scanMarker.size());
	}

	private Button createPreviousButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Previous");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				editorProcessor.setActivePage(EditorProcessor.PAGE_INDEX_SHIFT_HEATMAP);
			}
		});
		return button;
	}

	private Button createSaveButton(Composite parent, GridData gridData) {

		Shell shell = Display.getDefault().getActiveShell();
		//
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Save");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SAVE, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ProgressMonitorDialog monitor = new ProgressMonitorDialog(shell);
				try {
					monitor.run(true, true, new IRunnableWithProgress() {

						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

							editorProcessor.doSave(monitor);
						}
					});
				} catch(InvocationTargetException e1) {
					logger.warn(e1);
				} catch(InterruptedException e1) {
					logger.warn(e1);
				}
			}
		});
		return button;
	}

	private Button createExportButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Export");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXPORT, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				editorProcessor.doSaveAs();
			}
		});
		return button;
	}

	private void createInfoLabel(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("\nValidate Scans:\nCTRL + Space");
	}

	private void updateMassShiftList() {

		IStructuredSelection selection = scanMarkerListUI.getStructuredSelection();
		Object object = selection.getFirstElement();
		if(object instanceof IScanMarker) {
			/*
			 * Display the mass shifts
			 */
			IScanMarker scanMarker = (IScanMarker)object;
			massShiftListUI.setInput(scanMarker.getMassShifts());
		}
	}

	private void updateComparisonViews(Display display) {

		IStructuredSelection selection = scanMarkerListUI.getStructuredSelection();
		Object object = selection.getFirstElement();
		if(object instanceof IScanMarker) {
			/*
			 * Update the comparison UI.
			 */
			IScanMarker scanMarker = (IScanMarker)object;
			ProcessorData processorData = editorProcessor.getProcessorData();
			int scan = scanMarker.getScanNumber();
			IChromatogramMSD referenceChromatogram = processorData.getReferenceChromatogram();
			IChromatogramMSD isotopeChromatogram = processorData.getIsotopeChromatogram();
			if(referenceChromatogram != null && isotopeChromatogram != null) {
				IScanMSD referenceMassSpectrum = (IScanMSD)referenceChromatogram.getScan(scan);
				IScanMSD isotopeMassSpectrum = (IScanMSD)isotopeChromatogram.getScan(scan);
				UpdateNotifierUI.update(display, referenceMassSpectrum, isotopeMassSpectrum);
				/*
				 * Update the chromatogram selection.
				 */
				try {
					EPartService partService = Activator.getDefault().getPartService();
					if(partService != null) {
						Collection<MPart> parts = partService.getParts();
						for(MPart part : parts) {
							if(part.getObject() instanceof ChromatogramEditorMSD) {
								ChromatogramEditorMSD chromatogramEditorMSD = (ChromatogramEditorMSD)part.getObject();
								IChromatogramSelection<?, ?> chromatogramSelection = chromatogramEditorMSD.getChromatogramSelection();
								if(chromatogramSelection instanceof IChromatogramSelectionMSD) {
									IChromatogramSelectionMSD chromatogramSelectionMSD = (IChromatogramSelectionMSD)chromatogramSelection;
									if(chromatogramSelectionMSD.getChromatogram().getName().equals(referenceChromatogram.getName())) {
										chromatogramSelectionMSD.setSelectedScan(referenceMassSpectrum);
										int startRetentionTime = referenceMassSpectrum.getRetentionTime() - 5000; // -5 seconds
										int stopRetentionTime = referenceMassSpectrum.getRetentionTime() + 5000; // +5 seconds
										chromatogramSelectionMSD.setStopRetentionTime(stopRetentionTime);
										chromatogramSelectionMSD.setStartRetentionTime(startRetentionTime);
										chromatogramSelectionMSD.fireUpdateChange(true);
									}
								}
							}
						}
					}
				} catch(Exception e1) {
					//
				}
			}
		}
	}

	private void setScanMarkerInfoLabel(int scanMarkerSize) {

		scanMarkerInfoLabel.setText("In summary: " + scanMarkerSize + " possible scan(s) containing isotope shifts have been identified.");
	}
}
