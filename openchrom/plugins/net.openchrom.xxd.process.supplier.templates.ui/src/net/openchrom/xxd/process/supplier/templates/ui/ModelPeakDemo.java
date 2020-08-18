/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.IPrimaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.eclipse.swtchart.extensions.core.SeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;
import org.eclipse.swtchart.extensions.linecharts.LineChart;
import org.eclipse.swtchart.extensions.linecharts.LineSeriesData;

/*
 * See:
 * https://commons.apache.org/proper/commons-math/javadocs/api-3.5/org/apache/commons/math3/analysis/function/Gaussian.html
 * https://commons.apache.org/proper/commons-math/userguide/distribution.html
 */
public class ModelPeakDemo {

	private static Text textSigma;
	private static LineChart chartGaussian;
	//
	private static Text textShape;
	private static Text textScale;
	private static LineChart chartDistribution;

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Model Peak Demo");
		shell.setSize(500, 500);
		shell.setLayout(new FillLayout());
		//
		createSection(shell);
		shell.open();
		//
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void createSection(Shell shell) {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		//
		createSectionGaussian(composite);
		createSectionDistribution(composite);
		//
		redrawCharts();
	}

	private static void createSectionGaussian(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		createToolbarGaussian(composite);
		chartGaussian = createChart(composite);
	}

	private static void createToolbarGaussian(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		textSigma = createText(composite, 60.0d, "Sigma: 1 - 200");
	}

	private static void createSectionDistribution(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		createToolbarDistribution(composite);
		chartDistribution = createChart(composite);
	}

	private static void createToolbarDistribution(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		textShape = createText(composite, 9.0d, "Shape: 1 - 9");
		textScale = createText(composite, 0.1d, "Scale: 0.1 - 2");
	}

	private static Text createText(Composite parent, double init, String tooltip) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText(Double.toString(init));
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				super.keyReleased(e);
				redrawCharts();
			}
		});
		//
		return text;
	}

	private static LineChart createChart(Composite parent) {

		LineChart lineChart = new LineChart(parent, SWT.NONE);
		lineChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = lineChart.getChartSettings();
		chartSettings.setCreateMenu(true);
		adjustPrimaryAxes(chartSettings);
		chartSettings.setBufferSelection(true);
		lineChart.applySettings(chartSettings);
		//
		return lineChart;
	}

	private static void redrawCharts() {

		redrawChartGaussian();
		redrawChartDistribution();
	}

	private static void redrawChartGaussian() {

		chartGaussian.deleteSeries();
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		double sigma = parseDouble(textSigma);
		ISeriesData seriesData = getPeakSeriesGaussian(sigma);
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
		lineSeriesSettings.setLineColor(Colors.RED);
		lineSeriesSettings.setLineWidth(1);
		lineSeriesSettings.setEnableArea(false);
		lineSeriesDataList.add(lineSeriesData);
		chartGaussian.addSeriesData(lineSeriesDataList);
	}

	private static void redrawChartDistribution() {

		chartDistribution.deleteSeries();
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		double shape = parseDouble(textShape);
		double scale = parseDouble(textScale);
		ISeriesData seriesData = getPeakSeriesDistribution(shape, scale);
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
		lineSeriesSettings.setLineColor(Colors.RED);
		lineSeriesSettings.setLineWidth(1);
		lineSeriesSettings.setEnableArea(false);
		lineSeriesDataList.add(lineSeriesData);
		chartDistribution.addSeriesData(lineSeriesDataList);
	}

	private static double parseDouble(Text text) {

		double value;
		try {
			value = Double.parseDouble(text.getText().trim());
			if(value <= 0) {
				value = 1.0d;
			}
		} catch(NumberFormatException e) {
			value = 1.0d;
		}
		//
		return value;
	}

	public static ISeriesData getPeakSeriesGaussian(double sigma) {

		int start = 1;
		int stop = 500;
		int size = stop - start + 1;
		double norm = 1000.0d;
		double mean = 250.0d;
		//
		double[] ySeries = new double[size];
		double[] xSeries = new double[size];
		//
		Gaussian gaussian = new Gaussian(norm, mean, sigma);
		for(int x = start, i = 0; x <= stop; x++, i++) {
			xSeries[i] = x;
			ySeries[i] = gaussian.value(x);
		}
		//
		return new SeriesData(xSeries, ySeries, "Sigma: " + sigma);
	}

	public static ISeriesData getPeakSeriesDistribution(double shape, double scale) {

		int start = 1;
		int stop = 50000;
		int size = stop - start + 1;
		//
		double[] ySeries = new double[size];
		double[] xSeries = new double[size];
		//
		GammaDistribution distribution = new GammaDistribution(shape, scale);
		for(int x = start, i = 0; x <= stop; x++, i++) {
			xSeries[i] = x;
			ySeries[i] = distribution.density(x / 1000.0d);
		}
		//
		return new SeriesData(xSeries, ySeries, " Shape: " + shape + " Scale: " + scale);
	}

	private static void adjustPrimaryAxes(IChartSettings chartSettings) {

		IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
		primaryAxisSettingsX.setTitle("X");
		//
		IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
		primaryAxisSettingsY.setTitle("Y");
	}
}
