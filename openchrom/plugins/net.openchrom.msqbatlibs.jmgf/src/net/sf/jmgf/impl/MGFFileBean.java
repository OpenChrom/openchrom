/**********************************************************************
Copyright (c) 2012-2014 Alexander Kerner. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ***********************************************************************/

package net.sf.jmgf.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;

public class MGFFileBean implements MGFFile {

    private static final long serialVersionUID = -6619126415505379215L;

    private List<MGFElement> elements;

    public MGFFileBean() {
        this(new ArrayList<MGFElement>());
    }

    public MGFFileBean(final List<? extends MGFElement> elements) {
        this.elements = new ArrayList<MGFElement>(elements);
    }

    public List<MGFElement> getElements() {
        return elements;
    }

    public Iterator<MGFElement> iterator() {
        return elements.iterator();
    }

    public void setElements(final List<MGFElement> elements) {
        this.elements = elements;
    }
}
