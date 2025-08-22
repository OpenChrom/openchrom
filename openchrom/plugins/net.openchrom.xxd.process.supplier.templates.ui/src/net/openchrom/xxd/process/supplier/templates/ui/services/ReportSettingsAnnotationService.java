/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.services;

import org.eclipse.chemclipse.ux.extension.ui.methods.IAnnotationWidgetService;
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
		gridData.widthHint = 500;
		templateReportEditor.setLayoutData(gridData);

		if(currentSelection instanceof String text) {
			templateReportEditor.load(text);
		}

		return templateReportEditor;
	}

	@Override
	public Object getValue(Object currentSelection) {

		return templateReportEditor.getReportSettings();
	}
}
