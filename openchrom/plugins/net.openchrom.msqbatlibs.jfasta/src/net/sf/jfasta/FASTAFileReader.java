/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.jfasta;

import java.io.IOException;

import net.sf.jfasta.impl.FASTAElementIterator;
import net.sf.kerner.utils.io.buffered.IOIterable;

/**
 * A {@code FASTAFileReader} is used to read a FASTA file.
 * <p>
 * last reviewed: 2013-04-29
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-06
 * 
 */
public interface FASTAFileReader extends IOIterable<FASTAElement> {

	/**
	 * Closes this reader.
	 * 
	 * @see java.io.Reader#close()
	 * 
	 */
	void close();

	FASTAElementIterator getIterator() throws IOException;

	/**
	 * 
	 * Reads all {@link FASTAElement}s at once in one {@link FASTAFile}.
	 * <p>
	 * Use this with care, since FASTA files can be huge!
	 * </p>
	 * 
	 * @return {@link FASTAFile FASTAFile} that has been read
	 * 
	 * @throws IOException
	 */
	FASTAFile read() throws IOException;
}
