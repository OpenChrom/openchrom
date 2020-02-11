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
<<<<<<< HEAD
import org.eclipse.swtchart.extensions.core.IKeyboardSupport;
=======
>>>>>>> upstream/wavelet-peak-detector
import org.eclipse.swtchart.extensions.events.AbstractHandledEventProcessor;
import org.eclipse.swtchart.extensions.events.IHandledEventProcessor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class ReplacePeakToggleEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

	@Override
	public int getEvent() {

<<<<<<< HEAD
		return IKeyboardSupport.EVENT_KEY_UP;
=======
		return BaseChart.EVENT_KEY_UP;
>>>>>>> upstream/wavelet-peak-detector
	}

	@Override
	public int getButton() {

<<<<<<< HEAD
		return IKeyboardSupport.KEY_CODE_LC_D;
=======
		return BaseChart.KEY_CODE_d;
>>>>>>> upstream/wavelet-peak-detector
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
