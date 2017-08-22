/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.opentyper.ui.views;

import java.io.File;
import javax.inject.Inject;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.events.IPerspectiveAndViewIds;
import org.eclipse.chemclipse.support.settings.UserManagement;
import net.openchrom.msd.identifier.supplier.opentyper.ui.preferences.PreferenceSupplier;

import org.eclipse.chemclipse.ux.extension.msd.ui.editors.MassSpectrumEditor;
import org.eclipse.chemclipse.ux.extension.msd.ui.internal.support.MassSpectrumIdentifier;
import org.eclipse.chemclipse.ux.extension.msd.ui.provider.MassSpectrumFileExplorerContentProvider;
import org.eclipse.chemclipse.ux.extension.msd.ui.provider.MassSpectrumFileExplorerLabelProvider;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.e4.core.services.events.IEventBroker;
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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.navigator.IDescriptionProvider;

public abstract class AbstractSupplierMassSpectrumFileExplorer {

	@Inject
	private EPartService partService;
	@Inject
	private IEventBroker eventBroker;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;
	//
	private File lastClickedFile;
	//
	private TabItem drivesTab;
	private TreeViewer drivesTreeViewer;
	//
	private TreeViewer homeTreeViewer;
	private TabItem homeTab;
	//
	private TreeViewer userLocationTreeViewer;
	private TabItem userLocationTab;

	public AbstractSupplierMassSpectrumFileExplorer(Composite parent) {
		/*
		 * Create the tree viewer.
		 */
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		createDrivesTreeViewer(tabFolder);
		createHomeTreeViewer(tabFolder);
		createUserLocationTreeViewer(tabFolder);
	}

	private void createDrivesTreeViewer(TabFolder tabFolder) {

		drivesTab = new TabItem(tabFolder, SWT.NONE);
		drivesTab.setText("Drives");
		drivesTreeViewer = createTreeViewer(tabFolder);
		drivesTreeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		setTreeViewerContent(drivesTreeViewer, EFS.getLocalFileSystem());
		drivesTab.setControl(drivesTreeViewer.getControl());
	}

	private void createHomeTreeViewer(TabFolder tabFolder) {

		homeTab = new TabItem(tabFolder, SWT.NONE);
		homeTab.setText("Home");
		homeTreeViewer = createTreeViewer(tabFolder);
		homeTreeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		setTreeViewerContent(homeTreeViewer, new File(UserManagement.getUserHome()));
		homeTab.setControl(homeTreeViewer.getControl());
	}

	private void createUserLocationTreeViewer(TabFolder tabFolder) {

		userLocationTab = new TabItem(tabFolder, SWT.NONE);
		userLocationTab.setText("User Location");
		Composite compositeUserLocation = new Composite(tabFolder, SWT.NONE);
		compositeUserLocation.setLayoutData(new GridData(GridData.FILL_BOTH));
		compositeUserLocation.setLayout(new GridLayout());
		//
		Button buttonSelectUserLocation = new Button(compositeUserLocation, SWT.PUSH);
		buttonSelectUserLocation.setText("Select User Location");
		buttonSelectUserLocation.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FOLDER_OPENED, IApplicationImage.SIZE_16x16));
		buttonSelectUserLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonSelectUserLocation.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog directoryDialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.READ_ONLY);
				directoryDialog.setText("Select a directory.");
				String pathname = directoryDialog.open();
				if(pathname != null) {
					File directory = new File(pathname);
					if(directory.exists()) {
						userLocationTreeViewer.setInput(directory);
						PreferenceSupplier.setUserLocationPath(directory.getAbsolutePath());
					}
				}
			}
		});
		//
		String userLocationPath = PreferenceSupplier.getUserLocationPath();
		File userLocation = new File(userLocationPath);
		if(!userLocation.exists()) {
			userLocation = new File(UserManagement.getUserHome());
		}
		userLocationTreeViewer = createTreeViewer(compositeUserLocation);
		userLocationTreeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		setTreeViewerContent(userLocationTreeViewer, userLocation);
		userLocationTab.setControl(compositeUserLocation);
	}

	private TreeViewer createTreeViewer(Composite parent) {

		TreeViewer treeViewer = new TreeViewer(parent, SWT.NONE);
		treeViewer.setContentProvider(new MassSpectrumFileExplorerContentProvider());
		treeViewer.setLabelProvider(new MassSpectrumFileExplorerLabelProvider());
		/*
		 * Open the editor.
		 */
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				File file = (File)((IStructuredSelection)event.getSelection()).getFirstElement();
				openEditor(file);
			}
		});
		//
		return treeViewer;
	}

	private void openEditor(File file) {

		if(file != null) {
			if(MassSpectrumIdentifier.isMassSpectrum(file)) {
				saveDirectoryPath(file);
				/*
				 * Get the editor part stack.
				 */
				MPartStack partStack = (MPartStack)modelService.find(IPerspectiveAndViewIds.EDITOR_PART_STACK_ID, application);
				/*
				 * Create the input part and prepare it.
				 */
				MPart part = MBasicFactory.INSTANCE.createInputPart();
				part.setElementId(MassSpectrumEditor.ID);
				part.setContributionURI(MassSpectrumEditor.CONTRIBUTION_URI);
				part.setObject(file.getAbsolutePath());
				part.setIconURI(MassSpectrumEditor.ICON_URI);
				part.setLabel(file.getName());
				part.setTooltip(MassSpectrumEditor.TOOLTIP);
				part.setCloseable(true);
				/*
				 * Add it to the stack and show it.
				 */
				partStack.getChildren().add(part);
				partService.showPart(part, PartState.ACTIVATE);
			}
		}
	}

	@Focus
	private void setFocus() {

		if(drivesTab.getControl().isVisible()) {
			drivesTreeViewer.getTree().setFocus();
		} else if(homeTab.getControl().isVisible()) {
			homeTreeViewer.getTree().setFocus();
		} else if(userLocationTab.getControl().isVisible()) {
			userLocationTreeViewer.getTree().setFocus();
		}
	}

	private void saveDirectoryPath(File file) {

		String directoryPath = "";
		if(file.isFile()) {
			/*
			 * Sometimes the data is stored
			 * in nested directories.
			 */
			File directory = file.getParentFile();
			if(directory != null) {
				File directoryRoot = directory.getParentFile();
				if(getNumberOfChildDirectories(directoryRoot) <= 1) {
					directoryPath = directoryRoot.getAbsolutePath();
				} else {
					directoryPath = directory.getAbsolutePath();
				}
			}
		} else {
			directoryPath = file.getAbsolutePath();
		}
		/*
		 * Store the specific directory path.
		 */
		if(!directoryPath.equals("")) {
			if(drivesTab.getControl().isVisible()) {
				PreferenceSupplier.setSelectedDrivePath(directoryPath);
			} else if(homeTab.getControl().isVisible()) {
				PreferenceSupplier.setSelectedHomePath(directoryPath);
			} else if(userLocationTab.getControl().isVisible()) {
				PreferenceSupplier.setSelectedUserLocationPath(directoryPath);
			}
		}
	}

	private int getNumberOfChildDirectories(File directory) {

		int counter = 0;
		if(directory != null) {
			for(File file : directory.listFiles()) {
				if(file.isDirectory()) {
					counter++;
				}
			}
		}
		return counter;
	}

	private void setTreeViewerContent(TreeViewer treeViewer, Object input) {

		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {

				treeViewer.setInput(input);
				expandLastDirectoryPath(treeViewer);
			}
		});
	}

	private void expandLastDirectoryPath(TreeViewer treeViewer) {

		/*
		 * Get the specific directory path.
		 */
		String directoryPath = "";
		if(treeViewer == drivesTreeViewer) {
			directoryPath = PreferenceSupplier.getSelectedDrivePath();
		} else if(treeViewer == homeTreeViewer) {
			directoryPath = PreferenceSupplier.getSelectedHomePath();
		} else if(treeViewer == userLocationTreeViewer) {
			directoryPath = PreferenceSupplier.getSelectedUserLocationPath();
		}
		//
		File elementOrTreePath = new File(directoryPath);
		if(elementOrTreePath.exists()) {
			treeViewer.expandToLevel(elementOrTreePath, 1);
		}
	}
}
