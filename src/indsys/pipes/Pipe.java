package indsys.pipes;

/**
 * Created by mod on 11/1/15.
 */
public interface Pipe<T> {
    void put(T value);
    T getNext();
    boolean isFull();
    boolean isEmpty();
    void clean();

}
