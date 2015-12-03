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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractGenericReader2<T> extends AbstractGenericReader<T> implements GenericReader<T>,
        GenericCollectionReader<T> {

    protected final AbstractGenericCollectionReader<T> colReader = new AbstractGenericCollectionReader<T>() {

        public Collection<T> readAll(Reader reader) throws IOException {
            final Collection<T> result = new ArrayList<T>();
            T t = null;
            while ((t = read(reader)) != null) {
                result.add(t);
            }
            return result;
        }
    };

    public Collection<T> readAll(File file) throws IOException {
        return colReader.readAll(file);
    }

    public Collection<T> readAll(Reader reader) throws IOException {
        return colReader.readAll(reader);
    }

    public Collection<T> readAll(InputStream stream) throws IOException {
        return colReader.readAll(stream);
    }
}
