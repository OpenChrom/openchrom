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
import java.text.DecimalFormat;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
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
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;
import net.openchrom.msd.process.supplier.cms.ui.preferences.PreferencePage;

public class DecompositionResultUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private Button buttonLoad;
	private Button buttonSelect;
	private Button buttonSettings;
	private IMassSpectra cmsSpectra; // if cmsSpectra == null, then XYGraph data items are invalid
	private Composite compositeButtons;
	private Composite compositeLowerRangeSelect;
	private Composite compositeUpperRangeSelect;
	private DecimalFormat decimalFormatTextETimes = ValueFormat.getDecimalFormatEnglish("0.0");
	private boolean hasETimes; // true if all scans have valid ETimes, set when the CMS file is read
	private Label labelTextLowerETimes;
	private String signalUnits;
	private Spinner spinnerLowerScanNumber;
	private boolean spinnersIgnoreChange = false;
	private Spinner spinnerUpperScanNumber;
	private Text textCmsSpectraPath;
	private Text textLowerETimes;
	private Text textUpperETimes;
	private Composite topRowComposite;
	private Trace trace01; // XYGraph data item
	private CircularBufferDataProvider traceDataProvider; // XYGraph data item
	private double xdata[]; // XYGraph data item
	private XYGraph xyGraph;
	private int xyGraphNumberOfPoints = 0; // if xyNumberOfPoints > 0, then remainder of XYGraph data items are valid
	private double ydata[]; // XYGraph data item

	public DecompositionResultUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void addButtonLoad(Composite parent) {

		buttonLoad = new Button(parent, SWT.NONE);
		buttonLoad.setText("");
		buttonLoad.setToolTipText("Decompose the selected CMS spectra.");
		buttonLoad.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImageProvider.SIZE_16x16));
		buttonLoad.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					updateTextETimes(cmsSpectra);
					updateXYGraph(cmsSpectra);
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
	}

	private void addButtonSelect(Composite parent) {

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

		compositeLowerRangeSelect = new Composite(parent, SWT.NONE);
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
				updateTextETimes(cmsSpectra);
				updateXYGraph(cmsSpectra);
			}
		});
	}

	private void addSelectUpperSpectrum(Composite parent) {

		compositeUpperRangeSelect = new Composite(parent, SWT.NONE);
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
		topRowComposite = new Composite(composite, SWT.NONE);
		GridLayout topRowCompositeGridLayout = new GridLayout(2, false);
		topRowCompositeGridLayout.marginHeight = 0;
		topRowCompositeGridLayout.marginWidth = 0;
		topRowComposite.setLayout(topRowCompositeGridLayout);
		GridData topRowCompositeGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		topRowComposite.setLayoutData(topRowCompositeGridData);
		// CMS Path and Buttons
		textCmsSpectraPath = new Text(topRowComposite, SWT.BORDER);
		textCmsSpectraPath.setText("");
		GridData textCmsSpectraPathGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textCmsSpectraPath.setLayoutData(textCmsSpectraPathGridData);
		compositeButtons = new Composite(topRowComposite, SWT.NONE);
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
		GridLayout compositeGraphGridLayout = new GridLayout(1, false);
		compositeGraph.setLayout(compositeGraphGridLayout);
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayoutData(compositeGraphGridData);
		compositeGraph.setLayout(new FillLayout());
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraph = new XYGraph();
		xyGraph.setTitle("CMS (Calibrated Mass Spectra)");
		xyGraph.getPrimaryXAxis().setAutoScale(true);
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setAutoScale(true);
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraph.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraph);
		composite.layout();
	}

	private void readAndPlotCMSscanFile() {

		try {
			File file = new File(textCmsSpectraPath.getText().trim());
			if(file.exists()) {
				xyGraphNumberOfPoints = 0; // invalidate current XYGraph data
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
		System.out.println("Update XYGraph");
		// create a trace data provider, which will provide the data to the
		// trace.
		if(spinnerLowerScanNumber.getSelection() >= spinnerUpperScanNumber.getSelection()) {
			return;
		}
		if(0 == xyGraphNumberOfPoints) {
			traceDataProvider = new CircularBufferDataProvider(false);
			xyGraphNumberOfPoints = spectra.getList().size();
			traceDataProvider.setBufferSize(xyGraphNumberOfPoints);
			xdata = new double[xyGraphNumberOfPoints];
			ydata = new double[xyGraphNumberOfPoints];
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(1);
			signalUnits = spectrum.getSignalUnits();
			for(int i = spectra.getList().size(); i > 0;) {
				spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(i);
				if(!hasETimes) {
					xdata[--i] = i;
				} else {
					--i;
					xdata[i] = spectrum.getEtimes();
				}
				ydata[i] = spectrum.getTotalSignal();
				// am I being too clever?
			}
			traceDataProvider.setCurrentXDataArray(xdata);
			traceDataProvider.setCurrentYDataArray(ydata);
		}
		// create the trace
		if(null != trace01) {
			xyGraph.removeTrace(trace01);
		}
		trace01 = new Trace("sum(Signal)", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);
		// set trace property
		// trace01.setPointStyle(PointStyle.XCROSS);
		String newTitle = textCmsSpectraPath.getText();
		int index = newTitle.lastIndexOf(File.separator);
		if(0 < index) {
			newTitle = newTitle.substring(1 + index);
		}
		xyGraph.setTitle(newTitle);
		if(hasETimes) {
			xyGraph.getPrimaryXAxis().setTitle("Elapsed Time, s");
		} else {
			xyGraph.getPrimaryXAxis().setTitle("Scan Number");
		}
		xyGraph.getPrimaryYAxis().setTitle("Signal, " + signalUnits);
		if(hasETimes) {
			double xygraphMinETimes, xygraphMaxETimes;
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(spinnerLowerScanNumber.getSelection());
			xygraphMinETimes = spectrum.getEtimes();
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(spinnerUpperScanNumber.getSelection());
			xygraphMaxETimes = spectrum.getEtimes();
			xyGraph.getPrimaryXAxis().setAutoScale(false);
			xyGraph.getPrimaryXAxis().setRange(new Range(xygraphMinETimes, xygraphMaxETimes));
		} else {
			xyGraph.getPrimaryXAxis().setAutoScale(false);
			xyGraph.getPrimaryXAxis().setRange(new Range(spinnerLowerScanNumber.getSelection(), spinnerUpperScanNumber.getSelection()));
		}
		// add the trace to xyGraph
		xyGraph.addTrace(trace01);
		// Display display = Display.getDefault();
	}
}
