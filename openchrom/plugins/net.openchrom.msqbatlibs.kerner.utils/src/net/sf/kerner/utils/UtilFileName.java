/*******************************************************************************
 * Copyright (c) 2010-2015 Alexander Kerner. All rights reserved.
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
package net.sf.kerner.utils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * Utility class for file name operations.
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
 * <b>Threading:</b> Not thread safe.
 * </p>
 *
 * <p>
 * last reviewed: 0000-00-00
 * </p>
 *
 * @author <a href="mailto:alexander.kerner@silico-sciences.com">Alexander
 *         Kerner</a>
 *
 */
public class UtilFileName {

	public static File appendToFileName(File file, String string) {
		final String raw = getRawFileName(file.getName());
		final String nameNew = new StringBuilder().append(raw).append(string).append(getFileExtension(file)).toString();
		final File result = new File(file.getParent(), nameNew);
		return result;
	}

	/**
	 *
	 *
	 * @return the file name's extension, e.g. 'txt' or 'mp3' or an zero-length
	 *         string if there is no file name extension
	 *
	 * @deprecated Use {@link org.apache.commons.io.FileNameUtils#getExtension}
	 */
	@Deprecated
	public static String getFileExtension(File file) {
		final int posOfExt = file.getName().lastIndexOf(".");
		if (posOfExt < 0) {
			return "";
		}
		final String result = file.getName().substring(posOfExt, file.getName().length());
		return result;
	}

	/**
	 * <p>
	 * Given a {@code File} named "hans.txt". newName is "peter". Returning
	 * {@code String} will be "peter.txt".
	 * </p>
	 * <p>
	 * Original name is "hans.txt.tex". newName is "peter". Returning
	 * {@code String} will be "peter.tex".
	 * </p>
	 * <p>
	 * Original name is "hans.txt.tex". newName is "peter.txt". Returning
	 * {@code String} will be "peter.txt.tex".
	 * </p>
	 *
	 * @param file
	 *            {@code File}, for which new name is wanted.
	 * @param newName
	 *            the new filename discarding extension.
	 * @return the new filename including extension.
	 */
	public static String getNewFileName(File file, String newName) {
		final String fileName = file.getName();
		final int posOfExt = fileName.lastIndexOf(".");
		if (posOfExt < 0) {
			return newName;
		}
		final String ext = fileName.substring(posOfExt, fileName.length());
		return newName + ext;
	}

	/**
	 *
	 * @param fileName
	 *            file name
	 * @return the 'raw file name', i.e. the file name without the file
	 *         extension or the original file name if there is no extension
	 */
	public static String getRawFileName(String fileName) {
		final int posOfExt = fileName.lastIndexOf(".");
		if (posOfExt < 0) {
			return fileName;
		}
		return fileName.substring(0, posOfExt);
	}

	/**
	 * <p>
	 * Renames a file
	 * <p>
	 *
	 * @param file
	 *            file that is renamed
	 * @param newName
	 *            new name for the file
	 * @param keepExtension
	 *            if true, file extension will be appended to new name, if there
	 *            was any.
	 * @return true, if operation was successful, false otherwise
	 * @throws FileNotFoundException
	 */
	public static boolean renameFile(File file, String newName, boolean keepExtension) throws FileNotFoundException {
		synchronized (file) {

			if (!file.isFile()) {
				throw new FileNotFoundException("cannot access file \"" + file + "\"");
			}

			final String fileNameExtension = getFileExtension(file);

			if (keepExtension) {
				return file.renameTo(new File(file.getParentFile(), newName + fileNameExtension));
			} else {
				return file.renameTo(new File(file.getParentFile(), newName));
			}
		}
	}

	private UtilFileName() {
	}

}
