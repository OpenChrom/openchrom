package net.sf.bioutils.proteomics.mascot;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestParserReport {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private ParserMascotReport p;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        p = null;
    }

    @Test
    public final void testClose() {

    }

    @Test
    public final void testHasNext() {

    }

    @Test
    public final void testNext01() throws IOException {
        p = new ParserMascotReport(new File("src/test/resources/mascot_report_4VP_OldCol10minDelay_1stRepl2.csv"));
        assertTrue(p.hasNext());
    }

    @Test
    public final void testNext02() throws IOException {
        p = new ParserMascotReport(new File("src/test/resources/mascot_report_4VP_OldCol10minDelay_1stRepl2.csv"));
        MascotReportRow next = p.next();
        assertNotNull(next);
    }

    @Test
    public final void testNext03() throws IOException {
        p = new ParserMascotReport(new File("src/test/resources/mascot_report_4VP_OldCol10minDelay_1stRepl2.csv"));
        for (int i = 0; i < 4430; i++) {
            assertTrue(p.hasNext());
            MascotReportRow next = p.next();
            assertNotNull(next);
        }

    }

    @Test(expected = FileNotFoundException.class)
    public final void testParserReport() throws IOException {
        p = new ParserMascotReport(new File("noSuchFile"));
    }

}
