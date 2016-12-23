/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisitorRememberSeen<T> implements Visitor<T> {

	private final List<T> allElements = new ArrayList<T>();

	public synchronized List<T> getElementsSeen() {

		return Collections.unmodifiableList(allElements);
	}

	public synchronized Void transform(final T element) {

		allElements.add(element);
		return null;
	}
}
