package indsys.Data;

import java.util.LinkedList;

/**
 * Created by mod on 10/30/15.
 */
public class PackageAlphabetic {
    private int firstElement;
    private int index;
    private String value;

    public PackageAlphabetic(int index, String value){
        this.index = index;
        this.value = value;
        if(value.length() > 0) {
            String toUp = String.valueOf(value.charAt(0)).toUpperCase();
            firstElement = ((int) toUp.charAt(0)) - 65;
            if (firstElement < 0 || firstElement > 30) {
                firstElement = 0;
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
    public int getFirstElement(){
        return firstElement;
    }
    @Override
    public String toString() {
        return "PackagePAir{" +
                "index=" + index +
                ", value=" + value +
                '}';
    }
}
