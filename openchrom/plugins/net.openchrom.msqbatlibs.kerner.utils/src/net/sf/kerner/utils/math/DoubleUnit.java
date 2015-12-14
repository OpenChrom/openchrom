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
package net.sf.kerner.utils.math;

public enum DoubleUnit implements PrefixableDouble {
	PICO {

		
		public double toPicos(double units) {

			return units;
		}

		
		public double toNanos(double units) {

			return units / (C1 / C0);
		}

		
		public double toMicros(double units) {

			return units / (C2 / C0);
		}

		
		public double toMillis(double units) {

			return units / (C3 / C0);
		}

		
		public double toUnits(double units) {

			return units / (C4 / C0);
		}

		
		public double toKilos(double units) {

			return units / (C5 / C0);
		}

		
		public double toMegas(double units) {

			return units / (C6 / C0);
		}

		
		public double toGigas(double units) {

			return units / (C7 / C0);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C0);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toPicos(units);
		}
	},
	NANO {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C1 / C0);
		}

		
		public double toNanos(double units) {

			return units;
		}

		
		public double toMicros(double units) {

			return units / (C2 / C1);
		}

		
		public double toMillis(double units) {

			return units / (C3 / C1);
		}

		
		public double toUnits(double units) {

			return units / (C4 / C1);
		}

		
		public double toKilos(double units) {

			return units / (C5 / C1);
		}

		
		public double toMegas(double units) {

			return units / (C6 / C1);
		}

		
		public double toGigas(double units) {

			return units / (C7 / C1);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C1);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toNanos(units);
		}
	},
	MICRO {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C2 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C2 / C1);
		}

		
		public double toMicros(double units) {

			return units;
		}

		
		public double toMillis(double units) {

			return units / (C3 / C2);
		}

		
		public double toUnits(double units) {

			return units / (C4 / C2);
		}

		
		public double toKilos(double units) {

			return units / (C5 / C2);
		}

		
		public double toMegas(double units) {

			return units / (C6 / C2);
		}

		
		public double toGigas(double units) {

			return units / (C7 / C2);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C2);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toMicros(units);
		}
	},
	MILLI {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C3 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C3 / C1);
		}

		
		public double toMicros(double units) {

			return ArithmeticSavety.multiply(units, C3 / C2);
		}

		
		public double toMillis(double units) {

			return units;
		}

		
		public double toUnits(double units) {

			return units / (C4 / C3);
		}

		
		public double toKilos(double units) {

			return units / (C5 / C3);
		}

		
		public double toMegas(double units) {

			return units / (C6 / C3);
		}

		
		public double toGigas(double units) {

			return units / (C7 / C3);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C3);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toMillis(units);
		}
	},
	UNIT {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C4 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C4 / C1);
		}

		
		public double toMicros(double units) {

			return ArithmeticSavety.multiply(units, C4 / C2);
		}

		
		public double toMillis(double units) {

			return ArithmeticSavety.multiply(units, C4 / C3);
		}

		
		public double toUnits(double units) {

			return units;
		}

		
		public double toKilos(double units) {

			return units / (C5 / C4);
		}

		
		public double toMegas(double units) {

			return units / (C6 / C4);
		}

		
		public double toGigas(double units) {

			return units / (C7 / C4);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C4);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toUnits(units);
		}
	},
	KILO {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C5 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C5 / C1);
		}

		
		public double toMicros(double units) {

			return ArithmeticSavety.multiply(units, C5 / C2);
		}

		
		public double toMillis(double units) {

			return ArithmeticSavety.multiply(units, C5 / C3);
		}

		
		public double toUnits(double units) {

			return ArithmeticSavety.multiply(units, C5 / C4);
		}

		
		public double toKilos(double units) {

			return units;
		}

		
		public double toMegas(double units) {

			return units / (C6 / C5);
		}

		
		public double toGigas(double units) {

			return units / (C7 / C5);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C5);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toKilos(units);
		}
	},
	MEGA {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C6 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C6 / C1);
		}

		
		public double toMicros(double units) {

			return ArithmeticSavety.multiply(units, C6 / C2);
		}

		
		public double toMillis(double units) {

			return ArithmeticSavety.multiply(units, C6 / C3);
		}

		
		public double toUnits(double units) {

			return ArithmeticSavety.multiply(units, C6 / C4);
		}

		
		public double toKilos(double units) {

			return ArithmeticSavety.multiply(units, C6 / C5);
		}

		
		public double toMegas(double units) {

			return units;
		}

		
		public double toGigas(double units) {

			return units / (C7 / C6);
		}

		
		public double toTeras(double units) {

			return units / (C8 / C6);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toMegas(units);
		}
	},
	GIGA {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C7 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C7 / C1);
		}

		
		public double toMicros(double units) {

			return ArithmeticSavety.multiply(units, C7 / C2);
		}

		
		public double toMillis(double units) {

			return ArithmeticSavety.multiply(units, C7 / C3);
		}

		
		public double toUnits(double units) {

			return ArithmeticSavety.multiply(units, C7 / C4);
		}

		
		public double toKilos(double units) {

			return ArithmeticSavety.multiply(units, C7 / C5);
		}

		
		public double toMegas(double units) {

			return ArithmeticSavety.multiply(units, C7 / C6);
		}

		
		public double toGigas(double units) {

			return units;
		}

		
		public double toTeras(double units) {

			return units / (C8 / C7);
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toGigas(units);
		}
	},
	TERA {

		
		public double toPicos(double units) {

			return ArithmeticSavety.multiply(units, C8 / C0);
		}

		
		public double toNanos(double units) {

			return ArithmeticSavety.multiply(units, C8 / C1);
		}

		
		public double toMicros(double units) {

			return ArithmeticSavety.multiply(units, C8 / C2);
		}

		
		public double toMillis(double units) {

			return ArithmeticSavety.multiply(units, C8 / C3);
		}

		
		public double toUnits(double units) {

			return ArithmeticSavety.multiply(units, C8 / C4);
		}

		
		public double toKilos(double units) {

			return ArithmeticSavety.multiply(units, C8 / C5);
		}

		
		public double toMegas(double units) {

			return ArithmeticSavety.multiply(units, C8 / C6);
		}

		
		public double toGigas(double units) {

			return ArithmeticSavety.multiply(units, C8 / C7);
		}

		
		public double toTeras(double units) {

			return units;
		}

		
		public double convert(double units, DoubleUnit unit) {

			return unit.toTeras(units);
		}
	};

	static final double C0 = 1;
	static final double C1 = C0 * 1000;
	static final double C2 = C1 * 1000;
	static final double C3 = C2 * 1000;
	static final double C4 = C3 * 1000;
	static final double C5 = C4 * 1000;
	static final double C6 = C5 * 1000;
	static final double C7 = C6 * 1000;
	static final double C8 = C7 * 1000;

	public double toPicos(double units) {

		throw new AbstractMethodError();
	}

	public double toNanos(double units) {

		throw new AbstractMethodError();
	}

	/**
	 * Equivalent to UNIT.convert(units, this).
	 * 
	 * @param units
	 *            the units to convert
	 * @return the converted units
	 */
	public double toMicros(double units) {

		throw new AbstractMethodError();
	}

	public double toMillis(double units) {

		throw new AbstractMethodError();
	}

	public double toUnits(double units) {

		throw new AbstractMethodError();
	}

	public double toKilos(double units) {

		throw new AbstractMethodError();
	}

	public double toMegas(double units) {

		throw new AbstractMethodError();
	}

	public double toGigas(double units) {

		throw new AbstractMethodError();
	}

	public double toTeras(double units) {

		throw new AbstractMethodError();
	}

	public double convert(double units, DoubleUnit unit) {

		throw new AbstractMethodError();
	}
}
