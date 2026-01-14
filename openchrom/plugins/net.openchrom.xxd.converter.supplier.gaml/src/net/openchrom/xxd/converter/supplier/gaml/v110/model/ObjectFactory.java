/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.gaml.v110.model;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	private final static QName _Collectdate_QNAME = new QName("", "collectdate");

	public ObjectFactory() {

	}

	public Peaktable createPeaktable() {

		return new Peaktable();
	}

	public GAML createGAML() {

		return new GAML();
	}

	public Peaktable.Peak createPeaktablePeak() {

		return new Peaktable.Peak();
	}

	public Peaktable.Peak.Baseline createPeaktablePeakBaseline() {

		return new Peaktable.Peak.Baseline();
	}

	public Peaktable.Peak.Baseline.Basecurve createPeaktablePeakBaselineBasecurve() {

		return new Peaktable.Peak.Baseline.Basecurve();
	}

	public Trace createTrace() {

		return new Trace();
	}

	public Parameter createParameter() {

		return new Parameter();
	}

	public Coordinates createCoordinates() {

		return new Coordinates();
	}

	public Link createLink() {

		return new Link();
	}

	public Values createValues() {

		return new Values();
	}

	public Xdata createXdata() {

		return new Xdata();
	}

	public AltXdata createAltXdata() {

		return new AltXdata();
	}

	public Ydata createYdata() {

		return new Ydata();
	}

	public Experiment createExperiment() {

		return new Experiment();
	}

	public GAML.Integrity createGAMLIntegrity() {

		return new GAML.Integrity();
	}

	public Peaktable.Peak.Baseline.Basecurve.BaseXdata createPeaktablePeakBaselineBasecurveBaseXdata() {

		return new Peaktable.Peak.Baseline.Basecurve.BaseXdata();
	}

	public Peaktable.Peak.Baseline.Basecurve.BaseYdata createPeaktablePeakBaselineBasecurveBaseYdata() {

		return new Peaktable.Peak.Baseline.Basecurve.BaseYdata();
	}

	@XmlElementDecl(namespace = "", name = "collectdate")
	public JAXBElement<XMLGregorianCalendar> createCollectdate(XMLGregorianCalendar value) {

		return new JAXBElement<>(_Collectdate_QNAME, XMLGregorianCalendar.class, null, value);
	}
}
