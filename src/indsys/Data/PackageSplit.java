package indsys.Data;

import java.util.LinkedList;

/**
 * Created by mod on 11/2/15.
 */
public class PackageSplit implements Package{
    private int index = 0;
    private LinkedList<String> value = new LinkedList<>();

    public PackageSplit(int index,LinkedList<String>value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "PackageSplit{" +
                "index=" + index +
                ", value=" + value +
                '}';
    }
}
