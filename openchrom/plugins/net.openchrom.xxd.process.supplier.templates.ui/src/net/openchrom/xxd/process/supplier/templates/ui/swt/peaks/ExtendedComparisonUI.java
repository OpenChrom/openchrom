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
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.ux.extension.xxd.ui.runnables.LibraryServiceRunnable;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ScanChartUI;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class ExtendedComparisonUI extends Composite {

	private static final float NORMALIZATION_FACTOR = 1000.0f;
	//
	private ScanChartUI scanChartUI;

	public ExtendedComparisonUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		scanChartUI = createScanChart(this);
	}

	private ScanChartUI createScanChart(Composite parent) {

		ScanChartUI scanChartUI = new ScanChartUI(parent, SWT.BORDER);
		scanChartUI.setLayoutData(new GridData(GridData.FILL_BOTH));
		return scanChartUI;
	}

	public void update(IScanMSD unknownMassSpectrum, IIdentificationTarget identificationTarget) {

		scanChartUI.deleteSeries();
		if(unknownMassSpectrum != null && identificationTarget != null) {
			IScan scanUnkown = copyScan(unknownMassSpectrum);
			if(PreferenceSupplier.isReviewFetchLibrarySpectrum()) {
				runComparison(scanUnkown, identificationTarget);
			} else {
				updateChartComparison(scanUnkown, null);
			}
		} else {
			/*
			 * Redraw to empty the chart.
			 */
			scanChartUI.getBaseChart().redraw();
		}
	}

	private void runComparison(IScan scanUnkown, IIdentificationTarget identificationTarget) {

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
			//
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
