package indsys.pipes;

import indsys.Data.PackageEnd;

import java.util.LinkedList;

/**
 * Created by mod on 11/3/15.
 */
public class BufferedPipe<T> implements Pipe<T> {
    private int maxsize = 0;
    private int currsize = 0;
    private boolean isFull = false;
    LinkedList<T> buffer = new LinkedList<>();
    public BufferedPipe(int maxsize){
        this.maxsize = maxsize;
    }
    @Override
    public void put(T value) {
        if(currsize < maxsize){
            buffer.add(value);
            currsize++;
        } else {
            //buffer.add((T)new PackageEnd());
            isFull = true;
        }
    }

    @Override
    public T getNext() {
        if(buffer.size() > 0){
            return buffer.removeFirst();
        }
        return null;
    }

    @Override
    public boolean isFull() {
        if(currsize < maxsize){
            return false;
        }
        buffer.add((T)new PackageEnd());
        return true;
    }

    @Override
    public boolean isEmpty() {
        if(buffer.size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public void clean() {
        buffer = new LinkedList<>();
        isFull = false;
        currsize = 0;
    }

    @Override
    public String toString() {
        return "BufferedPipe{" +
                "maxsize=" + maxsize +
                ", currsize=" + currsize +
                ", isFull=" + isFull +
                ", buffer=" + buffer +
                '}';
    }
}
