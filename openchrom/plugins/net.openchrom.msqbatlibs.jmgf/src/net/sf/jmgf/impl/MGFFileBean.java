/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jmgf.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;

public class MGFFileBean implements MGFFile {

	private static final long serialVersionUID = -6619126415505379215L;
	private List<MGFElement> elements;

	public MGFFileBean() {

		this(new ArrayList<MGFElement>());
	}

	public MGFFileBean(final List<? extends MGFElement> elements) {

		this.elements = new ArrayList<MGFElement>(elements);
	}

	public List<MGFElement> getElements() {

		return elements;
	}

	public Iterator<MGFElement> iterator() {

		return elements.iterator();
	}

	public void setElements(final List<MGFElement> elements) {

		this.elements = elements;
	}
}
