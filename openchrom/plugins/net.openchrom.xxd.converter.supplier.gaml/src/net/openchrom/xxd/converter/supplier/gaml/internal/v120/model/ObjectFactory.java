/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.gaml.internal.v120.model;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

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

		return new JAXBElement<XMLGregorianCalendar>(_Collectdate_QNAME, XMLGregorianCalendar.class, null, value);
	}
}
