/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.metric;

import org.eclipse.chemclipse.model.identifier.IRatingSupplier;
import org.eclipse.chemclipse.model.identifier.IRatingSupplierFactory;

public class BaseCallerRatingSupplierFactory implements IRatingSupplierFactory {

	@Override
	public IRatingSupplier createRatingSupplier() {

		return new BaseCallerRatingSupplier();
	}
}
