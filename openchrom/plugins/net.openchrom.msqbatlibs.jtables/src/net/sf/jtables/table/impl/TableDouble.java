package net.sf.jtables.table.impl;

import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.Table;

public class TableDouble extends AnnotatedMutableTableImpl<Double> {

    public TableDouble() {
        super();
    }

    public TableDouble(final List<Row<Double>> rows) {
        super(rows);
    }

    public TableDouble(final Table<Double> template) {
        super(template);

    }
}
