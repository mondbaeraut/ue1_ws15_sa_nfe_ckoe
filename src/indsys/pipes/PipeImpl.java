package indsys.pipes;

import indsys.Data.PackageEnd;
import indsys.Data.PipeFull;
import indsys.interfaces.IOable;

import java.util.LinkedList;

/**
 * Created by mod on 10/30/15.
 */
public class PipeImpl<T> implements Pipe<T>{
    LinkedList<T> container = new LinkedList<>();
    private int maxsize = 0;
    private int currsize = 0;

    public PipeImpl(){}
    public PipeImpl(int size) {
        maxsize = size;
    }

    @Override
    public void put(T value) {
            container.add(value);
    }

    @Override
    public T getNext() {
        if(container.size() >0) {
            T object = container.removeFirst();
            return object;
        }
        return null;
    }

    @Override
    public boolean isFull() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * clear linkedlist
     *
     */
    @Override
    public void clean() {

    }

    @Override
    public String toString() {
        return "PipeImpl{" +
                "container=" + container +
                '}';
    }
}
