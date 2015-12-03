package net.sf.jtables.table.impl;

import java.util.List;

import net.sf.kerner.utils.UtilString;

public class ColumnString extends ColumnImpl<String> {

    public ColumnString() {
        super();

    }

    public ColumnString(final List<String> elements) {
        super(elements);

    }

    public ColumnString(final String... elements) {
        super(elements);

    }

    @Override
    public boolean isEmpty() {
        return UtilString.allEmpty(implementation);
    }

}
