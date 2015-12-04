package net.sf.bioutils.proteomics.annotation;

import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * A {@link Peak} which is also an {@link AnnotatableElement}.
 *
 *
 * <p>
 * last reviewed: 2014-12-02
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface PeakAnnotatable extends Peak, AnnotatableElement {

	@Override
	PeakAnnotatable clone();
}
