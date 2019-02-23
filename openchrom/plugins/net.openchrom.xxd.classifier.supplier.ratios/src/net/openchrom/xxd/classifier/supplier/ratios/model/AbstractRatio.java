/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;

public abstract class AbstractRatio {

	private IPeak peak = null; // optional
	private String name = "";
	private double deviation = 0.0d;
	private double deviationWarn = 0.0d; // 100 => 100%
	private double deviationError = 0.0d; // 100 => 100%

	public IPeak getPeak() {

		return peak;
	}

	public void setPeak(IPeakMSD peak) {

		this.peak = peak;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public double getDeviation() {

		return deviation;
	}

	public void setDeviation(double deviation) {

		this.deviation = deviation;
	}

	public double getDeviationWarn() {

		return deviationWarn;
	}

	public void setDeviationWarn(double deviationWarn) {

		this.deviationWarn = deviationWarn;
	}

	public double getDeviationError() {

		return deviationError;
	}

	public void setDeviationError(double deviationError) {

		this.deviationError = deviationError;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		AbstractRatio other = (AbstractRatio)obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "AbstractRatio [name=" + name + ", deviation=" + deviation + ", deviationWarn=" + deviationWarn + ", deviationError=" + deviationError + "]";
	}
}
