/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.transformer;

import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.kerner.utils.math.TransformerLog2;
import net.sf.kerner.utils.math.TransformerScale;
import net.sf.kerner.utils.math.UtilMath;

/**
 *
 * TODO description
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 * <p>
 * <b>Threading:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * Fully thread save (stateless).
 * </pre>
 *
 * </p>
 * <p>
 * last reviewed: 2014-02-18
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class TransformerPeakToIntensityLog2 extends TransformerPeakToIntensity {

	
	public TransformerScale getScale() {

		return new TransformerLog2();
	}

	
	public Double transform(final ProviderIntensity element) {

		return UtilMath.log2(super.transform(element));
	}
}
