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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.wizards;

import org.eclipse.chemclipse.support.ui.wizards.AbstractExtendedWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class PageDescription extends AbstractExtendedWizardPage {

	private IProcessorWizardElements wizardElements;
	private Text descriptionText;

	public PageDescription(IProcessorWizardElements wizardElements) {
		//
		super(PageDescription.class.getName());
		setTitle("Mass Shift Detector");
		setDescription("This processor helps to detect mass shifts.");
		this.wizardElements = wizardElements;
	}

	@Override
	public boolean canFinish() {

		String projectDescription = wizardElements.getDescription();
		if(projectDescription == null) {
			return false;
		}
		return true;
	}

	@Override
	public void setDefaultValues() {

	}

	@Override
	public void setVisible(boolean visible) {

		super.setVisible(visible);
		if(visible) {
			String projectDescription = wizardElements.getDescription();
			if(projectDescription != null) {
				descriptionText.setText(projectDescription);
			}
			validateDescription();
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		/*
		 * Description
		 */
		descriptionText = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP);
		descriptionText.setLayoutData(new GridData(GridData.FILL_BOTH));
		descriptionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				validateDescription();
			}
		});
		//
		validateDescription();
		//
		setControl(composite);
	}

	private void validateDescription() {

		String message = null;
		//
		String projectDescription = descriptionText.getText();
		if(projectDescription == null) {
			projectDescription = "";
		}
		wizardElements.setDescription(projectDescription.trim());
		/*
		 * Updates the status
		 */
		updateStatus(message);
	}
}
