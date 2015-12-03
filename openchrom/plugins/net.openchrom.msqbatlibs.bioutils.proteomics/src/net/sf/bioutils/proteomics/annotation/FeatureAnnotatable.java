package net.sf.bioutils.proteomics.annotation;

import net.sf.bioutils.proteomics.feature.Feature;

/**
 *
 * A {@link Feature} which is also an {@link AnnotatableElement}.
 *
 * <p>
 * <b>Example:</b><br>
 * </p>
 *
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 *
 *
 * <p>
 * last reviewed: 2014-05-16
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface FeatureAnnotatable extends Feature, PeakAnnotatable {

    @Override
    FeatureAnnotatable clone();

}
