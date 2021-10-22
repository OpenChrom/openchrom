/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
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
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
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
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + getStartRetentionTime();
		result = prime * result + getStopRetentionTime();
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
		ReportSetting other = (ReportSetting)obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		if(getStartRetentionTime() != other.getStartRetentionTime())
			return false;
		if(getStopRetentionTime() != other.getStopRetentionTime())
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "IdentifierSetting [" //
				+ "startRetentionTime=" + getStartRetentionTime() + //
				", stopRetentionTime=" + getStopRetentionTime() + //
				", name=" + name + //
				", casNumber=" + casNumber + //
				", reportStrategy=" + reportStrategy + //
				"]"; //
	}
}
