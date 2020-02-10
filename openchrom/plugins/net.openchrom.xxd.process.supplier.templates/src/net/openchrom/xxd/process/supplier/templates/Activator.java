/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - add TemplatePeakDetector API
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.chemclipse.msd.model.detector.TemplatePeakDetector;
import org.eclipse.chemclipse.processing.ProcessorFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static ServiceTracker<ProcessorFactory, ProcessorFactory> serviceTracker;

	public static BundleContext getContext() {

		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {

		Activator.context = bundleContext;
		serviceTracker = new ServiceTracker<>(context, ProcessorFactory.class, null);
		serviceTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {

		Activator.context = null;
		serviceTracker.close();
	}

	public static Collection<TemplatePeakDetector<?>> getDetectors() {

		ProcessorFactory service = serviceTracker.getService();
		if(service != null) {
			return service.getProcessors(ProcessorFactory.genericClass(TemplatePeakDetector.class), (always, ever) -> true);
		}
		return Collections.emptyList();
	}
}
