/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IKeyboardSupport;
import org.eclipse.swtchart.extensions.events.AbstractHandledEventProcessor;
import org.eclipse.swtchart.extensions.events.IHandledEventProcessor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class ReplacePeakToggleEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

	@Override
	public int getEvent() {

		return IKeyboardSupport.EVENT_KEY_UP;
	}

	@Override
	public int getButton() {

		return IKeyboardSupport.KEY_CODE_LC_D;
	}

	@Override
	public int getStateMask() {

		return SWT.NONE;
	}

	@Override
	public void handleEvent(BaseChart baseChart, Event event) {

		PreferenceSupplier.toggleDetectorReplacePeak();
	}
}
