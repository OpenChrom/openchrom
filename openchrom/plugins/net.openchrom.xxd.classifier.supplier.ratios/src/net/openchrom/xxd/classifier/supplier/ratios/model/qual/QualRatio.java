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
package net.openchrom.xxd.classifier.supplier.ratios.model.qual;

import org.eclipse.chemclipse.model.core.IPeak;

public class QualRatio {

	private IPeak peak = null; // optional
	private String comment = "";

	public QualRatio() {
		this(null, "");
	}

	public QualRatio(IPeak peak, String comment) {
		this.peak = peak;
		this.comment = comment;
	}

	public IPeak getPeak() {

		return peak;
	}

	public void setPeak(IPeak peak) {

		this.peak = peak;
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String comment) {

		this.comment = comment;
	}
}
