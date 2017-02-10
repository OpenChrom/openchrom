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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;
import net.openchrom.msd.process.supplier.cms.core.MassSpectraDecomposition;
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;
import net.openchrom.msd.process.supplier.cms.ui.preferences.PreferencePage;

public class DecompositionResultUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private DecimalFormat decimalFormatTextETimes = ValueFormat.getDecimalFormatEnglish("0.0");
	private CompositeLibrarySpectra compositeLibrarys;
	private CompositeCompositions compositeCompositions;
	private CompositeSignals compositeSignalsGraph;
	private IMassSpectra cmsSpectra; // if cmsSpectra == null, then XYGraph data items are invalid
	private DecompositionResults results = null;
	private boolean usingETimes; // set true if all scans have valid ETimes, set when the CMS file is read
	private Label labelTextLeftETimes;
	private Spinner spinnerLeftScanNumber;
	private boolean spinnersIgnoreChange = false;
	private Spinner spinnerRightScanNumber;
	private Text textCmsSpectraPath;
	private Text textLeftETimes;
	private Text textRightETimes;

	public DecompositionResultUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void addButtonDecompose(Composite parent) {

		Button buttonDecompose;
		buttonDecompose = new Button(parent, SWT.NONE);
		buttonDecompose.setText("");
		buttonDecompose.setToolTipText("Decompose the selected CMS spectra.");
		buttonDecompose.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImageProvider.SIZE_16x16));
		buttonDecompose.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					System.out.println("Decompose button clicked");
					decomposeSpectra();
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
	}

	private void decomposeSpectra() {

		// check if valid scan spectra are available
		if(null == cmsSpectra) {
			return;
		}
		if(null == spinnerLeftScanNumber) {
			return;
		}
		if(null == spinnerRightScanNumber) {
			return;
		}
		if(spinnerLeftScanNumber.getSelection() < 1) {
			return;
		}
		if(spinnerLeftScanNumber.getSelection() > cmsSpectra.getList().size()) {
			return;
		}
		if(spinnerRightScanNumber.getSelection() < 1) {
			return;
		}
		if(spinnerRightScanNumber.getSelection() > cmsSpectra.getList().size()) {
			return;
		}
		if(spinnerRightScanNumber.getSelection() <= spinnerLeftScanNumber.getSelection()) {
			return;
		}
		IMassSpectra scanSpectra = new MassSpectra();
		scanSpectra.setName(cmsSpectra.getName());
		for(int i = spinnerLeftScanNumber.getSelection(); i <= spinnerRightScanNumber.getSelection(); i++) {
			scanSpectra.addMassSpectrum(cmsSpectra.getList().get(i - 1)); // make shallow copy of spectra we want
		}
		MassSpectraDecomposition decomposer = new MassSpectraDecomposition();
		IMassSpectra libMassSpectra = compositeLibrarys.getLibSpectra();
		results = decomposer.decompose(scanSpectra, libMassSpectra, new NullProgressMonitor());
		
		//try {
		//	File libraryFile = new File("C:/Users/whitlow/git/cmsworkflow/openchrom/plugins/net.openchrom.msd.process.supplier.cms.fragment.test/testData/files/import/test1/LibrarySpectra.cms");
		//	MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
		//	IMassSpectra libMassSpectra = massSpectrumReader.read(libraryFile, new NullProgressMonitor());
		//	MassSpectraDecomposition decomposer = new MassSpectraDecomposition();
		//	results = decomposer.decompose(scanSpectra, libMassSpectra, new NullProgressMonitor());
		//} catch(FileNotFoundException e) {
		//	System.out.println(e);
		//} catch(FileIsNotReadableException e) {
		//	System.out.println(e);
		//} catch(FileIsEmptyException e) {
		//	System.out.println(e);
		//} catch(IOException e) {
		//	System.out.println(e);
		//}
		
		if(null == results) {
			return;
		}
		compositeSignalsGraph.clearResiduals();
		compositeSignalsGraph.updateXYGraph(scanSpectra, results, usingETimes);
		compositeCompositions.clearXYGraph();
		compositeCompositions.updateXYGraph(results, usingETimes);
		return;
	}

	private void addButtonSelect(Composite parent) {

		Button buttonSelect;
		buttonSelect = new Button(parent, SWT.NONE);
		buttonSelect.setText("");
		buttonSelect.setToolTipText("Select the *.cms spectra.");
		buttonSelect.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FILE, IApplicationImageProvider.SIZE_16x16));
		buttonSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String pathCmsSpectra = PreferenceSupplier.getPathCmsScanSpectra();
				FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.READ_ONLY);
				fileDialog.setText("Select the CMS spectra file.");
				fileDialog.setFilterExtensions(new String[]{"*.cms", "*.CMS"});
				fileDialog.setFilterNames(new String[]{"Calibrated Spectra (*.cms)", "Calibrated Spectra (*.CMS)"});
				fileDialog.setFilterPath(pathCmsSpectra);
				String pathname = fileDialog.open();
				if(pathname != null) {
					/*
					 * Remember the path.
					 */
					pathCmsSpectra = fileDialog.getFilterPath();
					PreferenceSupplier.setPathCmsScanSpectra(pathCmsSpectra);
					textCmsSpectraPath.setText(pathname);
					readAndPlotCMSscanFile();
				}
			}
		});
	}

	private void addButtonSettings(Composite parent) {

		Button buttonSettings;
		buttonSettings = new Button(parent, SWT.NONE);
		buttonSettings.setText("");
		buttonSettings.setToolTipText("Edit the CMS settings.");
		buttonSettings.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImageProvider.SIZE_16x16));
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

	private void addSelectLeftSpectrum(Composite parent) {

		Composite compositeLeftRangeSelect = new Composite(parent, SWT.NONE);
		GridLayout layoutCompositeLeftRangeSelect = new GridLayout(4, false);
		layoutCompositeLeftRangeSelect.marginHeight = 0;
		layoutCompositeLeftRangeSelect.marginWidth = 0;
		compositeLeftRangeSelect.setLayout(layoutCompositeLeftRangeSelect);
		GridData compositeLeftRangeSelectGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeLeftRangeSelect.setLayoutData(compositeLeftRangeSelectGridData);
		//
		labelTextLeftETimes = new Label(compositeLeftRangeSelect, SWT.RIGHT);
		labelTextLeftETimes.setText("Start Time, s:");
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		labelTextLeftETimes.setLayoutData(labelGridData);
		//
		textLeftETimes = new Text(compositeLeftRangeSelect, SWT.RIGHT | SWT.BORDER);
		textLeftETimes.setText("");
		GridData textLeftETimesGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textLeftETimes.setLayoutData(textLeftETimesGridData);
		//
		new Label(compositeLeftRangeSelect, SWT.RIGHT).setText("Scan #:");
		//
		spinnerLeftScanNumber = new Spinner(compositeLeftRangeSelect, SWT.RIGHT | SWT.BORDER | SWT.READ_ONLY);
		spinnerLeftScanNumber.setMinimum(1);
		spinnerLeftScanNumber.setMaximum(1);
		spinnerLeftScanNumber.setSelection(1);
		spinnerLeftScanNumber.setIncrement(1);
		spinnerLeftScanNumber.setPageIncrement(10);
		spinnerLeftScanNumber.setToolTipText("increment or decrement starting scan number");
		GridData spinnerLeftScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerLeftScanNumberGridData.widthHint = 32;
		spinnerLeftScanNumber.setLayoutData(spinnerLeftScanNumberGridData);
		spinnerLeftScanNumber.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(spinnersIgnoreChange) {
					return;
				}
				if(spinnerLeftScanNumber.getSelection() >= spinnerRightScanNumber.getSelection()) {
					spinnerLeftScanNumber.setSelection(spinnerRightScanNumber.getSelection() - 1);
				}
				updateTextLeftETimes(cmsSpectra);
			}
		});
	}

	private void addSelectRightSpectrum(Composite parent) {

		Composite compositeRightRangeSelect = new Composite(parent, SWT.NONE);
		GridLayout layoutCompositeRightRangeSelect = new GridLayout(4, false);
		layoutCompositeRightRangeSelect.marginHeight = 0;
		layoutCompositeRightRangeSelect.marginWidth = 0;
		compositeRightRangeSelect.setLayout(layoutCompositeRightRangeSelect);
		GridData compositeRightRangeSelectGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeRightRangeSelect.setLayoutData(compositeRightRangeSelectGridData);
		//
		Label labelTextRightETimes = new Label(compositeRightRangeSelect, SWT.RIGHT);
		labelTextRightETimes.setText("End Time, s:");
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		int width = labelTextLeftETimes.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		labelGridData.widthHint = width;
		labelTextRightETimes.setLayoutData(labelGridData);
		//
		textRightETimes = new Text(compositeRightRangeSelect, SWT.RIGHT | SWT.BORDER);
		textRightETimes.setText("");
		GridData textRightETimesGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textRightETimes.setLayoutData(textRightETimesGridData);
		//
		new Label(compositeRightRangeSelect, SWT.NONE).setText("Scan #:");
		//
		spinnerRightScanNumber = new Spinner(compositeRightRangeSelect, SWT.RIGHT | SWT.BORDER | SWT.READ_ONLY);
		spinnerRightScanNumber.setMinimum(1);
		spinnerRightScanNumber.setMaximum(1);
		spinnerRightScanNumber.setSelection(1);
		spinnerRightScanNumber.setIncrement(1);
		spinnerRightScanNumber.setPageIncrement(10);
		spinnerRightScanNumber.setToolTipText("increment or decrement ending scan number");
		GridData spinnerRightScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerRightScanNumberGridData.widthHint = 32;
		spinnerRightScanNumber.setLayoutData(spinnerRightScanNumberGridData);
		spinnerRightScanNumber.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(spinnersIgnoreChange) {
					return;
				}
				if(spinnerRightScanNumber.getSelection() <= spinnerLeftScanNumber.getSelection()) {
					spinnerRightScanNumber.setSelection(spinnerLeftScanNumber.getSelection() + 1);
				}
				updateTextRightETimes(cmsSpectra);
			}
		});
	}

	private void initialize() {

		this.setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout compositeGridLayout = new GridLayout(2, true);
		composite.setLayout(compositeGridLayout);
		compositeLibrarys = new CompositeLibrarySpectra(composite, SWT.NONE);
		this.initializeSignalsComposite(composite);
		compositeCompositions = new CompositeCompositions(composite, SWT.NONE);
	}

	private void initializeSignalsComposite(Composite parent) {

		Composite compositeSignals = new Composite(parent, SWT.NONE);
		GridLayout compositeSignalsGridLayout = new GridLayout(1, true);
		compositeSignalsGridLayout.marginHeight = 0;
		compositeSignalsGridLayout.marginWidth = 0;
		compositeSignals.setLayout(compositeSignalsGridLayout);
		GridData compositeSignalsGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeSignals.setLayoutData(compositeSignalsGridData);
		//
		Composite compositeTopRow = new Composite(compositeSignals, SWT.NONE);
		GridLayout topRowCompositeGridLayout = new GridLayout(2, false);
		topRowCompositeGridLayout.marginHeight = 0;
		topRowCompositeGridLayout.marginWidth = 0;
		compositeTopRow.setLayout(topRowCompositeGridLayout);
		GridData topRowCompositeGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeTopRow.setLayoutData(topRowCompositeGridData);
		// CMS Path and Buttons
		textCmsSpectraPath = new Text(compositeTopRow, SWT.BORDER);
		textCmsSpectraPath.setText("");
		GridData textCmsSpectraPathGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textCmsSpectraPath.setLayoutData(textCmsSpectraPathGridData);
		Composite compositeButtons = new Composite(compositeTopRow, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(3, true));
		GridData compositeButtonsGridData = new GridData(SWT.RIGHT, SWT.TOP, false, true);
		compositeButtons.setLayoutData(compositeButtonsGridData);
		addButtonSelect(compositeButtons);
		addButtonDecompose(compositeButtons);
		addButtonSettings(compositeButtons);
		// ETimes Text and scan # Spinners
		addSelectLeftSpectrum(compositeSignals);
		addSelectRightSpectrum(compositeSignals);
		compositeSignalsGraph = new CompositeSignals(compositeSignals, SWT.NONE);
	}

	private void readAndPlotCMSscanFile() {

		try {
			File file = new File(textCmsSpectraPath.getText().trim());
			if(file.exists()) {
				compositeSignalsGraph.clearXYGraph();
				results = null; // invalidate current decomposition results
				compositeCompositions.clearXYGraph();
				MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
				cmsSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
				if(null != cmsSpectra) {
					usingETimes = true;
					for(IScanMSD spectrum : cmsSpectra.getList()) {
						if((null != spectrum) && (spectrum instanceof ICalibratedVendorMassSpectrum)) {
							if(0 > ((ICalibratedVendorMassSpectrum)spectrum).getEtimes()) {
								usingETimes = false;
							}
						} else {
							String fileName = textCmsSpectraPath.getText().trim();
							int index = fileName.lastIndexOf(File.separator);
							if(0 < index) {
								fileName = fileName.substring(1 + index);
							}
							MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "\"" + fileName + "\" is a CMS library file\nPlease select a CMS scan file");
							cmsSpectra = null;
							textCmsSpectraPath.setText("");
							break; // for
						}
					} // for
				} else {
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a *.cms file first.");
				}
				spinnersIgnoreChange = true;
				if(null != cmsSpectra) {
					spinnerLeftScanNumber.setMinimum(1);
					spinnerLeftScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnerLeftScanNumber.setSelection(1);
					spinnerRightScanNumber.setMinimum(1);
					spinnerRightScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnerRightScanNumber.setSelection(cmsSpectra.getList().size());
				} else {
					spinnerLeftScanNumber.setMinimum(1);
					spinnerLeftScanNumber.setMaximum(1);
					spinnerLeftScanNumber.setSelection(1);
					spinnerRightScanNumber.setMinimum(1);
					spinnerRightScanNumber.setMaximum(1);
					spinnerRightScanNumber.setSelection(1);
				}
				spinnersIgnoreChange = false;
				updateTextLeftETimes(cmsSpectra);
				updateTextRightETimes(cmsSpectra);
			} // if(file.exists())
		} catch(Exception e1) {
			logger.warn(e1);
		}
	}

	private void updateTextLeftETimes(IMassSpectra cmsSpectra) {

		double markerXposition;
		int spinnerValue;
		spinnerValue = spinnerLeftScanNumber.getSelection();
		markerXposition = spinnerValue;
		if((null != cmsSpectra) && (usingETimes)) {
			ICalibratedVendorMassSpectrum spectrum;
			spinnerValue = spinnerLeftScanNumber.getSelection();
			if((spinnerValue >= 1) && (spinnerValue <= cmsSpectra.getList().size())) {
				spectrum = (ICalibratedVendorMassSpectrum)cmsSpectra.getMassSpectrum(spinnerValue);
				markerXposition = spectrum.getEtimes();
				textLeftETimes.setText(decimalFormatTextETimes.format(markerXposition));
			} else {
				textLeftETimes.setText("Error, spinner value " + spinnerValue + " is out of range");
			}
		} else {
			textLeftETimes.setText("Elapsed Time is not present in this file");
		}
		compositeSignalsGraph.setLeftMarker(markerXposition);
		compositeSignalsGraph.updateXYGraph(cmsSpectra, results, usingETimes);
	}

	private void updateTextRightETimes(IMassSpectra cmsSpectra) {

		double markerXposition;
		int spinnerValue;
		spinnerValue = spinnerRightScanNumber.getSelection();
		markerXposition = spinnerValue;
		if((null != cmsSpectra) && (usingETimes)) {
			ICalibratedVendorMassSpectrum spectrum;
			spinnerValue = spinnerRightScanNumber.getSelection();
			if((spinnerValue >= 1) && (spinnerValue <= cmsSpectra.getList().size())) {
				spectrum = (ICalibratedVendorMassSpectrum)cmsSpectra.getMassSpectrum(spinnerValue);
				markerXposition = spectrum.getEtimes();
				textRightETimes.setText(decimalFormatTextETimes.format(markerXposition));
			} else {
				textRightETimes.setText("Error, spinner value " + spinnerValue + " is out of range");
			}
		} else {
			textRightETimes.setText("Elapsed Time is not present in this file");
		}
		compositeSignalsGraph.setRightMarker(markerXposition);
		compositeSignalsGraph.updateXYGraph(cmsSpectra, results, usingETimes);
	}
}
