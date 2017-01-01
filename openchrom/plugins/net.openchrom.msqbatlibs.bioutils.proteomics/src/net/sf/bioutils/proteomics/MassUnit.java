/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics;

import net.sf.kerner.utils.math.DoubleUnit;
import net.sf.kerner.utils.math.PrefixableDouble;

public enum MassUnit implements PrefixableDouble {
	NANODALTON {

		protected DoubleUnit getUnit() {

			return DoubleUnit.NANO;
		}

		public double convert(double mass, MassUnit unit) {

			return unit.toNanos(mass);
		}
	},
	MICRODALTON {

		protected DoubleUnit getUnit() {

			return DoubleUnit.MICRO;
		}

		public double convert(double mass, MassUnit unit) {

			return unit.toMicros(mass);
		}
	},
	MILLIDALTON {

		protected DoubleUnit getUnit() {

			return DoubleUnit.MILLI;
		}

		public double convert(double mass, MassUnit unit) {

			return unit.toMillis(mass);
		}
	},
	DALTON {

		protected DoubleUnit getUnit() {

			return DoubleUnit.UNIT;
		}

		public double convert(double mass, MassUnit unit) {

			return unit.toUnits(mass);
		}
	},
	KILODALTON {

		protected DoubleUnit getUnit() {

			return DoubleUnit.KILO;
		}

		public double convert(double mass, MassUnit unit) {

			return unit.toKilos(mass);
		}
	};

	protected DoubleUnit getUnit() {

		throw new AbstractMethodError();
	}

	public double toPicos(double mass) {

		return getUnit().toPicos(mass);
	}

	public double toNanos(double mass) {

		return getUnit().toNanos(mass);
	}

	public double toMicros(double mass) {

		return getUnit().toMicros(mass);
	}

	public double toMillis(double mass) {

		return getUnit().toMillis(mass);
	}

	public double toUnits(double mass) {

		return getUnit().toUnits(mass);
	}

	public double toKilos(double mass) {

		return getUnit().toKilos(mass);
	}

	public double toMegas(double mass) {

		return getUnit().toMegas(mass);
	}

	public double toGigas(double mass) {

		return getUnit().toGigas(mass);
	}

	public double toTeras(double mass) {

		return getUnit().toTeras(mass);
	}

	public double convert(double mass, MassUnit unit) {

		throw new AbstractMethodError();
	}
}
