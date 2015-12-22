/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
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

import org.apache.log4j.Logger;

public class PeptideSearcher {

	// private final LogOnlyOnce logo = new LogOnlyOnce(log);
	private final static Logger logger = Logger.getLogger(PeptideSearcher.class);

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
		logger.info("Initiated with given cache (capacity of " + cache.getCapacity() + ", size of " + cache.getSize() + ", hash " + cache.hashCode() + ")");
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
