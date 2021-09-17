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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Base type for all value elements for use in Series and parameters. To be restricted.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnboundedValueType", propOrder = {"f", "i", "l", "d", "s"})
@XmlSeeAlso({IndividualValueSetType.class, NumericValueType.class, SingleValueType.class})
public class UnboundedValueType {

	@XmlElement(name = "F")
	protected List<Float> f;
	@XmlElement(name = "I")
	protected List<Integer> i;
	@XmlElement(name = "L")
	protected List<Long> l;
	@XmlElement(name = "D")
	protected List<Double> d;
	@XmlElement(name = "S")
	protected List<String> s;

	public List<Float> getF() {

		if(f == null) {
			f = new ArrayList<Float>();
		}
		return this.f;
	}

	public List<Integer> getI() {

		if(i == null) {
			i = new ArrayList<Integer>();
		}
		return this.i;
	}

	public List<Long> getL() {

		if(l == null) {
			l = new ArrayList<Long>();
		}
		return this.l;
	}

	public List<Double> getD() {

		if(d == null) {
			d = new ArrayList<Double>();
		}
		return this.d;
	}

	public List<String> getS() {

		if(s == null) {
			s = new ArrayList<String>();
		}
		return this.s;
	}
}
