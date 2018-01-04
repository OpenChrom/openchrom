/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.annotation;

import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * A {@link Peak} which is also an {@link AnnotatableElement}.
 *
 *
 * <p>
 * last reviewed: 2014-12-02
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface PeakAnnotatable extends Peak, AnnotatableElement {

	PeakAnnotatable clone();
}
