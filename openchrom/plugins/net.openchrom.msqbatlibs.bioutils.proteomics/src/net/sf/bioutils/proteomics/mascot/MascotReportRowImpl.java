package net.sf.bioutils.proteomics.mascot;

import java.io.IOException;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.impl.RowImpl;

public class MascotReportRowImpl implements MascotReportRow {

    private final Row<String> row;

    private final ParserFractionNumber parser = new ParserFractionNumber();

    public MascotReportRowImpl(Row<String> element) {
        this.row = element;
    }

    @Override
    public int getFractionNumber() throws IOException {
        return parser.parseFractionNumber(row.get(FileFormatMascotReport.PEP_SCAN_TITLE));
    }

    @Override
    public double getPeptideExpMr() {
        double result = Double.parseDouble(row.get(FileFormatMascotReport.PEP_EXP_MR));
        return result;
    }

    @Override
    public double getPeptideExpMz() {
        double result = Double.parseDouble(row.get(FileFormatMascotReport.PEP_EXP_MZ));
        return result;
    }

    @Override
    public String getProteinAccessionID() {
        String result = row.get(FileFormatMascotReport.PROT_ACC);
        return result;
    }

    @Override
    public Row<String> toRow() {
        return new RowImpl<String>(row);
    }

    @Override
    public String toString() {
        return row.toString();
    }
}
