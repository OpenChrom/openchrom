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
import org.eclipse.chemclipse.model.targets.TargetListUtil;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakStatusListUI;

public class ExtendedPeakReviewUI extends Composite {

	private static final String MENU_CATEGORY_PEAKS = "Peaks";
	//
	private ReviewController controller;
	private Text textTarget;
	private PeakStatusListUI peakStatusListUI;
	//
	private List<IPeak> peaks;
	private ReviewSetting reviewSetting;

	public ExtendedPeakReviewUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setInput(ReviewSetting reviewSetting, List<IPeak> peaks, IPeak peak) {

		this.reviewSetting = reviewSetting;
		this.peaks = peaks;
		//
		update(reviewSetting);
		peakStatusListUI.setInput(peaks);
		if(peak != null) {
			selectPeakMatch(peak);
		} else {
			autoSelectBestMatch();
		}
		//
		updateSelection(false);
	}

	public void setController(ReviewController controller) {

		this.controller = controller;
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		peakStatusListUI = createTablePeaks(this);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(5, false));
		//
		textTarget = createTargetText(composite);
		createSetTargetButton(composite);
		createMarkPeakButton(composite);
		createDeletePeakButton(composite);
		createDeletePeaksButton(composite);
	}

	private Text createTargetText(Composite parent) {

		Text text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		return text;
	}

	private void createSetTargetButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Set the target manually");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXECUTE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IPeak peak = getSelectedPeak();
				if(peak != null && reviewSetting != null) {
					ReviewSupport.setReview(peak, reviewSetting, true);
					peakStatusListUI.refresh();
					update(reviewSetting);
					updateSelection(true);
				}
			}
		});
	}

	private void createMarkPeakButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Edit the status of the selected peak");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_VALIDATE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				toggleIdentificationStatus();
			}
		});
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

	private PeakStatusListUI createTablePeaks(Composite parent) {

		PeakStatusListUI listUI = new PeakStatusListUI(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateSelection(false);
			}
		});
		//
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

				toggleIdentificationStatus();
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

		if(reviewSetting != null) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
			messageBox.setText("Delete Peak(s)");
			messageBox.setMessage("Would you like to delete the selected peak(s)?");
			if(messageBox.open() == SWT.YES) {
				/*
				 * Delete Peak(s)
				 */
				List<IPeak> peaksToDelete = new ArrayList<>();
				Iterator iterator = peakStatusListUI.getStructuredSelection().iterator();
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

	private void toggleIdentificationStatus() {

		IPeak peak = getSelectedPeak();
		if(peak != null) {
			boolean isPeakReviewed = ReviewSupport.isPeakReviewed(peak);
			ReviewSupport.setReview(peak, reviewSetting, !isPeakReviewed);
			peakStatusListUI.refresh();
			updateSelection(false);
		}
	}

	private IPeak getSelectedPeak() {

		Object object = peakStatusListUI.getStructuredSelection().getFirstElement();
		if(object instanceof IPeak) {
			return (IPeak)object;
		}
		return null;
	}

	private List<IPeak> getSelectedPeaks() {

		List<IPeak> peaks = new ArrayList<>();
		Iterator<?> iterator = peakStatusListUI.getStructuredSelection().iterator();
		while(iterator.hasNext()) {
			Object object = iterator.next();
			if(object instanceof IPeak) {
				peaks.add((IPeak)object);
			}
		}
		return peaks;
	}

	private void selectPeakMatch(IPeak peak) {

		if(peaks != null && peak != null) {
			exitloop:
			for(int i = 0; i < peaks.size(); i++) {
				if(peaks.get(i) == peak) {
					if(ReviewSupport.isCompoundAvailable(peak.getTargets(), reviewSetting)) {
						peakStatusListUI.getTable().select(i);
						break exitloop;
					}
				}
			}
		}
	}

	private void autoSelectBestMatch() {

		if(PreferenceSupplier.isAutoSelectBestPeakMatch()) {
			if(peaks != null) {
				exitloop:
				for(int i = 0; i < peaks.size(); i++) {
					IPeak peak = peaks.get(i);
					if(ReviewSupport.isCompoundAvailable(peak.getTargets(), reviewSetting)) {
						peakStatusListUI.getTable().select(i);
						break exitloop;
					}
				}
			}
		}
	}

	private void update(ReviewSetting reviewSetting) {

		if(reviewSetting != null) {
			boolean isCompoundAvailable = ReviewSupport.isCompoundAvailable(peaks, reviewSetting);
			textTarget.setBackground(isCompoundAvailable ? Colors.GREEN : Colors.YELLOW);
			StringBuilder builder = new StringBuilder();
			builder.append(reviewSetting.getName());
			builder.append(" ");
			builder.append(TargetListUtil.SEPARATOR_ENTRY);
			builder.append(" ");
			builder.append(reviewSetting.getCasNumber());
			textTarget.setText(builder.toString());
		} else {
			textTarget.setBackground(null);
			textTarget.setText("");
		}
		/*
		 * Color code for the identification.
		 */
		peakStatusListUI.setReviewSetting(reviewSetting);
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
