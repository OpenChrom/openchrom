/*******************************************************************************
 * Copyright (c) 2017 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * whitlow - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import java.io.File;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;

public class CompositeLibrarySpectra extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private Text textCmsLibraryFilePath;
	private List listCmsComponents;
	private IMassSpectra cmsLibSpectra;
	private boolean isSelected[];

	public CompositeLibrarySpectra(Composite parent, int style) {
		super(parent, style);
		this.initialize();
	}

	public IMassSpectra getLibSpectra() {

		IMassSpectra spectra = new MassSpectra();
		if(null == cmsLibSpectra) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a CMS library file");
			return (null);
		}
		for(int i = 0; i < cmsLibSpectra.getList().size(); i++) {
			if(isSelected[i]) {
				spectra.addMassSpectrum(cmsLibSpectra.getList().get(i));
			}
		}
		if(0 >= spectra.getList().size()) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select library components");
			return (null);
		}
		spectra.setName(cmsLibSpectra.getName());
		return spectra;
	}

	private void initialize() {

		GridLayout thisGridLayout = new GridLayout(2, false);
		thisGridLayout.marginHeight = 0;
		thisGridLayout.marginWidth = 0;
		this.setLayout(thisGridLayout);
		GridData thisGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		thisGridData.horizontalSpan = 2;
		thisGridData.heightHint = 300;
		this.setLayoutData(thisGridData);
		// CMS library path
		textCmsLibraryFilePath = new Text(this, SWT.BORDER);
		textCmsLibraryFilePath.setText("");
		GridData textCmsLibraryFilePathGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textCmsLibraryFilePath.setLayoutData(textCmsLibraryFilePathGridData);
		// Load button
		addButtonSelect(this);
		// Component List
		listCmsComponents = new List(this, SWT.SINGLE | SWT.V_SCROLL);
		listCmsComponents.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {

				String newString;
				int selection = listCmsComponents.getSelectionIndex();
				if(null != cmsLibSpectra) {
					ICalibratedVendorLibraryMassSpectrum spectrum = (ICalibratedVendorLibraryMassSpectrum)cmsLibSpectra.getList().get(selection);
					isSelected[selection] = !isSelected[selection];
					newString = spectrum.getLibraryInformation().getName();
					if(isSelected[selection]) {
						newString = " *     " + newString;
					} else {
						newString = "       " + newString;
					}
					listCmsComponents.setItem(selection, newString);
				}
				// String outString = "";
				// for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++)
				// outString += selectedItems[loopIndex] + " ";
				return;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

				int[] selectedItems = listCmsComponents.getSelectionIndices();
				for(int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++) {
				}
				return;
			}
		});
		GridData listCmsComponentsGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		listCmsComponentsGridData.horizontalSpan = 2;
		listCmsComponents.setLayoutData(listCmsComponentsGridData);
	}

	private void addButtonSelect(Composite parent) {

		Button buttonSelect;
		buttonSelect = new Button(parent, SWT.NONE);
		buttonSelect.setText("");
		buttonSelect.setToolTipText("Select the *.cms library file.");
		buttonSelect.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FILE, IApplicationImageProvider.SIZE_16x16));
		buttonSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String pathCmsLibrarySpectra = PreferenceSupplier.getPathCmsLibrarySpectra();
				FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.READ_ONLY);
				fileDialog.setText("Select the CMS library file.");
				fileDialog.setFilterExtensions(new String[]{"*.cms", "*.CMS"});
				fileDialog.setFilterNames(new String[]{"Calibrated Spectra (*.cms)", "Calibrated Spectra (*.CMS)"});
				fileDialog.setFilterPath(pathCmsLibrarySpectra);
				String pathname = fileDialog.open();
				if(pathname != null) {
					/*
					 * Remember the path.
					 */
					pathCmsLibrarySpectra = fileDialog.getFilterPath();
					PreferenceSupplier.setPathCmsLibrarySpectra(pathCmsLibrarySpectra);
					textCmsLibraryFilePath.setText(pathname);
					readAndLoadCMSlibraryFile();
				}
			}
		});
	}

	private void readAndLoadCMSlibraryFile() {

		try {
			File file = new File(textCmsLibraryFilePath.getText().trim());
			if(file.exists()) {
				MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
				cmsLibSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
				if(null != cmsLibSpectra) {
					isSelected = new boolean[cmsLibSpectra.getList().size()];
					listCmsComponents.removeAll();
					StringBuilder stringBuilder = new StringBuilder();
					for(IScanMSD spectrum : cmsLibSpectra.getList()) {
						if((null != spectrum) && (spectrum instanceof ICalibratedVendorLibraryMassSpectrum)) {
							stringBuilder.setLength(0);
							stringBuilder.append(((ICalibratedVendorLibraryMassSpectrum)spectrum).getLibraryInformation().getName());
							listCmsComponents.add("        " + stringBuilder.toString());
						} else {
							String fileName = textCmsLibraryFilePath.getText().trim();
							int index = fileName.lastIndexOf(File.separator);
							if(0 < index) {
								fileName = fileName.substring(1 + index);
							}
							listCmsComponents.removeAll();
							cmsLibSpectra = null;
							textCmsLibraryFilePath.setText("");
							MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "\"" + fileName + "\" is not a CMS library file\nPlease select a CMS library file");
							break; // for
						}
					} // for
				} else {
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a *.cms library file first.");
				}
			} // if(file.exists())
		} catch(Exception e1) {
			logger.warn(e1);
		}
	}
}
