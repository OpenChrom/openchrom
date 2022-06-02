/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings;

import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.ChromatogramIdentifierAdapterSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class CleanerSettings extends ChromatogramIdentifierAdapterSettings {

	@JsonPropertyDescription(value = "Remove targets where the SMILES formula is not set.")
	@JsonProperty(value = "Delete Scan Targets", defaultValue = "false")
	private boolean deleteScanTargets = PreferenceSupplier.DEF_DELETE_SCAN_TARGETS;
	@JsonPropertyDescription(value = "Remove targets where the SMILES formula is not set.")
	@JsonProperty(value = "Delete Peak Targets", defaultValue = "false")
	private boolean deletePeakTargets = PreferenceSupplier.DEF_DELETE_PEAK_TARGETS;

	public boolean isDeleteScanTargets() {

		return deleteScanTargets;
	}

	public void setDeleteScanTargets(boolean deleteScanTargets) {

		this.deleteScanTargets = deleteScanTargets;
	}

	public boolean isDeletePeakTargets() {

		return deletePeakTargets;
	}

	public void setDeletePeakTargets(boolean deletePeakTargets) {

		this.deletePeakTargets = deletePeakTargets;
	}
}