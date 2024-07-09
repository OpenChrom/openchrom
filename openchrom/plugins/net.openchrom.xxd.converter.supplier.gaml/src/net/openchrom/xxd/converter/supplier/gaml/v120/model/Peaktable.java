/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.gaml.v120.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"parameter", "peak"})
@XmlRootElement(name = "peaktable")
public class Peaktable {

	protected List<Parameter> parameter;
	protected List<Peaktable.Peak> peak;
	@XmlAttribute(name = "name")
	protected String name;

	public List<Parameter> getParameter() {

		if(parameter == null) {
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public List<Peaktable.Peak> getPeak() {

		if(peak == null) {
			peak = new ArrayList<Peaktable.Peak>();
		}
		return this.peak;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"parameter", "peakXvalue", "peakYvalue", "baseline"})
	public static class Peak {

		protected List<Parameter> parameter;
		protected double peakXvalue;
		protected double peakYvalue;
		protected Peaktable.Peak.Baseline baseline;
		@XmlAttribute(name = "number", required = true)
		protected BigInteger number;
		@XmlAttribute(name = "group")
		protected String group;
		@XmlAttribute(name = "name")
		protected String name;

		public List<Parameter> getParameter() {

			if(parameter == null) {
				parameter = new ArrayList<Parameter>();
			}
			return this.parameter;
		}

		public double getPeakXvalue() {

			return peakXvalue;
		}

		public void setPeakXvalue(double value) {

			this.peakXvalue = value;
		}

		public double getPeakYvalue() {

			return peakYvalue;
		}

		public void setPeakYvalue(double value) {

			this.peakYvalue = value;
		}

		public Peaktable.Peak.Baseline getBaseline() {

			return baseline;
		}

		public void setBaseline(Peaktable.Peak.Baseline value) {

			this.baseline = value;
		}

		public BigInteger getNumber() {

			return number;
		}

		public void setNumber(BigInteger value) {

			this.number = value;
		}

		public String getGroup() {

			return group;
		}

		public void setGroup(String value) {

			this.group = value;
		}

		public String getName() {

			return name;
		}

		public void setName(String value) {

			this.name = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {"parameter", "startXvalue", "startYvalue", "endXvalue", "endYvalue", "basecurve"})
		public static class Baseline {

			protected List<Parameter> parameter;
			protected double startXvalue;
			protected double startYvalue;
			protected double endXvalue;
			protected double endYvalue;
			protected Peaktable.Peak.Baseline.Basecurve basecurve;

			public List<Parameter> getParameter() {

				if(parameter == null) {
					parameter = new ArrayList<Parameter>();
				}
				return this.parameter;
			}

			public double getStartXvalue() {

				return startXvalue;
			}

			public void setStartXvalue(double value) {

				this.startXvalue = value;
			}

			public double getStartYvalue() {

				return startYvalue;
			}

			public void setStartYvalue(double value) {

				this.startYvalue = value;
			}

			public double getEndXvalue() {

				return endXvalue;
			}

			public void setEndXvalue(double value) {

				this.endXvalue = value;
			}

			public double getEndYvalue() {

				return endYvalue;
			}

			public void setEndYvalue(double value) {

				this.endYvalue = value;
			}

			public Peaktable.Peak.Baseline.Basecurve getBasecurve() {

				return basecurve;
			}

			public void setBasecurve(Peaktable.Peak.Baseline.Basecurve value) {

				this.basecurve = value;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {"baseXdata", "baseYdata"})
			public static class Basecurve {

				@XmlElement(required = true)
				protected Peaktable.Peak.Baseline.Basecurve.BaseXdata baseXdata;
				@XmlElement(required = true)
				protected Peaktable.Peak.Baseline.Basecurve.BaseYdata baseYdata;

				public Peaktable.Peak.Baseline.Basecurve.BaseXdata getBaseXdata() {

					return baseXdata;
				}

				public void setBaseXdata(Peaktable.Peak.Baseline.Basecurve.BaseXdata value) {

					this.baseXdata = value;
				}

				public Peaktable.Peak.Baseline.Basecurve.BaseYdata getBaseYdata() {

					return baseYdata;
				}

				public void setBaseYdata(Peaktable.Peak.Baseline.Basecurve.BaseYdata value) {

					this.baseYdata = value;
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {"values"})
				public static class BaseXdata {

					@XmlElement(required = true)
					protected Values values;

					public Values getValues() {

						return values;
					}

					public void setValues(Values value) {

						this.values = value;
					}
				}

				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = {"values"})
				public static class BaseYdata {

					@XmlElement(required = true)
					protected Values values;

					public Values getValues() {

						return values;
					}

					public void setValues(Values value) {

						this.values = value;
					}
				}
			}
		}
	}
}
