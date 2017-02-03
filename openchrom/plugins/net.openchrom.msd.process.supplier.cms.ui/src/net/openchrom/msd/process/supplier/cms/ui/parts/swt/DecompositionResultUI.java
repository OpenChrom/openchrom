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
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
	private IMassSpectra cmsSpectra; // if cmsSpectra == null, then XYGraph data items are invalid
	private DecompositionResults results = null;
	private boolean hasETimes; // true if all scans have valid ETimes, set when the CMS file is read
	private Label labelTextLowerETimes;
	private String signalUnits;
	private Spinner spinnerLowerScanNumber;
	private boolean spinnersIgnoreChange = false;
	private Spinner spinnerUpperScanNumber;
	private Text textCmsSpectraPath;
	private Text textLowerETimes;
	private Text textUpperETimes;
	private Trace traceScanSignalSum; // XYGraph data item
	private Trace traceResidualSignalSum; // XYGraph data item
	private Trace traceLeftMarker;
	private Trace traceRightMarker;
	private CircularBufferDataProvider dataProviderTraceSignalSum; // XYGraph data item
	private CircularBufferDataProvider dataProviderTraceResidualSignalSum; // XYGraph data item
	private CircularBufferDataProvider dataProviderLeftMarker;
	private CircularBufferDataProvider dataProviderRightMarker;
	private double xDataTraceLeftMarker[];
	private double xDataTraceRightMarker[];
	private double xDataTraceScanSignalSum[]; // XYGraph data item
	private double xDataTraceResidualSignalSum[]; // XYGraph data item
	private XYGraph xyGraph;
	private int xyGraphNumberOfScanPoints = 0; // if xyGraphNumberOfScanPoints > 0, then remainder of XYGraph scan data items are valid
	private int xyGraphNumberOfResidualPoints = 0; // if xyGraphNumberOfResidualPoints > 0, then remainder of XYGraph residual data items are valid
	private double yDataTraceScanSignalSum[]; // XYGraph data item
	private double yDataTraceResidualSignalSum[]; // XYGraph data item
	private double yDataTraceLeftMarker[];
	private double yDataTraceRightMarker[];

	public DecompositionResultUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void addButtonLoad(Composite parent) {

		Button buttonLoad;
		buttonLoad = new Button(parent, SWT.NONE);
		buttonLoad.setText("");
		buttonLoad.setToolTipText("Decompose the selected CMS spectra.");
		buttonLoad.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImageProvider.SIZE_16x16));
		buttonLoad.addSelectionListener(new SelectionAdapter() {

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
		if(null == spinnerLowerScanNumber) {
			return;
		}
		if(null == spinnerUpperScanNumber) {
			return;
		}
		if(spinnerLowerScanNumber.getSelection() < 1) {
			return;
		}
		if(spinnerLowerScanNumber.getSelection() > cmsSpectra.getList().size()) {
			return;
		}
		if(spinnerUpperScanNumber.getSelection() < 1) {
			return;
		}
		if(spinnerUpperScanNumber.getSelection() > cmsSpectra.getList().size()) {
			return;
		}
		if(spinnerUpperScanNumber.getSelection() <= spinnerLowerScanNumber.getSelection()) {
			return;
		}
		IMassSpectra scanSpectra = new MassSpectra();
		scanSpectra.setName(cmsSpectra.getName());
		for(int i = spinnerLowerScanNumber.getSelection(); i <= spinnerUpperScanNumber.getSelection(); i++) {
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
		xyGraphNumberOfResidualPoints = 0;
		updateXYGraph(cmsSpectra);
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

	private void addSelectLowerSpectrum(Composite parent) {

		Composite compositeLowerRangeSelect = new Composite(parent, SWT.NONE);
		GridLayout layoutCompositeLowerRangeSelect = new GridLayout(4, false);
		layoutCompositeLowerRangeSelect.marginHeight = 0;
		layoutCompositeLowerRangeSelect.marginWidth = 0;
		compositeLowerRangeSelect.setLayout(layoutCompositeLowerRangeSelect);
		GridData compositeLowerRangeSelectGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeLowerRangeSelect.setLayoutData(compositeLowerRangeSelectGridData);
		//
		labelTextLowerETimes = new Label(compositeLowerRangeSelect, SWT.RIGHT);
		labelTextLowerETimes.setText("Start Time, s:");
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		labelTextLowerETimes.setLayoutData(labelGridData);
		//
		textLowerETimes = new Text(compositeLowerRangeSelect, SWT.RIGHT | SWT.BORDER);
		textLowerETimes.setText("");
		GridData textLowerETimesGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textLowerETimes.setLayoutData(textLowerETimesGridData);
		//
		new Label(compositeLowerRangeSelect, SWT.RIGHT).setText("Scan #:");
		//
		spinnerLowerScanNumber = new Spinner(compositeLowerRangeSelect, SWT.RIGHT | SWT.BORDER | SWT.READ_ONLY);
		spinnerLowerScanNumber.setMinimum(1);
		spinnerLowerScanNumber.setMaximum(1);
		spinnerLowerScanNumber.setSelection(1);
		spinnerLowerScanNumber.setIncrement(1);
		spinnerLowerScanNumber.setPageIncrement(10);
		spinnerLowerScanNumber.setToolTipText("increment or decrement starting scan number");
		GridData spinnerLowerScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerLowerScanNumberGridData.widthHint = 32;
		spinnerLowerScanNumber.setLayoutData(spinnerLowerScanNumberGridData);
		spinnerLowerScanNumber.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(spinnersIgnoreChange) {
					return;
				}
				if(spinnerLowerScanNumber.getSelection() >= spinnerUpperScanNumber.getSelection()) {
					spinnerLowerScanNumber.setSelection(spinnerUpperScanNumber.getSelection() - 1);
				}
				updateTextETimes(cmsSpectra);
				updateXYGraph(cmsSpectra);
			}
		});
	}

	private void addSelectUpperSpectrum(Composite parent) {

		Composite compositeUpperRangeSelect = new Composite(parent, SWT.NONE);
		GridLayout layoutCompositeUpperRangeSelect = new GridLayout(4, false);
		layoutCompositeUpperRangeSelect.marginHeight = 0;
		layoutCompositeUpperRangeSelect.marginWidth = 0;
		compositeUpperRangeSelect.setLayout(layoutCompositeUpperRangeSelect);
		GridData compositeUpperRangeSelectGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeUpperRangeSelect.setLayoutData(compositeUpperRangeSelectGridData);
		//
		Label labelTextUpperETimes = new Label(compositeUpperRangeSelect, SWT.RIGHT);
		labelTextUpperETimes.setText("End Time, s:");
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		int width = labelTextLowerETimes.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		labelGridData.widthHint = width;
		labelTextUpperETimes.setLayoutData(labelGridData);
		//
		textUpperETimes = new Text(compositeUpperRangeSelect, SWT.RIGHT | SWT.BORDER);
		textUpperETimes.setText("");
		GridData textUpperETimesGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textUpperETimes.setLayoutData(textUpperETimesGridData);
		//
		new Label(compositeUpperRangeSelect, SWT.NONE).setText("Scan #:");
		//
		spinnerUpperScanNumber = new Spinner(compositeUpperRangeSelect, SWT.RIGHT | SWT.BORDER | SWT.READ_ONLY);
		spinnerUpperScanNumber.setMinimum(1);
		spinnerUpperScanNumber.setMaximum(1);
		spinnerUpperScanNumber.setSelection(1);
		spinnerUpperScanNumber.setIncrement(1);
		spinnerUpperScanNumber.setPageIncrement(10);
		spinnerUpperScanNumber.setToolTipText("increment or decrement ending scan number");
		GridData spinnerUpperScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerUpperScanNumberGridData.widthHint = 32;
		spinnerUpperScanNumber.setLayoutData(spinnerUpperScanNumberGridData);
		spinnerUpperScanNumber.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(spinnersIgnoreChange) {
					return;
				}
				if(spinnerUpperScanNumber.getSelection() <= spinnerLowerScanNumber.getSelection()) {
					spinnerUpperScanNumber.setSelection(spinnerLowerScanNumber.getSelection() + 1);
				}
				updateTextETimes(cmsSpectra);
				updateXYGraph(cmsSpectra);
			}
		});
	}

	private void initialize() {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout compositeGridLayout = new GridLayout(1, false);
		composite.setLayout(compositeGridLayout);
		//
		Composite compositeTopRow = new Composite(composite, SWT.NONE);
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
		addButtonLoad(compositeButtons);
		addButtonSettings(compositeButtons);
		// ETimes Text and scan # Spinners
		addSelectLowerSpectrum(composite);
		addSelectUpperSpectrum(composite);
		/*
		 * XY Graph
		 */
		Composite compositeGraph = new Composite(composite, SWT.NONE);
		compositeGraph.setLayout(new FillLayout());
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraph = new XYGraph();
		xyGraph.setTitle("Signal");
		xyGraph.getPrimaryXAxis().setAutoScale(true);
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setAutoScale(true);
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraph.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraph);
	}

	private void readAndPlotCMSscanFile() {

		try {
			File file = new File(textCmsSpectraPath.getText().trim());
			if(file.exists()) {
				xyGraphNumberOfScanPoints = 0; // invalidate current XYGraph data
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
					spinnerLowerScanNumber.setMinimum(1);
					spinnerLowerScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnerLowerScanNumber.setEnabled(false); // does this prevent a ModifyEvent?
					spinnerLowerScanNumber.setSelection(1);
					spinnerLowerScanNumber.setEnabled(true);
					spinnerUpperScanNumber.setMinimum(1);
					spinnerUpperScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnersIgnoreChange = false;
					spinnerUpperScanNumber.setSelection(cmsSpectra.getList().size());
					// updateTextETimes(cmsSpectra); // not needed
					// updateXYGraph(cmsSpectra); // not needed
				} else {
					spinnersIgnoreChange = true;
					spinnerLowerScanNumber.setMinimum(1);
					spinnerLowerScanNumber.setMaximum(1);
					spinnerLowerScanNumber.setSelection(1);
					spinnerUpperScanNumber.setMinimum(1);
					spinnerUpperScanNumber.setMaximum(1);
					spinnersIgnoreChange = false;
					spinnerUpperScanNumber.setSelection(1);
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
			spinnerValue = spinnerLowerScanNumber.getSelection();
			if((spinnerValue >= 1) && (spinnerValue <= cmsSpectra.getList().size())) {
				spectrum = (ICalibratedVendorMassSpectrum)cmsSpectra.getMassSpectrum(spinnerValue);
				eTimes = spectrum.getEtimes();
				textLowerETimes.setText(decimalFormatTextETimes.format(eTimes));
			} else {
				textLowerETimes.setText("Error, spinner value " + spinnerValue + " is out of range");
			}
			spinnerValue = spinnerUpperScanNumber.getSelection();
			if((spinnerValue >= 1) && (spinnerValue <= cmsSpectra.getList().size())) {
				spectrum = (ICalibratedVendorMassSpectrum)cmsSpectra.getMassSpectrum(spinnerValue);
				eTimes = spectrum.getEtimes();
				textUpperETimes.setText(decimalFormatTextETimes.format(eTimes));
			} else {
				textUpperETimes.setText("Error, spinner value " + spinnerValue + " is out of range");
			}
		} else {
			textLowerETimes.setText("Elapsed Time is not present in this file");
			textUpperETimes.setText("Elapsed Time is not present in this file");
		}
	}

	private void updateXYGraph(IMassSpectra spectra) {

		ICalibratedVendorMassSpectrum spectrum;
		// hasETimes = !hasETimes; // whw, for testing
		System.out.println("Update Signal XYGraph for " + spectra.getName());
		if(spinnerLowerScanNumber.getSelection() >= spinnerUpperScanNumber.getSelection()) {
			return;
		}
		if(0 == xyGraphNumberOfScanPoints) {
			String newTitle = textCmsSpectraPath.getText();
			int index = newTitle.lastIndexOf(File.separator);
			if(0 < index) {
				newTitle = newTitle.substring(1 + index);
			}
			newTitle = "Signal: " + newTitle;
			xyGraph.setTitle(newTitle);
			if(hasETimes) {
				xyGraph.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraph.getPrimaryXAxis().setTitle("Scan Number");
			}
			xyGraph.getPrimaryYAxis().setTitle("Signal, " + signalUnits);
			// create a trace data provider, which will provide the data to the trace.
			dataProviderTraceSignalSum = new CircularBufferDataProvider(false);
			xyGraphNumberOfScanPoints = spectra.getList().size();
			dataProviderTraceSignalSum.setBufferSize(xyGraphNumberOfScanPoints);
			xDataTraceScanSignalSum = new double[xyGraphNumberOfScanPoints];
			yDataTraceScanSignalSum = new double[xyGraphNumberOfScanPoints];
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(1);
			signalUnits = spectrum.getSignalUnits();
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
				xyGraph.removeTrace(traceScanSignalSum);
			}
			if(null != traceResidualSignalSum) {
				xyGraph.removeTrace(traceResidualSignalSum);
			}
			traceScanSignalSum = new Trace("sum(Signal)", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProviderTraceSignalSum);
			traceScanSignalSum.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_BLUE));
			// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
			xyGraph.addTrace(traceScanSignalSum);
			//
			dataProviderLeftMarker = new CircularBufferDataProvider(false);
			dataProviderLeftMarker.setBufferSize(2);
			xDataTraceLeftMarker = new double[2];
			yDataTraceLeftMarker = new double[2];
			if(null != traceLeftMarker) {
				xyGraph.removeTrace(traceLeftMarker);
			}
			traceLeftMarker = new Trace("", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProviderLeftMarker);
			traceLeftMarker.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
			xyGraph.addTrace(traceLeftMarker);
			//
			dataProviderRightMarker = new CircularBufferDataProvider(false);
			dataProviderRightMarker.setBufferSize(2);
			xDataTraceRightMarker = new double[2];
			yDataTraceRightMarker = new double[2];
			if(null != traceRightMarker) {
				xyGraph.removeTrace(traceRightMarker);
			}
			traceRightMarker = new Trace("", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProviderRightMarker);
			traceRightMarker.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
			xyGraph.addTrace(traceRightMarker);
		}
		if(0 == xyGraphNumberOfResidualPoints) {
			if(null != results) {
				dataProviderTraceResidualSignalSum = new CircularBufferDataProvider(false);
				xyGraphNumberOfResidualPoints = results.getResults().size();
				dataProviderTraceResidualSignalSum.setBufferSize(xyGraphNumberOfResidualPoints);
				xDataTraceResidualSignalSum = new double[xyGraphNumberOfResidualPoints];
				yDataTraceResidualSignalSum = new double[xyGraphNumberOfResidualPoints];
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
					xyGraph.removeTrace(traceResidualSignalSum);
				}
				traceResidualSignalSum = new Trace("sum(Residual)", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProviderTraceResidualSignalSum);
				traceResidualSignalSum.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_PURPLE));
				// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
				xyGraph.addTrace(traceResidualSignalSum);
				// now I want to call the updateXYGraph(DecompositionResults results) method for the
				// instance of DecompositionCompositionResultUI in this perspective but I don't know how to do it
				DecompositionCompositionResultUI.updateXYGraph(results);
			}
		}
		if(hasETimes) {
			double xygraphMinETimes, xygraphMaxETimes;
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(spinnerLowerScanNumber.getSelection());
			xygraphMinETimes = spectrum.getEtimes();
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(spinnerUpperScanNumber.getSelection());
			xygraphMaxETimes = spectrum.getEtimes();
			xDataTraceLeftMarker[0] = xygraphMinETimes;
			xDataTraceLeftMarker[1] = xygraphMinETimes;
			xDataTraceRightMarker[0] = xygraphMaxETimes;
			xDataTraceRightMarker[1] = xygraphMaxETimes;
			xyGraph.getPrimaryXAxis().setAutoScale(true);
			// xyGraph.getPrimaryXAxis().setRange(new Range(xygraphMinETimes, xygraphMaxETimes));
		} else {
			xDataTraceLeftMarker[0] = spinnerLowerScanNumber.getSelection();
			xDataTraceLeftMarker[1] = spinnerLowerScanNumber.getSelection();
			xDataTraceRightMarker[0] = spinnerUpperScanNumber.getSelection();
			xDataTraceRightMarker[1] = spinnerUpperScanNumber.getSelection();
			xyGraph.getPrimaryXAxis().setAutoScale(true);
			// xyGraph.getPrimaryXAxis().setRange(new Range(spinnerLowerScanNumber.getSelection(), spinnerUpperScanNumber.getSelection()));
		}
		yDataTraceLeftMarker[0] = xyGraph.getPrimaryYAxis().getRange().getLower();
		yDataTraceLeftMarker[1] = xyGraph.getPrimaryYAxis().getRange().getUpper();
		yDataTraceRightMarker[0] = xyGraph.getPrimaryYAxis().getRange().getLower();
		yDataTraceRightMarker[1] = xyGraph.getPrimaryYAxis().getRange().getUpper();
		dataProviderRightMarker.setCurrentXDataArray(xDataTraceRightMarker);
		dataProviderRightMarker.setCurrentYDataArray(yDataTraceRightMarker);
		dataProviderLeftMarker.setCurrentXDataArray(xDataTraceLeftMarker);
		dataProviderLeftMarker.setCurrentYDataArray(yDataTraceLeftMarker);
		// xyGraph.setShowLegend(!xyGraph.isShowLegend());
		// Display display = Display.getDefault();
	}
}
