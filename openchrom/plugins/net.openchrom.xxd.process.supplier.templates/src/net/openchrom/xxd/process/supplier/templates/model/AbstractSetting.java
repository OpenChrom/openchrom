/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import java.util.Objects;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;

public abstract class AbstractSetting {

	public static final int MISSING_RETENTION_TIME = Integer.MIN_VALUE;
	public static final int FULL_RETENTION_TIME = 0;
	//
	private double positionStart = 0.0d;
	private double positionStop = 0.0d;
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;

	/**
	 * This method shall be only used in the UI context.
	 * Otherwise use the position fields.
	 * 
	 * @return int
	 */
	public int getStartRetentionTime() {

		return getRetentionTime(positionStart, null);
	}

	/**
	 * This method shall be only used in the UI context.
	 * Otherwise use the position fields.
	 * 
	 * @param startRetentionTime
	 */
	public void setStartRetentionTime(int startRetentionTime) {

		positionDirective = PositionDirective.RETENTION_TIME_MIN;
		positionStart = startRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR;
	}

	/**
	 * This method shall be only used in the UI context.
	 * Otherwise use the position fields.
	 * 
	 * @return int
	 */
	public int getStopRetentionTime() {

		return getRetentionTime(positionStop, null);
	}

	/**
	 * This method shall be only used in the UI context.
	 * Otherwise use the position fields.
	 * 
	 * @param stopRetentionTime
	 */
	public void setStopRetentionTime(int stopRetentionTime) {

		positionDirective = PositionDirective.RETENTION_TIME_MIN;
		positionStop = stopRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR;
	}

	public int getRetentionTimeStart(RetentionIndexMap retentionIndexMap) {

		return getRetentionTime(positionStart, retentionIndexMap);
	}

	public int getRetentionTimeStop(RetentionIndexMap retentionIndexMap) {

		return getRetentionTime(positionStop, retentionIndexMap);
	}

	public double getPositionStart() {

		return positionStart;
	}

	public void setPositionStart(double positionStart) {

		this.positionStart = positionStart;
	}

	public double getPositionStop() {

		return positionStop;
	}

	public void setPositionStop(double positionStop) {

		this.positionStop = positionStop;
	}

	public PositionDirective getPositionDirective() {

		return positionDirective;
	}

	public void setPositionDirective(PositionDirective positionDirective) {

		this.positionDirective = positionDirective;
	}

	private int getRetentionTime(double value, RetentionIndexMap retentionIndexMap) {

		int retentionTime = MISSING_RETENTION_TIME;
		switch(positionDirective) {
			case RETENTION_TIME_MS:
				retentionTime = (int)Math.round(value);
				break;
			case RETENTION_TIME_MIN:
				retentionTime = (int)Math.round(value * IChromatogram.MINUTE_CORRELATION_FACTOR);
				break;
			case RETENTION_INDEX:
				if(retentionIndexMap != null) {
					int calculatedRetentionTime = retentionIndexMap.getRetentionTime((int)value);
					if(calculatedRetentionTime != RetentionIndexMap.VALUE_NOT_AVAILABLE) {
						retentionTime = calculatedRetentionTime;
					}
				}
				break;
			default:
				break;
		}
		//
		return retentionTime;
	}

	@Override
	public int hashCode() {

		return Objects.hash(positionDirective, positionStart, positionStop);
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		AbstractSetting other = (AbstractSetting)obj;
		return positionDirective == other.positionDirective && Double.doubleToLongBits(positionStart) == Double.doubleToLongBits(other.positionStart) && Double.doubleToLongBits(positionStop) == Double.doubleToLongBits(other.positionStop);
	}

	@Override
	public String toString() {

		return "AbstractSetting [positionDirective=" + positionDirective + ", positionStart=" + positionStart + ", positionStop=" + positionStop + "]";
	}
}