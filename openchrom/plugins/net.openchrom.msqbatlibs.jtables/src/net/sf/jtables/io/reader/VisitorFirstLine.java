/*******************************************************************************
 * Copyright (c) 2015, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.sf.jtables.io.reader;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import net.sf.kerner.utils.collections.filter.Filter;

public class VisitorFirstLine implements Filter<String> {

	private final Collection<String> needsToMatch;

	public VisitorFirstLine(Collection<String> needsToMatch) {

		this.needsToMatch = new HashSet<String>(needsToMatch);
	}

	public VisitorFirstLine(String needsToMatch) {

		this(Arrays.asList(needsToMatch));
	}

	public boolean filter(String line) {

		for(String value : needsToMatch) {
			if(!line.contains(value)) {
				return false;
			}
		}
		return true;
	}
}