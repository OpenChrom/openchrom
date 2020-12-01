/*******************************************************************************
 * Copyright (c) 2017, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.ux.extension.xxd.ui.parts.AbstractPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.ui.parts.swt.IonMeasurementChartUI;

public class IonMeasurementChartPart extends AbstractPart<IonMeasurementChartUI> {

	private static final String TOPIC = IChemClipseEvents.TOPIC_SCAN_XXD_UPDATE_SELECTION;

	@Inject
	public IonMeasurementChartPart(Composite parent) {

		super(parent, TOPIC);
	}

	@Override
	protected IonMeasurementChartUI createControl(Composite parent) {

		return new IonMeasurementChartUI(parent, SWT.NONE);
	}

	@Override
	protected boolean updateData(List<Object> objects, String topic) {

		if(objects.size() == 1) {
			Object object = objects.get(0);
			if(object instanceof ICalibratedVendorMassSpectrum) {
				ICalibratedVendorMassSpectrum calibratedVendorMassSpectrum = (ICalibratedVendorMassSpectrum)object;
				getControl().update(calibratedVendorMassSpectrum);
				return true;
			} else if(object instanceof ICalibratedVendorLibraryMassSpectrum) {
				ICalibratedVendorLibraryMassSpectrum calibratedVendorLibraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)object;
				getControl().update(calibratedVendorLibraryMassSpectrum);
				return true;
			} else {
				getControl().update(null);
				return false;
			}
		}
		//
		return false;
	}

	@Override
	protected boolean isUpdateTopic(String topic) {

		return TOPIC.equals(topic);
	}
}