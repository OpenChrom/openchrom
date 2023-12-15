/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
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

import java.util.Objects;

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

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		if(!super.equals(obj)) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		ReportSetting other = (ReportSetting)obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		return "ReportSetting [name=" + name + ", getPositionStart()=" + getPositionStart() + ", getPositionStop()=" + getPositionStop() + "]";
	}
}
