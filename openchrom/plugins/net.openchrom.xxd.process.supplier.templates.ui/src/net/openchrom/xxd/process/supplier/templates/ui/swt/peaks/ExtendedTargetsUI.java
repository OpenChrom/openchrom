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

import java.util.Set;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.TargetsListUI;
import org.eclipse.swt.SWT;
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

public class ExtendedTargetsUI extends Composite {

	private ReviewController reviewController;
	//
	private Label labelReviewSetting;
	private TargetsListUI targetListUI;
	//
	private IPeak peak;
	private Set<IIdentificationTarget> targets;

	public ExtendedTargetsUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setReviewController(ReviewController reviewController) {

		this.reviewController = reviewController;
	}

	public void setInput(IPeak peak, Set<IIdentificationTarget> targets) {

		this.peak = peak;
		this.targets = targets;
		targetListUI.setInput(targets);
	}

	public void update(ReviewSetting reviewSetting) {

		if(reviewSetting != null) {
			boolean isCompoundAvailable = ReviewSupport.isCompoundAvailable(targets, reviewSetting);
			labelReviewSetting.setBackground(isCompoundAvailable ? Colors.GREEN : Colors.YELLOW);
			labelReviewSetting.setText(reviewSetting.getName());
		} else {
			labelReviewSetting.setBackground(null);
			labelReviewSetting.setText("");
		}
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		targetListUI = createTableTargets(this);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(2, false));
		//
		labelReviewSetting = createLabel(composite);
		createDeleteTargetButton(composite);
	}

	private Label createLabel(Composite parent) {

		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText("");
		label.setToolTipText("The selected review setting is displayed here.");
		return label;
	}

	private void createDeleteTargetButton(Composite parent) {

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

	private TargetsListUI createTableTargets(Composite parent) {

		TargetsListUI listUI = new TargetsListUI(parent, SWT.BORDER);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(reviewController != null) {
					Object object = listUI.getStructuredSelection().getFirstElement();
					if(object instanceof IIdentificationTarget) {
						IIdentificationTarget identificationTarget = (IIdentificationTarget)object;
						reviewController.updateTarget(peak, identificationTarget);
					}
				}
			}
		});
		//
		return listUI;
	}
}
