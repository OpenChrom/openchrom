/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.services;

import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.IAnnotationWidgetService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import net.openchrom.xxd.process.supplier.templates.service.ReportSettingsSerializationService;
import net.openchrom.xxd.process.supplier.templates.ui.swt.TemplateReportEditor;

@Component(service = {IAnnotationWidgetService.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class ReportSettingsAnnotationService extends ReportSettingsSerializationService implements IAnnotationWidgetService {

	private TemplateReportEditor templateReportEditor;

	@Override
	public Control createWidget(Composite parent, String description, Object currentSelection) {

		templateReportEditor = new TemplateReportEditor(parent, SWT.NONE);
		templateReportEditor.setToolTipText(description);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 250;
		templateReportEditor.setLayoutData(gridData);
		//
		if(currentSelection instanceof String) {
			templateReportEditor.load((String)currentSelection);
		}
		//
		return templateReportEditor;
	}

	@Override
	public Object getValue(Object currentSelection) {

		return templateReportEditor.getReportSettings();
	}
}