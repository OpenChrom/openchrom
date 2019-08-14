/*******************************************************************************
 * Copyright (c) 2019 Alexander Stark.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.peakdetection;

import org.eclipse.chemclipse.model.core.PeakPosition;
import org.eclipse.chemclipse.model.core.PeakType;

public class WavletPeakPosition implements PeakPosition {

	@Override
	public int getPeakMaximum() {

		return 100;
	}

	@Override
	public int getPeakStart() {

		return 90;
	}

	@Override
	public int getPeakEnd() {

		return 105;
	}

	@Override
	public PeakType getPeakType() {

		return PeakType.DEFAULT;
	}
}
