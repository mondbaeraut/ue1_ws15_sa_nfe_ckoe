package indsys.Data;

import java.util.LinkedList;

/**
 * Created by mod on 11/3/15.
 */
public class PackageRotate<T> implements Package<T> {
    private int index = 0;
    private LinkedList<String> value = new LinkedList<>();

    public PackageRotate(int index,LinkedList<String>value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public T getValue() {
        return (T)value;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "PackageRotate{" +
                "index=" + index +
                ", value=" + value +
                '}';
    }
}
