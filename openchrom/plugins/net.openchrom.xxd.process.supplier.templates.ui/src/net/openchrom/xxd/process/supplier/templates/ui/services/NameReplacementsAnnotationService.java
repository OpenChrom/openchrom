/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.support.ui.services.IAnnotationWidgetService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import net.openchrom.xxd.process.supplier.templates.service.NameReplacementsSerializationService;
import net.openchrom.xxd.process.supplier.templates.ui.swt.NameReplacementsEditor;

@Component(service = {IAnnotationWidgetService.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class NameReplacementsAnnotationService extends NameReplacementsSerializationService implements IAnnotationWidgetService {

	private AtomicReference<NameReplacementsEditor> editorControl = new AtomicReference<>();

	@Override
	public Control createWidget(Composite parent, String description, Object currentSelection) {

		NameReplacementsEditor editor = new NameReplacementsEditor(parent, SWT.NONE);
		editor.setToolTipText(description);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 200;
		gridData.widthHint = 500;
		editor.setLayoutData(gridData);
		if(currentSelection instanceof String text) {
			editor.load(text);
		}
		editorControl.set(editor);

		return editor;
	}

	@Override
	public Object getValue(Object currentSelection) {

		return editorControl.get().getNameReplacements();
	}
}
