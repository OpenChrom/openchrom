/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Lorenz Gerber - adding support for alignment, batch deconvolution
 *******************************************************************************/
package net.openchrom.xxd.base.ui;

import org.eclipse.chemclipse.support.ui.activator.AbstractActivatorUI;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import net.openchrom.xxd.base.ui.services.IAlignmentService;
import net.openchrom.xxd.base.ui.services.IAnalysisSegmentService;
import net.openchrom.xxd.base.ui.services.IAverageChromatogramService;
import net.openchrom.xxd.base.ui.services.IDeconvolutionBatchService;
import net.openchrom.xxd.base.ui.services.IDeconvolutionService;
import net.openchrom.xxd.base.ui.services.IIdentificationService;
import net.openchrom.xxd.base.ui.services.ILocalMaximaScanService;
import net.openchrom.xxd.base.ui.services.ILocalMinimaScanService;

public class Activator extends AbstractActivatorUI {

	private static Activator activator;
	private static BundleContext context;
	//
	private ServiceTracker<IDeconvolutionService, IDeconvolutionService> deconvolutionServiceTracker = null;
	private ServiceTracker<IDeconvolutionBatchService, IDeconvolutionBatchService> deconvolutionBatchServiceTracker = null;
	private ServiceTracker<IIdentificationService, IIdentificationService> identificationServiceTracker = null;
	private ServiceTracker<IAlignmentService, IAlignmentService> alignmentServiceTracker = null;
	private ServiceTracker<IAverageChromatogramService, IAverageChromatogramService> averageChromatogramServiceTracker = null;
	private ServiceTracker<ILocalMinimaScanService, ILocalMinimaScanService> localMinimaScanServiceTracker = null;
	private ServiceTracker<ILocalMaximaScanService, ILocalMaximaScanService> localMaximaScanServiceTracker = null;
	private ServiceTracker<IAnalysisSegmentService, IAnalysisSegmentService> analysisSegmentServiceTracker = null;

	public static BundleContext getContext() {

		return context;
	}

	public static Activator getDefault() {

		return activator;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		activator = this;
		Activator.context = bundleContext;
		startServices(context);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

		stopServices();
		activator = null;
		Activator.context = null;
	}

	public Object[] getDeconvolutionServices() {

		return deconvolutionServiceTracker.getServices();
	}

	public Object[] getDeconvolutionBatchServices() {

		return deconvolutionBatchServiceTracker.getServices();
	}

	public Object[] getIdentificationServices() {

		return identificationServiceTracker.getServices();
	}

	public Object[] getAlignmentServices() {

		return alignmentServiceTracker.getServices();
	}

	public Object[] getAverageChromatogramServices() {

		return averageChromatogramServiceTracker.getServices();
	}

	public Object[] getLocalMinimaScanServices() {

		return localMinimaScanServiceTracker.getServices();
	}

	public Object[] getLocalMaximaScanServices() {

		return localMaximaScanServiceTracker.getServices();
	}

	public Object[] getAnalysisSegmentServices() {

		return analysisSegmentServiceTracker.getServices();
	}

	private void startServices(BundleContext context) {

		deconvolutionServiceTracker = new ServiceTracker<>(context, IDeconvolutionService.class, null);
		deconvolutionServiceTracker.open();
		//
		deconvolutionBatchServiceTracker = new ServiceTracker<>(context, IDeconvolutionBatchService.class, null);
		deconvolutionBatchServiceTracker.open();
		//
		identificationServiceTracker = new ServiceTracker<>(context, IIdentificationService.class, null);
		identificationServiceTracker.open();
		//
		alignmentServiceTracker = new ServiceTracker<>(context, IAlignmentService.class, null);
		alignmentServiceTracker.open();
		//
		averageChromatogramServiceTracker = new ServiceTracker<>(context, IAverageChromatogramService.class, null);
		averageChromatogramServiceTracker.open();
		//
		localMinimaScanServiceTracker = new ServiceTracker<>(context, ILocalMinimaScanService.class, null);
		localMinimaScanServiceTracker.open();
		//
		localMaximaScanServiceTracker = new ServiceTracker<>(context, ILocalMaximaScanService.class, null);
		localMaximaScanServiceTracker.open();
		//
		analysisSegmentServiceTracker = new ServiceTracker<>(context, IAnalysisSegmentService.class, null);
		analysisSegmentServiceTracker.open();
	}

	private void stopServices() {

		deconvolutionServiceTracker.close();
		deconvolutionBatchServiceTracker.close();
		identificationServiceTracker.close();
		alignmentServiceTracker.close();
		averageChromatogramServiceTracker.close();
		localMinimaScanServiceTracker.close();
		localMaximaScanServiceTracker.close();
		analysisSegmentServiceTracker.close();
	}
}