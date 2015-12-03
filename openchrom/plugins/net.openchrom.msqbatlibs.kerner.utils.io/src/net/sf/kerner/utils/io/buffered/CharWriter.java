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
package net.sf.kerner.utils.io.buffered;

import java.io.IOException;

/**
 * A {@code CharWriter} provides methods for writing characters.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 */
public interface CharWriter {

    /**
     * Write the next single char.
     * 
     * @param c
     *            char to write
     * @throws IOException
     *             if writing fails
     */
    void write(char c) throws IOException;

    /**
     * Write the next bunch of chars
     * 
     * @param chars
     *            char array to write
     * @throws IOException
     *             if writing fails
     */
    void write(char[] chars) throws IOException;

}
