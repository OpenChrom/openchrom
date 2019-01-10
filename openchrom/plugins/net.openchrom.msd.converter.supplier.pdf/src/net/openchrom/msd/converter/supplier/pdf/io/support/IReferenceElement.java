/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

public interface IReferenceElement<T> extends IElement<T> {

	ReferenceX getReferenceX();

	T setReferenceX(ReferenceX referenceX);

	ReferenceY getReferenceY();

	T setReferenceY(ReferenceY referenceY);
}