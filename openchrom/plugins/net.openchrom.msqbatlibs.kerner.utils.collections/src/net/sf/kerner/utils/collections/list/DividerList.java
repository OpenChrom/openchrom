/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.list;

import java.util.LinkedList;
import java.util.List;

import net.sf.kerner.utils.transformer.Transformer;

public class DividerList<T> implements Transformer<List<T>, List<List<T>>> {

	private final int partitionSize;

	public DividerList(int partitionSize) {
		super();
		this.partitionSize = partitionSize;
	}

	public List<List<T>> transform(List<T> originalList) {

		List<List<T>> partitions = new LinkedList<List<T>>();
		for(int i = 0; i < originalList.size(); i += partitionSize) {
			partitions.add(originalList.subList(i, Math.min(i + partitionSize, originalList.size())));
		}
		return partitions;
	}
}
