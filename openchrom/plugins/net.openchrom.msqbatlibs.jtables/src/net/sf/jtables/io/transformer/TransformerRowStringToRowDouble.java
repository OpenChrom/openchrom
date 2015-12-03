package net.sf.jtables.io.transformer;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.impl.RowImpl;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerRowStringToRowDouble extends
        AbstractTransformingListFactory<Row<String>, Row<Double>> {

    public Row<Double> transform(final Row<String> element) {
        final Row<Double> result = new RowImpl<Double>();
        for (final String s : element) {
            result.add(Double.parseDouble(s));
        }
        return result;
    }

}
