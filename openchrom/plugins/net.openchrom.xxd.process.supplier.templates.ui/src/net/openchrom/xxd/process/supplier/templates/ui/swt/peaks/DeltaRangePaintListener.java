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
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.marker.AbstractBaseChartPaintListener;
import org.eclipse.swtchart.extensions.marker.IBaseChartPaintListener;

public class DeltaRangePaintListener extends AbstractBaseChartPaintListener implements IBaseChartPaintListener {

	private int deltaRetentionTimeLeft = 0;
	private int deltaRetentionTimeRight = 0;

	public DeltaRangePaintListener(BaseChart baseChart) {

		super(baseChart);
	}

	public void setDeltaRetentionTime(int deltaRetentionTimeLeft, int deltaRetentionTimeRight) {

		this.deltaRetentionTimeLeft = deltaRetentionTimeLeft;
		this.deltaRetentionTimeRight = deltaRetentionTimeRight;
	}

	@Override
	public void paintControl(PaintEvent e) {

		BaseChart baseChart = getBaseChart();
		double rangeX = baseChart.getMaxX() - baseChart.getMinX() + 1;
		int width = e.width;
		e.gc.setForeground(getForegroundColor());
		e.gc.setLineStyle(SWT.LINE_DASHDOT);
		/*
		 * Left
		 */
		if(deltaRetentionTimeLeft > 0) {
			int partOffset = (int)(width / rangeX * deltaRetentionTimeLeft);
			if(partOffset > 0) {
				int leftOffset = partOffset;
				e.gc.drawLine(leftOffset, 0, leftOffset, e.height);
			}
		}
		/*
		 * Right
		 */
		if(deltaRetentionTimeRight > 0) {
			int partOffset = (int)(width / rangeX * deltaRetentionTimeRight);
			if(partOffset > 0) {
				int rightOffset = width - partOffset;
				e.gc.drawLine(rightOffset, 0, rightOffset, e.height);
			}
		}
	}
}
