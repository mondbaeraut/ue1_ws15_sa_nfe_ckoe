package indsys.filter;

import indsys.interfaces.IOable;

/**
 * Created by mod on 10/30/15.
 */
public abstract class AbstractFilter<T> implements IOable<T,T> {
    public abstract T read();
    public abstract void write(T value);
}
