/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.xxd.identifier.supplier.cdk.ui.converter.ImageConverter;

@TestInstance(Lifecycle.PER_CLASS)
public class MoleculeImage_Test {

	private ImageConverter converter = ImageConverter.getInstance();

	@Test
	public void testAspirin() {

		assertNotNull(converter.smilesToImage("CC(=O)OC1=CC=CC=C1C(=O)O", 100, 100));
	}
}
