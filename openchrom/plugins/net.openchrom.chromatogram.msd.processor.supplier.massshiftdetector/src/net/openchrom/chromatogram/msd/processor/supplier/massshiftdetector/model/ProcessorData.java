/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;

public class ProcessorData {

	private IChromatogramMSD chromatogramReference;
	private IChromatogramMSD chromatogramShifted;
	private int level;
	private boolean useAbsoluteValues;

	public IChromatogramMSD getChromatogramReference() {

		return chromatogramReference;
	}

	public void setChromatogramReference(IChromatogramMSD chromatogramReference) {

		this.chromatogramReference = chromatogramReference;
	}

	public IChromatogramMSD getChromatogramShifted() {

		return chromatogramShifted;
	}

	public void setChromatogramShifted(IChromatogramMSD chromatogramShifted) {

		this.chromatogramShifted = chromatogramShifted;
	}

	public int getLevel() {

		return level;
	}

	public void setLevel(int level) {

		this.level = level;
	}

	public boolean isUseAbsoluteValues() {

		return useAbsoluteValues;
	}

	public void setUseAbsoluteValues(boolean useAbsoluteValues) {

		this.useAbsoluteValues = useAbsoluteValues;
	}
}
