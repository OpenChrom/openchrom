/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.chemclipse.support.events.IPerspectiveAndViewIds;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;
import net.openchrom.msd.process.supplier.cms.ui.editors.CmsLibraryEditor;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.CmsFileExplorerContentProvider;
import net.openchrom.msd.process.supplier.cms.ui.internal.provider.CmsFileExplorerLabelProvider;

public class CmsLibraryExplorerPart {

	private TreeViewer treeViewer;
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;

	@Inject
	public CmsLibraryExplorerPart(Composite parent) {
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new CmsFileExplorerContentProvider());
		treeViewer.setLabelProvider(new CmsFileExplorerLabelProvider());
		setTreeViewerContent(treeViewer, EFS.getLocalFileSystem());
		/*
		 * Register single (selection changed)/double click listener here.<br/>
		 * OK, it's not the best way, but it still works at beginning.
		 */
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				/*
				 * Check the first element if null otherwise an
				 * NullPointerException would be thrown if the
				 * firstElement is null.
				 */
				Object firstElement = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(firstElement != null) {
					File file = (File)firstElement;
					/*
					 * Update the directories content, until there is
					 * actual no way to monitor the file system outside
					 * of the workbench without using operating system
					 * specific function via e.g. JNI.
					 */
					if(file.isDirectory()) {
						treeViewer.refresh(firstElement);
					}
				}
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				File file = (File)((IStructuredSelection)event.getSelection()).getFirstElement();
				openCmsLibraryEditor(file);
			}
		});
	}

	@Focus
	private void setFocus() {

		treeViewer.getTree().setFocus();
	}

	private void setTreeViewerContent(TreeViewer treeViewer, Object input) {

		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {

				treeViewer.setInput(input);
				File elementOrTreePath = new File(PreferenceSupplier.getPathLibraryExplorer());
				if(elementOrTreePath.exists()) {
					treeViewer.expandToLevel(elementOrTreePath, 1);
				}
			}
		});
	}

	private void openCmsLibraryEditor(final File file) {

		/*
		 * Check that the selected file or directory is a valid chromatogram.
		 */
		if(CmsFileExplorerContentProvider.isLibraryFile(file)) {
			/*
			 * Save the path of the parent directory.
			 */
			PreferenceSupplier.setPathLibraryExplorer(file.getParentFile().getAbsolutePath());
			/*
			 * Get the editor part stack.
			 */
			MPartStack partStack = (MPartStack)modelService.find(IPerspectiveAndViewIds.EDITOR_PART_STACK_ID, application);
			/*
			 * Create the input part and prepare it.
			 */
			MPart part = MBasicFactory.INSTANCE.createInputPart();
			part.setElementId(CmsLibraryEditor.ID);
			part.setContributionURI(CmsLibraryEditor.CONTRIBUTION_URI);
			part.setObject(file.getAbsolutePath());
			part.setIconURI(CmsLibraryEditor.ICON_URI);
			part.setLabel(file.getName());
			part.setTooltip(CmsLibraryEditor.TOOLTIP);
			part.setCloseable(true);
			/*
			 * Add it to the stack and show it.
			 */
			partStack.getChildren().add(part);
			partService.showPart(part, PartState.ACTIVATE);
		}
	}
}
