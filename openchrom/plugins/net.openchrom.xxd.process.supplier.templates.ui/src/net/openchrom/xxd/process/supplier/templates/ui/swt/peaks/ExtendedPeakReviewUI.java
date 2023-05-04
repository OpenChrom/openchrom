/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.model.targets.TargetListUtil;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.chemclipse.swt.ui.components.InformationUI;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.IExtendedPartUI;
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
import net.openchrom.xxd.process.supplier.templates.ui.support.ReviewSupport;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakStatusListUI;

public class ExtendedPeakReviewUI extends Composite implements IExtendedPartUI {

	private static final String MENU_CATEGORY_PEAKS = "Peaks";
	//
	private ReviewController controller;
	private Text textTarget;
	//
	private AtomicReference<PeakStatusListUI> peakStatusControl = new AtomicReference<>();
	private Button buttonToolbarInfo;
	private AtomicReference<InformationUI> toolbarInfo = new AtomicReference<>();
	//
	private List<IPeak> peaks;
	private ReviewSetting reviewSetting;
	//
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0000");

	public ExtendedPeakReviewUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setInput(ReviewSetting reviewSetting, List<IPeak> peaks, IPeak peak) {

		this.reviewSetting = reviewSetting;
		this.peaks = peaks;
		//
		update(reviewSetting);
		peakStatusControl.get().setInput(peaks);
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
		createTablePeaks(this);
		createToolbarInfo(this);
		//
		initialize();
	}

	private void initialize() {

		enableToolbar(toolbarInfo, buttonToolbarInfo, IApplicationImage.IMAGE_INFO, TOOLTIP_INFO, true);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(5, false));
		//
		textTarget = createTargetText(composite);
		buttonToolbarInfo = createButtonToggleToolbar(composite, toolbarInfo, IMAGE_INFO, TOOLTIP_INFO);
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
		button.setToolTipText("Delete the selected peaks");
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
		button.setToolTipText("Delete all peaks");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE_ALL, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(controller != null) {
					MessageBox messageBox = new MessageBox(e.display.getActiveShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					messageBox.setText("Delete Peaks");
					messageBox.setMessage("Would you like to delete all peaks?");
					if(messageBox.open() == SWT.YES) {
						controller.deletePeaks(messageBox.getParent(), peaks);
					}
				}
			}
		});
	}

	private void createTablePeaks(Composite parent) {

		PeakStatusListUI peakStatusListUI = new PeakStatusListUI(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = peakStatusListUI.getTable();
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
		Shell shell = peakStatusListUI.getTable().getShell();
		ITableSettings tableSettings = peakStatusListUI.getTableSettings();
		addDeleteMenuEntry(shell, tableSettings);
		addKeyEventProcessors(shell, tableSettings);
		peakStatusListUI.applySettings(tableSettings);
		//
		peakStatusControl.set(peakStatusListUI);
	}

	private void createToolbarInfo(Composite parent) {

		InformationUI informationUI = new InformationUI(parent, SWT.NONE);
		informationUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		toolbarInfo.set(informationUI);
	}

	private void addDeleteMenuEntry(Shell shell, ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Delete Peaks";
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
			messageBox.setText("Delete Peaks");
			messageBox.setMessage("Would you like to delete the selected peaks?");
			if(messageBox.open() == SWT.YES) {
				/*
				 * Delete Peaks
				 */
				List<IPeak> peaksToDelete = new ArrayList<>();
				Iterator iterator = peakStatusControl.get().getStructuredSelection().iterator();
				while(iterator.hasNext()) {
					Object object = iterator.next();
					if(object instanceof IPeak peak) {
						peaksToDelete.add(peak);
					}
				}
				//
				controller.deletePeaks(messageBox.getParent(), peaksToDelete);
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
			boolean setTarget = PreferenceSupplier.isReviewSetTargetVerification();
			ReviewSupport.setReview(peak, reviewSetting, setTarget, !isPeakReviewed);
			peakStatusControl.get().refresh();
			update(reviewSetting);
			updateSelection(true);
		}
	}

	private IPeak getSelectedPeak() {

		Object object = peakStatusControl.get().getStructuredSelection().getFirstElement();
		if(object instanceof IPeak peak) {
			return peak;
		}
		return null;
	}

	private List<IPeak> getSelectedPeaks() {

		List<IPeak> peaks = new ArrayList<>();
		Iterator<?> iterator = peakStatusControl.get().getStructuredSelection().iterator();
		while(iterator.hasNext()) {
			Object object = iterator.next();
			if(object instanceof IPeak peak) {
				peaks.add(peak);
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
						peakStatusControl.get().getTable().select(i);
						break exitloop;
					}
				}
			}
		}
	}

	private void autoSelectBestMatch() {

		if(PreferenceSupplier.isReviewAutoSelectBestMatch()) {
			if(peaks != null) {
				exitloop:
				for(int i = 0; i < peaks.size(); i++) {
					IPeak peak = peaks.get(i);
					if(ReviewSupport.isCompoundAvailable(peak.getTargets(), reviewSetting)) {
						peakStatusControl.get().getTable().select(i);
						break exitloop;
					}
				}
			}
		}
	}

	private void update(ReviewSetting reviewSetting) {

		if(reviewSetting != null) {
			boolean isCompoundAvailable = ReviewSupport.isCompoundAvailable(peaks, reviewSetting);
			textTarget.setBackground(isCompoundAvailable ? Colors.getColor(Colors.LIGHT_GREEN) : Colors.getColor(Colors.LIGHT_YELLOW));
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
		peakStatusControl.get().setReviewSetting(reviewSetting);
	}

	private void updateSelection(boolean updateChart) {

		StringBuilder builder = new StringBuilder();
		Object object = peakStatusControl.get().getStructuredSelection().getFirstElement();
		if(object instanceof IPeak peak) {
			/*
			 * Peak -> Quant
			 */
			List<IQuantitationEntry> quantitationEntries = peak.getQuantitationEntries();
			//
			builder.append("Quantitations");
			builder.append(" ");
			builder.append("(");
			builder.append(quantitationEntries.size());
			builder.append(")");
			builder.append(":");
			builder.append(" ");
			//
			if(!quantitationEntries.isEmpty()) {
				IQuantitationEntry quantitationEntry = peak.getQuantitationEntries().get(0);
				builder.append(quantitationEntry.getName());
				builder.append(" ");
				builder.append(decimalFormat.format(quantitationEntry.getConcentration()));
				builder.append(" ");
				builder.append(quantitationEntry.getConcentrationUnit());
			} else {
				builder.append("--");
			}
		}
		toolbarInfo.get().setText(builder.toString());
		//
		if(controller != null) {
			List<IPeak> peaks = getSelectedPeaks();
			if(updateChart) {
				controller.updateDetectorChart();
			}
			controller.update(peaks);
		}
	}
}
