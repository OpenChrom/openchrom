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
package net.sf.bioutils.proteomics.sample;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import net.sf.bioutils.proteomics.User;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.Cloneable;
import net.sf.kerner.utils.collections.map.MapList;

/**
 *
 * TODO description
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 * <p>
 * last reviewed: 2015-05-01
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Sample extends Cloneable<Sample> {

	long getId();

	/**
	 * Creates a clone of this {@code Sample}.
	 */
	Sample clone();

	/**
	 *
	 * Clones sample, using new given name.
	 *
	 * @see #clone()
	 */
	Sample clone(String newName);

	Sample cloneWOPeaks(String newName);

	@Deprecated
	ReadWriteLock getLock();

	String getName();

	String getNameBase();

	List<Peak> getPeaks();

	MapList<String, Object> getProperties();

	/**
	 *
	 * @return the number of {@link Peak peaks} in this {@code Sample}.
	 */
	int getSize();

	User getUser();

	void setPeaks(List<Peak> peaks);

	RawSample getRawSample();

	void setProperties(MapList<String, Object> properties);
}
