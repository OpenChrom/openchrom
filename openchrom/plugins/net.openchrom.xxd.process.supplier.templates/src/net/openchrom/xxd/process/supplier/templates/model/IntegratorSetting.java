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
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

public class IntegratorSetting extends AbstractSetting {

	public static final String INTEGRATOR_NAME_TRAPEZOID = "Trapezoid";
	public static final String INTEGRATOR_ID_TRAPEZOID = "org.eclipse.chemclipse.chromatogram.xxd.integrator.supplier.trapezoid.peakIntegrator";
	public static final String INTEGRATOR_NAME_MAX = "Max";
	public static final String INTEGRATOR_ID_MAX = "org.eclipse.chemclipse.chromatogram.msd.integrator.supplier.peakmax.peakIntegrator";
	//
	private String identifier = "";
	private String integrator = "";

	public void copyFrom(IntegratorSetting setting) {

		if(setting != null) {
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPositionDirective(setting.getPositionDirective());
			setIdentifier(setting.getIdentifier());
			setIntegrator(setting.getIntegrator());
		}
	}

	public String getIdentifier() {

		return identifier;
	}

	public void setIdentifier(String identifier) {

		this.identifier = identifier;
	}

	public String getIntegrator() {

		return integrator;
	}

	public void setIntegrator(String integrator) {

		this.integrator = integrator;
	}
}