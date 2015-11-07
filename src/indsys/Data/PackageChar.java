package indsys.Data;

/**
 * Created by mod on 11/6/15.
 */
public class PackageChar implements Package{
    private int index;
    private  char value;

    public PackageChar(int index, char value) {
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
        return "["+ value + " " + index+"]";
    }
}
