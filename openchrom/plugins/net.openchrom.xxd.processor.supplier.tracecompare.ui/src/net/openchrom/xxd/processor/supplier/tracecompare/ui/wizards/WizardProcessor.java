/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.wizards;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.wizards.AbstractFileWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;
import net.openchrom.xxd.processor.supplier.tracecompare.io.ProcessorModelWriter;
import net.openchrom.xxd.processor.supplier.tracecompare.model.IProcessorModel;

public class WizardProcessor extends AbstractFileWizard {

	private static final Logger logger = Logger.getLogger(WizardProcessor.class);
	//
	private IProcessorWizardElements wizardElements = new ProcessorWizardElements();
	private PageDirectorySelection pageDirectorySelection;

	public WizardProcessor() {
		super("TraceCompare_" + new Date().getTime(), DataProcessor.PROCESSOR_FILE_EXTENSION);
	}

	@Override
	public void addPages() {

		super.addPages();
		/*
		 * Pages must implement IExtendedWizardPage / extend AbstractExtendedWizardPage
		 */
		pageDirectorySelection = new PageDirectorySelection(wizardElements);
		addPage(pageDirectorySelection);
	}

	@Override
	public boolean canFinish() {

		boolean canFinish = pageDirectorySelection.canFinish();
		return canFinish;
	}

	@Override
	public void doFinish(IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("TraceCompare", IProgressMonitor.UNKNOWN);
		final IFile file = super.prepareProject(monitor);
		//
		try {
			File projectFile = file.getLocation().toFile();
			String projectName = projectFile.getName();
			/*
			 * Images
			 */
			String projectDirectory = projectFile.getParentFile().toString() + File.separator + projectName.substring(0, projectName.length() - DataProcessor.PROCESSOR_FILE_EXTENSION.length());
			String imageDirectory = projectDirectory + File.separator + "Images";
			File images = new File(imageDirectory);
			images.mkdirs();
			/*
			 * Write the model
			 */
			IProcessorModel processorModel = wizardElements.getProcessorModel();
			processorModel.setImageDirectory(imageDirectory);
			ProcessorModelWriter processorModelWriter = new ProcessorModelWriter();
			processorModelWriter.write(file.getLocation().toFile(), processorModel, monitor);
		} catch(JAXBException e) {
			logger.warn(e);
		}
		/*
		 * Refresh
		 */
		super.refreshWorkspace(monitor);
		super.runOpenEditor(file, monitor);
	}
}
