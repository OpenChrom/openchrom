/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.settings;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.settings.IChromatogramIdentifierSettings;
import org.eclipse.chemclipse.model.identifier.AbstractIdentifierSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class CleanerSettings extends AbstractIdentifierSettings implements IChromatogramIdentifierSettings {

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
