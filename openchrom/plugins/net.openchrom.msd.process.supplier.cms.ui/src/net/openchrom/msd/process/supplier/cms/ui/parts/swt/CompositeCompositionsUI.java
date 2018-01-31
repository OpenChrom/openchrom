/*******************************************************************************
 * Copyright (c) 2017 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.ISample;
import org.eclipse.nebula.visualization.xygraph.dataprovider.Sample;
import org.eclipse.nebula.visualization.xygraph.figures.Annotation;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class CompositeCompositionsUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	//
	private DecimalFormat decimalFormatscaleOffset = ValueFormat.getDecimalFormatEnglish("0.0##E00");
	private DecimalFormat decimalFormatMouseHover = ValueFormat.getDecimalFormatEnglish("0.0##E00");
	private TreeMap<String, Trace> traceCompositionsMap; // key is composition name string, value is Trace for that composition, needed so trace can be removed
	private XYGraph xyGraphComposition;
	private int xyGraphCompositionNumberOfPoints = 0; // if xyGraphCompositionNumberOfPoints > 0, then remainder of xyGraphComposition data items are valid
	private Button buttonPP; // select partial pressures
	private Button buttonMF; // select mol fraction
	private Button buttonLF; // select library fraction
	private Button buttonLogScale; // if checked, use offset log scale, otherwise use linear scale
	private DecompositionResults results = null;
	private boolean usingETimes = false;
	private boolean usingOffsetLogScale = false;
	private double scaleOffset;
	private Text textLogScaleOffset;
	private Text textMouseOut;
	private boolean txtLogScaleOffsetIgnoreEvent = false;
	private Trace traceScaleOffset = null;
	private Yunits yUnits = Yunits.PP;
	private TreeMap<String, ArrayList<Double>> lookup;
	private Polyline pLine;

	public CompositeCompositionsUI(Composite parent, int style) {
		super(parent, style);
		this.initialize();
	}

	private enum Yunits {
		PP, MF, LF // Partial Pressure, Mol Fraction, Library Fraction
	};

	private void setyUnits(Yunits yUnits) {

		if((null != results) && !results.isCalibrated()) {
			yUnits = Yunits.LF;
			buttonPP.setSelection(false);
			buttonMF.setSelection(false);
			buttonLF.setSelection(true);
		} else {
			this.yUnits = yUnits;
		}
		updateXYGraph();
	}

	private void initialize() {

		GridLayout thisGridLayout = new GridLayout(1, false);
		this.setLayout(thisGridLayout);
		GridData thisGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(thisGridData);
		//
		Composite compositeTopRow = new Composite(this, SWT.NONE);
		GridLayout topRowCompositeGridLayout = new GridLayout(2, false);
		compositeTopRow.setLayout(topRowCompositeGridLayout);
		GridData topRowCompositeGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeTopRow.setLayoutData(topRowCompositeGridData);
		//
		Group compositGroup = new Group(compositeTopRow, SWT.NONE);
		GridLayout compositGroupGridLayout = new GridLayout(4, false);
		compositGroup.setLayout(compositGroupGridLayout);
		GridData compositGroupGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		compositGroup.setLayoutData(compositGroupGridData);
		// display units Buttons
		Label label = new Label(compositGroup, SWT.NONE);
		label.setText("Display Units:");
		//
		buttonPP = new Button(compositGroup, SWT.RADIO);
		buttonPP.setText("Partial Pressure");
		buttonPP.setSelection(true);
		buttonPP.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonPP.getSelection()) {
						// System.out.println("buttonPP selection event, selected");
						setyUnits(Yunits.PP);
					} else {
						// System.out.println("buttonPP selection event, not selected");
					}
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
		//
		buttonMF = new Button(compositGroup, SWT.RADIO);
		buttonMF.setText("Mol Fraction");
		buttonMF.setSelection(false);
		buttonMF.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonMF.getSelection()) {
						// System.out.println("buttonMF selection event, selected");
						setyUnits(Yunits.MF);
					} else {
						// System.out.println("buttonMF selection event, not selected");
					}
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
		//
		buttonLF = new Button(compositGroup, SWT.RADIO);
		buttonLF.setText("Library Fraction");
		buttonLF.setSelection(false);
		buttonLF.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonLF.getSelection()) {
						// System.out.println("buttonLF selection event, selected");
						setyUnits(Yunits.LF);
					} else {
						// System.out.println("buttonLF selection event, not selected");
					}
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
		//
		//
		Group compositGroup2 = new Group(compositeTopRow, SWT.NONE); // place for log scale controls
		GridLayout compositGroup2GridLayout = new GridLayout(1, false);
		compositGroup2.setLayout(compositGroup2GridLayout);
		GridData compositGroup2GridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		compositGroup2.setLayoutData(compositGroup2GridData);
		//
		buttonLogScale = new Button(compositGroup2, SWT.CHECK);
		buttonLogScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		buttonLogScale.setText("Use Offset Log Scale");
		buttonLogScale.setSelection(false);
		buttonLogScale.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonLogScale.getSelection()) {
						// System.out.println("buttonLogScale selection event, checked");
						usingOffsetLogScale = true;
					} else {
						// System.out.println("buttonLogScale selection event, not checked");
						usingOffsetLogScale = false;
					}
					txtLogScaleOffsetIgnoreEvent = true;
					textLogScaleOffset.setText("");
					txtLogScaleOffsetIgnoreEvent = false;
					updateXYGraph();
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
		//
		textLogScaleOffset = new Text(compositGroup2, SWT.RIGHT | SWT.BORDER);
		textLogScaleOffset.setText("");
		GridData textLogScaleOffsetGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textLogScaleOffset.setLayoutData(textLogScaleOffsetGridData);
		textLogScaleOffset.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if(!txtLogScaleOffsetIgnoreEvent && usingOffsetLogScale) {
					updateXYGraph();
				}
			}
		});
		// www mouse hover text was here
		// place mouse hover text here
		textMouseOut = new Text(this, SWT.BORDER);
		textMouseOut.setText("empty");
		GridData textMouseOutGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		textMouseOut.setLayoutData(textMouseOutGridData);
		// www
		Composite compositeGraph = new Composite(this, SWT.NONE);
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayout(new FillLayout());
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
		xyGraphComposition.getPlotArea().addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent me) {

			}

			@Override
			public void mouseEntered(MouseEvent me) {

			}

			@Override
			public void mouseExited(MouseEvent me) {

				// textMouseOut.setText("");
				if(pLine != null) {
					xyGraphComposition.getPlotArea().remove(pLine);
					pLine = null;
				}
			}

			@Override
			public void mouseHover(MouseEvent me) {

				/// double xValue, yValue;
				int xPos, yPos;
				ISample sample;
				if(0 == xyGraphComposition.getPlotArea().getTraceList().size())
					return;
				/// xValue = xyGraphComposition.getPrimaryXAxis().getPositionValue(me.x, false);
				/// yValue = xyGraphComposition.getPrimaryYAxis().getPositionValue(me.y, false);
				String txt = new String();
				sample = findClosestPoint(me.x, me.y);
				xPos = xyGraphComposition.getPrimaryXAxis().getValuePosition(sample.getXValue(), false);
				yPos = xyGraphComposition.getPrimaryYAxis().getValuePosition(sample.getYValue(), false);
				// txt = txt + "(";
				// txt = txt + me.x + "," + me.y;
				// txt = txt + ")@(";
				// txt = txt + xValue + "," + yValue;
				// txt = txt + ")->(";
				// txt = txt + xPos + "," + yPos;
				// txt = txt + ")@(";
				// txt = txt + ")";
				txt = txt + "(";
				txt = txt + sample.getXValue() + ", ";
				txt = txt + decimalFormatMouseHover.format(sample.getYValue()) + ")";
				txt = txt + ": " + sample.getInfo();
				textMouseOut.setText(txt);
				if(null == pLine) {
					pLine = new Polyline();
					pLine.setLineWidthFloat(2.0f);
					pLine.setForegroundColor(ColorConstants.red);
					xyGraphComposition.getPlotArea().add(pLine, 0);
				}
				pLine.setPoints(new PointList(new int[]{me.x, me.y, xPos, yPos}));
			}

			@Override
			public void mouseMoved(MouseEvent me) {

			}
		});
		lightweightSystem.setContents(xyGraphComposition);
	}

	private boolean isValidDoubleString(String myString) {

		final String Digits = "(\\p{Digit}+)";
		final String HexDigits = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally
		// signed decimal integer.
		final String Exp = "[eE][+-]?" + Digits;
		final String fpRegex = ("[\\x00-\\x20]*" + // Optional leading "whitespace"
				"[+-]?(" + // Optional sign character
				"NaN|" + // "NaN" string
				"Infinity|" + // "Infinity" string
				// A decimal floating-point string representing a finite positive
				// number without a leading sign has at most five basic pieces:
				// Digits . Digits ExponentPart FloatTypeSuffix
				//
				// Since this method allows integer-only strings as input
				// in addition to strings of floating-point literals, the
				// two sub-patterns below are simplifications of the grammar
				// productions from section 3.10.2 of
				// The Java™ Language Specification.
				// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
				"(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +
				// . Digits ExponentPart_opt FloatTypeSuffix_opt
				"(\\.(" + Digits + ")(" + Exp + ")?)|" +
				// Hexadecimal strings
				"((" +
				// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "(\\.)?)|" +
				// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
				"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits + "))" + "[fFdD]?))" + "[\\x00-\\x20]*");// Optional trailing "whitespace"
		if(Pattern.matches(fpRegex, myString))
			return true; // Will not throw NumberFormatException
		else {
			return false;
		}
	}

	private ISample findClosestPoint(int x, int y) { // x and y are cursor position in pixels, returns x & y value for closest point

		int testXposition, testYposition;
		double minDist, testDist, closestXvalue, closestYvalue, testXvalue,
				testYvalue;
		boolean initialized = false;
		List<Trace> traceList = new ArrayList<Trace>();
		CircularBufferDataProvider tempDataProvider = new CircularBufferDataProvider(false);
		traceList = xyGraphComposition.getPlotArea().getTraceList();
		String traceName = new String("no name");
		minDist = 0.0;
		closestXvalue = 0.0;
		closestYvalue = 0.0;
		for(Trace traceTemp : traceList) {
			if(null != traceTemp) {
				if(!(traceTemp.getDataProvider() instanceof CircularBufferDataProvider))
					continue;
				else {
					tempDataProvider = (CircularBufferDataProvider)traceTemp.getDataProvider();
					for(int i = 0; i < tempDataProvider.getSize(); i++) { // linear search over values
						testXvalue = tempDataProvider.getSample(i).getXValue();
						testYvalue = tempDataProvider.getSample(i).getYValue();
						testXposition = xyGraphComposition.getPrimaryXAxis().getValuePosition(testXvalue, false); // x position
						testYposition = xyGraphComposition.getPrimaryYAxis().getValuePosition(testYvalue, false); // y position
						testDist = Math.pow(x - testXposition, 2) + Math.pow(y - testYposition, 2); // distance in position units
						if(!initialized) {
							initialized = true;
							minDist = testDist;
							closestXvalue = testXvalue;
							closestYvalue = testYvalue;
							traceName = traceTemp.getName();
						} else if(testDist < minDist) {
							minDist = testDist;
							closestXvalue = testXvalue;
							closestYvalue = testYvalue;
							traceName = traceTemp.getName();
						}
					}
				}
			}
		}
		return new Sample(closestXvalue, closestYvalue, 0, 0, 0, 0, traceName);
	}

	// Returns the index of the closest left (if left is true) or right of the key value
	// assumes values in trace are sorted increase with index
	// Copied from Class Trace, not sure if it is useful for findClosesPoint()
	private int nearBinarySearchX(Trace trace, double key, boolean left) {

		IDataProvider traceDataProvider = trace.getDataProvider();
		int low = 0;
		int high = traceDataProvider.getSize() - 1;
		while(low <= high) {
			int mid = (low + high) >>> 1;
			double midVal = traceDataProvider.getSample(mid).getXValue();
			int cmp;
			if(midVal < key) {
				cmp = -1; // Neither val is NaN, thisVal is smaller
			} else if(midVal > key) {
				cmp = 1; // Neither val is NaN, thisVal is larger
			} else {
				long midBits = Double.doubleToLongBits(midVal);
				long keyBits = Double.doubleToLongBits(key);
				cmp = (midBits == keyBits ? 0 : // Values are equal
						(midBits < keyBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
								1)); // (0.0, -0.0) or (NaN, !NaN)
			}
			if(cmp < 0) {
				if(mid < traceDataProvider.getSize() - 1 && key < traceDataProvider.getSample(mid + 1).getXValue()) {
					if(left)
						return mid;
					else
						return mid + 1;
				}
				low = mid + 1;
			} else if(cmp > 0) {
				if(mid > 0 && key > traceDataProvider.getSample(mid - 1).getXValue())
					if(left)
						return mid - 1;
					else
						return mid;
				high = mid - 1;
			} else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}

	private void clearXYGraph() {

		if(null != traceCompositionsMap) {
			for(Trace traceTemp : traceCompositionsMap.values()) {
				xyGraphComposition.removeTrace(traceTemp);
			}
		}
		xyGraphComposition.setTitle("Composition");
		xyGraphComposition.getPrimaryXAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraphComposition.getPrimaryYAxis().setAutoScaleThreshold(0);
		xyGraphCompositionNumberOfPoints = 0; // invalidate current XYGraph composition plots
	}

	public void updateXYGraph(DecompositionResults results) {

		this.results = results;
		if(null == results) {
			clearXYGraph();
			return;
		} else if(!results.isCalibrated()) {
			yUnits = Yunits.LF;
			buttonPP.setSelection(false);
			buttonMF.setSelection(false);
			buttonLF.setSelection(true);
		}
		this.usingETimes = results.isUsingETimes();
		updateXYGraph();
	}

	private void updateXYGraph() {

		if(null != results) {
			System.out.println("Update Composition XYGraph for " + results.getName());
			// if(0 == xyGraphNumberOfPoints) {
			String newTitle = results.getName();
			String ppUnits = results.getDecompositionResultsList().get(0).getSourcePressureUnits();
			newTitle = "Composition: " + newTitle;
			xyGraphComposition.setTitle(newTitle);
			if(usingETimes) {
				xyGraphComposition.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphComposition.getPrimaryXAxis().setTitle("Scan Number");
			}
			if(results.isCalibrated() && !(Yunits.LF == yUnits)) {
				if(Yunits.PP == yUnits) {
					xyGraphComposition.getPrimaryYAxis().setTitle("Partial Pressure, " + ppUnits);
				} else if(Yunits.MF == yUnits) {
					xyGraphComposition.getPrimaryYAxis().setTitle("Mol Fraction, dimensionless");
				}
			} else {
				xyGraphComposition.getPrimaryYAxis().setTitle("Library Contribution, uncalibrated");
			}
			xyGraphComposition.getPrimaryYAxis().setLogScale(usingOffsetLogScale);
			lookup = new TreeMap<String, ArrayList<Double>>();
			xyGraphCompositionNumberOfPoints = results.getDecompositionResultsList().size();
			double[] xDataTraceComposition = new double[xyGraphCompositionNumberOfPoints];
			String componentName;
			DecompositionResult result;
			for(int i = 0; i < xyGraphCompositionNumberOfPoints; i++) {
				result = results.getDecompositionResultsList().get(i);
				for(int j = 0; j < result.getNumberOfComponents(); j++) {
					componentName = result.getLibCompName(j);
					if(null == lookup.get(componentName)) {
						lookup.put(componentName, new ArrayList<Double>());
					}
					if(results.isCalibrated() && !(Yunits.LF == yUnits)) {
						if(Yunits.PP == yUnits) {
							lookup.get(componentName).add(i, result.getPartialPressure(j, ppUnits));
						} else if(Yunits.MF == yUnits) {
							lookup.get(componentName).add(i, result.getMolFraction(j)); // MF units
						}
					} else {
						lookup.get(componentName).add(i, result.getLibraryFraction(j));
					}
				}
				if(usingETimes) {
					xDataTraceComposition[i] = result.getETimeS();
				} else {
					xDataTraceComposition[i] = result.getResidualSpectrum().getScanNumber();
				}
			}
			if(0 >= lookup.size()) {
				return; // no composition results
			}
			double minY = 0, minAbsY = 0, maxY = 0;
			for(String strName : lookup.keySet()) {
				ArrayList<Double> templist;
				templist = lookup.get(strName);
				Double[] tempdata = new Double[1];
				tempdata = templist.toArray(tempdata);
				for(int ii = 0; ii < tempdata.length; ii++) {
					double signal;
					signal = tempdata[ii];
					if(maxY == 0 && minY == 0) {
						maxY = minY = signal;
						minAbsY = java.lang.StrictMath.abs(signal);
					} else {
						if(maxY < signal) {
							maxY = signal;
						}
						//
						if(minY > signal) {
							minY = signal;
						}
						//
						if(signal != 0) {
							signal = java.lang.StrictMath.abs(signal);
							if(minAbsY == 0 || minAbsY > signal) {
								minAbsY = signal;
							}
						}
					}
				}
			}
			if(usingOffsetLogScale) {
				if("".equals(textLogScaleOffset.getText())) {
					scaleOffset = minAbsY - minY;
					txtLogScaleOffsetIgnoreEvent = true;
					textLogScaleOffset.setText(String.valueOf(scaleOffset));
					txtLogScaleOffsetIgnoreEvent = false;
				} else {
					if(isValidDoubleString(textLogScaleOffset.getText())) {
						scaleOffset = Double.parseDouble(textLogScaleOffset.getText());
						minAbsY = java.lang.StrictMath.abs(scaleOffset + minY); // compute new minAbsY for Y-axis limits
					}
				}
			}
			for(String strName : lookup.keySet()) {
				double temp;
				ArrayList<Double> templist;
				Color traceColor;
				templist = lookup.get(strName);
				Double[] tempdata = new Double[1];
				tempdata = templist.toArray(tempdata);
				double[] ydata = new double[tempdata.length];
				if(usingOffsetLogScale) {
					for(int ii = 0; ii < tempdata.length; ii++) {
						temp = tempdata[ii].doubleValue() + scaleOffset;
						if(0d >= temp) {
							temp = minAbsY;
						}
						ydata[ii] = temp;
					}
				} else {
					for(int ii = 0; ii < tempdata.length; ii++) {
						ydata[ii] = tempdata[ii].doubleValue();
					}
				}
				// create a trace data provider
				CircularBufferDataProvider dataProviderTraceComposition = new CircularBufferDataProvider(false); // XYGraph data item
				dataProviderTraceComposition.setBufferSize(xyGraphCompositionNumberOfPoints);
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
				// traceComposition.setPointStyle(PointStyle.XCROSS);
				xyGraphComposition.addTrace(traceComposition);
			}
			if(null != traceScaleOffset) {
				xyGraphComposition.removeTrace(traceScaleOffset);
				traceScaleOffset = null;
			}
			xyGraphComposition.getPrimaryYAxis().setAutoScale(false);
			if(!usingOffsetLogScale) {
				if(0 < minY) {
					xyGraphComposition.getPrimaryYAxis().setRange(0, 1.05 * maxY);
				} else {
					xyGraphComposition.getPrimaryYAxis().setRange(1.05 * minY, 1.05 * maxY);
				}
			} else {
				xyGraphComposition.getPrimaryYAxis().setRange(0.95 * minAbsY, 1.05 * (maxY + scaleOffset));
				CircularBufferDataProvider dataProviderTraceScaleOffset = new CircularBufferDataProvider(false); // XYGraph data item
				dataProviderTraceScaleOffset.setBufferSize(2);
				double[] ydata = new double[2];
				double[] xdata = new double[2];
				ydata[0] = ydata[1] = scaleOffset;
				xdata[0] = xyGraphComposition.getPrimaryXAxis().getRange().getLower();
				xdata[1] = xyGraphComposition.getPrimaryXAxis().getRange().getUpper();
				dataProviderTraceScaleOffset.setCurrentXDataArray(xdata);
				dataProviderTraceScaleOffset.setCurrentYDataArray(ydata);
				traceScaleOffset = new Trace("Zero(" + decimalFormatscaleOffset.format(scaleOffset) + ")", xyGraphComposition.getPrimaryXAxis(), xyGraphComposition.getPrimaryYAxis(), dataProviderTraceScaleOffset);
				traceScaleOffset.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
				// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
				xyGraphComposition.addTrace(traceScaleOffset);
			}
		}
		// xyGraph.setShowLegend(!xyGraph.isShowLegend());
	}
}
