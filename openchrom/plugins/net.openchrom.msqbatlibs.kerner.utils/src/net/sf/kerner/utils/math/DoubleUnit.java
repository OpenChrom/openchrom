/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.math;

public enum DoubleUnit implements PrefixableDouble {

    PICO {
        @Override
        public double toPicos(double units) {
            return units;
        }

        @Override
        public double toNanos(double units) {
            return units / (C1 / C0);
        }

        @Override
        public double toMicros(double units) {
            return units / (C2 / C0);
        }

        @Override
        public double toMillis(double units) {
            return units / (C3 / C0);
        }

        @Override
        public double toUnits(double units) {
            return units / (C4 / C0);
        }

        @Override
        public double toKilos(double units) {
            return units / (C5 / C0);
        }

        @Override
        public double toMegas(double units) {
            return units / (C6 / C0);
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C0);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C0);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toPicos(units);
        }
    },
    NANO {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C1 / C0);
        }

        @Override
        public double toNanos(double units) {
            return units;
        }

        @Override
        public double toMicros(double units) {
            return units / (C2 / C1);
        }

        @Override
        public double toMillis(double units) {
            return units / (C3 / C1);
        }

        @Override
        public double toUnits(double units) {
            return units / (C4 / C1);
        }

        @Override
        public double toKilos(double units) {
            return units / (C5 / C1);
        }

        @Override
        public double toMegas(double units) {
            return units / (C6 / C1);
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C1);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C1);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toNanos(units);
        }

    },
    MICRO {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C2 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C2 / C1);
        }

        @Override
        public double toMicros(double units) {
            return units;
        }

        @Override
        public double toMillis(double units) {
            return units / (C3 / C2);
        }

        @Override
        public double toUnits(double units) {
            return units / (C4 / C2);
        }

        @Override
        public double toKilos(double units) {
            return units / (C5 / C2);
        }

        @Override
        public double toMegas(double units) {
            return units / (C6 / C2);
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C2);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C2);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toMicros(units);
        }
    },
    MILLI {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C3 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C3 / C1);
        }

        @Override
        public double toMicros(double units) {
            return ArithmeticSavety.multiply(units, C3 / C2);
        }

        @Override
        public double toMillis(double units) {
            return units;
        }

        @Override
        public double toUnits(double units) {
            return units / (C4 / C3);
        }

        @Override
        public double toKilos(double units) {
            return units / (C5 / C3);
        }

        @Override
        public double toMegas(double units) {
            return units / (C6 / C3);
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C3);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C3);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toMillis(units);
        }
    },
    UNIT {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C4 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C4 / C1);
        }

        @Override
        public double toMicros(double units) {
            return ArithmeticSavety.multiply(units, C4 / C2);
        }

        @Override
        public double toMillis(double units) {
            return ArithmeticSavety.multiply(units, C4 / C3);
        }

        @Override
        public double toUnits(double units) {
            return units;
        }

        @Override
        public double toKilos(double units) {
            return units / (C5 / C4);
        }

        @Override
        public double toMegas(double units) {
            return units / (C6 / C4);
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C4);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C4);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toUnits(units);
        }
    },
    KILO {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C5 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C5 / C1);
        }

        @Override
        public double toMicros(double units) {
            return ArithmeticSavety.multiply(units, C5 / C2);
        }

        @Override
        public double toMillis(double units) {
            return ArithmeticSavety.multiply(units, C5 / C3);
        }

        @Override
        public double toUnits(double units) {
            return ArithmeticSavety.multiply(units, C5 / C4);
        }

        @Override
        public double toKilos(double units) {
            return units;
        }

        @Override
        public double toMegas(double units) {
            return units / (C6 / C5);
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C5);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C5);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toKilos(units);
        }
    },
    MEGA {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C6 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C6 / C1);
        }

        @Override
        public double toMicros(double units) {
            return ArithmeticSavety.multiply(units, C6 / C2);
        }

        @Override
        public double toMillis(double units) {
            return ArithmeticSavety.multiply(units, C6 / C3);
        }

        @Override
        public double toUnits(double units) {
            return ArithmeticSavety.multiply(units, C6 / C4);
        }

        @Override
        public double toKilos(double units) {
            return ArithmeticSavety.multiply(units, C6 / C5);
        }

        @Override
        public double toMegas(double units) {
            return units;
        }

        @Override
        public double toGigas(double units) {
            return units / (C7 / C6);
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C6);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toMegas(units);
        }
    },
    GIGA {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C7 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C7 / C1);
        }

        @Override
        public double toMicros(double units) {
            return ArithmeticSavety.multiply(units, C7 / C2);
        }

        @Override
        public double toMillis(double units) {
            return ArithmeticSavety.multiply(units, C7 / C3);
        }

        @Override
        public double toUnits(double units) {
            return ArithmeticSavety.multiply(units, C7 / C4);
        }

        @Override
        public double toKilos(double units) {
            return ArithmeticSavety.multiply(units, C7 / C5);
        }

        @Override
        public double toMegas(double units) {
            return ArithmeticSavety.multiply(units, C7 / C6);
        }

        @Override
        public double toGigas(double units) {
            return units;
        }

        @Override
        public double toTeras(double units) {
            return units / (C8 / C7);
        }

        @Override
        public double convert(double units, DoubleUnit unit) {
            return unit.toGigas(units);
        }
    },
    TERA {
        @Override
        public double toPicos(double units) {
            return ArithmeticSavety.multiply(units, C8 / C0);
        }

        @Override
        public double toNanos(double units) {
            return ArithmeticSavety.multiply(units, C8 / C1);
        }

        @Override
        public double toMicros(double units) {
            return ArithmeticSavety.multiply(units, C8 / C2);
        }

        @Override
        public double toMillis(double units) {
            return ArithmeticSavety.multiply(units, C8 / C3);
        }

        @Override
        public double toUnits(double units) {
            return ArithmeticSavety.multiply(units, C8 / C4);
        }

        @Override
        public double toKilos(double units) {
            return ArithmeticSavety.multiply(units, C8 / C5);
        }

        @Override
        public double toMegas(double units) {
            return ArithmeticSavety.multiply(units, C8 / C6);
        }

        @Override
        public double toGigas(double units) {
            return ArithmeticSavety.multiply(units, C8 / C7);
        }

        @Override
        public double toTeras(double units) {
            return units;
        }

        @Override
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
