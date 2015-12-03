package net.sf.jtables.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

import net.sf.jtables.io.transformer.TransformerObjectToMultipleRowsString;
import net.sf.jtables.table.Row;

public abstract class WriterTableObjectToMultipleRows<T> extends WriterTableObjectToRowsAbstract<T> {

    public WriterTableObjectToMultipleRows(final File file) throws IOException {
        super(file);
    }

    public WriterTableObjectToMultipleRows(final OutputStream stream) throws IOException {
        super(stream);
    }

    public WriterTableObjectToMultipleRows(final Writer writer) throws IOException {
        super(writer);
    }

    protected abstract TransformerObjectToMultipleRowsString<T> getTransformer();

    @Override
    public WriterTableObjectToMultipleRows<T> writeElement(final String delimiter, final T element) throws IOException {
        if (getTransformer() == null) {
            throw new IllegalStateException("set transformer first");
        }

        final Iterator<Row<String>> it = getTransformer().transform(element).iterator();
        while (it.hasNext()) {
            final Row<String> row = it.next();

            super.write(delimiter, row);
            if (it.hasNext()) {
                super.writer.newLine();
            }

        }
        return this;
    }

}
