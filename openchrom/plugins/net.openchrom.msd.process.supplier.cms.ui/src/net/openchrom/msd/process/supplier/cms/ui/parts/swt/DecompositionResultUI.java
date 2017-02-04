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
import java.util.ArrayList;
import java.util.TreeMap;

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
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;
import net.openchrom.msd.process.supplier.cms.core.MassSpectraDecomposition;
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;
import net.openchrom.msd.process.supplier.cms.ui.preferences.PreferencePage;

public class DecompositionResultUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private IMassSpectra cmsSpectra; // if cmsSpectra == null, then XYGraph data items are invalid
	private DecompositionResults results = null;
	private boolean hasETimes; // true if all scans have valid ETimes, set when the CMS file is read
	private Label labelTextLeftETimes;
	private String signalUnits;
	private Spinner spinnerLeftScanNumber;
	private boolean spinnersIgnoreChange = false;
	private Spinner spinnerRightScanNumber;
	private Text textCmsSpectraPath;
	private Text textLeftETimes;
	private Text textRightETimes;
	private Trace traceScanSignalSum; // xyGraphSignal scan data item
	private Trace traceResidualSignalSum; // xyGraphSignal scan data item
	private Trace traceLeftMarker; // xyGraphSignal scan data item
	private Trace traceRightMarker; // xyGraphSignal scan data item
	private TreeMap<String, Trace> traceCompositionsMap; // key is composition name string, value is Trace for that composition, needed so trace can be removed
	private CircularBufferDataProvider dataProviderTraceSignalSum; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceResidualSignalSum; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceLeftMarker; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceRightMarker; // xyGraphSignal scan data item
	private double xDataTraceLeftMarker[]; // xyGraphSignal scan data item
	private double xDataTraceRightMarker[]; // xyGraphSignal scan data item
	private double xDataTraceScanSignalSum[]; // xyGraphSignal scan data item
	private double xDataTraceResidualSignalSum[]; // xyGraphSignal scan data item
	private XYGraph xyGraphComposition;
	private XYGraph xyGraphSignal;
	private int xyGraphSignalNumberOfScanPoints = 0; // if xyGraphSignalNumberOfScanPoints > 0, then remainder of xyGraphSignal scan data items are valid
	private int xyGraphSignalNumberOfResidualPoints = 0; // if xyGraphSignalNumberOfResidualPoints > 0, then remainder of xyGraphSignal residual data items are valid
	private int xyGraphCompositionNumberOfPoints = 0; // if xyGraphCompositionNumberOfPoints > 0, then remainder of xyGraphComposition data items are valid
	private double yDataTraceScanSignalSum[]; // xyGraphSignal scan data item
	private double yDataTraceResidualSignalSum[]; // xyGraphSignal scan data item
	private double yDataTraceLeftMarker[]; // xyGraphSignal scan data item
	private double yDataTraceRightMarker[]; // xyGraphSignal scan data item

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
		/*
		 * argon, nitrogen, oxygen, ethane, ethylene
		 */
		try {
			File libraryFile = new File("C:/Users/whitlow/git/cmsworkflow/openchrom/plugins/net.openchrom.msd.process.supplier.cms.fragment.test/testData/files/import/test1/LibrarySpectra.cms");
			MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
			IMassSpectra libMassSpectra = massSpectrumReader.read(libraryFile, new NullProgressMonitor());
			MassSpectraDecomposition decomposer = new MassSpectraDecomposition();
			results = decomposer.decompose(scanSpectra, libMassSpectra, new NullProgressMonitor());
		} catch(FileNotFoundException e) {
			System.out.println(e);
		} catch(FileIsNotReadableException e) {
			System.out.println(e);
		} catch(FileIsEmptyException e) {
			System.out.println(e);
		} catch(IOException e) {
			System.out.println(e);
		}
		if(null == results) {
			return;
		}
		xyGraphSignalNumberOfResidualPoints = 0;
		updateXYGraphSignals(cmsSpectra);
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

				String pathCmsSpectra = PreferenceSupplier.getPathCmsSpectra();
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
					PreferenceSupplier.setPathCmsSpectra(pathCmsSpectra);
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
				updateTextETimes(cmsSpectra);
				updateXYGraphSignals(cmsSpectra);
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
				updateTextETimes(cmsSpectra);
				updateXYGraphSignals(cmsSpectra);
			}
		});
	}

	private void initialize() {

		this.setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout compositeGridLayout = new GridLayout(2, true);
		// compositeGridLayout.marginHeight = 0;
		// compositeGridLayout.marginWidth = 0;
		composite.setLayout(compositeGridLayout);
		this.initializeSignalsComposite(composite);
		this.initializeCompositionsComposite(composite);
	}

	private void initializeCompositionsComposite(Composite parent) {

		/*
		 * XY Graph
		 */
		Composite compositeGraph = new Composite(parent, SWT.NONE);
		compositeGraph.setLayout(new FillLayout());
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraphComposition = new XYGraph();
		traceCompositionsMap = new TreeMap<String, Trace>();
		xyGraphComposition.setTitle("Composition");
		xyGraphComposition.getPrimaryXAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraphComposition.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraphComposition);
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
		/*
		 * XY Graph
		 */
		Composite compositeGraph = new Composite(compositeSignals, SWT.NONE);
		compositeGraph.setLayout(new FillLayout());
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraphSignal = new XYGraph();
		xyGraphSignal.setTitle("Signal");
		xyGraphSignal.getPrimaryXAxis().setAutoScale(true);
		xyGraphSignal.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphSignal.getPrimaryYAxis().setAutoScale(true);
		xyGraphSignal.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphSignal.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraphSignal.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraphSignal);
	}

	private void readAndPlotCMSscanFile() {

		try {
			File file = new File(textCmsSpectraPath.getText().trim());
			if(file.exists()) {
				xyGraphSignalNumberOfScanPoints = 0; // invalidate current XYGraph data
				MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
				cmsSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
				if(null != cmsSpectra) {
					hasETimes = true;
					for(IScanMSD spectrum : cmsSpectra.getList()) {
						if((null != spectrum) && (spectrum instanceof ICalibratedVendorMassSpectrum)) {
							if(0 > ((ICalibratedVendorMassSpectrum)spectrum).getEtimes()) {
								hasETimes = false;
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
				if(null != cmsSpectra) {
					spinnersIgnoreChange = true;
					spinnerLeftScanNumber.setMinimum(1);
					spinnerLeftScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnerLeftScanNumber.setEnabled(false); // does this prevent a ModifyEvent?
					spinnerLeftScanNumber.setSelection(1);
					spinnerLeftScanNumber.setEnabled(true);
					spinnerRightScanNumber.setMinimum(1);
					spinnerRightScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnersIgnoreChange = false;
					spinnerRightScanNumber.setSelection(cmsSpectra.getList().size());
					// updateTextETimes(cmsSpectra); // not needed
					// updateXYGraph(cmsSpectra); // not needed
				} else {
					spinnersIgnoreChange = true;
					spinnerLeftScanNumber.setMinimum(1);
					spinnerLeftScanNumber.setMaximum(1);
					spinnerLeftScanNumber.setSelection(1);
					spinnerRightScanNumber.setMinimum(1);
					spinnerRightScanNumber.setMaximum(1);
					spinnersIgnoreChange = false;
					spinnerRightScanNumber.setSelection(1);
				}
			} // if(file.exists())
		} catch(Exception e1) {
			logger.warn(e1);
		}
	}

	private void updateTextETimes(IMassSpectra cmsSpectra) {

		DecimalFormat decimalFormatTextETimes = ValueFormat.getDecimalFormatEnglish("0.0");
		if((null != cmsSpectra) && (hasETimes)) {
			int spinnerValue;
			double eTimes;
			ICalibratedVendorMassSpectrum spectrum;
			spinnerValue = spinnerLeftScanNumber.getSelection();
			if((spinnerValue >= 1) && (spinnerValue <= cmsSpectra.getList().size())) {
				spectrum = (ICalibratedVendorMassSpectrum)cmsSpectra.getMassSpectrum(spinnerValue);
				eTimes = spectrum.getEtimes();
				textLeftETimes.setText(decimalFormatTextETimes.format(eTimes));
			} else {
				textLeftETimes.setText("Error, spinner value " + spinnerValue + " is out of range");
			}
			spinnerValue = spinnerRightScanNumber.getSelection();
			if((spinnerValue >= 1) && (spinnerValue <= cmsSpectra.getList().size())) {
				spectrum = (ICalibratedVendorMassSpectrum)cmsSpectra.getMassSpectrum(spinnerValue);
				eTimes = spectrum.getEtimes();
				textRightETimes.setText(decimalFormatTextETimes.format(eTimes));
			} else {
				textRightETimes.setText("Error, spinner value " + spinnerValue + " is out of range");
			}
		} else {
			textLeftETimes.setText("Elapsed Time is not present in this file");
			textRightETimes.setText("Elapsed Time is not present in this file");
		}
	}

	private void updateXYGraphSignals(IMassSpectra spectra) {

		ICalibratedVendorMassSpectrum spectrum;
		// hasETimes = !hasETimes; // whw, for testing
		System.out.println("Update Signal XYGraph for " + spectra.getName());
		if(spinnerLeftScanNumber.getSelection() >= spinnerRightScanNumber.getSelection()) {
			return;
		}
		if(0 == xyGraphSignalNumberOfScanPoints) {
			String newTitle = textCmsSpectraPath.getText();
			int index = newTitle.lastIndexOf(File.separator);
			if(0 < index) {
				newTitle = newTitle.substring(1 + index);
			}
			newTitle = "Signal: " + newTitle;
			xyGraphSignal.setTitle(newTitle);
			if(hasETimes) {
				xyGraphSignal.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphSignal.getPrimaryXAxis().setTitle("Scan Number");
			}
			// create a trace data provider, which will provide the data to the trace.
			dataProviderTraceSignalSum = new CircularBufferDataProvider(false);
			xyGraphSignalNumberOfScanPoints = spectra.getList().size();
			dataProviderTraceSignalSum.setBufferSize(xyGraphSignalNumberOfScanPoints);
			xDataTraceScanSignalSum = new double[xyGraphSignalNumberOfScanPoints];
			yDataTraceScanSignalSum = new double[xyGraphSignalNumberOfScanPoints];
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(1);
			signalUnits = spectrum.getSignalUnits();
			xyGraphSignal.getPrimaryYAxis().setTitle("Signal, " + signalUnits);
			for(int i = spectra.getList().size(); i > 0;) {
				spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(i);
				--i;
				if(!hasETimes) {
					xDataTraceScanSignalSum[i] = spectrum.getSpectrumNumber();
				} else {
					xDataTraceScanSignalSum[i] = spectrum.getEtimes();
				}
				yDataTraceScanSignalSum[i] = spectrum.getTotalSignal();
				// am I being too clever?
			}
			dataProviderTraceSignalSum.setCurrentXDataArray(xDataTraceScanSignalSum);
			dataProviderTraceSignalSum.setCurrentYDataArray(yDataTraceScanSignalSum);
			if(null != traceScanSignalSum) {
				xyGraphSignal.removeTrace(traceScanSignalSum);
			}
			if(null != traceResidualSignalSum) {
				xyGraphSignal.removeTrace(traceResidualSignalSum);
			}
			traceScanSignalSum = new Trace("sum(Signal)", xyGraphSignal.getPrimaryXAxis(), xyGraphSignal.getPrimaryYAxis(), dataProviderTraceSignalSum);
			traceScanSignalSum.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_BLUE));
			// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
			xyGraphSignal.addTrace(traceScanSignalSum);
			//
			dataProviderTraceLeftMarker = new CircularBufferDataProvider(false);
			dataProviderTraceLeftMarker.setBufferSize(2);
			xDataTraceLeftMarker = new double[2];
			yDataTraceLeftMarker = new double[2];
			if(null != traceLeftMarker) {
				xyGraphSignal.removeTrace(traceLeftMarker);
			}
			traceLeftMarker = new Trace("", xyGraphSignal.getPrimaryXAxis(), xyGraphSignal.getPrimaryYAxis(), dataProviderTraceLeftMarker);
			traceLeftMarker.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
			xyGraphSignal.addTrace(traceLeftMarker);
			//
			dataProviderTraceRightMarker = new CircularBufferDataProvider(false);
			dataProviderTraceRightMarker.setBufferSize(2);
			xDataTraceRightMarker = new double[2];
			yDataTraceRightMarker = new double[2];
			if(null != traceRightMarker) {
				xyGraphSignal.removeTrace(traceRightMarker);
			}
			traceRightMarker = new Trace("", xyGraphSignal.getPrimaryXAxis(), xyGraphSignal.getPrimaryYAxis(), dataProviderTraceRightMarker);
			traceRightMarker.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
			xyGraphSignal.addTrace(traceRightMarker);
		}
		if(0 == xyGraphSignalNumberOfResidualPoints) {
			if(null != results) {
				dataProviderTraceResidualSignalSum = new CircularBufferDataProvider(false);
				xyGraphSignalNumberOfResidualPoints = results.getResults().size();
				dataProviderTraceResidualSignalSum.setBufferSize(xyGraphSignalNumberOfResidualPoints);
				xDataTraceResidualSignalSum = new double[xyGraphSignalNumberOfResidualPoints];
				yDataTraceResidualSignalSum = new double[xyGraphSignalNumberOfResidualPoints];
				for(int i = 0; i < results.getResults().size(); i++) {
					spectrum = results.getResults().get(i).getResidualSpectrum();
					if(!hasETimes) {
						xDataTraceResidualSignalSum[i] = spectrum.getSpectrumNumber();
					} else {
						xDataTraceResidualSignalSum[i] = spectrum.getEtimes();
					}
					yDataTraceResidualSignalSum[i] = spectrum.getTotalSignal();
				}
				dataProviderTraceResidualSignalSum.setCurrentXDataArray(xDataTraceResidualSignalSum);
				dataProviderTraceResidualSignalSum.setCurrentYDataArray(yDataTraceResidualSignalSum);
				if(null != traceResidualSignalSum) {
					xyGraphSignal.removeTrace(traceResidualSignalSum);
				}
				traceResidualSignalSum = new Trace("sum(Residual)", xyGraphSignal.getPrimaryXAxis(), xyGraphSignal.getPrimaryYAxis(), dataProviderTraceResidualSignalSum);
				traceResidualSignalSum.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_PURPLE));
				// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
				xyGraphSignal.addTrace(traceResidualSignalSum);
				//
				updateXYGraphCompositions(results);
			}
		}
		if(hasETimes) {
			double xygraphMinETimes, xygraphMaxETimes;
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(spinnerLeftScanNumber.getSelection());
			xygraphMinETimes = spectrum.getEtimes();
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(spinnerRightScanNumber.getSelection());
			xygraphMaxETimes = spectrum.getEtimes();
			xDataTraceLeftMarker[0] = xygraphMinETimes;
			xDataTraceLeftMarker[1] = xygraphMinETimes;
			xDataTraceRightMarker[0] = xygraphMaxETimes;
			xDataTraceRightMarker[1] = xygraphMaxETimes;
			xyGraphSignal.getPrimaryXAxis().setAutoScale(true);
			// xyGraph.getPrimaryXAxis().setRange(new Range(xygraphMinETimes, xygraphMaxETimes));
		} else {
			xDataTraceLeftMarker[0] = spinnerLeftScanNumber.getSelection();
			xDataTraceLeftMarker[1] = spinnerLeftScanNumber.getSelection();
			xDataTraceRightMarker[0] = spinnerRightScanNumber.getSelection();
			xDataTraceRightMarker[1] = spinnerRightScanNumber.getSelection();
			xyGraphSignal.getPrimaryXAxis().setAutoScale(true);
			// xyGraph.getPrimaryXAxis().setRange(new Range(spinnerLeftScanNumber.getSelection(), spinnerRightScanNumber.getSelection()));
		}
		yDataTraceLeftMarker[0] = xyGraphSignal.getPrimaryYAxis().getRange().getLower();
		yDataTraceLeftMarker[1] = xyGraphSignal.getPrimaryYAxis().getRange().getUpper();
		yDataTraceRightMarker[0] = xyGraphSignal.getPrimaryYAxis().getRange().getLower();
		yDataTraceRightMarker[1] = xyGraphSignal.getPrimaryYAxis().getRange().getUpper();
		dataProviderTraceRightMarker.setCurrentXDataArray(xDataTraceRightMarker);
		dataProviderTraceRightMarker.setCurrentYDataArray(yDataTraceRightMarker);
		dataProviderTraceLeftMarker.setCurrentXDataArray(xDataTraceLeftMarker);
		dataProviderTraceLeftMarker.setCurrentYDataArray(yDataTraceLeftMarker);
		// xyGraph.setShowLegend(!xyGraph.isShowLegend());
		// Display display = Display.getDefault();
	}

	public void updateXYGraphCompositions(DecompositionResults results) {

		if(null != results) {
			hasETimes = results.hasETimes();
			System.out.println("Update Composition XYGraph for " + results.getName());
			// if(0 == xyGraphNumberOfPoints) {
			String newTitle = results.getName();
			String ppUnits = results.getResults().get(0).getSourcePressureUnits();
			newTitle = "Composition: " + newTitle;
			xyGraphComposition.setTitle(newTitle);
			if(hasETimes) {
				xyGraphComposition.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphComposition.getPrimaryXAxis().setTitle("Scan Number");
			}
			xyGraphComposition.erase();
			TreeMap<String, ArrayList<Double>> lookup = new TreeMap<String, ArrayList<Double>>();
			if(results.isCalibrated()) {
				xyGraphComposition.getPrimaryYAxis().setTitle("Partial Pressure, " + ppUnits);
				xyGraphCompositionNumberOfPoints = results.getResults().size();
				double[] xDataTraceComposition = new double[xyGraphCompositionNumberOfPoints];
				String componentName;
				DecompositionResult result;
				for(int i = 0; i < xyGraphCompositionNumberOfPoints; i++) {
					result = results.getResults().get(i);
					for(int j = 0; j < result.getNumberOfComponents(); j++) {
						componentName = result.getLibCompName(j);
						if(null == lookup.get(componentName)) {
							lookup.put(componentName, new ArrayList<Double>());
						}
						lookup.get(componentName).add(i, result.getPartialPressure(j, ppUnits));
					}
					if(hasETimes) {
						xDataTraceComposition[i] = result.getETimeS();
					} else {
						xDataTraceComposition[i] = result.getResidualSpectrum().getSpectrumNumber();
					}
				}
				if(0 >= lookup.size()) {
					return;
				}
				double minY = 0;
				for(String strName : lookup.keySet()) {
					ArrayList<Double> templist;
					Color traceColor;
					templist = lookup.get(strName);
					Double[] tempdata = new Double[1];
					;
					tempdata = templist.toArray(tempdata);
					double[] ydata = new double[tempdata.length];
					for(int ii = 0; ii < tempdata.length; ii++) {
						ydata[ii] = tempdata[ii].doubleValue();
						if(minY > ydata[ii]) {
							minY = ydata[ii];
						}
					}
					// create a trace data provider, which will provide the data to the trace.
					CircularBufferDataProvider dataProviderTraceComposition = new CircularBufferDataProvider(false); // XYGraph data item
					dataProviderTraceComposition.setCurrentXDataArray(xDataTraceComposition);
					dataProviderTraceComposition.setCurrentYDataArray(ydata);
					Trace traceTemp = traceCompositionsMap.get(strName);
					traceColor = null;
					if(null != traceTemp) {
						traceColor = traceTemp.getTraceColor();
						xyGraphComposition.removeTrace(traceTemp);
					}
					Trace traceComposition = new Trace(strName, xyGraphComposition.getPrimaryXAxis(), xyGraphComposition.getPrimaryYAxis(), dataProviderTraceComposition);
					traceCompositionsMap.put(strName, traceComposition);
					if(null != traceColor) {
						traceComposition.setTraceColor(traceColor);
					}
					// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
					xyGraphComposition.addTrace(traceComposition);
				}
				if(0 > minY) {
					xyGraphComposition.getPrimaryYAxis().setAutoScale(true);
				} else {
					Range range;
					xyGraphComposition.getPrimaryYAxis().setAutoScale(false);
					range = xyGraphComposition.getPrimaryYAxis().getRange();
					xyGraphComposition.getPrimaryYAxis().setRange(minY, range.getUpper());
					;
				}
			}
			// }
			// xyGraph.setShowLegend(!xyGraph.isShowLegend());
			// Display display = Display.getDefault();
		}
	}
}
