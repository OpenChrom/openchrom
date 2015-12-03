/**
 * *****************************************************************************
 * Copyright 2011-2015 Alexander Kerner. All rights reserved.
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
 *****************************************************************************
 */
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
    @Override
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
