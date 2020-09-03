/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

public enum Visibility {
	TIC, //
	TRACE, //
	BOTH; //

	public static boolean isTIC(Visibility visibility) {

		return TIC.equals(visibility) || BOTH.equals(visibility);
	}

	public static boolean isTRACE(Visibility visibility) {

		return TRACE.equals(visibility) || BOTH.equals(visibility);
	}
}
