/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import org.eclipse.swt.graphics.Point;

public class BarSeriesIon {

	private double mz;
	private double intensity;
	private Point point;

	public BarSeriesIon(double mz, double intensity, Point point) {
		this.mz = mz;
		this.intensity = intensity;
		this.point = point;
	}

	public double getMz() {

		return mz;
	}

	public double getIntensity() {

		return intensity;
	}

	public Point getPoint() {

		return point;
	}
}
