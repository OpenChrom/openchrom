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

import org.eclipse.chemclipse.support.ui.wizards.AbstractFileWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.processor.supplier.tracecompare.core.Processor;
import net.openchrom.xxd.processor.supplier.tracecompare.io.ProcessorModelWriter;
import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class WizardProcessor extends AbstractFileWizard {

	private IProcessorWizardElements wizardElements = new ProcessorWizardElements();
	private PageFileSelection pageFileSelection;

	public WizardProcessor() {
		super("TraceCompare_" + new Date().getTime(), Processor.PROCESSOR_FILE_EXTENSION);
	}

	@Override
	public void addPages() {

		super.addPages();
		/*
		 * Pages must implement IExtendedWizardPage / extend AbstractExtendedWizardPage
		 */
		pageFileSelection = new PageFileSelection(wizardElements);
		addPage(pageFileSelection);
	}

	@Override
	public boolean canFinish() {

		boolean canFinish = pageFileSelection.canFinish();
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
			String projectDirectory = projectFile.getParentFile().toString() + File.separator + projectName.substring(0, projectName.length() - Processor.PROCESSOR_FILE_EXTENSION.length());
			String imageDirectory = projectDirectory + File.separator + "Images";
			File images = new File(imageDirectory);
			images.mkdirs();
			/*
			 * Write the model
			 */
			ProcessorModel processorModel = wizardElements.getProcessorModel();
			processorModel.setScanVelocity(PreferenceSupplier.getScanVelocity());
			processorModel.setImageDirectory(imageDirectory);
			ProcessorModelWriter processorModelWriter = new ProcessorModelWriter();
			processorModelWriter.write(file.getLocation().toFile(), processorModel, monitor);
		} catch(JAXBException e) {
			System.out.println(e);
		}
		/*
		 * Refresh
		 */
		super.refreshWorkspace(monitor);
		super.runOpenEditor(file, monitor);
	}
}
