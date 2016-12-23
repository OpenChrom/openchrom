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
package net.sf.bioutils.proteomics.peptides;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.TransformerList;

public class PeptideSequencesImpl implements PeptideSequences {

	public final static TransformerList<Peptide, Double> TRANSFORMER_TO_MASS_LIST = new AbstractTransformingListFactory<Peptide, Double>() {

		public Double transform(final Peptide element) {

			return element.getMolWeight();
		}
	};
	private final Collection<Peptide> delegate;

	public PeptideSequencesImpl(final Collection<Peptide> collection) {
		super();
		delegate = collection;
	}

	public boolean add(final Peptide e) {

		return delegate.add(e);
	}

	public boolean addAll(final Collection<? extends Peptide> c) {

		return delegate.addAll(c);
	}

	public List<Double> asMassList() {

		return TRANSFORMER_TO_MASS_LIST.transformCollection(delegate);
	}

	public void clear() {

		delegate.clear();
	}

	public boolean contains(final Object o) {

		return delegate.contains(o);
	}

	public boolean containsAll(final Collection<?> c) {

		return delegate.containsAll(c);
	}

	public boolean equals(final Object o) {

		return delegate.equals(o);
	}

	public int hashCode() {

		return delegate.hashCode();
	}

	public boolean isEmpty() {

		return delegate.isEmpty();
	}

	public Iterator<Peptide> iterator() {

		return delegate.iterator();
	}

	public boolean remove(final Object o) {

		return delegate.remove(o);
	}

	public boolean removeAll(final Collection<?> c) {

		return delegate.removeAll(c);
	}

	public boolean retainAll(final Collection<?> c) {

		return delegate.retainAll(c);
	}

	public int size() {

		return delegate.size();
	}

	public Object[] toArray() {

		return delegate.toArray();
	}

	public <T> T[] toArray(final T[] a) {

		return delegate.toArray(a);
	}
}
