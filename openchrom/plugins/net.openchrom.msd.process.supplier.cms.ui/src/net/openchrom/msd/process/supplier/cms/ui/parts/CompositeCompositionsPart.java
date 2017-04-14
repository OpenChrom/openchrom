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
package net.openchrom.msd.process.supplier.cms.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;
import net.openchrom.msd.process.supplier.cms.ui.EventDataHolder;
import net.openchrom.msd.process.supplier.cms.ui.parts.swt.CompositeCompositionsUI;

public class CompositeCompositionsPart {

	private DecompositionResults decompositionResults = null;
	//
	@Inject
	private Composite parent;
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private EventHandler eventHandler;
	@Inject
	private MPart part;
	@Inject
	private EPartService partService;
	//
	private CompositeCompositionsUI compositeCompositionsUI;

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		compositeCompositionsUI = new CompositeCompositionsUI(parent, SWT.NONE);
		decompositionResults = (DecompositionResults)EventDataHolder.getData(DecompositionResultPart.TOPIC_PROCESS_SUPPLIER_CMS_UPDATE_RESULT);
		subscribe();
		update(decompositionResults);
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		update(decompositionResults);
		compositeCompositionsUI.setFocus();
	}

	private void update(DecompositionResults decompositionResults) {

		if(isPartVisible()) {
			compositeCompositionsUI.updateXYGraph(decompositionResults);
		}
	}

	/**
	 * Subscribes the selection update events.
	 */
	private void subscribe() {

		if(eventBroker != null) {
			eventHandler = new EventHandler() {

				@Override
				public void handleEvent(Event event) {

					decompositionResults = (DecompositionResults)event.getProperty(DecompositionResultPart.PROPERTY_RESULT);
					update(decompositionResults);
				}
			};
			eventBroker.subscribe(DecompositionResultPart.TOPIC_PROCESS_SUPPLIER_CMS_UPDATE_RESULT, eventHandler);
		}
	}

	private void unsubscribe() {

		if(eventBroker != null && eventHandler != null) {
			eventBroker.unsubscribe(eventHandler);
		}
	}

	private boolean isPartVisible() {

		if(partService != null && partService.isPartVisible(part)) {
			return true;
		}
		return false;
	}
}
