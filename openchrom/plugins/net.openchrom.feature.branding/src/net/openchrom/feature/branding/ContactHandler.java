/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.feature.branding;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;

public class ContactHandler {

	@Execute
	public void execute(Shell shell) {

		Program.launch("https://lablicate.com/about/contact");
	}
}
