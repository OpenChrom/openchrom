/*******************************************************************************
 * Copyright (c) 2014, 2020 Dr. Philip Wenig, Matthias Mailänder.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.opentyper.ui.parts;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.swt.ui.components.massspectrum.ExtendedMassSpectrumIonsListUI;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.ux.extension.xxd.ui.parts.AbstractPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class MassSpectrumPeakListPart extends AbstractPart<ExtendedMassSpectrumIonsListUI> {

	private static final String TOPIC = IChemClipseEvents.TOPIC_SCAN_XXD_UPDATE_SELECTION;

	@Inject
	public MassSpectrumPeakListPart(Composite parent) {

		super(parent, TOPIC);
	}

	@Override
	protected ExtendedMassSpectrumIonsListUI createControl(Composite parent) {

		return new ExtendedMassSpectrumIonsListUI(parent, SWT.NONE);
	}

	@Override
	protected boolean updateData(List<Object> objects, String topic) {

		if(objects.size() == 1) {
			Object object = objects.get(0);
			if(object instanceof IScanMSD) {
				getControl().update((IScanMSD)object);
				return true;
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
