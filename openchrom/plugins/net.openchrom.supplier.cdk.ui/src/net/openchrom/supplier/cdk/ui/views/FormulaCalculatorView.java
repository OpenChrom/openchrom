/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.ui.views;

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
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.logging.core.Logger;
import net.openchrom.support.events.IOpenChromEvents;

public class FormulaCalculatorView {

	private static final Logger logger = Logger.getLogger(FormulaCalculatorView.class);
	private Label label;
	@Inject
	private Composite parent;
	/*
	 * Event Handler
	 */
	@Inject
	private EPartService partService;
	@Inject
	private MPart part;
	private IEventBroker eventBroker;
	private EventHandler eventHandler;

	@Inject
	public FormulaCalculatorView(IEventBroker eventBroker, EventHandler eventHandler) {

		this.eventBroker = eventBroker;
		this.eventHandler = eventHandler;
		subscribe();
	}

	@PostConstruct
	private void createControl() {

		parent.setLayout(new FillLayout());
		label = new Label(parent, SWT.NONE);
		label.setText("Selected Ion");
	}

	@PreDestroy
	private void preDestroy() {

		unsubscribe();
	}

	@Focus
	public void setFocus() {

		label.setFocus();
	}

	private void update(Double ion) {

		if(isPartVisible()) {
			System.out.println(ion);
			label.setText(ion.toString());
		}
	}

	private boolean isPartVisible() {

		if(partService != null && partService.isPartVisible(part)) {
			return true;
		}
		return false;
	}

	private void unsubscribe() {

		if(eventBroker != null && eventHandler != null) {
			eventBroker.unsubscribe(eventHandler);
		}
	}

	private void subscribe() {

		if(eventBroker != null) {
			/*
			 * Receives and handles chromatogram selection updates.
			 */
			eventHandler = new EventHandler() {

				public void handleEvent(Event event) {

					try {
						Double ion = (Double)event.getProperty(IOpenChromEvents.PROPERTY_SELECTED_ION);
						update(ion);
					} catch(Exception e) {
						logger.warn(e);
					}
				}
			};
			eventBroker.subscribe(IOpenChromEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_ION_SELECTION, eventHandler);
		}
	}
}
