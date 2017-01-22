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
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Axis;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.linearscale.AbstractScale.LabelSide;
import org.eclipse.nebula.visualization.xygraph.linearscale.LinearScaleTickLabels;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumReader;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.preferences.PreferenceSupplier;
import net.openchrom.msd.process.supplier.cms.ui.preferences.PreferencePage;

public class DecompositionResultUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private Text textCmsSpectraPath;
	private Button buttonSelect;
	private Button buttonLoad;
	private Button buttonSettings;
	private Composite topRowComposite;
	private Composite compositeButtons;
	private Composite compositeLowerRangeSelect;
	private Composite compositeUpperRangeSelect;
	private Text textLowerETimes;
	private Text textUpperETimes;
	private Spinner spinnerLowerScanNumber;
	private Spinner spinnerUpperScanNumber;
	private XYGraph xyGraph;
	private double xdata[];
	private double ydata[];
	private Trace trace01;

	public DecompositionResultUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {

		FormLayout formLayout;
		FormData formData;
		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		formLayout = new FormLayout();
		formLayout.marginWidth = 3;
		formLayout.marginHeight = 3;
		composite.setLayout(formLayout);
		topRowComposite = new Composite(composite, SWT.NONE);
		GridLayout topRowCompositeGridLayout = new GridLayout(2, false);
		topRowCompositeGridLayout.marginHeight = 0;
		topRowCompositeGridLayout.marginWidth = 0;
		topRowComposite.setLayout(topRowCompositeGridLayout);
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		topRowComposite.setLayoutData(formData);
		// CMS Path and Buttons
		textCmsSpectraPath = new Text(topRowComposite, SWT.BORDER);
		textCmsSpectraPath.setText("");
		// GridData textCmsSpectraPathGridData = new GridData(GridData.FILL_HORIZONTAL); // not recommended
		GridData textCmsSpectraPathGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		// GridData textCmsSpectraPathGridData = new GridData (SWT.LEFT, SWT.TOP, true, true);
		// textCmsSpectraPathGridData.horizontalAlignment = SWT.LEFT;
		// textCmsSpectraPathGridData.grabExcessHorizontalSpace = true;
		textCmsSpectraPath.setLayoutData(textCmsSpectraPathGridData);
		// textCmsSpectraPath.setLayoutData(formData);
		// formData = new FormData();
		// formData.left = new FormAttachment(0, 0);
		// formData.top = new FormAttachment(0, 0);
		// formData.right = new FormAttachment(compositeButtons, 0, SWT.LEFT);
		// formData.right = new FormAttachment(85, 0);
		// textCmsSpectraPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// textCmsSpectraPath.setLayoutData(formData);
		//
		// compositeButtons.setLayout(new GridLayout(3, true));
		// GridData gridDataCompositeButtons = new GridData();
		// gridDataCompositeButtons.horizontalAlignment = SWT.RIGHT;
		compositeButtons = new Composite(topRowComposite, SWT.NONE);
		compositeButtons.setLayout(new GridLayout(3, true));
		GridData gridDataCompositeButtons = new GridData(SWT.RIGHT, SWT.TOP, false, true);
		// GridData gridDataCompositeButtons = new GridData (GridData.FILL_HORIZONTAL);
		// GridData gridDataCompositeButtons = new GridData();
		// gridDataCompositeButtons.horizontalAlignment = SWT.RIGHT;
		compositeButtons.setLayoutData(gridDataCompositeButtons);
		// compositeButtons.setLayout(new FormLayout());
		// formData = new FormData();
		// formData.left = new FormAttachment(textCmsSpectraPath);
		// formData.top = new FormAttachment(0, 0);
		// formData.right = new FormAttachment(100, 0);
		// compositeButtons.setLayoutData(formData);
		//
		addButtonSelect(compositeButtons);
		addButtonLoad(compositeButtons);
		addButtonSettings(compositeButtons);
		// compositeButtons.pack();
		// textCmsSpectraPath.setLayoutData(formData);
		//
		// Composite compositeRangeSelect = new Composite(composite, SWT.NONE);
		// compositeRangeSelect.setLayout(new GridLayout(1, true));
		// GridData gridDataRangeSelect = new GridData();
		// gridDataRangeSelect.horizontalSpan = 2;
		// compositeRangeSelect.setLayoutData(gridDataRangeSelect);
		// compositeRangeSelect.setLayout(new RowLayout());
		// FormLayout formLayoutRangeSelect = new FormLayout();
		// formLayoutRangeSelect.marginWidth = 3;
		// formLayoutRangeSelect.marginHeight = 3;
		// compositeRangeSelect.setLayout(formLayoutRangeSelect);
		//
		addSelectLowerSpectrum(composite);
		addSelectUpperSpectrum(composite);
		/*
		 * XY Graph
		 */
		Composite compositeGraph = new Composite(composite, SWT.NONE);
		// GridData gridData = new GridData(GridData.FILL_BOTH);
		/// GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		/// gridData.horizontalSpan = 2;
		/// compositeGraph.setLayoutData(gridData);
		compositeGraph.setLayout(new FillLayout());
		formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(compositeUpperRangeSelect);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		compositeGraph.setLayoutData(formData);
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
		// composite.layout();
	}

	private void addSelectLowerSpectrum(Composite parent) {

		compositeLowerRangeSelect = new Composite(parent, SWT.NONE);
		GridLayout layoutCompositeLowerRangeSelect = new GridLayout(2, false);
		layoutCompositeLowerRangeSelect.marginHeight = 0;
		layoutCompositeLowerRangeSelect.marginWidth = 0;
		compositeLowerRangeSelect.setLayout(layoutCompositeLowerRangeSelect);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(topRowComposite, 0);
		formData.right = new FormAttachment(100, 0);
		compositeLowerRangeSelect.setLayoutData(formData);
		//
		textLowerETimes = new Text(compositeLowerRangeSelect, SWT.BORDER);
		textLowerETimes.setText("");
		GridData textLowerETimesGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textLowerETimes.setLayoutData(textLowerETimesGridData);
		//
		spinnerLowerScanNumber = new Spinner(compositeLowerRangeSelect, SWT.READ_ONLY);
		spinnerLowerScanNumber.setMinimum(0);
		spinnerLowerScanNumber.setMaximum(0);
		spinnerLowerScanNumber.setSelection(0);
		spinnerLowerScanNumber.setIncrement(1);
		spinnerLowerScanNumber.setPageIncrement(10);
		spinnerLowerScanNumber.setToolTipText("increment or decrement starting scan number");
		GridData spinnerLowerScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerLowerScanNumberGridData.widthHint = 32;
		spinnerLowerScanNumber.setLayoutData(spinnerLowerScanNumberGridData);
		spinnerLowerScanNumber.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

			}
		});
	}

	private void addSelectUpperSpectrum(Composite parent) {

		compositeUpperRangeSelect = new Composite(parent, SWT.NONE);
		GridLayout layoutCompositeUpperRangeSelect = new GridLayout(2, false);
		layoutCompositeUpperRangeSelect.marginHeight = 0;
		layoutCompositeUpperRangeSelect.marginWidth = 0;
		compositeUpperRangeSelect.setLayout(layoutCompositeUpperRangeSelect);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 0);
		formData.top = new FormAttachment(compositeLowerRangeSelect, 0);
		formData.right = new FormAttachment(100, 0);
		compositeUpperRangeSelect.setLayoutData(formData);
		//
		textUpperETimes = new Text(compositeUpperRangeSelect, SWT.BORDER);
		textUpperETimes.setText("");
		GridData textUpperETimesGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textUpperETimes.setLayoutData(textUpperETimesGridData);
		//
		spinnerUpperScanNumber = new Spinner(compositeUpperRangeSelect, SWT.READ_ONLY);
		spinnerUpperScanNumber.setMinimum(0);
		spinnerUpperScanNumber.setMaximum(0);
		spinnerUpperScanNumber.setSelection(0);
		spinnerUpperScanNumber.setIncrement(1);
		spinnerUpperScanNumber.setPageIncrement(10);
		spinnerUpperScanNumber.setToolTipText("increment or decrement ending scan number");
		GridData spinnerUpperScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerUpperScanNumberGridData.widthHint = 32;
		spinnerUpperScanNumber.setLayoutData(spinnerUpperScanNumberGridData);
	}

	private void addButtonSelect(Composite parent) {

		buttonSelect = new Button(parent, SWT.NONE);
		buttonSelect.setText("");
		buttonSelect.setToolTipText("Select the *.cms spectra.");
		buttonSelect.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_FILE, IApplicationImage.SIZE_16x16));
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

	private void readAndPlotCMSscanFile() {

		try {
			File file = new File(textCmsSpectraPath.getText().trim());
			if(file.exists()) {
				MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
				IMassSpectra cmsSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
				IScanMSD spectrum = cmsSpectra.getMassSpectrum(1);
				if((null != spectrum) && !(spectrum instanceof ICalibratedVendorMassSpectrum)) {
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "This is a CMS library file.");
				} else {
					spinnerLowerScanNumber.setMinimum(1);
					spinnerLowerScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnerLowerScanNumber.setSelection(1);
					// spinnerLowerScanNumber.layout();
					// compositeLowerRangeSelect.layout();
					if(0d <= ((ICalibratedVendorMassSpectrum)spectrum).getEtimes()) {
						textLowerETimes.setText("testing textLowerETimes");
					} else
						textLowerETimes.setText("Elapsed Time is not present in this file");
					spectrum = cmsSpectra.getMassSpectrum(cmsSpectra.getList().size());
					spinnerUpperScanNumber.setMinimum(1);
					spinnerUpperScanNumber.setMaximum(cmsSpectra.getList().size());
					spinnerUpperScanNumber.setSelection(cmsSpectra.getList().size());
					// spinnerUpperScanNumber.layout();
					// compositeUpperRangeSelect.layout();
					if(0d <= ((ICalibratedVendorMassSpectrum)spectrum).getEtimes()) {
						textUpperETimes.setText("testing textUpperETimes");
					} else
						textLowerETimes.setText("Elapsed Time is not present in this file");
					updateXYGraph(cmsSpectra);
				}
			} else {
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "Please select a *.cms file first.");
			}
		} catch(Exception e1) {
			logger.warn(e1);
		}
	}

	private void addTextCmsSpectraPath(Composite parent) {

		textCmsSpectraPath = new Text(parent, SWT.BORDER);
		textCmsSpectraPath.setText("");
		textCmsSpectraPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void addButtonLoad(Composite parent) {

		buttonLoad = new Button(parent, SWT.NONE);
		buttonLoad.setText("");
		buttonLoad.setToolTipText("Decompose the selected CMS spectra.");
		buttonLoad.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		buttonLoad.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					File file = new File(textCmsSpectraPath.getText().trim());
					if(file.exists()) {
						MassSpectrumReader massSpectrumReader = new MassSpectrumReader();
						IMassSpectra cmsSpectra = massSpectrumReader.read(file, new NullProgressMonitor());
						IScanMSD spectrum = cmsSpectra.getMassSpectrum(1);
						if((null != spectrum) && !(spectrum instanceof ICalibratedVendorMassSpectrum)) {
							MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "CMS File", "This is a CMS library file.");
						} else {
							updateXYGraph(cmsSpectra);
						}
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

		buttonSettings = new Button(parent, SWT.NONE);
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

	private void updateXYGraph(IMassSpectra spectra) {

		boolean hasETimes = false;
		int numberOfPoints;
		String signalUnits;
		ICalibratedVendorMassSpectrum spectrum;
		System.out.println("Update XYGraph");
		// create a trace data provider, which will provide the data to the
		// trace.
		CircularBufferDataProvider traceDataProvider = new CircularBufferDataProvider(false);
		numberOfPoints = spectra.getList().size();
		traceDataProvider.setBufferSize(numberOfPoints);
		xdata = new double[numberOfPoints];
		ydata = new double[numberOfPoints];
		spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(1);
		signalUnits = spectrum.getSignalUnits();
		hasETimes = false;
		if(0d <= spectrum.getEtimes())
			hasETimes = true;
		for(int i = spectra.getList().size(); i > 0;) {
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(i);
			if(!hasETimes)
				xdata[--i] = (double)i;
			else {
				--i;
				xdata[i] = spectrum.getEtimes();
			}
			ydata[i] = spectrum.getTotalSignal();
			// am I being too clever?
		}
		traceDataProvider.setCurrentXDataArray(xdata);
		traceDataProvider.setCurrentYDataArray(ydata);
		// create the trace
		if(null != trace01) {
			xyGraph.removeTrace(trace01);
		}
		trace01 = new Trace("sum(Signal)", xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), traceDataProvider);
		// set trace property
		// trace01.setPointStyle(PointStyle.XCROSS);
		String newTitle = textCmsSpectraPath.getText();
		int index = newTitle.lastIndexOf(File.separator);
		if(0 < index)
			newTitle = newTitle.substring(1 + index);
		if(hasETimes)
			xyGraph.getPrimaryXAxis().setTitle("Elapsed Time, s");
		else
			xyGraph.getPrimaryXAxis().setTitle("Scan Number");
		xyGraph.getPrimaryYAxis().setTitle("Signal, " + signalUnits);
		xyGraph.setTitle(newTitle);
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
