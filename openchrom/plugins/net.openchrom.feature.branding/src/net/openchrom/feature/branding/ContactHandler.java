/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
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
package net.openchrom.feature.branding;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.program.Program;

public class ContactHandler {

	@Execute
	public void execute() {

		Program.launch("https://lablicate.com/about/contact");
	}
}
