/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - adjust API
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model;

import org.eclipse.chemclipse.model.core.IPeak;

public interface IPeakRatio {

	IPeak getPeak();

	void setPeak(IPeak peak);

	String getName();

	void setName(String name);

	double getDeviation();

	void setDeviation(double deviation);

	double getDeviationWarn();

	void setDeviationWarn(double deviationWarn);

	double getDeviationError();

	void setDeviationError(double deviationError);
}