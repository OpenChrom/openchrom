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
package net.sf.jfasta.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestHeaderDialectUniprot {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private HeaderDialectUniprot dia;

    @Before
    public void setUp() throws Exception {
        dia = new HeaderDialectUniprot();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetAccessionNumber01() {
        dia.setHeaderString(">sp|Q13085|ACACA_HUMAN Acetyl-CoA carboxylase 2 OS=Homo sapiens GN=ACACA PE=1 SV=2");
        assertEquals("Q13085", dia.getAccessionNumber());
    }

    @Test
    public final void testGetAccessionNumber02() {
        dia.setHeaderString(">sp|Q13085|ACACA_HUMAN Acetyl-CoA carboxylase 1 OS=Homo sapiens GN=ACACA PE=1 SV=2");
        assertEquals("ACACA", dia.getGeneName());
    }

    @Test
    public final void testGetAccessionNumber03() {
        dia.setHeaderString("sp|Q3UNZ8|QORL2_MOUSE Quinone oxidoreductase-like protein 2 OS=Mus musculus PE=2 SV=1");
        assertEquals("Q3UNZ8", dia.getAccessionNumber());
    }

    @Test
    public final void testGetAccessionNumber04() {
        dia.setHeaderString("sp|A6NKZ8|YI016_HUMAN Putative tubulin beta chain-like protein ENSP00000290377 OS=Homo sapiens PE=5 SV=2");
        assertEquals("A6NKZ8", dia.getAccessionNumber());
    }

    @Test
    public final void testGetAccessionNumber05() {
        dia.setHeaderString("P02768ups|ALBU_HUMAN_UPS Serum albumin (Chain 26-609) - Homo sapiens (Human)");
        assertEquals("P02768ups", dia.getAccessionNumber());
    }

    @Test
    public final void testGetAccessionNumber06() {
        dia.setHeaderString("sp|Q99L13|3HIDH_MOUSE 3-hydroxyisobutyrate dehydrogenase, mitochondrial OS=Mus musculus GN=Hibadh PE=1 SV=1");
        assertEquals("Q99L13", dia.getAccessionNumber());
    }

    @Test
    public final void testGetAccessionNumber07() {
        dia.setHeaderString("P12081ups|SYHC_HUMAN_UPS Histidyl-tRNA synthetase, cytoplasmic (Chain 1-509, C terminal His tag) - Homo sapiens (Human)");
        assertEquals("P12081ups", dia.getAccessionNumber());
    }

    @Test
    public final void testGetDBIdentifier01() {
        dia.setHeaderString("sp|Q3UNZ8|QORL2_MOUSE Quinone oxidoreductase-like protein 2 OS=Mus musculus PE=2 SV=1");
        assertEquals("sp", dia.getDBIdentifier());
    }

    @Test
    public final void testGetGeneName01() {
        dia.setHeaderString("sp|Q99L13|3HIDH_MOUSE 3-hydroxyisobutyrate dehydrogenase, mitochondrial OS=Mus musculus GN=Hibadh PE=1 SV=1");
        assertEquals("3-hydroxyisobutyrate dehydrogenase, mitochondrial", dia.getProteinName());
    }

    @Test
    public final void testGetGeneName02() {
        dia.setHeaderString(">sp|P61604|CH10_HUMAN 10 kDa heat shock protein, mitochondrial OS=Homo sapiens GN=HSPE1 PE=1 SV=2");
        assertEquals("HSPE1", dia.getGeneName());
    }

    @Test
    public final void testGetProteinName01() {
        dia.setHeaderString("sp|Q3UNZ8|QORL2_MOUSE Quinone oxidoreductase-like protein 2 OS=Mus musculus PE=2 SV=1");
        assertEquals("Quinone oxidoreductase-like protein 2", dia.getProteinName());
    }

    @Test
    public final void testGetProteinName02() {
        dia.setHeaderString("m|P51965ups|UB2E1_HUMAN_UPS Ubiquitin-conjugating enzyme E2 E1 (Chain 1-193, N terminal His tag)OS=Homo sapiens (Human)");
        assertEquals("Ubiquitin-conjugating enzyme E2 E1 (Chain 1-193, N terminal His tag)",
                dia.getProteinName());
    }

    @Test
    public final void testGetSpeciesName01() {
        dia.setHeaderString(">sp|Q13085|ACACA_HUMAN Acetyl-CoA carboxylase 1 OS=Homo sapiens GN=ACACA PE=1 SV=2");
        assertEquals("Homo sapiens", dia.getSpeciesName());
    }

    @Test
    public final void testGetSpeciesName02() {
        dia.setHeaderString(">sp|P10599up4|THIO_HUMAN_UPS Thioredoxin Chain 2-105, N-terminal His tag OS=Homo sapiens GN=NotDefined");
        assertEquals("Homo sapiens", dia.getSpeciesName());
    }

}
