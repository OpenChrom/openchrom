package net.sf.bioutils.proteomics.mascot;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.impl.RowImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMascotReportRowImpl {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private Row<String> row;
	private MascotReportRowImpl mr;

	@Before
	public void setUp() throws Exception {

		row = new RowImpl<String>("1", "ODP1_ECO57", "Pyruvate dehydrogenase E1 component OS=Escherichia coli O157:H7 GN=aceE PE=1 SV=2", "2013", "100236", "37", "35", "35", "33", "48.6", "887", "5.46", "", "", "MSERFPNDVDPIETRDWLQAIESVIREEGVERAQYLIDQLLAEARKGGVNVAAGTGISNYINTIPVEEQPEYPGNLELERRIRSAIRWNAIMTVLRASKKDLELGGHMASFQSSATIYDVCFNHFFRARNEQDGGDLVYFQGHISPGVYARAFLEGRLTQEQLDNFRQEVHGNGLSSYPHPKLMPEFWQFPTVSMGLGPIGAIYQAKFLKYLEHRGLKDTSKQTVYAFLGDGEMDEPESKGAITIATREKLDNLVFVINCNLQRLDGPVTGNGKIINELEGIFEGAGWNVIKVMWGSRWDELLRKDTSGKLIQLMNETVDGDYQTFKSKDGAYVREHFFGKYPETAALVADWTDEQIWALNRGGHDPKKIYAAFKKAQETKGKATVILAHTIKGYGMGDAAEGKNIAHQVKKMNMDGVRHIRDRFNVPVSDADIEKLPYITFPEGSEEHTYLHAQRQKLHGYLPSRQPNFTEKLELPSLQDFGALLEEQSKEISTTIAFVRALNVMLKNKSIKDRLVPIIADEARTFGMEGLFRQIGIYSPNGQQYTPQDREQVAYYKEDEKGQILQEGINELGAGCSWLAAATSYSTNNLPMIPFYIYYSMFGFQRIGDLCWAAGDQQARGFLIGGTSGRTTLNGEGLQHEDGHSHIQSLTIPNCISYDPAYAYEVAVIMHDGLERMYGEKQENVYYYITTLNENYHMPAMPEGAEEGIRKGIYKLETIEGSKGKVQLLGSGSILRHVREAAEILAKDYGVGSDVYSVTSFTELARDGQDCERWNMLHPLETPRVPYIAQVMNDAPAVASTDYMKLFAEQVRTYVPADDYRVLGTDGFGRSDSRENLRHHFEVDASYVVVAALGELAKRGEIDKKVVADAIAKFNIDADKVNPRLA", "106", "1", "1", "1", "764.3627", "763.3555", "1", "763.3653", "-0.0099", "0", "23.96", "0.0094", "R", "EHFFGK", "Y", "", "", "", "Locus:1.4VP_7mu.12.223.17");
		row.setIdentifier(FileFormatMascotReport.getHeaders());
		mr = new MascotReportRowImpl(row);
	}

	@After
	public void tearDown() throws Exception {

		row = null;
		mr = null;
	}

	@Test
	public final void testGetFractionNumber01() throws IOException {

		int result = mr.getFractionNumber();
		assertEquals(223, result);
	}

	@Test
	public final void testGetPeptideExpMr01() {

		double value = mr.getPeptideExpMr();
		assertEquals(763.3555, value, 0);
	}

	@Test
	public final void testGetPeptideExpMz01() {

		double value = mr.getPeptideExpMz();
		assertEquals(764.3627, value, 0);
	}

	@Test
	public final void testGetProteinAccessionID01() {

		String protAcc = mr.getProteinAccessionID();
		assertEquals("ODP1_ECO57", protAcc);
	}
}
