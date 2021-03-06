/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.listener.AbstractControllerComposite;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.TrackStatistics;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.editors.EditorProcessor;

public class EnhancedResultsEditor extends AbstractControllerComposite {

	private static final Logger logger = Logger.getLogger(EnhancedResultsEditor.class);
	//
	private EditorProcessor editorProcessor;
	//
	private Button buttonCalculate;
	private Button buttonPrevious;
	private List<Button> buttons;
	//
	private ResultsEditorUI resultsEditorUI;
	//
	private DataProcessor dataProcessor;

	public EnhancedResultsEditor(Composite parent, int style) {
		super(parent, style);
		buttons = new ArrayList<Button>();
		dataProcessor = new DataProcessor();
		createControl();
	}

	public void setEditorProcessor(EditorProcessor editorProcessor) {

		this.editorProcessor = editorProcessor;
		editorProcessor.setDirty(true);
		resultsEditorUI.update(editorProcessor);
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
		}
	}

	private void createControl() {

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Standards Table
		 */
		Composite compositeMain = new Composite(composite, SWT.NONE);
		compositeMain.setLayout(new GridLayout(1, true));
		compositeMain.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Results
		 */
		resultsEditorUI = new ResultsEditorUI(compositeMain, SWT.NONE);
		resultsEditorUI.setLayoutData(new GridData(GridData.FILL_BOTH));
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
	}

	private Button createCalculateButton(Composite parent, GridData gridData) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Calculate");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CALCULATE, IApplicationImage.SIZE_16x16));
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.YES | SWT.NO | SWT.CANCEL);
				messageBox.setText("Calculate Identification");
				messageBox.setMessage("Overwrite the current calculation result?.");
				if(messageBox.open() == SWT.YES) {
					/*
					 * Calculate
					 */
					IProcessorModel processorModel = editorProcessor.getProcessorModel();
					if(processorModel != null) {
						DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
						List<TrackStatistics> trackStatisticsList = dataProcessor.getTrackStatistics(processorModel);
						StringBuilder builder = new StringBuilder();
						Iterator<TrackStatistics> iterator = trackStatisticsList.iterator();
						while(iterator.hasNext()) {
							TrackStatistics trackStatistics = iterator.next();
							builder.append("Sample: ");
							builder.append(trackStatistics.getSampleGroup());
							builder.append(" > ");
							builder.append("Reference: ");
							builder.append(trackStatistics.getReferenceGroup());
							builder.append(" (");
							builder.append(decimalFormat.format(trackStatistics.getMatchProbability()));
							builder.append("%)");
							if(iterator.hasNext()) {
								builder.append("\n");
							}
						}
						String calculatedResult = builder.toString();
						processorModel.setCalculatedResult(calculatedResult);
						resultsEditorUI.update(editorProcessor);
					}
				}
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

				fireUpdatePrevious();
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

							Display.getDefault().asyncExec(new Runnable() {

								@Override
								public void run() {

									editorProcessor.doSave(monitor);
								}
							});
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
}
