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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.swt.ui.components.massspectrum.MassValueDisplayPrecision;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.listener.AbstractControllerComposite;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.swt.widgets.Shell;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.editors.EditorProcessor;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables.ScanMarkerDetectorRunnable;

public class EnhancedScanMarkerEditor extends AbstractControllerComposite {

	private static final Logger logger = Logger.getLogger(EnhancedScanMarkerEditor.class);
	//
	private EditorProcessor editorProcessor;
	//
	private Button buttonCalculate;
	private Button buttonReset;
	private Button buttonPrevious;
	private Button buttonCheck;
	private Button buttonExport;
	private List<Button> buttons;
	//
	private ScanMarkerListUI scanMarkerListUI;
	private MassShiftListUI massShiftListUI;
	private MassSpectrumComparisonUI massSpectrumComparisonUI;

	public EnhancedScanMarkerEditor(Composite parent, int style) {
		super(parent, style);
		buttons = new ArrayList<Button>();
		createControl();
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
	}

	@Override
	public boolean setFocus() {

		plotData();
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
			buttonReset.setEnabled(true);
			buttonPrevious.setEnabled(true);
			buttonCheck.setEnabled(true);
			buttonExport.setEnabled(true);
		}
	}

	/**
	 * Sets the table viewer input.
	 * 
	 * @param input
	 */
	public void setInput(Object input) {

		System.out.println("TODO");
	}

	private void createControl() {

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		Composite mainComposite = new Composite(composite, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, true));
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		createListComposite(mainComposite);
		//
		massSpectrumComparisonUI = new MassSpectrumComparisonUI(mainComposite, SWT.BORDER, MassValueDisplayPrecision.NOMINAL);
		massSpectrumComparisonUI.setLayoutData(new GridData(GridData.FILL_BOTH));
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
		buttons.add(buttonCheck = createCheckButton(compositeButtons, gridDataButtons));
		buttons.add(buttonReset = createResetButton(compositeButtons, gridDataButtons));
		buttons.add(buttonPrevious = createPreviousButton(compositeButtons, gridDataButtons));
		buttons.add(createSaveButton(compositeButtons, gridDataButtons));
		buttons.add(buttonExport = createExportButton(compositeButtons, gridDataButtons));
	}

	private void createListComposite(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Lists
		 */
		scanMarkerListUI = new ScanMarkerListUI(composite, SWT.BORDER);
		scanMarkerListUI.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		scanMarkerListUI.getTable().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection selection = scanMarkerListUI.getStructuredSelection();
				Object object = selection.getFirstElement();
				if(object instanceof ScanMarker) {
					//
					ScanMarker scanMarker = (ScanMarker)object;
					int scan = scanMarker.getScan();
					IChromatogramMSD referenceChromatogram = editorProcessor.getProcessorData().getReferenceChromatogram();
					IChromatogramMSD isotopeChromatogram = editorProcessor.getProcessorData().getIsotopeChromatogram();
					//
					massShiftListUI.setInput(scanMarker.getMassShifts());
					//
					IScanMSD referenceMassSpectrum = (IScanMSD)referenceChromatogram.getScan(scan);
					IScanMSD isotopeMassSpectrum = (IScanMSD)isotopeChromatogram.getScan(scan);
					massSpectrumComparisonUI.update(referenceMassSpectrum, isotopeMassSpectrum, true);
				}
			}
		});
		//
		massShiftListUI = new MassShiftListUI(composite, SWT.BORDER);
		massShiftListUI.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	private Button createCalculateButton(Composite parent, GridData gridData) {

		Shell shell = Display.getCurrent().getActiveShell();
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Calculate");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CALCULATE, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				processAction();
				ProcessorData processorData = editorProcessor.getProcessorData();
				//
				List<ScanMarker> scanMarker = null;
				ScanMarkerDetectorRunnable runnable = new ScanMarkerDetectorRunnable(processorData);
				ProgressMonitorDialog monitor = new ProgressMonitorDialog(shell);
				//
				try {
					monitor.run(true, true, runnable);
					scanMarker = runnable.getScanMarker();
				} catch(InterruptedException e1) {
					logger.warn(e1);
				} catch(InvocationTargetException e1) {
					logger.warn(e1);
				}
				scanMarkerListUI.setInput(scanMarker);
			}
		});
		return button;
	}

	private Button createResetButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Reset");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_RESET, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("TODO");
				boolean error = false;
				if(!error) {
					plotData();
				}
			}
		});
		return button;
	}

	/*
	 * Plot the data if there is no validation error.
	 */
	private void plotData() {

		System.out.println("TODO");
	}

	private Button createCheckButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Check");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CHECK, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("TODO");
				processAction();
			}
		});
		return button;
	}

	private Button createPreviousButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Previous");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				editorProcessor.focusPage(EditorProcessor.PAGE_INDEX_SHIFT_HEATMAP);
			}
		});
		return button;
	}

	private Button createSaveButton(Composite parent, GridData gridData) {

		Shell shell = Display.getCurrent().getActiveShell();
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
}
