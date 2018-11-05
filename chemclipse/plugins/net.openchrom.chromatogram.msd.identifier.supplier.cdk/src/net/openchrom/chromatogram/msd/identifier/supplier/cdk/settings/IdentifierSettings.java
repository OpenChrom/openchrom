/*******************************************************************************
 * Copyright (c) 2014, 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings;

import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.AbstractChromatogramIdentifierSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class IdentifierSettings extends AbstractChromatogramIdentifierSettings {

	@JsonProperty(value = "Delete identifications without formula", defaultValue = "true")
	@JsonPropertyDescription(value = "All targets which don't have a formula will be deleted.")
	private boolean deleteIdentificationsWithoutFormula = true;

	public boolean isDeleteIdentificationsWithoutFormula() {

		return deleteIdentificationsWithoutFormula;
	}

	public void setDeleteIdentificationsWithoutFormula(boolean deleteIdentificationsWithoutFormula) {

		this.deleteIdentificationsWithoutFormula = deleteIdentificationsWithoutFormula;
	}
}
