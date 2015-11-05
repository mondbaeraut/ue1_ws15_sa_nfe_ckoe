package indsys.Data;


/**
 * Created by mod on 11/2/15.
 */
public class PackageOrderEnrichment implements Package {
    private int index;
    private  String value;

    public PackageOrderEnrichment(String value) {
        this.value = value;
        String temp = value.toUpperCase();
        index = temp.charAt(0)-65;
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
