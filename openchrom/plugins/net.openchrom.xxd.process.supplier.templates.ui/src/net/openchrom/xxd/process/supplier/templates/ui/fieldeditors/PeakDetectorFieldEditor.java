/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - refactor to use editor
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.fieldeditors;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.xxd.process.supplier.templates.ui.swt.TemplatePeakListEditor;

public class PeakDetectorFieldEditor extends AbstractFieldEditor {

	private TemplatePeakListEditor editor;

	public PeakDetectorFieldEditor(String name, String labelText, Composite parent) {

		init(name, labelText);
		createControl(parent);
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {

		getLabelControl(parent);
		editor = new TemplatePeakListEditor(parent, null, null);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		editor.getControl().setLayoutData(gridData);
	}

	@Override
	protected void doLoad() {

		String entries = getPreferenceStore().getString(getPreferenceName());
		editor.load(entries);
	}

	@Override
	protected void doLoadDefault() {

		String entries = getPreferenceStore().getDefaultString(getPreferenceName());
		editor.load(entries);
	}

	@Override
	protected void doStore() {

		getPreferenceStore().setValue(getPreferenceName(), editor.getValues());
	}

	@Override
	public int getNumberOfControls() {

		return 1;
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {

		if(numColumns >= 2) {
			GridData gridData = (GridData)editor.getControl().getLayoutData();
			gridData.horizontalSpan = numColumns - 1;
			gridData.grabExcessHorizontalSpace = true;
		}
	}
}