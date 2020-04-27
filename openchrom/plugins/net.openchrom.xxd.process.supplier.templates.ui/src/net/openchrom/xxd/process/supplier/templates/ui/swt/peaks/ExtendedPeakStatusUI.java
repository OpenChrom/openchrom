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

import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakStatusListUI;

public class ExtendedPeakStatusUI extends Composite {

	private ReviewController reviewController;
	private Label labelReviewSetting;
	private PeakStatusListUI peakStatusListUI;
	//
	private List<IPeak> peaks;
	private ReviewSetting reviewSetting;

	public ExtendedPeakStatusUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setInput(List<IPeak> peaks, ReviewSetting reviewSetting) {

		this.peaks = peaks;
		this.reviewSetting = reviewSetting;
		//
		update(reviewSetting);
		peakStatusListUI.setInput(peaks);
		//
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
		//
		updateSelection();
	}

	public void setReviewController(ReviewController reviewController) {

		this.reviewController = reviewController;
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
		composite.setLayout(new GridLayout(4, false));
		//
		labelReviewSetting = createLabel(composite);
		createMarkPeakButton(composite);
		createDeletePeakButton(composite);
		createDeletePeaksButton(composite);
	}

	private Label createLabel(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText("");
		label.setToolTipText("The selected review setting is displayed here.");
		//
		return label;
	}

	private void createMarkPeakButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Edit the status of the selected peak");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_VALIDATE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				togglePeakReviewStatus();
			}
		});
	}

	private void createDeletePeakButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Delete the selected peak(s)");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@SuppressWarnings({"unchecked"})
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(reviewController != null) {
					// ProcessReviewSettings processSettings = reviewController.getProcessReviewSettings();
					// if(processSettings != null) {
					// IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
					// if(chromatogram != null) {
					// List<IPeak> peaksToDelete = new ArrayList<>();
					// Iterator<Object> iterator = peakStatusListUI.getStructuredSelection().iterator();
					// while(iterator.hasNext()) {
					// Object object = iterator.next();
					// if(object instanceof IPeak) {
					// IPeak peak = (IPeak)object;
					// peaksToDelete.add(peak);
					// }
					// }
					// chromatogram.removePeaks(peaksToDelete);
					// reviewController.update();
					// }
					// }
				}
			}
		});
	}

	private void createDeletePeaksButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Delete all peak(s)");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE_ALL, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@SuppressWarnings({"unchecked"})
			@Override
			public void widgetSelected(SelectionEvent e) {

				if(reviewController != null) {
					// ProcessReviewSettings processSettings = reviewController.getProcessReviewSettings();
					// if(processSettings != null) {
					// IChromatogram<IPeak> chromatogram = (IChromatogram<IPeak>)processSettings.getChromatogram();
					// if(chromatogram != null) {
					// List<IPeak> peaksToDelete = new ArrayList<>();
					// for(TableItem tableItem : peakStatusListUI.getTable().getItems()) {
					// Object object = tableItem.getData();
					// if(object instanceof IPeak) {
					// IPeak peak = (IPeak)object;
					// peaksToDelete.add(peak);
					// }
					// }
					// chromatogram.removePeaks(peaksToDelete);
					// reviewController.update();
					// }
					// }
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

				updateSelection();
			}
		});
		//
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

				togglePeakReviewStatus();
			}
		});
		//
		return listUI;
	}

	private void togglePeakReviewStatus() {

		IPeak peak = getSelectedPeak();
		if(peak != null) {
			if(ReviewSupport.isPeakReviewed(peak)) {
				peak.removeClassifier(ReviewSetting.CLASSIFIER_REVIEW_OK);
			} else {
				peak.addClassifier(ReviewSetting.CLASSIFIER_REVIEW_OK);
			}
			peakStatusListUI.refresh();
		}
	}

	private IPeak getSelectedPeak() {

		Object object = peakStatusListUI.getStructuredSelection().getFirstElement();
		if(object instanceof IPeak) {
			return (IPeak)object;
		}
		return null;
	}

	private void update(ReviewSetting reviewSetting) {

		if(reviewSetting != null) {
			boolean isCompoundAvailable = ReviewSupport.isCompoundAvailable(peaks, reviewSetting);
			labelReviewSetting.setBackground(isCompoundAvailable ? Colors.GREEN : Colors.YELLOW);
			labelReviewSetting.setText(reviewSetting.getName());
		} else {
			labelReviewSetting.setBackground(null);
			labelReviewSetting.setText("");
		}
		/*
		 * Color code for the identification.
		 */
		peakStatusListUI.setReviewSetting(reviewSetting);
	}

	private void updateSelection() {

		if(reviewController != null) {
			IPeak peak = getSelectedPeak();
			reviewController.update(reviewSetting, peak);
		}
	}
}
