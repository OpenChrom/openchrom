/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

public class ReportSetting extends AbstractSetting {

	private String name = "";
	private String casNumber = "";
	private ReportStrategy reportStrategy = ReportStrategy.ALL;

	public void copyFrom(ReportSetting setting) {

		if(setting != null) {
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPositionDirective(setting.getPositionDirective());
			setName(setting.getName());
			setCasNumber(setting.getCasNumber());
			setReportStrategy(setting.getReportStrategy());
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCasNumber() {

		return casNumber;
	}

	public void setCasNumber(String casNumber) {

		this.casNumber = casNumber;
	}

	public ReportStrategy getReportStrategy() {

		return reportStrategy;
	}

	public void setReportStrategy(ReportStrategy reportStrategy) {

		this.reportStrategy = reportStrategy;
	}
}