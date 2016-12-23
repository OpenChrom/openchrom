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
package net.sf.kerner.utils.collections.trasformer;

import java.util.Collection;
import net.sf.kerner.utils.collections.TransformerAbstract;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.transformer.Transformer;

/**
 *
 * @author alex
 */
public abstract class TransformerApplierProto<T, V> extends TransformerAbstract<T, V> implements TransformerApplier<T, V> {

	protected final Collection<Transformer<T, V>> transformers = UtilCollection.newCollection();

	public synchronized void addTransformer(final Transformer<T, V> transformer) {

		transformers.add(transformer);
	}

	public synchronized void clearTransformers() {

		transformers.clear();
	}
}
