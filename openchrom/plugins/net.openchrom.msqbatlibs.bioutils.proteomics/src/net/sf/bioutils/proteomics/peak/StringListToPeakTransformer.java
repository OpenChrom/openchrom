/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics.peak;

import java.util.List;

import net.sf.kerner.utils.transformer.Transformer;

/**
 * {@link Transformer} to parse a single {@link Peak} from a {@link List} of
 * Strings.
 * 
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * last reviewed: 2013-07-08
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-07-08
 * 
 * 
 * @see Transformer
 * @see Peak
 * @param <P>
 *            type of {@link Peak}
 */
public interface StringListToPeakTransformer<P extends Peak> extends Transformer<List<? extends String>, P> {

	/**
	 * @return newly parsed {@code Peak} or null, if parsing failed
	 */
	@Override
	public P transform(List<? extends String> element);
}
