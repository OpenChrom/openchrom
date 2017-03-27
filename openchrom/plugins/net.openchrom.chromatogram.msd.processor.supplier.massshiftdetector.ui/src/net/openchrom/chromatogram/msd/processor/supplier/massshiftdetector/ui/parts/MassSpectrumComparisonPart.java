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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.swt.ui.components.massspectrum.MassValueDisplayPrecision;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.swt.MassSpectrumComparisonUI;

public class MassSpectrumComparisonPart {

	@Inject
	private Composite parent;
	@Inject
	private EPartService partService;
	@Inject
	private MPart part;
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private EventHandler eventHandler;
	//
	private MassSpectrumComparisonUI massSpectrumComparisonUI;
	private IScanMSD referenceMassSpectrum;
	private IScanMSD comparisonMassSpectrum;

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		//
		massSpectrumComparisonUI = new MassSpectrumComparisonUI(parent, SWT.BORDER, MassValueDisplayPrecision.NOMINAL);
		//
		subscribe();
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		massSpectrumComparisonUI.setFocus();
		update(referenceMassSpectrum, comparisonMassSpectrum);
	}

	/**
	 * Subscribes the selection update events.
	 */
	private void subscribe() {

		if(eventBroker != null) {
			eventHandler = new EventHandler() {

				public void handleEvent(Event event) {

					referenceMassSpectrum = (IScanMSD)event.getProperty(IChemClipseEvents.PROPERTY_REFERENCE_MS);
					comparisonMassSpectrum = (IScanMSD)event.getProperty(IChemClipseEvents.PROPERTY_COMPARISON_MS);
					update(referenceMassSpectrum, comparisonMassSpectrum);
				}
			};
			eventBroker.subscribe(IChemClipseEvents.TOPIC_SCAN_MSD_UPDATE_COMPARISON, eventHandler);
		}
	}

	private void unsubscribe() {

		if(eventBroker != null && eventHandler != null) {
			eventBroker.unsubscribe(eventHandler);
		}
	}

	private boolean doUpdate() {

		if(isPartVisible()) {
			return true;
		}
		return false;
	}

	private boolean isPartVisible() {

		if(partService != null && partService.isPartVisible(part)) {
			return true;
		}
		return false;
	}

	private void update(IScanMSD referenceMassSpectrum, IScanMSD comparisonMassSpectrum) {

		if(doUpdate()) {
			massSpectrumComparisonUI.update(referenceMassSpectrum, comparisonMassSpectrum, true);
		}
	}
}
