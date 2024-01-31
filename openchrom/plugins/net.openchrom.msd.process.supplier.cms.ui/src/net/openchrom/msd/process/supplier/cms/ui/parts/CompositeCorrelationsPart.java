/*******************************************************************************
 * Copyright (c) 2017, 2024 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;
import net.openchrom.msd.process.supplier.cms.ui.EventDataHolder;
import net.openchrom.msd.process.supplier.cms.ui.parts.swt.CompositeCorrelationsUI;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

public class CompositeCorrelationsPart {

	private DecompositionResults decompositionResults = null;
	//
	@Inject
	private Composite parent;
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private EventHandler eventHandler;
	//
	private CompositeCorrelationsUI compositeCorrelationsUI;

	CompositeCorrelationsPart() {

		subscribe();
	}

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		compositeCorrelationsUI = new CompositeCorrelationsUI(parent, SWT.NONE);
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
		compositeCorrelationsUI.setFocus();
	}

	private void update(DecompositionResults decompositionResults) {

		// if(isPartVisible()) { // whw, update even if hidden
		compositeCorrelationsUI.updateXYGraph(decompositionResults);
		// }
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
}
