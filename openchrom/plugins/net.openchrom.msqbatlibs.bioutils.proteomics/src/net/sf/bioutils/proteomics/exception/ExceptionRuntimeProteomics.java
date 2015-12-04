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
package net.sf.bioutils.proteomics.exception;

import java.util.Properties;

import net.sf.kerner.utils.exception.ExceptionRuntimeProperty;

public class ExceptionRuntimeProteomics extends ExceptionRuntimeProperty {

	private static final long serialVersionUID = -7186253440348928800L;

	public ExceptionRuntimeProteomics() {

		super();
	}

	public ExceptionRuntimeProteomics(final Properties pro) {

		super(pro);
	}

	public ExceptionRuntimeProteomics(final String arg0) {

		super(arg0);
	}

	public ExceptionRuntimeProteomics(final String arg0, final Properties pro) {

		super(arg0, pro);
	}

	public ExceptionRuntimeProteomics(final String arg0, final Throwable arg1) {

		super(arg0, arg1);
	}

	public ExceptionRuntimeProteomics(final String arg0, final Throwable arg1, final Properties pro) {

		super(arg0, arg1, pro);
	}

	public ExceptionRuntimeProteomics(final Throwable arg0) {

		super(arg0);
	}

	public ExceptionRuntimeProteomics(final Throwable arg0, final Properties pro) {

		super(arg0, pro);
	}
}
