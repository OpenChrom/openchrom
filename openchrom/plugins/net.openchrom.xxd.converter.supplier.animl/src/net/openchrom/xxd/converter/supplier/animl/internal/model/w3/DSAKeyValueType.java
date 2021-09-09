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
package net.openchrom.xxd.converter.supplier.animl.internal.model.w3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSAKeyValueType", propOrder = {"p", "q", "g", "y", "j", "seed", "pgenCounter"})
public class DSAKeyValueType {

	@XmlElement(name = "P")
	protected byte[] p;
	@XmlElement(name = "Q")
	protected byte[] q;
	@XmlElement(name = "G")
	protected byte[] g;
	@XmlElement(name = "Y", required = true)
	protected byte[] y;
	@XmlElement(name = "J")
	protected byte[] j;
	@XmlElement(name = "Seed")
	protected byte[] seed;
	@XmlElement(name = "PgenCounter")
	protected byte[] pgenCounter;

	public byte[] getP() {

		return p;
	}

	public void setP(byte[] value) {

		this.p = value;
	}

	public byte[] getQ() {

		return q;
	}

	public void setQ(byte[] value) {

		this.q = value;
	}

	public byte[] getG() {

		return g;
	}

	public void setG(byte[] value) {

		this.g = value;
	}

	public byte[] getY() {

		return y;
	}

	public void setY(byte[] value) {

		this.y = value;
	}

	public byte[] getJ() {

		return j;
	}

	public void setJ(byte[] value) {

		this.j = value;
	}

	public byte[] getSeed() {

		return seed;
	}

	public void setSeed(byte[] value) {

		this.seed = value;
	}

	public byte[] getPgenCounter() {

		return pgenCounter;
	}

	public void setPgenCounter(byte[] value) {

		this.pgenCounter = value;
	}
}
