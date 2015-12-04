package net.sf.bioutils.proteomics.mascot;

import java.io.File;
import java.io.IOException;

import net.sf.jtables.io.reader.ReaderTableString;
import net.sf.jtables.io.reader.VisitorFirstLine;
import net.sf.jtables.io.transformer.TransformerRowToObject;
import net.sf.jtables.table.Row;
import net.sf.kerner.utils.io.buffered.IOIterator;

public class ParserMascotReport implements IOIterator<MascotReportRow>, TransformerRowToObject<String, MascotReportRow> {

	private ReaderTableString reader;

	public ParserMascotReport(File file) throws IOException {

		reader = new ReaderTableString(file, true, false);
		reader.addVisitorFirstLine(new VisitorFirstLine(FileFormatMascotReport.PROT_HIT_NUM));
	}

	@Override
	public void close() {

		synchronized(reader) {
			reader.close();
		}
	}

	@Override
	public boolean hasNext() throws IOException {

		synchronized(reader) {
			return reader.hasNext();
		}
	}

	@Override
	public MascotReportRow next() throws IOException {

		synchronized(reader) {
			Row<String> nextRow = reader.next();
			return transform(nextRow);
		}
	}

	@Override
	public MascotReportRow transform(Row<String> element) {

		synchronized(reader) {
			MascotReportRowImpl result = new MascotReportRowImpl(element);
			return result;
		}
	}
}
