package net.sf.jtables.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.jtables.io.transformer.TransformerRowToObjectString;
import net.sf.jtables.table.Row;

public abstract class ReaderTableObjectFromRow<T> extends ReaderTableObjectAbstract<T> {

    public ReaderTableObjectFromRow(BufferedReader reader, boolean columnIds, boolean rowIds, String delim)
            throws IOException {
        super(reader, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromRow(BufferedReader reader, boolean columnIds, boolean rowIds) throws IOException {
        super(reader, columnIds, rowIds);

    }

    public ReaderTableObjectFromRow(File file, boolean columnIds, boolean rowIds, String delim) throws IOException {
        super(file, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromRow(File file, boolean columnIds, boolean rowIds) throws IOException {
        super(file, columnIds, rowIds);

    }

    public ReaderTableObjectFromRow(File file) throws IOException {
        super(file);

    }

    public ReaderTableObjectFromRow(InputStream stream, boolean columnIds, boolean rowIds, String delim)
            throws IOException {
        super(stream, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromRow(InputStream stream, boolean columnIds, boolean rowIds) throws IOException {
        super(stream, columnIds, rowIds);

    }

    public ReaderTableObjectFromRow(Reader reader, boolean columnIds, boolean rowIds, String delim) throws IOException {
        super(reader, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromRow(Reader reader, boolean columnIds, boolean rowIds) throws IOException {
        super(reader, columnIds, rowIds);

    }

    public synchronized boolean hasNext() throws IOException {
        return reader.hasNext();
    }

    public synchronized T next() throws IOException {
        if (getTransformer() == null) {
            throw new IllegalStateException("set transformer first");
        }
        Row<String> next = reader.next();
        if (next == null) {
            return null;
        }
        return getTransformer().transform(next);
    }

    public abstract TransformerRowToObjectString<T> getTransformer();
}
