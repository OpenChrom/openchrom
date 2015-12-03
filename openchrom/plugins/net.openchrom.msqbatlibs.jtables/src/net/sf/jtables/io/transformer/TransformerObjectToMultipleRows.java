package net.sf.jtables.io.transformer;

import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.kerner.utils.transformer.Transformer;

public interface TransformerObjectToMultipleRows<V, T> extends Transformer<V, List<Row<T>>> {

}
