/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.model.cas.CasSupport;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakIdentifierListUI;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierValidator;

public class PeakIdentifierEditingSupport extends AbstractTemplateEditingSupport {

	public PeakIdentifierEditingSupport(PeakIdentifierListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof IdentifierSetting) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				IdentifierSetting setting = (IdentifierSetting)element;
				switch(getColumn()) {
					/*
					 * Do not edit the name
					 */
					case AbstractTemplateLabelProvider.CAS_NUMBER:
						return setting.getCasNumber();
					case AbstractTemplateLabelProvider.COMMENTS:
						return setting.getComments();
					case AbstractTemplateLabelProvider.CONTRIBUTOR:
						return setting.getContributor();
					case AbstractTemplateLabelProvider.REFERENCE:
						return setting.getReference();
					case AbstractTemplateLabelProvider.TRACES:
						return setting.getTraces();
					case AbstractTemplateLabelProvider.REFERENCE_IDENTIFIER:
						return setting.getReferenceIdentifier();
				}
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof IdentifierSetting) {
			IdentifierSetting setting = (IdentifierSetting)element;
			super.setValue(element, value);
			switch(getColumn()) {
				/*
				 * Do not edit the name
				 */
				case AbstractTemplateLabelProvider.CAS_NUMBER:
					setting.setCasNumber(CasSupport.format(((String)value).trim()));
					break;
				case AbstractTemplateLabelProvider.COMMENTS:
					setting.setComments(((String)value).trim());
					break;
				case AbstractTemplateLabelProvider.CONTRIBUTOR:
					setting.setContributor(((String)value).trim());
					break;
				case AbstractTemplateLabelProvider.REFERENCE:
					setting.setReference(((String)value).trim());
					break;
				case AbstractTemplateLabelProvider.TRACES:
					String traces = ((String)value).trim();
					PeakIdentifierValidator validator = new PeakIdentifierValidator();
					IStatus status = validator.validateTraces(traces);
					if(status.isOK()) {
						setting.setTraces(traces);
					}
					break;
				case AbstractTemplateLabelProvider.REFERENCE_IDENTIFIER:
					setting.setReferenceIdentifier(((String)value).trim());
					break;
			}
			//
			updateTableViewer();
		}
	}
}