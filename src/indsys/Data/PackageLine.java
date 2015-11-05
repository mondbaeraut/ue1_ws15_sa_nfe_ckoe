package indsys.Data;

/**
 * Created by mod on 11/1/15.
 */
public class PackageLine implements Package {
    private int index;
    private  String value;

    public PackageLine(int index, String value) {
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
