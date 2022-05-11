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