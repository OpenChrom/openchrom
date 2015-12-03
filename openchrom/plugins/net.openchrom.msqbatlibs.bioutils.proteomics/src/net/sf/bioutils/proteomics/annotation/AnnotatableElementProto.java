package net.sf.bioutils.proteomics.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Prototype implementation for {@link AnnotatableElement}.
 *
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class AnnotatableElementProto implements AnnotatableElement {

    private Collection<AnnotationSerializable> annotation = new ArrayList<AnnotationSerializable>(0);

    @Override
    public Collection<AnnotationSerializable> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(final AnnotationSerializable annotation) {
        this.setAnnotation(Arrays.asList(annotation));
    }

    @Override
    public void setAnnotation(final Collection<AnnotationSerializable> annotation) {
        this.annotation = annotation;
    }
}
