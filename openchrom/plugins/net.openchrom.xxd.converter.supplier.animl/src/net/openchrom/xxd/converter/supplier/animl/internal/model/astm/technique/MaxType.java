/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Upper range boundary; may be marked as inclusive or exclusive.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaxType")
public class MaxType extends NumericValueType {

	@XmlAttribute(name = "included")
	protected Boolean included;

	public boolean isIncluded() {

		if(included == null) {
			return true;
		} else {
			return included;
		}
	}

	public void setIncluded(Boolean value) {

		this.included = value;
	}
}
