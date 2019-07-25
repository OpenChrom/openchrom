/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.ui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.chemclipse.ux.extension.xxd.ui.editors.EditorExtension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings;

public class PhaseCorrectionSettingsEditorExtension implements EditorExtension {

	private PhaseCorrectionSettings settings;

	public PhaseCorrectionSettingsEditorExtension(PhaseCorrectionSettings settings) {
		this.settings = settings;
	}

	@Override
	public Observable createExtension(Composite parent) {

		PhaseCorrectionSettingsExtension extension = new PhaseCorrectionSettingsExtension(parent, settings);
		return settings;
	}

	private static class PhaseCorrectionSettingsExtension implements Observer {

		public PhaseCorrectionSettingsExtension(Composite parent, PhaseCorrectionSettings settings) {
			parent.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {

					settings.deleteObserver(PhaseCorrectionSettingsExtension.this);
				}
			});
			settings.addObserver(this);
			new Label(parent, SWT.NONE).setText("Hello From PhaseCorrectionSettingsExtension");
		}

		@Override
		public void update(Observable o, Object arg) {

		}
	}
}
