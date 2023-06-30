/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"projectInfo", "analytes"})
@XmlRootElement(name = "MspMatchResult")
public class MspMatchResult {

	@XmlElement(name = "ProjectInfo", required = true)
	protected ProjectInfo projectInfo;
	@XmlElement(name = "Analytes", required = true)
	protected Analytes analytes;

	public ProjectInfo getProjectInfo() {

		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo value) {

		this.projectInfo = value;
	}

	public Analytes getAnalytes() {

		return analytes;
	}

	public void setAnalytes(Analytes value) {

		this.analytes = value;
	}
}
