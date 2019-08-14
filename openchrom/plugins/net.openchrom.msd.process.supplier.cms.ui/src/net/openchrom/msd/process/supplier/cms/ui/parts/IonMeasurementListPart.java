/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.ux.extension.msd.ui.views.AbstractMassSpectrumSelectionView;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.process.supplier.cms.ui.EventDataHolder;
import net.openchrom.msd.process.supplier.cms.ui.parts.swt.IonMeasurementListUI;

public class IonMeasurementListPart extends AbstractMassSpectrumSelectionView {

	@Inject
	private Composite parent;
	private IonMeasurementListUI ionMeasurementListUI;

	@Inject
	public IonMeasurementListPart(EPartService partService, MPart part, IEventBroker eventBroker) {
		super(part, partService, eventBroker);
	}

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		ionMeasurementListUI = new IonMeasurementListUI(parent, SWT.NONE);
		update((IScanMSD)EventDataHolder.getData(IChemClipseEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_MASSSPECTRUM, IChemClipseEvents.PROPERTY_MASSPECTRUM), true);
	}

	@PreDestroy
	private void preDestroy() {

	}

	@Focus
	public void setFocus() {

		ionMeasurementListUI.getTable().setFocus();
	}

	@Override
	public void update(IScanMSD massSpectrum, boolean forceReload) {

		// if(isPartVisible()) { // need to update even when not visible otherwise incorrect result is shown when view is made visible
		if(massSpectrum instanceof ICalibratedVendorLibraryMassSpectrum) {
			ICalibratedVendorLibraryMassSpectrum spectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
			ionMeasurementListUI.update(spectrum);
		} else {
			ionMeasurementListUI.update(null);
		}
		// }
	}
}