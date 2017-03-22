/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.wizards;

public interface IProcessorWizardElements {

	String getC12ChromatogramPath();

	void setC12ChromatogramPath(String c12ChromatogramPath);

	String getC13ChromatogramPath();

	void setC13ChromatogramPath(String c13ChromatogramPath);

	int getLevel();

	void setLevel(int level);

	String getNotes();

	void setNotes(String notes);

	String getDescription();

	void setDescription(String evaluationDescription);
}
