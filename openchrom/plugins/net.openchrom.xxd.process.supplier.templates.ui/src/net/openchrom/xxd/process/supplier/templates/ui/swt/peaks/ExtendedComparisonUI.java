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

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.ux.extension.xxd.ui.runnables.LibraryServiceRunnable;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ScanChartUI;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ExtendedComparisonUI extends Composite {

	private static final float NORMALIZATION_FACTOR = 1000.0f;
	//
	private ReviewController reviewController;
	private ScanChartUI scanChartUI;

	public ExtendedComparisonUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setReviewController(ReviewController reviewController) {

		this.reviewController = reviewController;
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarComparison(this);
		scanChartUI = createScanChart(this);
	}

	private void createToolbarComparison(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(1, false));
		//
		createButton(composite);
	}

	private void createButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Delete the selected peak(s)");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
	}

	private ScanChartUI createScanChart(Composite parent) {

		ScanChartUI scanChartUI = new ScanChartUI(parent, SWT.BORDER);
		scanChartUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		return scanChartUI;
	}

	public void update(IScanMSD unknownMassSpectrum, IIdentificationTarget identificationTarget) {

		scanChartUI.deleteSeries();
		if(unknownMassSpectrum != null) {
			IScan scanUnkown = copyScan(unknownMassSpectrum);
			LibraryServiceRunnable runnable = new LibraryServiceRunnable(identificationTarget, new Consumer<IScanMSD>() {

				@Override
				public void accept(IScanMSD referenceMassSpectrum) {

					IScan scanReference = copyScan(referenceMassSpectrum);
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {

							updateChartComparison(scanUnkown, scanReference);
						}
					});
				}
			});
			//
			try {
				if(runnable.requireProgressMonitor()) {
					DisplayUtils.executeInUserInterfaceThread(() -> {
						ProgressMonitorDialog monitor = new ProgressMonitorDialog(this.getDisplay().getActiveShell());
						monitor.run(true, true, runnable);
						return null;
					});
				} else {
					DisplayUtils.executeBusy(() -> {
						runnable.run(new NullProgressMonitor());
						return null;
					});
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch(ExecutionException e) {
				// Activator.getDefault().getLog().log(new Status(IStatus.ERROR, getClass().getName(), "Update scan failed", e));
			}
		}
	}

	private static IScanMSD copyScan(IScanMSD scan) {

		if(scan != null) {
			try {
				return scan.makeDeepCopy().normalize(NORMALIZATION_FACTOR);
			} catch(CloneNotSupportedException e) {
			}
		}
		return null;
	}

	private void updateChartComparison(IScan scanUnknown, IScan scanReference) {

		if(scanUnknown != null && scanReference != null) {
			scanChartUI.setInput(scanUnknown, scanReference, true);
		} else {
			if(scanUnknown != null) {
				scanChartUI.setInput(scanUnknown);
			}
		}
	}
}
