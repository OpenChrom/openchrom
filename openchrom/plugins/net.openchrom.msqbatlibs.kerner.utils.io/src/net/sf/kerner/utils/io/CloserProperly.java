/**********************************************************************
Copyright (c) 2015 Alexander Kerner. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ***********************************************************************/

package net.sf.kerner.utils.io;

import java.io.Closeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * A {@code CloserPropery} is used to close resources properly and without the
 * need for exception handling.
 *
 * <p>
 * <b>Example:</b><br>
 * </p>
 *
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 * <p>
 * <b>Threading:</b> Fully thread save.
 * </p>
 *
 * <p>
 * last reviewed: 2015-08-22
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class CloserProperly {

    private final static Logger log = LoggerFactory.getLogger(CloserProperly.class);

    /**
     * Closes {@code closable} if {@code closable != null} and instanceof
     * {@link Closable}. Exceptions are catched and logged (level WARN).
     *
     * @param closable
     *            object to close
     */
    public void closeProperly(final Object closable) {
        if (closable != null && closable instanceof Closeable) {
            Closeable c = (Closeable) closable;
            try {
                c.close();
            } catch (final Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn(e.getLocalizedMessage(), e);
                }
            }
        }
    }
}
