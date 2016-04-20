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
package net.openchrom.msd.converter.supplier.mgf.converter.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IIonTransition;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

import net.openchrom.msqbatlibs.bridge.TransformerIonPeak;
import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.jmgf.impl.MGFElementBean;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerIScanMSDMGFElement extends AbstractTransformingListFactory<IScanMSD, List<MGFElement>> {

	private final static TransformerIonPeak TRANSFORMER = new TransformerIonPeak();

	@Override
	public List<MGFElement> transform(IScanMSD element) {

		List<MGFElement> result = new ArrayList<>();
		MGFElementBean mgfElement = new MGFElementBean();
		mgfElement.addTags(MGFElement.Identifier.TITLE.toString(), element.getIdentifier());
		mgfElement.addTags(MGFElement.Identifier.SCANS.toString(), Integer.toString(element.getScanNumber()));
		mgfElement.addTags(MGFElement.Identifier.RTINSECONDS.toString(), Integer.toString(element.getRetentionTime() / 1000));
		mgfElement.setPeaks(TRANSFORMER.transformCollection(element.getIons()));
		result.add(mgfElement);
		for(IIon ion : element.getIons()) {
			result.addAll(processTransitions(element, ion));
		}
		return result;
	}

	List<MGFElement> processTransitions(IScanMSD element, IIon ion) {

		List<MGFElement> result = new ArrayList<>();
		IIonTransition ionTransition = ion.getIonTransition();
		if(ionTransition != null) {
			MGFElementBean mgfElement = new MGFElementBean();
			mgfElement.addTags(MGFElement.Identifier.TITLE.toString(), element.getIdentifier() + " transition");
			mgfElement.addTags(MGFElement.Identifier.SCANS.toString(), Integer.toString(element.getScanNumber()));
			mgfElement.addTags(MGFElement.Identifier.PEPMASS.toString(), Double.toString(ion.getIon()) + MGFFile.Format.PEAK_PROPERTIES_SEPARATOR + Double.toString(ion.getAbundance()));
			result.add(mgfElement);
		}
		return result;
	}
}
