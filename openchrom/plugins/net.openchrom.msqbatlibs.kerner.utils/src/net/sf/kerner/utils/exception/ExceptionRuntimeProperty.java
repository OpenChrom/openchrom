/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.exception;

import java.util.Properties;

import net.sf.kerner.utils.UtilString;

/**
 * A {@link RuntimeException} with an additional {@link Properties} field to
 * store some additional properties that will be printed together with stack
 * trace if {@code toString()} method is called.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-04-25
 */
public class ExceptionRuntimeProperty extends RuntimeException {

    private static final long serialVersionUID = 71806291218857338L;

    private final Properties pro;

    public ExceptionRuntimeProperty() {
        this.pro = new Properties();
    }

    public ExceptionRuntimeProperty(String arg0) {
        this(arg0, new Properties());

    }

    public ExceptionRuntimeProperty(Properties pro) {
        super();
        this.pro = pro;
    }

    public ExceptionRuntimeProperty(String arg0, Properties pro) {
        super(arg0);
        this.pro = pro;
    }

    public ExceptionRuntimeProperty(Throwable arg0) {
        this(arg0, new Properties());
    }

    public ExceptionRuntimeProperty(Throwable arg0, Properties pro) {
        super(arg0);
        this.pro = pro;
    }

    public ExceptionRuntimeProperty(String arg0, Throwable arg1) {
        this(arg0, arg1, new Properties());
    }

    public ExceptionRuntimeProperty(String arg0, Throwable arg1, Properties pro) {
        super(arg0, arg1);
        this.pro = pro;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (!pro.isEmpty()) {
            sb.append(pro.toString());
            sb.append(UtilString.NEW_LINE_STRING);
        }
        sb.append(super.toString());
        return sb.toString();
    }

    public Properties getPro() {
        return pro;
    }

}
