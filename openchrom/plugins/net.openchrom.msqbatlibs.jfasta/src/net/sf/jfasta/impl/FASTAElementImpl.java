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
package net.sf.jfasta.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
import net.sf.jfasta.Utils;
import net.sf.kerner.utils.io.UtilIO;

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
 * <p>
 * last reviewed: 2013-04-29
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-04-29
 * 
 */
public class FASTAElementImpl implements FASTAElement {

	/**
	 * This {@code  FASTAElementImpl} header string.
	 */
	protected final String header;
	/**
	 * This {@code FASTAElementImpl} line length.
	 */
	protected volatile int lineLength = FASTAFile.DEFAULT_LINE_LENGTH;
	/**
	 * This {@code FASTAElementImpl} meta infos.
	 */
	protected final Map<String, Serializable> map = new LinkedHashMap<String, Serializable>();
	protected final StringBuilder sequence;

	public FASTAElementImpl(final String header, final char[] sequence) {

		this.header = header;
		this.sequence = new StringBuilder(String.copyValueOf(sequence));
	}

	public FASTAElementImpl(final String header, final char[] sequence, final Map<String, Serializable> metainfo) {

		this.header = header;
		this.sequence = new StringBuilder(String.copyValueOf(sequence));
		map.putAll(metainfo);
	}

	public FASTAElementImpl(final String header, final String sequence) {

		this.header = header;
		this.sequence = new StringBuilder(sequence);
	}

	public FASTAElementImpl(final String header, final String sequence, final Map<String, Serializable> metainfo) {

		this.header = header;
		this.sequence = new StringBuilder(sequence);
		map.putAll(metainfo);
	}

	public FASTAElementImpl(final String header, final StringBuilder sequence) {

		this.header = header;
		this.sequence = sequence;
	}

	public FASTAElementImpl(final String header, final StringBuilder sequence, final Map<String, Serializable> metainfo) {

		this.header = header;
		this.sequence = sequence;
		map.putAll(metainfo);
	}

	public void clearMethaInfo() {

		map.clear();
	}

	@Override
	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof FASTAElementImpl))
			return false;
		final FASTAElementImpl other = (FASTAElementImpl)obj;
		if(header == null) {
			if(other.header != null)
				return false;
		} else if(!header.equals(other.header))
			return false;
		return true;
	}

	public String getHeader() {

		return header;
	}

	public int getLineLength() {

		return lineLength;
	}

	public Map<String, Serializable> getMethaInfo() {

		return new LinkedHashMap<String, Serializable>(map);
	}

	public Serializable getMethaInfo(final String ident) {

		if(ident == null)
			throw new NullPointerException();
		return map.get(ident);
	}

	public String getSequence() {

		return sequence.toString();
	}

	public int getSequenceLength() {

		return sequence.length();
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}

	public void setLineLength(final int length) {

		if(length < 1)
			throw new NumberFormatException();
		lineLength = length;
	}

	public void setMethaInfo(final Map<String, Serializable> map) {

		if(map == null)
			throw new NullPointerException();
		this.map.clear();
		this.map.putAll(map);
	}

	public void setMethaInfo(final String ident, final Serializable info) {

		if(ident == null || info == null)
			throw new NullPointerException();
		map.put(ident, info);
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();
		sb.append(FASTAFile.HEADER_IDENT);
		sb.append(getHeader());
		sb.append(UtilIO.NEW_LINE_STRING);
		sb.append(Utils.formatStringToMultiLinesStrings(getSequence(), lineLength));
		return sb.toString();
	}
}
