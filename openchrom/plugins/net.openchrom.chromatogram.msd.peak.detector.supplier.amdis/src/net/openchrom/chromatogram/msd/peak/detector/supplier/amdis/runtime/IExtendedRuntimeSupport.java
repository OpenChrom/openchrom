/*******************************************************************************
 * Copyright (c) 2014, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.runtime;

import org.eclipse.chemclipse.support.runtime.IRuntimeSupport;

public interface IExtendedRuntimeSupport extends IRuntimeSupport {

	/**
	 * Returns the AMDIS specific settings instance.
	 * 
	 * @return {@link IAmdisSupport}
	 */
	IAmdisSupport getAmdisSupport();
}
