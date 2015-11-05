package indsys.Data;


import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by mod on 11/2/15.
 */
public class PackageOrderEnrichment implements Package {
    private HashMap<Integer,LinkedList<String>> value;


    public PackageOrderEnrichment(HashMap<Integer,LinkedList<String>> value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public int getIndex() {
        return 0;
    }


}
