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
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import java.io.File;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;
import net.openchrom.msd.process.supplier.cms.ui.preferences.PreferencePage;

public class DecompositionResultUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private Text textCmsSpectraPath;
	private XYGraph xyGraph;

	public DecompositionResultUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		/*
		 * CMS Path and Buttons
		 */
		addTextCmsSpectraPath(composite);
		//
		Composite compositeButtons = new Composite(composite, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(3, true));
		GridData gridDataComposite = new GridData();
		gridDataComposite.horizontalAlignment = SWT.RIGHT;
		compositeButtons.setLayoutData(gridDataComposite);
		//
		addButtonSelect(compositeButtons);
		addButtonLoad(compositeButtons);
		addButtonSettings(compositeButtons);
		/*
		 * XY Graph
		 */
		Composite compositeGraph = new Composite(composite, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		compositeGraph.setLayoutData(gridData);
		compositeGraph.setLayout(new FillLayout());
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraph = new XYGraph();
		xyGraph.setTitle("CMS (Calibrated Mass Spectra)");
		lightweightSystem.setContents(xyGraph);
	}

	private void addButtonSelect(Composite parent) {

		Button buttonSelect = new Button(parent, SWT.NONE);
		buttonSelect.setText("");
		buttonSelect.setToolTipText("Select the *.cms spectra.");
		buttonSelect.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FILE, IApplicationImage.SIZE_16x16));
		buttonSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String pathCmsSpectra = PreferenceSupplier.getPathCmsSpectra();
				FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.READ_ONLY);
				fileDialog.setText("Select the CMS spectra.");
				fileDialog.setFilterExtensions(new String[]{"*.cms", "*.CMS"});
				fileDialog.setFilterNames(new String[]{"Calibrated Spectra (*.cms)", "Calibrated Spectra (*.CMS)"});
				fileDialog.setFilterPath(pathCmsSpectra);
				String pathname = fileDialog.open();
				if(pathname != null) {
					/*
					 * Remember the path.
					 */
					pathCmsSpectra = fileDialog.getFilterPath();
					PreferenceSupplier.setPathCmsSpectra(pathCmsSpectra);
					textCmsSpectraPath.setText(pathname);
				}
			}
		});
	}

	private void addTextCmsSpectraPath(Composite parent) {

		textCmsSpectraPath = new Text(parent, SWT.BORDER);
		textCmsSpectraPath.setText("");
		textCmsSpectraPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void addButtonLoad(Composite parent) {

		Button buttonLoad = new Button(parent, SWT.NONE);
		buttonLoad.setText("");
		buttonLoad.setToolTipText("Load the *.cms spectra.");
		buttonLoad.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		buttonLoad.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					File file = new File(textCmsSpectraPath.getText().trim());
					if(file.exists()) {
						MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
						IMassSpectra cmsSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
						updateXYGraph(cmsSpectra);
					} else {
						MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a *.cms file first.");
					}
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
	}

	private void addButtonSettings(Composite parent) {

		Button buttonSettings = new Button(parent, SWT.NONE);
		buttonSettings.setText("");
		buttonSettings.setToolTipText("Edit the CMS settings.");
		buttonSettings.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		buttonSettings.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IPreferencePage preferencePage = new PreferencePage();
				preferencePage.setTitle("CMS Preferences");
				//
				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", preferencePage));
				//
				PreferenceDialog preferenceDialog = new PreferenceDialog(Display.getCurrent().getActiveShell(), preferenceManager);
				preferenceDialog.create();
				preferenceDialog.setMessage("CMS Calibrated Mass Spectra");
				preferenceDialog.open();
			}
		});
	}

	private void updateXYGraph(IMassSpectra cmsSpectra) {

		System.out.println("Update XYGraph");
	}
}
