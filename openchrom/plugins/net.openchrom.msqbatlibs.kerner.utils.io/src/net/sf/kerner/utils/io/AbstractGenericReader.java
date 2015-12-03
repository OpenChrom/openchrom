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
package net.sf.kerner.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@code AbstractGenericReader} is a prototye implementation for {@link net.sf.kerner.utils.io.GenericReader
 * GenericReader}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-26
 */
public abstract class AbstractGenericReader<T> implements GenericReader<T> {

    // Implement //

    /**
	 * 
	 */
    public T read(File file) throws IOException {
        return read(new FileInputStream(file));
    }

    /**
	 * 
	 */
    public T read(InputStream stream) throws IOException {
        return read(UtilIO.inputStreamToReader(stream));
    }

}
