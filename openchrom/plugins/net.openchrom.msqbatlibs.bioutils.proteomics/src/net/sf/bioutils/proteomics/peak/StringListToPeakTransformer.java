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
package net.sf.bioutils.proteomics.peak;

import java.util.List;

import net.sf.kerner.utils.transformer.Transformer;

/**
 * {@link Transformer} to parse a single {@link Peak} from a {@link List} of
 * Strings.
 * 
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * last reviewed: 2013-07-08
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-07-08
 * 
 * 
 * @see Transformer
 * @see Peak
 * @param
 * 			<P>
 *            type of {@link Peak}
 */
public interface StringListToPeakTransformer<P extends Peak> extends Transformer<List<? extends String>, P> {

	/**
	 * @return newly parsed {@code Peak} or null, if parsing failed
	 */
	public P transform(List<? extends String> element);
}
