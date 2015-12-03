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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.jmgf.MGFFileReader;
import net.sf.jmgf.exception.ExceptionMGFIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMGFFileReaderImpl {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test01() throws Exception {
        final MGFFileReader reader = new MGFFileReaderImpl(new File(
                "src/test/resources/singleElement.mgf"));
        final MGFFile file = reader.read();
        reader.close();
        assertNotNull(file);
        assertEquals(1, file.getElements().size());
        final MGFElement element = file.getElements().iterator().next();
        assertEquals("1+", element.getCharge());
    }

    @Test
    public void test02() throws Exception {
        final MGFFileReader reader = new MGFFileReaderImpl(new File(
                "src/test/resources/manyElements.mgf"));
        final MGFFile file = reader.read();
        reader.close();
        assertNotNull(file);
        assertEquals(7532, file.getElements().size());
    }

    @Test(expected = ExceptionMGFIO.class)
    public void test03() throws Exception {
        final MGFFileReader reader = new MGFFileReaderImpl(new File(
                "src/test/resources/manyElementsUnexpectedEnd.mgf"));
        reader.read();

    }

    @Test(expected = ExceptionMGFIO.class)
    public void test04() throws Exception {
        final MGFFileReader reader = new MGFFileReaderImpl(new File(
                "src/test/resources/manyElementsInvalidPeakLine.mgf"));
        reader.read();

    }

    @Test(expected = ExceptionMGFIO.class)
    public void test05() throws Exception {
        final MGFFileReader reader = new MGFFileReaderImpl(new File(
                "src/test/resources/manyElementsInvalidMissingChargeLine.mgf"));
        reader.read();

    }

}
