package indsys.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by mod on 11/6/15.
 */
public class SourceFileChar<T> {

    private static LinkedList<PackageChar> charlist = new LinkedList<>();
    public SourceFileChar(File file){
        readFromFile(file);
    }

    private void readFromFile(File file){
        BufferedReader br = null;
        try {

            int r;

            br = new BufferedReader(new FileReader(file));
            int index = 0;
            while ((r = br.read()) != -1) {
                char ch = (char) r;
                charlist.add(new PackageChar(index,ch));
                index ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T getNextChar(){
        if(charlist.size() > 0) {
            return (T) charlist.removeFirst();
        }else{
            return (T)new PackageEndFile();
        }
    }

}
