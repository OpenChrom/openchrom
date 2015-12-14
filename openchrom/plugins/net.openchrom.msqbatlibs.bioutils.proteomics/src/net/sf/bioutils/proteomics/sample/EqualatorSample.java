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
package net.sf.bioutils.proteomics.sample;

import net.sf.kerner.utils.equal.Equalator;
import net.sf.kerner.utils.pair.Pair;

public class EqualatorSample implements Equalator<Sample> {

	
	public boolean areEqual(final Sample element, final Object obj) {

		if(element == obj)
			return true;
		if(element == null)
			return false;
		if(obj == null)
			return false;
		if(!(obj instanceof Sample))
			return false;
		final Sample other = (Sample)obj;
		if(element.getName() == null) {
			if(other.getName() != null)
				return false;
		} else if(!element.getName().equals(other.getName()))
			return false;
		if(element.getNameBase() == null) {
			if(other.getNameBase() != null)
				return false;
		} else if(!element.getNameBase().equals(other.getNameBase()))
			return false;
		if(element.getUser() == null) {
			if(other.getUser() != null)
				return false;
		} else if(!element.getUser().equals(other.getUser()))
			return false;
		// if (element.getProperties() == null) {
		// if (other.getProperties() != null)
		// return false;
		// } else if (!element.getProperties().equals(other.getProperties()))
		// return false;
		return true;
	}

	
	public Boolean transform(final Pair<Sample, Object> element) {

		return areEqual(element.getFirst(), element.getSecond());
	}
}
