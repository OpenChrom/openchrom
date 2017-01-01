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
package net.sf.kerner.utils.math;

public enum LongUnit implements PrefixableLong {
	PICO {

		public long toPicos(long units) {

			return units;
		}

		public long toNanos(long units) {

			return units / (C1 / C0);
		}

		public long toMicros(long units) {

			return units / (C2 / C0);
		}

		public long toMillis(long units) {

			return units / (C3 / C0);
		}

		public long toUnits(long units) {

			return units / (C4 / C0);
		}

		public long toKilos(long units) {

			return units / (C5 / C0);
		}

		public long toMegas(long units) {

			return units / (C6 / C0);
		}

		public long toGigas(long units) {

			return units / (C7 / C0);
		}

		public long toTeras(long units) {

			return units / (C8 / C0);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toPicos(units);
		}
	},
	NANO {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C1 / C0);
		}

		public long toNanos(long units) {

			return units;
		}

		public long toMicros(long units) {

			return units / (C2 / C1);
		}

		public long toMillis(long units) {

			return units / (C3 / C1);
		}

		public long toUnits(long units) {

			return units / (C4 / C1);
		}

		public long toKilos(long units) {

			return units / (C5 / C1);
		}

		public long toMegas(long units) {

			return units / (C6 / C1);
		}

		public long toGigas(long units) {

			return units / (C7 / C1);
		}

		public long toTeras(long units) {

			return units / (C8 / C1);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toNanos(units);
		}
	},
	MICRO {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C2 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C2 / C1);
		}

		public long toMicros(long units) {

			return units;
		}

		public long toMillis(long units) {

			return units / (C3 / C2);
		}

		public long toUnits(long units) {

			return units / (C4 / C2);
		}

		public long toKilos(long units) {

			return units / (C5 / C2);
		}

		public long toMegas(long units) {

			return units / (C6 / C2);
		}

		public long toGigas(long units) {

			return units / (C7 / C2);
		}

		public long toTeras(long units) {

			return units / (C8 / C2);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toMicros(units);
		}
	},
	MILLI {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C3 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C3 / C1);
		}

		public long toMicros(long units) {

			return ArithmeticSavety.multiply(units, C3 / C2);
		}

		public long toMillis(long units) {

			return units;
		}

		public long toUnits(long units) {

			return units / (C4 / C3);
		}

		public long toKilos(long units) {

			return units / (C5 / C3);
		}

		public long toMegas(long units) {

			return units / (C6 / C3);
		}

		public long toGigas(long units) {

			return units / (C7 / C3);
		}

		public long toTeras(long units) {

			return units / (C8 / C3);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toMillis(units);
		}
	},
	UNIT {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C4 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C4 / C1);
		}

		public long toMicros(long units) {

			return ArithmeticSavety.multiply(units, C4 / C2);
		}

		public long toMillis(long units) {

			return ArithmeticSavety.multiply(units, C4 / C3);
		}

		public long toUnits(long units) {

			return units;
		}

		public long toKilos(long units) {

			return units / (C5 / C4);
		}

		public long toMegas(long units) {

			return units / (C6 / C4);
		}

		public long toGigas(long units) {

			return units / (C7 / C4);
		}

		public long toTeras(long units) {

			return units / (C8 / C4);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toUnits(units);
		}
	},
	KILO {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C5 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C5 / C1);
		}

		public long toMicros(long units) {

			return ArithmeticSavety.multiply(units, C5 / C2);
		}

		public long toMillis(long units) {

			return ArithmeticSavety.multiply(units, C5 / C3);
		}

		public long toUnits(long units) {

			return ArithmeticSavety.multiply(units, C5 / C4);
		}

		public long toKilos(long units) {

			return units;
		}

		public long toMegas(long units) {

			return units / (C6 / C5);
		}

		public long toGigas(long units) {

			return units / (C7 / C5);
		}

		public long toTeras(long units) {

			return units / (C8 / C5);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toKilos(units);
		}
	},
	MEGA {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C6 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C6 / C1);
		}

		public long toMicros(long units) {

			return ArithmeticSavety.multiply(units, C6 / C2);
		}

		public long toMillis(long units) {

			return ArithmeticSavety.multiply(units, C6 / C3);
		}

		public long toUnits(long units) {

			return ArithmeticSavety.multiply(units, C6 / C4);
		}

		public long toKilos(long units) {

			return ArithmeticSavety.multiply(units, C6 / C5);
		}

		public long toMegas(long units) {

			return units;
		}

		public long toGigas(long units) {

			return units / (C7 / C6);
		}

		public long toTeras(long units) {

			return units / (C8 / C6);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toMegas(units);
		}
	},
	GIGA {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C7 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C7 / C1);
		}

		public long toMicros(long units) {

			return ArithmeticSavety.multiply(units, C7 / C2);
		}

		public long toMillis(long units) {

			return ArithmeticSavety.multiply(units, C7 / C3);
		}

		public long toUnits(long units) {

			return ArithmeticSavety.multiply(units, C7 / C4);
		}

		public long toKilos(long units) {

			return ArithmeticSavety.multiply(units, C7 / C5);
		}

		public long toMegas(long units) {

			return ArithmeticSavety.multiply(units, C7 / C6);
		}

		public long toGigas(long units) {

			return units;
		}

		public long toTeras(long units) {

			return units / (C8 / C7);
		}

		public long convert(long units, LongUnit unit) {

			return unit.toGigas(units);
		}
	},
	TERA {

		public long toPicos(long units) {

			return ArithmeticSavety.multiply(units, C8 / C0);
		}

		public long toNanos(long units) {

			return ArithmeticSavety.multiply(units, C8 / C1);
		}

		public long toMicros(long units) {

			return ArithmeticSavety.multiply(units, C8 / C2);
		}

		public long toMillis(long units) {

			return ArithmeticSavety.multiply(units, C8 / C3);
		}

		public long toUnits(long units) {

			return ArithmeticSavety.multiply(units, C8 / C4);
		}

		public long toKilos(long units) {

			return ArithmeticSavety.multiply(units, C8 / C5);
		}

		public long toMegas(long units) {

			return ArithmeticSavety.multiply(units, C8 / C6);
		}

		public long toGigas(long units) {

			return ArithmeticSavety.multiply(units, C8 / C7);
		}

		public long toTeras(long units) {

			return units;
		}

		public long convert(long units, LongUnit unit) {

			return unit.toTeras(units);
		}
	};

	static final long C0 = 1;
	static final long C1 = C0 * 1000;
	static final long C2 = C1 * 1000;
	static final long C3 = C2 * 1000;
	static final long C4 = C3 * 1000;
	static final long C5 = C4 * 1000;
	static final long C6 = C5 * 1000;
	static final long C7 = C6 * 1000;
	static final long C8 = C7 * 1000;

	public long toPicos(long units) {

		throw new AbstractMethodError();
	}

	public long toNanos(long units) {

		throw new AbstractMethodError();
	}

	/**
	 * Equivalent to UNIT.convert(units, this).
	 * 
	 * @param units
	 *            the units to convert
	 * @return the converted units
	 */
	public long toMicros(long units) {

		throw new AbstractMethodError();
	}

	public long toMillis(long units) {

		throw new AbstractMethodError();
	}

	public long toUnits(long units) {

		throw new AbstractMethodError();
	}

	public long toKilos(long units) {

		throw new AbstractMethodError();
	}

	public long toMegas(long units) {

		throw new AbstractMethodError();
	}

	public long toGigas(long units) {

		throw new AbstractMethodError();
	}

	public long toTeras(long units) {

		throw new AbstractMethodError();
	}

	public long convert(long units, LongUnit unit) {

		throw new AbstractMethodError();
	}
}
