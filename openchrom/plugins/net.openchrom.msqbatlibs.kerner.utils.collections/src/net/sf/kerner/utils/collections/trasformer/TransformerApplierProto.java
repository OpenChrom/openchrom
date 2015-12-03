/*
 * Copyright 2015 alex.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.kerner.utils.collections.trasformer;

import java.util.Collection;
import net.sf.kerner.utils.collections.TransformerAbstract;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.transformer.Transformer;

/**
 *
 * @author alex
 */
public abstract class TransformerApplierProto<T, V> extends TransformerAbstract<T, V> implements TransformerApplier<T, V> {

    protected final Collection<Transformer<T, V>> transformers = UtilCollection
            .newCollection();

    public synchronized void addTransformer(final Transformer<T, V> transformer) {
        transformers.add(transformer);
    }

    public synchronized void clearTransformers() {
        transformers.clear();
    }

}
