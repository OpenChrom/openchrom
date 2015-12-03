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
package net.sf.jranges.range;

import net.sf.jranges.range.integerrange.BigRange;
import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.jranges.range.longrange.RangeLong;

/**
 *
 * A {@code Range} is a collection of sequential positions and an interval.<br>
 *
 * It is defined by the following attributes:
 * <ol>
 * <li>
 * The first position, inclusively</li>
 * <li>
 * The last position, inclusively</li>
 * <li>
 * An interval</li>
 * </ol>
 * <p>
 * {@code start} and {@code stop} must both be a multiple of {@code interval}
 * </p>
 * <p>
 * All positions that match
 * <ol>
 * <li>
 * {@code start <= position <= stop}</li>
 * <li>
 * {@code (start + (n * interval) == position) && (stop + (n * interval) == position)}
 * </li>
 * </ol>
 * are also considered to be a member of this {@code Range}.
 * </p>
 * <p>
 * A {@code Range} can be <i>shifted</i>, which will result in an equivalent
 * modification of start and stop position in the <i>same</i> direction.
 * </p>
 * <p>
 * A {@code Range} can also be <i>expanded</i>, which will result in an
 * equivalent modification of start and stop position in the <i>opposite</i>
 * direction.
 * </p>
 * <p>
 * Mathematically, a {@code Range} is defined as follows:<br>
 * given
 * <ol>
 * <li>{@code a = start}</li>
 * <li>{@code i = interval}</li>
 * <li>{@code s = stop}</li>
 * </ol>
 * A {@code Range} can be denoted as the following sequence:<br>
 * {@code (a,a+i,...,s-i,s)}
 *
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 *
 * @see RangeInteger
 * @see RangeLong
 * @see BigRange
 *
 */
public interface Range {

}
