/**********************************************************************
Copyright (c) 2012-2015 Alexander Kerner. All rights reserved.
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

package net.sf.jmgf;

import java.io.IOException;

import net.sf.bioutils.proteomics.peak.FactoryPeak;
import net.sf.jmgf.impl.MGFElementIterator;
import net.sf.kerner.utils.io.buffered.IOIterable;

public interface MGFFileReader extends IOIterable<MGFElement> {

    FactoryPeak getFactoryPeak();

    MGFElementIterator getIterator() throws IOException;

    MGFFile read() throws IOException;

    void setFactoryPeak(FactoryPeak factoryPeak);

}
