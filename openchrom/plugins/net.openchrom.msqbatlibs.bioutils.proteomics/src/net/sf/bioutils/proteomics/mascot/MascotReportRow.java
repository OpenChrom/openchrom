package net.sf.bioutils.proteomics.mascot;

import java.io.IOException;

import net.sf.jtables.table.Row;

public interface MascotReportRow {

	int getFractionNumber() throws IOException;

	double getPeptideExpMr() throws IOException;

	double getPeptideExpMz() throws IOException;

	String getProteinAccessionID() throws IOException;

	Row<String> toRow();
}
