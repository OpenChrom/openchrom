package net.sf.jtables.io.reader;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import net.sf.kerner.utils.collections.filter.Filter;

public class VisitorFirstLine implements Filter<String> {

    private final Collection<String> needsToMatch;

    public VisitorFirstLine(Collection<String> needsToMatch) {
        this.needsToMatch = new HashSet<String>(needsToMatch);
    }

    public VisitorFirstLine(String needsToMatch) {
        this(Arrays.asList(needsToMatch));
    }

    public boolean filter(String line) {
        for (String s : needsToMatch) {
            if (!line.contains(s)) {
                return false;
            }
        }
        return true;
    }

}
