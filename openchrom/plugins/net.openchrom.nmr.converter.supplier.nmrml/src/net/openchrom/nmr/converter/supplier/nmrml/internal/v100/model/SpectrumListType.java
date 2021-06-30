/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpectrumListType", propOrder = {"spectrum1D", "spectrumMultiD"})
public class SpectrumListType {

	protected List<Spectrum1DType> spectrum1D;
	protected List<SpectrumMultiDType> spectrumMultiD;

	public List<Spectrum1DType> getSpectrum1D() {

		if(spectrum1D == null) {
			spectrum1D = new ArrayList<Spectrum1DType>();
		}
		return this.spectrum1D;
	}

	public List<SpectrumMultiDType> getSpectrumMultiD() {

		if(spectrumMultiD == null) {
			spectrumMultiD = new ArrayList<SpectrumMultiDType>();
		}
		return this.spectrumMultiD;
	}
}
