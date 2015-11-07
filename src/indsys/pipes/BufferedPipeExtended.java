package indsys.pipes;

import indsys.filter.AbstractFilter;

/**
 * Created by mod on 11/6/15.
 */
public class BufferedPipeExtended<T> implements Pipe {
    T in;
    T out;

    public BufferedPipeExtended(T in, T out) {
        this.in = in;
        this.out = out;
    }
    public BufferedPipeExtended(T in){
        this.in = in;
    }

    @Override
    public void put(Object value) {
        ((AbstractFilter)out).write(value);
    }

    @Override
    public Object getNext() {
        return ((AbstractFilter)in).read();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clean() {

    }
}
