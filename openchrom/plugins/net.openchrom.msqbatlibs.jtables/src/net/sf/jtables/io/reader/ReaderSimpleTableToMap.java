package net.sf.jtables.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import net.sf.kerner.utils.collections.map.MapList;
import net.sf.kerner.utils.exception.ExceptionFileFormat;

public class ReaderSimpleTableToMap {

	public MapList<String, String> read(BufferedReader reader) throws IOException {
		final MapList<String, String> result = new MapList<String, String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			final String[] ss = line.split("\\s");
			if (ss.length < 2) {
				throw new ExceptionFileFormat("Not enough columns (" + ss.length + ")");
			}
			final String key = ss[0];
			for (int i = 1; i < ss.length; i++) {
				result.put(key, ss[i]);
			}
		}
		return result;
	}

	public MapList<String, String> read(File file) throws IOException {
		final FileReader reader = new FileReader(file);
		try {
			return read(reader);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public MapList<String, String> read(Reader reader) throws IOException {
		return read(new BufferedReader(reader));
	}

}
