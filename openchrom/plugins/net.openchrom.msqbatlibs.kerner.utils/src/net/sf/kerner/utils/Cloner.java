package net.sf.kerner.utils;

public interface Cloner<T extends Cloneable<T>> {

    T clone(T element);

}
