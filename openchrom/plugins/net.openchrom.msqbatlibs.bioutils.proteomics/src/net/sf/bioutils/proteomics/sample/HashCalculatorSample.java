package net.sf.bioutils.proteomics.sample;

import net.sf.kerner.utils.hash.HashCalculator;
import net.sf.kerner.utils.hash.UtilHash;

public class HashCalculatorSample implements HashCalculator<Sample> {

    @Override
    public int calculateHash(final Sample element) {
        return UtilHash.getHash(element.getName(), element.getNameBase(), element.getUser(),
                element.getProperties(), element.getPeaks());
    }

    @Override
    public Integer transform(final Sample element) {
        return calculateHash(element);
    }

}
