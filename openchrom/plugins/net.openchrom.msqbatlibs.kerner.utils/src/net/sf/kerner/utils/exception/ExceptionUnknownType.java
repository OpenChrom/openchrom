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

import java.io.Serializable;

public class ExceptionUnknownType extends RuntimeException {

    private static final long serialVersionUID = -1661770337806543702L;

    private final Serializable unknownType;

    public ExceptionUnknownType(final Serializable unknownType) {
        super();
        this.unknownType = unknownType;
    }

    public ExceptionUnknownType(final Serializable unknownType, final String message) {
        super(message);
        this.unknownType = unknownType;
    }

    public ExceptionUnknownType(final Serializable unknownType, final String message,
            final Throwable cause) {
        super(message, cause);
        this.unknownType = unknownType;
    }

    public ExceptionUnknownType(final Serializable unknownType, final Throwable cause) {
        super(cause);
        this.unknownType = unknownType;
    }

    @Override
    public String toString() {
        return "unknown type: " + unknownType + ", " + super.toString();
    }

}
