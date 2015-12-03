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

public enum LongUnit implements PrefixableLong {

    PICO {
        @Override
        public long toPicos(long units) {
            return units;
        }

        @Override
        public long toNanos(long units) {
            return units / (C1 / C0);
        }

        @Override
        public long toMicros(long units) {
            return units / (C2 / C0);
        }

        @Override
        public long toMillis(long units) {
            return units / (C3 / C0);
        }

        @Override
        public long toUnits(long units) {
            return units / (C4 / C0);
        }

        @Override
        public long toKilos(long units) {
            return units / (C5 / C0);
        }

        @Override
        public long toMegas(long units) {
            return units / (C6 / C0);
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C0);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C0);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toPicos(units);
        }
    },
    NANO {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C1 / C0);
        }

        @Override
        public long toNanos(long units) {
            return units;
        }

        @Override
        public long toMicros(long units) {
            return units / (C2 / C1);
        }

        @Override
        public long toMillis(long units) {
            return units / (C3 / C1);
        }

        @Override
        public long toUnits(long units) {
            return units / (C4 / C1);
        }

        @Override
        public long toKilos(long units) {
            return units / (C5 / C1);
        }

        @Override
        public long toMegas(long units) {
            return units / (C6 / C1);
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C1);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C1);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toNanos(units);
        }

    },
    MICRO {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C2 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C2 / C1);
        }

        @Override
        public long toMicros(long units) {
            return units;
        }

        @Override
        public long toMillis(long units) {
            return units / (C3 / C2);
        }

        @Override
        public long toUnits(long units) {
            return units / (C4 / C2);
        }

        @Override
        public long toKilos(long units) {
            return units / (C5 / C2);
        }

        @Override
        public long toMegas(long units) {
            return units / (C6 / C2);
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C2);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C2);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toMicros(units);
        }
    },
    MILLI {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C3 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C3 / C1);
        }

        @Override
        public long toMicros(long units) {
            return ArithmeticSavety.multiply(units, C3 / C2);
        }

        @Override
        public long toMillis(long units) {
            return units;
        }

        @Override
        public long toUnits(long units) {
            return units / (C4 / C3);
        }

        @Override
        public long toKilos(long units) {
            return units / (C5 / C3);
        }

        @Override
        public long toMegas(long units) {
            return units / (C6 / C3);
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C3);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C3);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toMillis(units);
        }
    },
    UNIT {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C4 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C4 / C1);
        }

        @Override
        public long toMicros(long units) {
            return ArithmeticSavety.multiply(units, C4 / C2);
        }

        @Override
        public long toMillis(long units) {
            return ArithmeticSavety.multiply(units, C4 / C3);
        }

        @Override
        public long toUnits(long units) {
            return units;
        }

        @Override
        public long toKilos(long units) {
            return units / (C5 / C4);
        }

        @Override
        public long toMegas(long units) {
            return units / (C6 / C4);
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C4);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C4);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toUnits(units);
        }
    },
    KILO {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C5 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C5 / C1);
        }

        @Override
        public long toMicros(long units) {
            return ArithmeticSavety.multiply(units, C5 / C2);
        }

        @Override
        public long toMillis(long units) {
            return ArithmeticSavety.multiply(units, C5 / C3);
        }

        @Override
        public long toUnits(long units) {
            return ArithmeticSavety.multiply(units, C5 / C4);
        }

        @Override
        public long toKilos(long units) {
            return units;
        }

        @Override
        public long toMegas(long units) {
            return units / (C6 / C5);
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C5);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C5);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toKilos(units);
        }
    },
    MEGA {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C6 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C6 / C1);
        }

        @Override
        public long toMicros(long units) {
            return ArithmeticSavety.multiply(units, C6 / C2);
        }

        @Override
        public long toMillis(long units) {
            return ArithmeticSavety.multiply(units, C6 / C3);
        }

        @Override
        public long toUnits(long units) {
            return ArithmeticSavety.multiply(units, C6 / C4);
        }

        @Override
        public long toKilos(long units) {
            return ArithmeticSavety.multiply(units, C6 / C5);
        }

        @Override
        public long toMegas(long units) {
            return units;
        }

        @Override
        public long toGigas(long units) {
            return units / (C7 / C6);
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C6);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toMegas(units);
        }
    },
    GIGA {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C7 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C7 / C1);
        }

        @Override
        public long toMicros(long units) {
            return ArithmeticSavety.multiply(units, C7 / C2);
        }

        @Override
        public long toMillis(long units) {
            return ArithmeticSavety.multiply(units, C7 / C3);
        }

        @Override
        public long toUnits(long units) {
            return ArithmeticSavety.multiply(units, C7 / C4);
        }

        @Override
        public long toKilos(long units) {
            return ArithmeticSavety.multiply(units, C7 / C5);
        }

        @Override
        public long toMegas(long units) {
            return ArithmeticSavety.multiply(units, C7 / C6);
        }

        @Override
        public long toGigas(long units) {
            return units;
        }

        @Override
        public long toTeras(long units) {
            return units / (C8 / C7);
        }

        @Override
        public long convert(long units, LongUnit unit) {
            return unit.toGigas(units);
        }
    },
    TERA {
        @Override
        public long toPicos(long units) {
            return ArithmeticSavety.multiply(units, C8 / C0);
        }

        @Override
        public long toNanos(long units) {
            return ArithmeticSavety.multiply(units, C8 / C1);
        }

        @Override
        public long toMicros(long units) {
            return ArithmeticSavety.multiply(units, C8 / C2);
        }

        @Override
        public long toMillis(long units) {
            return ArithmeticSavety.multiply(units, C8 / C3);
        }

        @Override
        public long toUnits(long units) {
            return ArithmeticSavety.multiply(units, C8 / C4);
        }

        @Override
        public long toKilos(long units) {
            return ArithmeticSavety.multiply(units, C8 / C5);
        }

        @Override
        public long toMegas(long units) {
            return ArithmeticSavety.multiply(units, C8 / C6);
        }

        @Override
        public long toGigas(long units) {
            return ArithmeticSavety.multiply(units, C8 / C7);
        }

        @Override
        public long toTeras(long units) {
            return units;
        }

        @Override
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
