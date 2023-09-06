/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Lorenz Gerber - adding support for alignment
 *******************************************************************************/
package net.openchrom.xxd.base.ui;

import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import net.openchrom.xxd.base.ui.services.IAlignmentService;
import net.openchrom.xxd.base.ui.services.IDeconvolutionService;
import net.openchrom.xxd.base.ui.services.IIdentificationService;

public class Activator extends AbstractActivatorUI {

	private static Activator activator;
	private static BundleContext context;
	//
	private ServiceTracker<IDeconvolutionService, IDeconvolutionService> deconvolutionServiceTracker = null;
	private ServiceTracker<IIdentificationService, IIdentificationService> identificationServiceTracker = null;
	private ServiceTracker<IAlignmentService, IAlignmentService> alignmentServiceTracker = null;

	public static BundleContext getContext() {

		return context;
	}

	public static Activator getDefault() {

		return activator;
	}

	public void start(BundleContext bundleContext) throws Exception {

		activator = this;
		Activator.context = bundleContext;
		startServices(context);
	}

	public void stop(BundleContext bundleContext) throws Exception {

		stopServices();
		activator = null;
		Activator.context = null;
	}

	public Object[] getDeconvolutionServices() {

		return deconvolutionServiceTracker.getServices();
	}

	public Object[] getIdentificationServices() {

		return identificationServiceTracker.getServices();
	}

	public Object[] getAlignmentServices() {

		return alignmentServiceTracker.getServices();
	}

	private void startServices(BundleContext context) {

		deconvolutionServiceTracker = new ServiceTracker<>(context, IDeconvolutionService.class, null);
		deconvolutionServiceTracker.open();
		//
		identificationServiceTracker = new ServiceTracker<>(context, IIdentificationService.class, null);
		identificationServiceTracker.open();
		//
		alignmentServiceTracker = new ServiceTracker<>(context, IAlignmentService.class, null);
		alignmentServiceTracker.open();
	}

	private void stopServices() {

		deconvolutionServiceTracker.close();
		identificationServiceTracker.close();
		alignmentServiceTracker.close();
	}
}