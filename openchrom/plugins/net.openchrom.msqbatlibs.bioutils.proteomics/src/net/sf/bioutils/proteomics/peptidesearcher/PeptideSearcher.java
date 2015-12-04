/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
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
package net.sf.bioutils.proteomics.peptidesearcher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
import net.sf.jfasta.FASTAFileReader;
import net.sf.jfasta.impl.FASTAElementIterator;
import net.sf.jfasta.impl.FASTAFileReaderImpl;
import net.sf.kerner.utils.Cache;
import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.list.UtilList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeptideSearcher {

	// private final LogOnlyOnce logo = new LogOnlyOnce(log);
	private final static Logger log = LoggerFactory.getLogger(PeptideSearcher.class);

	private static List<String> getList(final FASTAElementIterator it, final String seq) throws IOException {

		final List<String> result = UtilList.newList();
		while(it.hasNext()) {
			final FASTAElement nextElement = it.next();
			if(nextElement.getSequence().contains(seq)) {
				result.add(nextElement.getHeader());
			}
		}
		return result;
	}

	private static List<String> getList(final Iterator<FASTAElement> it, final String seq) throws IOException {

		final List<String> result = UtilList.newList();
		while(it.hasNext()) {
			final FASTAElement nextElement = it.next();
			if(nextElement.getSequence().contains(seq)) {
				result.add(nextElement.getHeader());
			}
		}
		return result;
	}

	private final Cache<String, List<String>> cache;
	private boolean cacheFASTAFile = false;
	private final Map<File, FASTAFile> cacheFASTAFileMap;
	private final FactoryResult factoryResult = new FactoryResult();
	public final static int DEFAULT_CACHE_SIZE = 10000;

	public PeptideSearcher() {

		cache = new Cache<String, List<String>>(DEFAULT_CACHE_SIZE);
		cacheFASTAFileMap = new HashMap<File, FASTAFile>();
	}

	public PeptideSearcher(final Cache<String, List<String>> cache) {

		this.cache = cache;
		cacheFASTAFileMap = new HashMap<File, FASTAFile>();
	}

	public PeptideSearcher(final Cache<String, List<String>> cache, final Map<File, FASTAFile> cacheFASTAFileMap) {

		this.cache = cache;
		this.cacheFASTAFileMap = cacheFASTAFileMap;
		if(log.isInfoEnabled()) {
			log.info("Initiated with given cache (capacity of " + cache.getCapacity() + ", size of " + cache.getSize() + ", hash " + cache.hashCode() + ")");
		}
	}

	public PeptideSearcher(final int cacheSize) {

		cache = new Cache<String, List<String>>(cacheSize);
		cacheFASTAFileMap = new HashMap<File, FASTAFile>();
	}

	public List<String> getFromDBFile(final File db, final String seq) throws IOException {

		final List<String> result;
		if(isCacheFASTAFile()) {
			FASTAFile file;
			synchronized(cacheFASTAFileMap) {
				file = cacheFASTAFileMap.get(db);
				if(file == null) {
					// if (log.isDebugEnabled()) {
					// log.debug("read from file");
					// }
					file = new FASTAFileReaderImpl(db).read();
					// if (log.isDebugEnabled()) {
					// log.debug("done reading");
					// }
					cacheFASTAFileMap.put(db, file);
					// if (log.isDebugEnabled()) {
					// log.debug("write to cache");
					// }
				} else {
					// if (log.isDebugEnabled()) {
					// logo.debug("got from cache");
					// }
				}
			}
			result = getList(file.iterator(), seq);
		} else {
			final FASTAFileReader fastaReader = new FASTAFileReaderImpl(db);
			final FASTAElementIterator it = fastaReader.getIterator();
			result = getList(it, seq);
			fastaReader.close();
		}
		cache.put(seq, result);
		return result;
	}

	public Result getResult(final List<String> headers, final DatabaseID type) {

		return factoryResult.create(headers, type);
	}

	public boolean isCacheFASTAFile() {

		return cacheFASTAFile;
	}

	public Result reduceToProteotipic(final String seq, final File db, final DatabaseID type) throws IOException {

		return reduceToProteotipic(seq, Arrays.asList(db), type);
	}

	public Result reduceToProteotipic(final String seq, final List<File> dbs, final DatabaseID type) throws IOException {

		if(UtilString.emptyString(seq)) {
			throw new IllegalArgumentException();
		}
		Result result = Result.buildNotSearched();
		for(final File db : dbs) {
			final List<String> headers = getFromDBFile(db, seq);
			result = getResult(headers, type);
			if(result.type.equals(Result.Type.NOT_FOUND)) {
				// continue other DBs
			} else {
				return result;
			}
		}
		return result;
	}

	public synchronized void setCacheFASTAFile(final boolean cacheFASTAFile) {

		this.cacheFASTAFile = cacheFASTAFile;
	}
}
