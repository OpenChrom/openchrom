package net.sf.jtables.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import net.sf.jtables.table.Row;

public abstract class ReaderTableObjectFromMultipleRows<T> extends ReaderTableObjectAbstract<T> {

    public ReaderTableObjectFromMultipleRows(BufferedReader reader, boolean columnIds, boolean rowIds, String delim)
            throws IOException {
        super(reader, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromMultipleRows(BufferedReader reader, boolean columnIds, boolean rowIds)
            throws IOException {
        super(reader, columnIds, rowIds);

    }

    public ReaderTableObjectFromMultipleRows(File file, boolean columnIds, boolean rowIds, String delim)
            throws IOException {
        super(file, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromMultipleRows(File file, boolean columnIds, boolean rowIds) throws IOException {
        super(file, columnIds, rowIds);

    }

    public ReaderTableObjectFromMultipleRows(File file) throws IOException {
        super(file);

    }

    public ReaderTableObjectFromMultipleRows(InputStream stream, boolean columnIds, boolean rowIds, String delim)
            throws IOException {
        super(stream, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromMultipleRows(InputStream stream, boolean columnIds, boolean rowIds) throws IOException {
        super(stream, columnIds, rowIds);

    }

    public ReaderTableObjectFromMultipleRows(Reader reader, boolean columnIds, boolean rowIds, String delim)
            throws IOException {
        super(reader, columnIds, rowIds, delim);

    }

    public ReaderTableObjectFromMultipleRows(Reader reader, boolean columnIds, boolean rowIds) throws IOException {
        super(reader, columnIds, rowIds);

    }

    protected abstract T buildNewElement(List<Row<String>> rows);

}
