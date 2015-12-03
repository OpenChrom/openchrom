package net.sf.jtables.table.impl;

import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.kerner.utils.UtilString;

public class RowString extends RowImpl<String> {

    public RowString() {
        super();

    }

    public RowString(final List<String> elements) {
        super(elements);

    }

    public RowString(final Row<String> template) {
        super(template);

    }

    public RowString(final String... elements) {
        super(elements);

    }

    @Override
    public boolean isEmpty() {
        return UtilString.allEmpty(implementation);
    }

}
