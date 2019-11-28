/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IKeyboardSupport;
import org.eclipse.swtchart.extensions.events.AbstractHandledEventProcessor;
import org.eclipse.swtchart.extensions.events.IHandledEventProcessor;

public class PeakDetectionEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

	@Override
	public int getEvent() {

		return IKeyboardSupport.EVENT_KEY_UP;
	}

	@Override
	public int getButton() {

		return IKeyboardSupport.KEY_CODE_LC_N;
	}

	@Override
	public int getStateMask() {

		return SWT.NONE;
	}

	@Override
	public void handleEvent(BaseChart baseChart, Event event) {

	}
}
