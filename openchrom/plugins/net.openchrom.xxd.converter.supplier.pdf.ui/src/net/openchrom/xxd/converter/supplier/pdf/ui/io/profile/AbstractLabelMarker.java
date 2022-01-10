/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.pdf.ui.io.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.marker.AbstractBaseChartPaintListener;
import org.eclipse.swtchart.extensions.marker.IBaseChartPaintListener;

@SuppressWarnings("rawtypes")
public abstract class AbstractLabelMarker extends AbstractBaseChartPaintListener implements IBaseChartPaintListener {

	private Transform transform = null;
	private Map<Integer, String> labels = new HashMap<Integer, String>();
	private int indexSeries = -1;
	private ISeries serie;

	public AbstractLabelMarker(BaseChart baseChart) {

		super(baseChart);
	}

	public void setLabels(List<String> labels, int indexSeries, int orientation) {

		Map<Integer, String> labelsMap = new HashMap<Integer, String>();
		int index = 0;
		for(String label : labels) {
			labelsMap.put(index++, label);
		}
		setLabels(labelsMap, indexSeries, orientation);
	}

	public void setLabels(Map<Integer, String> labels, int indexSeries, int orientation) {

		setSeriesIndex(indexSeries);
		setLabels(labels, orientation);
	}

	public void setLabels(Map<Integer, String> labels, int orientation) {

		this.labels = (labels != null) ? labels : new HashMap<Integer, String>();
		if(orientation == SWT.VERTICAL) {
			disposeTransform();
			transform = new Transform(Display.getDefault());
			transform.rotate(-90);
		} else {
			transform = null;
		}
	}

	public void setSeriesIndex(int indexSeries) {

		this.indexSeries = indexSeries;
	}

	public void setSeries(ISeries serie) {

		this.serie = serie;
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
		disposeTransform();
	}

	public void clear() {

		labels.clear();
	}

	@Override
	public void paintControl(PaintEvent e) {

		ISeries serie = getSeries();
		if(serie == null) {
			return;
		}
		//
		BaseChart baseChart = getBaseChart();
		Rectangle rectangle = baseChart.getPlotArea().getBounds();
		int size = serie.getXSeries().length;
		for(int index : labels.keySet()) {
			if(index < size) {
				/*
				 * Draw the label if the index is within the
				 * range of the double array.
				 */
				String label = labels.get(index);
				Point point = serie.getPixelCoordinates(index);
				//
				if(point.x > 0 && rectangle.contains(point)) {
					/*
					 * Calculate x and y
					 */
					int x;
					int y;
					Point labelSize = e.gc.textExtent(label);
					GC gc = e.gc;
					if(transform != null) {
						gc.setTransform(transform);
						x = -labelSize.x - (point.y - labelSize.x - 15);
						y = point.x - (labelSize.y / 2);
					} else {
						x = point.x - labelSize.x / 2;
						y = point.y - labelSize.y - 15;
					}
					gc.drawText(label, x, y, true);
					gc.setTransform(null);
				}
			}
		}
	}

	private void disposeTransform() {

		if(transform != null) {
			transform.dispose();
		}
	}

	private ISeries getSeries() {

		if(serie != null) {
			return serie;
		}
		//
		ISeries[] series = getBaseChart().getSeriesSet().getSeries();
		if(indexSeries >= 0 && indexSeries < series.length) {
			return series[indexSeries];
		}
		return null;
	}
}
