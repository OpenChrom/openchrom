/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakListUI;

public class ExtendedPeaksUI extends Composite {

	private static final String MENU_CATEGORY_PEAKS = "Peaks";
	//
	private DetectorController controller;
	private PeakListUI peakListUI;
	//
	private List<IPeak> peaks;
	//
	private DetectorSetting detectorSetting;

	public ExtendedPeaksUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setInput(DetectorSetting detectorSetting, List<IPeak> peaks, IPeak peak) {

		this.detectorSetting = detectorSetting;
		this.peaks = peaks;
		peakListUI.setInput(peaks);
		if(peak != null) {
			selectPeakMatch(peak);
		}
		updateSelection(false);
	}

	public void setController(DetectorController controller) {

		this.controller = controller;
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		peakListUI = createTablePeaks(this);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(2, false));
		//
		createDeletePeakButton(composite);
		createDeletePeaksButton(composite);
	}

	private void createDeletePeakButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Delete the selected peak(s)");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				deletePeaks(e.display.getActiveShell());
			}
		});
	}

	private void createDeletePeaksButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Delete all peak(s)");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE_ALL, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(controller != null) {
					MessageBox messageBox = new MessageBox(e.display.getActiveShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					messageBox.setText("Delete Peak(s)");
					messageBox.setMessage("Would you like to delete all peak(s)?");
					if(messageBox.open() == SWT.YES) {
						controller.deletePeaks(peaks);
					}
				}
			}
		});
	}

	private PeakListUI createTablePeaks(Composite parent) {

		PeakListUI listUI = new PeakListUI(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateSelection(false);
			}
		});
		/*
		 * Add the delete targets support.
		 */
		Shell shell = listUI.getTable().getShell();
		ITableSettings tableSettings = listUI.getTableSettings();
		addDeleteMenuEntry(shell, tableSettings);
		addKeyEventProcessors(shell, tableSettings);
		listUI.applySettings(tableSettings);
		//
		return listUI;
	}

	private void addDeleteMenuEntry(Shell shell, ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Delete Peak(s)";
			}

			@Override
			public String getCategory() {

				return MENU_CATEGORY_PEAKS;
			}

			@Override
			public void execute(ExtendedTableViewer extendedTableViewer) {

				deletePeaks(shell);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void deletePeaks(Shell shell) {

		if(detectorSetting != null) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
			messageBox.setText("Delete Peak(s)");
			messageBox.setMessage("Would you like to delete the selected peak(s)?");
			if(messageBox.open() == SWT.YES) {
				/*
				 * Delete Peak(s)
				 */
				List<IPeak> peaksToDelete = new ArrayList<>();
				Iterator iterator = peakListUI.getStructuredSelection().iterator();
				while(iterator.hasNext()) {
					Object object = iterator.next();
					if(object instanceof IPeak) {
						peaksToDelete.add((IPeak)object);
					}
				}
				controller.deletePeaks(peaksToDelete);
			}
		}
	}

	private void addKeyEventProcessors(Shell shell, ITableSettings tableSettings) {

		tableSettings.addKeyEventProcessor(new IKeyEventProcessor() {

			@Override
			public void handleEvent(ExtendedTableViewer extendedTableViewer, KeyEvent e) {

				if(e.keyCode == SWT.DEL) {
					deletePeaks(shell);
				} else {
					updateSelection(false);
				}
			}
		});
	}

	private void selectPeakMatch(IPeak peak) {

		if(peaks != null && peak != null) {
			exitloop:
			for(int i = 0; i < peaks.size(); i++) {
				if(peaks.get(i) == peak) {
					peakListUI.getTable().select(i);
					break exitloop;
				}
			}
		}
	}

	private List<IPeak> getSelectedPeaks() {

		List<IPeak> peaks = new ArrayList<>();
		Iterator<?> iterator = peakListUI.getStructuredSelection().iterator();
		while(iterator.hasNext()) {
			Object object = iterator.next();
			if(object instanceof IPeak) {
				peaks.add((IPeak)object);
			}
		}
		return peaks;
	}

	private void updateSelection(boolean updateChart) {

		if(controller != null) {
			List<IPeak> peaks = getSelectedPeaks();
			if(updateChart) {
				controller.updateDetectorChart();
			}
			controller.update(peaks);
		}
	}
}
