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

	String getReferenceChromatogramPath();

	void setReferenceChromatogramPath(String referenceChromatogramPath);

	String getIsotopeChromatogramPath();

	void setIsotopeChromatogramPath(String isotopeChromatogramPath);

	int getIsotopeLevel();

	void setIsotopeLevel(int isotopeLevel);

	String getNotes();

	void setNotes(String notes);

	String getDescription();

	void setDescription(String evaluationDescription);
}
