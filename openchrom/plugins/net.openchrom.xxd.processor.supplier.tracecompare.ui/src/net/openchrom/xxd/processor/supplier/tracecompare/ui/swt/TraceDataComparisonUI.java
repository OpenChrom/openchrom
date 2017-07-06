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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignals;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;
import org.eclipse.eavp.service.swtchart.export.ImageSupplier;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ProcessorModel;
import net.openchrom.xxd.processor.supplier.tracecompare.model.TraceModel;

public class TraceDataComparisonUI extends Composite {

	private static final int HORIZONTAL_INDENT = 15;
	//
	private Label labelTrace;
	private Button buttonIsEvaluated;
	private Button buttonIsMatched;
	private TraceDataUI sampleDataUI;
	private TraceDataUI referenceDataUI;
	private Text notesText;
	private Button buttonCreateSnapshot;
	//
	private ProcessorModel processorModel;
	private TraceModel traceModel;
	private String sample;
	private String reference;

	public TraceDataComparisonUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setData(int wavelength, IExtractedWavelengthSignals extractedWavelengthSignalsSample, IExtractedWavelengthSignals extractedWavelengthSignalsReference, ProcessorModel processorModel, TraceModel traceModel) {

		sampleDataUI.addSeriesData(getLineSeriesDataList(wavelength, extractedWavelengthSignalsSample), LineChart.MEDIUM_COMPRESSION);
		referenceDataUI.addSeriesData(getLineSeriesDataList(wavelength, extractedWavelengthSignalsReference), LineChart.MEDIUM_COMPRESSION);
		//
		this.processorModel = processorModel;
		this.traceModel = traceModel;
		notesText.setText(traceModel.getNotes());
		this.sample = extractedWavelengthSignalsSample.getChromatogram().getName();
		this.reference = extractedWavelengthSignalsReference.getChromatogram().getName();
		setTrace(wavelength, sample, reference);
		String imageEvaluated = (traceModel.isEvaluated()) ? IApplicationImage.IMAGE_EVALUATED : IApplicationImage.IMAGE_EVALUATE;
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(imageEvaluated, IApplicationImage.SIZE_16x16));
		String imageMatched = (traceModel.isMatched()) ? IApplicationImage.IMAGE_SELECTED : IApplicationImage.IMAGE_DESELECTED;
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(imageMatched, IApplicationImage.SIZE_16x16));
	}

	private void setTrace(int wavelength, String sample, String reference) {

		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		labelTrace.setFont(font);
		if(wavelength == (int)IScanSignalWSD.TIC_SIGNAL) {
			labelTrace.setText("Trace: TIC (" + sample + " vs. " + reference + ")");
		} else {
			labelTrace.setText("Trace: " + Integer.toString(wavelength) + " nm (" + sample + " vs. " + reference + ")");
		}
		font.dispose();
	}

	private List<ILineSeriesData> getLineSeriesDataList(int trace, IExtractedWavelengthSignals extractedWavelengthSignals) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		ISeriesData seriesData = getSeriesXY(trace, extractedWavelengthSignals);
		//
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSeriesDataList.add(lineSeriesData);
		//
		return lineSeriesDataList;
	}

	private ISeriesData getSeriesXY(int wavelength, IExtractedWavelengthSignals extractedWavelengthSignals) {

		IChromatogramWSD chromatogramWSD = extractedWavelengthSignals.getChromatogram();
		List<IExtractedWavelengthSignal> wavelengthSignals = extractedWavelengthSignals.getExtractedWavelengthSignals();
		double[] xSeries = new double[wavelengthSignals.size()];
		double[] ySeries = new double[wavelengthSignals.size()];
		//
		int i = 0;
		for(IExtractedWavelengthSignal wavelengthSignal : wavelengthSignals) {
			xSeries[i] = wavelengthSignal.getRetentionTime();
			if(wavelength == (int)IScanSignalWSD.TIC_SIGNAL) {
				ySeries[i] = wavelengthSignal.getTotalSignal();
			} else {
				ySeries[i] = wavelengthSignal.getAbundance(wavelength);
			}
			i++;
		}
		//
		ISeriesData seriesData = new SeriesData(xSeries, ySeries, chromatogramWSD.getName());
		return seriesData;
	}

	private void createControl() {

		setLayout(new GridLayout(2, true));
		//
		createButtonSection(this);
		createCommentsSection(this);
		createTraceDataSection(this);
		//
		showComments(false);
	}

	private void createButtonSection(Composite parent) {

		labelTrace = new Label(parent, SWT.NONE);
		labelTrace.setText("");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalIndent = HORIZONTAL_INDENT;
		labelTrace.setLayoutData(gridDataLabel);
		//
		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		gridDataComposite.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(4, false));
		//
		buttonIsEvaluated = new Button(composite, SWT.PUSH);
		buttonIsEvaluated.setText("");
		buttonIsEvaluated.setToolTipText("Flag as evaluated.");
		buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EVALUATE, IApplicationImage.SIZE_16x16));
		buttonIsEvaluated.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isEvaluated = traceModel.isEvaluated();
				if(isEvaluated) {
					traceModel.setEvaluated(false);
					buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EVALUATE, IApplicationImage.SIZE_16x16));
				} else {
					traceModel.setEvaluated(true);
					buttonIsEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EVALUATED, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		buttonIsMatched = new Button(composite, SWT.PUSH);
		buttonIsMatched.setText("");
		buttonIsMatched.setToolTipText("Flag as matched");
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED, IApplicationImage.SIZE_16x16));
		buttonIsMatched.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isMatched = traceModel.isMatched();
				if(isMatched) {
					traceModel.setMatched(false);
					buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED, IApplicationImage.SIZE_16x16));
				} else {
					traceModel.setMatched(true);
					buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SELECTED, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		Button buttonFlipComments = new Button(composite, SWT.PUSH);
		buttonFlipComments.setText("");
		buttonFlipComments.setToolTipText("Show/Hide Comments");
		buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
		buttonFlipComments.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isVisible = !notesText.isVisible();
				showComments(isVisible);
				//
				if(isVisible) {
					buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_COLLAPSE_ALL, IApplicationImage.SIZE_16x16));
				} else {
					buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		buttonCreateSnapshot = new Button(composite, SWT.PUSH);
		buttonCreateSnapshot.setText("");
		buttonCreateSnapshot.setToolTipText("Create a snapshot.");
		buttonCreateSnapshot.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CREATE_SNAPSHOT, IApplicationImage.SIZE_16x16));
		buttonCreateSnapshot.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ImageSupplier imageSupplier = new ImageSupplier();
				//
				String fileNameSample = processorModel.getImageDirectory() + File.separator + "Sample_" + sample + "_vs_" + reference + "_" + (int)traceModel.getTrace() + "nm.png";
				ImageData imageDataSample = imageSupplier.getImageData(sampleDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataSample, fileNameSample, SWT.IMAGE_PNG);
				traceModel.setPathSnapshotSample(fileNameSample);
				//
				String fileNameReference = processorModel.getImageDirectory() + File.separator + "Reference_" + sample + "_vs_" + reference + "_" + (int)traceModel.getTrace() + "nm.png";
				ImageData imageDataReference = imageSupplier.getImageData(referenceDataUI.getBaseChart());
				imageSupplier.saveImage(imageDataReference, fileNameReference, SWT.IMAGE_PNG);
				traceModel.setPathSnapshotReference(fileNameReference);
				//
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Save Image", "A screenshot of the sample and reference has been saved.");
			}
		});
	}

	private void createCommentsSection(Composite parent) {

		notesText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		notesText.setText("");
		GridData gridData = getGridData();
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		notesText.setLayoutData(gridData);
		notesText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				traceModel.setNotes(notesText.getText().trim());
			}
		});
	}

	private void createTraceDataSection(Composite parent) {

		TraceDataSettings traceDataSettingsSample = new TraceDataSettings();
		traceDataSettingsSample.setEnableRangeInfo(true);
		traceDataSettingsSample.setShowAxisTitle(false);
		traceDataSettingsSample.setEnableHorizontalSlider(false);
		traceDataSettingsSample.setCreateMenu(true);
		sampleDataUI = new TraceDataUI(parent, SWT.NONE, traceDataSettingsSample);
		sampleDataUI.setLayoutData(getGridData());
		//
		TraceDataSettings traceDataSettingsReference = new TraceDataSettings();
		traceDataSettingsReference.setEnableRangeInfo(false);
		traceDataSettingsReference.setShowAxisTitle(true);
		traceDataSettingsReference.setEnableHorizontalSlider(true);
		traceDataSettingsReference.setCreateMenu(false);
		referenceDataUI = new TraceDataUI(parent, SWT.NONE, traceDataSettingsReference);
		referenceDataUI.setLayoutData(getGridData());
		/*
		 * Link both charts.
		 */
		sampleDataUI.addLinkedScrollableChart(referenceDataUI);
		referenceDataUI.addLinkedScrollableChart(sampleDataUI);
	}

	private GridData getGridData() {

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		return gridData;
	}

	private void showComments(boolean isVisible) {

		GridData gridData = (GridData)notesText.getLayoutData();
		gridData.exclude = !isVisible;
		notesText.setVisible(isVisible);
		Composite parent = notesText.getParent();
		parent.layout(false);
		parent.redraw();
	}
}
