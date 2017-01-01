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
package net.sf.kerner.utils.io.buffered;

/**
 * An instance of {@code IOIterablePrimitive} can be iterated using an {@link IOIterator}
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-10-25
 * @see IOIterator
 */
public interface IOIterablePrimitve {

	/**
	 * Retrieve the {@link IOIterator}.
	 * 
	 * @return the {@code IOIterator}
	 */
	GeneralIOIterator getIterator();
}
