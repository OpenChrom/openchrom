/*******************************************************************************
 * Copyright (c) 2016 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IIonTransition;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

import net.openchrom.msd.converter.supplier.mgf.converter.TransformerIonPeak;
import net.sf.jmgf.MGFElement;
import net.sf.jmgf.impl.MGFElementBean;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerIScanMSDMGFElement extends AbstractTransformingListFactory<IScanMSD, MGFElement> {

	private final static TransformerIonPeak TRANSFORMER = new TransformerIonPeak();

	static IIon getPrecursorIon(IScanMSD element) {

		return null;
	}

	@Override
	public MGFElement transform(IScanMSD element) {

		MGFElementBean result = new MGFElementBean();
		getPrecursorIon(element);
		for(IIon ion : element.getIons()) {
			IIonTransition transition = ion.getIonTransition();
			if(transition != null) {
			}
		}
		result.addElement(MGFElement.Identifier.TITLE.toString(), element.getIdentifier());
		result.addElement(MGFElement.Identifier.SCANS.toString(), Integer.toString(element.getScanNumber()));
		result.addElement(MGFElement.Identifier.RTINSECONDS.toString(), Integer.toString(element.getRetentionTime() / 1000));
		result.setPeaks(TRANSFORMER.transformCollection(element.getIons()));
		return result;
	}
}
