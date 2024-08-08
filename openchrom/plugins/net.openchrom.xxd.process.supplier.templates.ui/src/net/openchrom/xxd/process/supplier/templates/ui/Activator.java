/*******************************************************************************
 * Copyright (c) 2018, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - icons
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.eclipse.chemclipse.support.ui.editors.SystemEditor;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.icon.Icon;

public class Activator extends AbstractActivatorUI {

	private static final Logger logger = Logger.getLogger(Activator.class);
	//
	private static Activator plugin;
	private List<EventHandler> registeredEventHandler = new ArrayList<>();

	@Override
	public void start(BundleContext context) throws Exception {

		super.start(context);
		plugin = this;
		initializePreferenceStore(PreferenceSupplier.INSTANCE());
		initializeImageRegistry(getImageHashMap());
		registerEventBroker(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

		plugin = null;
		super.stop(context);
	}

	public static AbstractActivatorUI getDefault() {

		return plugin;
	}

	private Map<String, String> getImageHashMap() {

		Map<String, String> imageHashMap = new HashMap<>();
		imageHashMap.put(Icon.TEMPLATE, "icons/16x16/template.gif"); // $NON-NLS-1$
		return imageHashMap;
	}

	private void registerEventBroker(BundleContext bundleContext) {

		IEventBroker eventBroker = getEventBroker(bundleContext);
		if(eventBroker != null) {
			registeredEventHandler.add(registerEventHandler(eventBroker, IChemClipseEvents.EVENT_BROKER_DATA, IChemClipseEvents.TOPIC_PROCESSING_FILE_CREATED));
		}
	}

	private EventHandler registerEventHandler(IEventBroker eventBroker, String property, String topic) {

		EventHandler eventHandler = new EventHandler() {

			@Override
			public void handleEvent(Event event) {

				try {
					Object object = event.getProperty(property);
					if(object instanceof File file) {
						if(file.exists()) {
							if(IChemClipseEvents.TOPIC_PROCESSING_FILE_CREATED.equals(topic)) {
								if(PreferenceSupplier.isOpenReportAfterProcessing()) {
									SystemEditor.open(file);
								}
							}
						}
					}
				} catch(Exception e) {
					logger.warn(e);
				}
			}
		};
		eventBroker.subscribe(topic, eventHandler);
		return eventHandler;
	}

	private IEventBroker getEventBroker(BundleContext bundleContext) {

		IEclipseContext eclipseContext = EclipseContextFactory.getServiceContext(bundleContext);
		eclipseContext.set(Logger.class, null);
		return eclipseContext.get(IEventBroker.class);
	}
}
