/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.model.cas.CasSupport;
import org.eclipse.chemclipse.model.cas.CasValidator;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.IExtendedPartUI;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class IdentifierSettingsPage extends WizardPage implements IExtendedPartUI {

	private AtomicReference<Text> nameControl = new AtomicReference<>();
	private AtomicReference<Text> casNumberControl = new AtomicReference<>();
	private AtomicReference<Text> referenceIdentifierControl = new AtomicReference<>();
	//
	private IdentifierSetting identifierSetting = null;

	protected IdentifierSettingsPage(IdentifierSetting identifierSetting) {

		super(IdentifierSettingsPage.class.getName());
		//
		setTitle("Identifier Settings");
		setDescription("Edit the identifier details.");
		//
		this.identifierSetting = identifierSetting;
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		createNameSection(composite);
		createCasSection(composite);
		createReferenceIdentifierSection(composite);
		//
		updateInput();
		setControl(composite);
	}

	private void createNameSection(Composite parent) {

		createLabel(parent, "Name", "");
		//
		Text text = createText(parent);
		text.setEnabled(false);
		//
		nameControl.set(text);
	}

	private void createCasSection(Composite parent) {

		createLabel(parent, "CAS#", "Enter a valid CAS# or leave it empty.");
		//
		Text text = createText(parent);
		CasValidator casValidator = new CasValidator(true);
		ControlDecoration controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(identifierSetting != null) {
					identifierSetting.setCasNumber(text.getText().trim());
					validate(casValidator, controlDecoration, text);
				}
			}
		});
		//
		//
		casNumberControl.set(text);
	}

	private void createReferenceIdentifierSection(Composite parent) {

		createLabel(parent, "Reference Identifier", "Enter e.g. an internal tracking number.");
		//
		Text text = createText(parent);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(identifierSetting != null) {
					identifierSetting.setReferenceIdentifier(text.getText().trim());
				}
			}
		});
		//
		referenceIdentifierControl.set(text);
	}

	private void createLabel(Composite parent, String text, String tooltip) {

		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setToolTipText(tooltip);
	}

	private Text createText(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		return text;
	}

	private void updateInput() {

		if(identifierSetting != null) {
			nameControl.get().setText(identifierSetting.getName());
			casNumberControl.get().setText(identifierSetting.getCasNumber().isBlank() ? CasSupport.CAS_DEFAULT : identifierSetting.getCasNumber());
			referenceIdentifierControl.get().setText(identifierSetting.getReferenceIdentifier());
		} else {
			nameControl.get().setText("");
			casNumberControl.get().setText("");
			referenceIdentifierControl.get().setText("");
		}
	}

	private IStatus validate(IValidator<String> validator, ControlDecoration controlDecoration, Text text) {

		IStatus status = validator.validate(text.getText());
		if(status.isOK()) {
			controlDecoration.hide();
			setErrorMessage(null);
		} else {
			controlDecoration.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage());
			controlDecoration.showHoverText("CAS#");
			controlDecoration.show();
			setErrorMessage(status.getMessage());
		}
		//
		return status;
	}
}