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
/**
 * 
 */
package net.sf.kerner.utils.counter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-21
 */
public class TestCounter {

    private Counter c1;
    private Counter c2;
    private volatile int performed = 0;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        c1 = new Counter();
        c2 = new Counter();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#hashCode()}.
     */
    @Test
    public final void testHashCode01() {
        c1.count();
        assertNotSame(c1.hashCode(), c2.hashCode());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#hashCode()}.
     */
    @Test
    public final void testHashCode02() {
        c1.count();
        c2.count();
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#Counter()}.
     */
    @Test
    public final void testCounter() {
        c1 = new Counter();
        c1.count();
        assertEquals(1, c1.getCount());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#Counter()}.
     */
    @Test
    public final void testCounter01() {
        c1 = new Counter(1);
        c1.count();
        assertEquals(2, c1.getCount());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#Counter()}.
     */
    @Test
    public final void testCounter02() {
        c1 = new Counter(-1);
        c1.count();
        assertEquals(0, c1.getCount());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#Counter(java.lang.Runnable)}.
     */
    @Test
    public final void testCounterRunnable() {
        c1 = new Counter(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#Counter(int)}.
     */
    @Test
    public final void testCounterInt() {
        c1 = new Counter(1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#Counter(int, java.lang.Runnable)}
     * .
     */
    @Test
    public final void testCounterIntRunnable() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#count()}.
     */
    @Test
    public final void testCount() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
            }
        });
        c1.count();
        assertEquals(2, c1.getCount());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#finish()}.
     */
    @Test
    public final void testFinish() {
        c1 = new Counter(0, new Runnable() {
            public void run() {
                // System.err.println("performed");
                performed++;
            }
        });
        c1.setInterval(11);
        for (int i = 0; i < 10; i++) {
            c1.count();
        }
        assertEquals(0, performed);
        c1.count();
        assertEquals(1, performed);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#finish()}.
     */
    @Test
    public final void testFinish01() {
        c1 = new Counter(new Runnable() {
            public void run() {
                performed++;
            }
        });
        assertEquals(0, performed);
        c1.finish();
        assertEquals(1, performed);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#finish()}.
     */
    @Test
    public final void testFinish02() {
        c1 = new Counter(new Runnable() {
            public void run() {
                performed++;
            }
        });
        assertEquals(0, performed);
        c1.count();
        c1.setInterval(10);
        c1.finish();
        assertEquals(1, performed);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#finish()}.
     */
    @Test
    public final void testFinish03() {
        c1 = new Counter(new Runnable() {
            public void run() {
                performed++;
            }
        });
        assertEquals(0, performed);
        c1.count();
        assertEquals(1, performed);
        c1.finish();
        assertEquals(1, performed);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#finish()}.
     */
    @Test
    public final void testFinish04() {
        c1 = new Counter(new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.setInterval(2);
        c1.count();
        c1.finish();
        assertEquals(1, performed);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.setInterval(11);
        assertEquals(11, c1.getInterval());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval01() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.setInterval(2);
        c1.count();
        assertEquals(0, performed);
        c1.count();
        assertEquals(1, performed);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test(expected = NumberFormatException.class)
    public final void testSetInterval02() {
        c1.setInterval(0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval03() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.count();
        assertEquals(1, performed);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval04() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.setInterval(3);
        c1.count();
        c1.count();
        c1.count();
        assertEquals(1, performed);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval05() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.setInterval(3);
        c1.count();
        c1.count();
        assertEquals(0, performed);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval06() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.count(); // 1
        c1.count(); // 2
        c1.setInterval(3);
        c1.count(); // 1
        assertEquals(2, performed);
        c1.count(); // 2
        c1.count(); // 3
        assertEquals(3, performed);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#setInterval(int)}.
     */
    @Test
    public final void testSetInterval07() {
        c1 = new Counter(1, new Runnable() {
            public void run() {
                performed++;
            }
        });
        c1.count();
        c1.count();
        c1.setInterval(3);
        c1.count();
        c1.finish();
        assertEquals(3, performed);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#getInterval()}
     * .
     */
    @Test
    public final void testGetInterval() {
        assertEquals(1, c1.getInterval());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#getCount()}.
     */
    @Test
    public final void testGetCount() {
        assertEquals(0, c1.getCount());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#getInitCount()}.
     */
    @Test
    public final void testGetInitCount() {
        assertEquals(0, c1.getInitCount());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#getInitCount()}.
     */
    @Test
    public final void testGetInitCount01() {
        c1 = new Counter(1);
        assertEquals(1, c1.getInitCount());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#setCount(int)}
     * .
     */
    @Test
    public final void testSetCount() {
        c1.setCount(2);
        assertEquals(2, c1.getCount());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#toString()}.
     */
    @Test
    public final void testToString() {
        assertEquals(Integer.toString(c1.getCount()), c1.toString());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject() {
        assertEquals(c1, c2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject01() {
        c1.count();
        c2.count();
        assertEquals(c1, c2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject02() {
        c1 = new Counter(1);
        c2.count();
        assertEquals(c1, c2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject03() {
        c1 = new Counter(1);
        assertEquals(c1, c1);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.counter.Counter#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject04() {
        c1 = new Counter(1);
        assertNotSame(c1, null);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#perform()}.
     */
    @Test
    public final void testPerform() {
        c1 = new Counter(0, new Runnable() {
            public void run() {
                // System.err.println("performed");
                performed++;
            }
        });
        c1.perform();
        assertEquals(1, performed);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.counter.Counter#perform()}.
     */
    @Test
    public final void testPerform01() {
        c1 = new Counter(0, new Runnable() {
            public void run() {
                // System.err.println("performed");
                performed++;
            }
        });
        c1.count();
        assertEquals(1, performed);
    }
}
