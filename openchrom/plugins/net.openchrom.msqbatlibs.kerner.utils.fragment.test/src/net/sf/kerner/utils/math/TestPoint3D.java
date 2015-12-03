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
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import net.sf.kerner.utils.math.point.Point3D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPoint3D {

    private Point3D p1;

    private Point3D p2;

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
    public final void testHashCode01() {
        p1 = new Point3D(1, 1, 1);
        p2 = new Point3D(1, 1, 1);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public final void testEqualsObject01() {
        p1 = new Point3D(1, 1, 1);
        p2 = new Point3D(1, 1, 1);
        assertEquals(p1, p2);
    }

    @Test
    public final void testHashCode02() {
        p1 = new Point3D(1, 1, 1);
        p2 = new Point3D(1, 1, 2);
        assertFalse(p1.hashCode() == p2.hashCode());
    }

    @Test
    public final void testEqualsObject02() {
        p1 = new Point3D(1, 1, 1);
        p2 = new Point3D(1, 1, 2);
        assertFalse(p1.equals(p2));
    }

}
