package net.sf.jtables.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.sf.jtables.io.transformer.TransformerObjectToRowString;

public abstract class WriterTableObjectToRow<T> extends WriterTableObjectToRowsAbstract<T> {

    public WriterTableObjectToRow(final File file) throws IOException {
        super(file);
    }

    public WriterTableObjectToRow(final OutputStream stream) throws IOException {
        super(stream);
    }

    public WriterTableObjectToRow(final Writer writer) throws IOException {
        super(writer);
    }

    protected abstract TransformerObjectToRowString<T> getTransformer();

    @Override
    public WriterTableObjectToRow<T> writeElement(final String delimiter, final T element) throws IOException {
        if (getTransformer() == null) {
            throw new IllegalStateException("set transformer first");
        }
        super.write(delimiter, getTransformer().transform(element));
        return this;
    }

}
