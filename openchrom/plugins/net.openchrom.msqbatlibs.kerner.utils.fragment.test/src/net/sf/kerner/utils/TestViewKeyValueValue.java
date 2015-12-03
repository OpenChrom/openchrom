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
package net.sf.kerner.utils;

import static org.junit.Assert.assertEquals;
import net.sf.kerner.utils.pair.KeyValue;
import net.sf.kerner.utils.transformer.ViewKeyValueValue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestViewKeyValueValue {

    private KeyValue<String, String> k;

    private ViewKeyValueValue<String> v;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        v = new ViewKeyValueValue<String>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testTransform01() {
        k = new KeyValue<String, String>("key", "value");
        assertEquals("value", v.transform(k));
    }

}
