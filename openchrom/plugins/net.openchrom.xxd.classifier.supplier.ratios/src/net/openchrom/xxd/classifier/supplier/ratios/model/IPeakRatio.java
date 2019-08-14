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

public interface IPeakRatio {

	IPeak getPeak();

	void setPeak(IPeakMSD peak);

	String getName();

	void setName(String name);

	double getDeviation();

	void setDeviation(double deviation);

	double getDeviationWarn();

	void setDeviationWarn(double deviationWarn);

	double getDeviationError();

	void setDeviationError(double deviationError);
}