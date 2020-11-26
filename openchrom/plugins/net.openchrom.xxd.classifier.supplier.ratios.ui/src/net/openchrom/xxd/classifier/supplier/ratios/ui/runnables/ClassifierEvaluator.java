/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.runnables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.chemclipse.chromatogram.msd.classifier.core.ChromatogramClassifier;
import org.eclipse.chemclipse.model.processor.AbstractChromatogramProcessor;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoPartSupport;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ClassifierEvaluator extends AbstractChromatogramProcessor implements IRunnableWithProgress {

	private String description = "";
	private String classifierId = "";

	public ClassifierEvaluator(IChromatogramSelectionMSD chromatogramSelection, String description, String classifierId) {

		super(chromatogramSelection);
		this.description = description;
		this.classifierId = classifierId;
	}

	@Override
	public void execute(IProgressMonitor monitor) {

		final IChromatogramSelection<?, ?> chromatogramSelection = getChromatogramSelection();
		if(chromatogramSelection instanceof IChromatogramSelectionMSD) {
			/*
			 * Apply the classifier.
			 */
			final IProcessingInfo<?> processingInfo = ChromatogramClassifier.applyClassifier((IChromatogramSelectionMSD)chromatogramSelection, classifierId, monitor);
			ProcessingInfoPartSupport.getInstance().update(processingInfo, false);
			chromatogramSelection.update(true);
		}
	}

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			getChromatogramSelection().getChromatogram().doOperation(this, false, false, monitor);
		} finally {
			monitor.done();
		}
	}
}
